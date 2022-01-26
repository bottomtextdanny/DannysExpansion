package net.bottomtextdanny.danny_expannny.objects.entities.mob.slime;

import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.Animation;
import net.bottomtextdanny.braincell.mod.world.builtin_entities.ModuledMob;
import net.bottomtextdanny.danny_expannny.object_tables.items.DEItems;
import net.bottomtextdanny.dannys_expansion.common.Evaluations;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.Timer;
import net.bottomtextdanny.dannys_expansion.core.Util.mixin.IItemEntityExt;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;
import java.util.Random;

public abstract class AbstractSlimeEntity extends ModuledMob implements Enemy {
    public static final float DEFAULT_HORIZONTAL_HOP_SPEED = 0.1F;
    public static final float DEFAULT_HOP_HEIGHT = 0.7F;
    public static final int DEFAULT_ATTACK_DELAY = 40;
	public final Animation jump = addAnimation(new Animation(17));
	public final Animation death = addAnimation(new Animation(10));
    public int timeCollidedHorizontally;
    public Timer attackDelay;
    public Timer hopDelay;
    public float horizontalHopSpeed = DEFAULT_HORIZONTAL_HOP_SPEED;
    public float hopHeight = DEFAULT_HOP_HEIGHT;
    public boolean wasJumping;

    public AbstractSlimeEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        refreshDimensions();
        this.attackDelay = new Timer(DEFAULT_ATTACK_DELAY);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        refreshDimensions();
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    protected void registerExtraGoals() {
        this.goalSelector.addGoal(1, new AbstractSlimeEntity.RotateToTargetGoal());
        this.goalSelector.addGoal(2, new AbstractSlimeEntity.RotateRandomlyGoal());
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackablePlayerGoal(this, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return true;
    }

    @Override
    public void tick() {
        this.attackDelay.tryUp();

        if (this.horizontalCollision) {
            this.timeCollidedHorizontally++;
        }
        super.tick();
        this.fallDistance = 0;

        if (this.mainAnimationHandler.isPlaying(this.jump)) {
            if (this.mainAnimationHandler.getTick() >= 3 && this.mainAnimationHandler.getTick() < 8) {
                float d0 = DEMath.sin(getYRot() * ((float) Math.PI / 180F));
                float d1 = DEMath.cos(getYRot() * ((float) Math.PI / 180F));

                push(this.horizontalHopSpeed * -d0, 0, this.horizontalHopSpeed * d1);

                if (this.mainAnimationHandler.getTick() == 3) {

                    push(0, this.hopHeight, 0);
                    playSound(SoundEvents.HONEY_BLOCK_BREAK, 0.3F + this.random.nextFloat() * 0.4F, 1 + this.random.nextFloat() * 0.2F);
                }

                if (this.mainAnimationHandler.getTick() == 5) {
                    this.wasJumping = true;
                }
            }
        }

        if (!this.level.isClientSide) {
            this.hopDelay.tryUpOrElse(t -> {
                if (onGround() || InFluid()) {
                    this.hopDelay.reset();
                    this.mainAnimationHandler.play(this.jump);
                }
            });

            if (this.wasJumping) {
                if (onGround()) {
                    playSound(SoundEvents.HONEY_BLOCK_BREAK, 0.3F + this.random.nextFloat() * 0.4F, 1 + this.random.nextFloat() * 0.2F);
                    this.wasJumping = false;
                }
            }
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.InFluid()) {
            this.push(0, 0.05, 0);
        }
    }

    // TODO: 12/10/2021 image-drive this
    @Override
    public void onDeathAnimationEnd() {
        super.onDeathAnimationEnd();
        if (!this.level.isClientSide) {
            float f = getGelAmount();
            int i = (int) getGelAmount();
            f -= i;
            float f1 = this.random.nextFloat();
            if (f1 < f) {
                i++;
            }

            if (i != 0) {
                ItemStack gel = new ItemStack(DEItems.GEL.get());

                gel.setCount(i);

                ItemEntity itemEntity = new ItemEntity(this.level, getX(), getY(), getZ(), gel);

                ((IItemEntityExt)itemEntity).de_setShowingModel(getGelVariant());

                itemEntity.setDeltaMovement(0.0F, 0.5F, 0.0F);
                this.level.addFreshEntity(itemEntity);
            }
        }
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.HONEY_BLOCK_FALL;
    }

    public float getGelAmount() {
        return 1;
    }

    public int getGelVariant() {
        return -1;
    }
	
	public boolean isSurfaceSlime() {
		return false;
	}

    class RotateRandomlyGoal extends Goal {

        @Override
        public void start() {
            super.start();
            if (AbstractSlimeEntity.this.timeCollidedHorizontally > 40) {
                setYRot((float) (Mth.wrapDegrees(getYRot()) + 180 + new Random().nextGaussian() * 10));
                AbstractSlimeEntity.this.timeCollidedHorizontally = 0;
            } else {
                setYRot(new Random().nextFloat() * 360);
            }
        }

        @Override
        public boolean canContinueToUse() {
            return false;
        }

        @Override
        public boolean canUse() {
            return new Random().nextInt(200) == 5 || AbstractSlimeEntity.this.timeCollidedHorizontally > 40;
        }
    }

    class RotateToTargetGoal extends Goal {

        @Override
        public void start() {
            super.start();
            if (getTarget() != null) {
                getLookControl().setLookAt(getTarget(), 30.0F, 30.0F);

            }
        }

        @Override
        public void tick() {
            super.tick();
            if (getTarget() != null) {
                LivingEntity livingEntity = getTarget();
                double d0 = livingEntity.getX() - getX();
                double d1 = livingEntity.getZ() - getZ();

                if (livingEntity.getBoundingBox().intersects(getBoundingBox().inflate(0.1))) {
                    AbstractSlimeEntity.this.attackDelay.reset();
                    doHurtTarget(livingEntity);
                }

                setYRot((float) (Mth.atan2(d1, d0) * (double) (180F / (float) Math.PI)) - 90.0F);
            }
        }

        @Override
        public boolean canContinueToUse() {
            return getTarget() != null && AbstractSlimeEntity.this.attackDelay.hasEnded();
        }

        @Override
        public boolean canUse() {
            return getTarget() != null && AbstractSlimeEntity.this.attackDelay.hasEnded();
        }
    }
	
	static class NearestAttackablePlayerGoal extends NearestAttackableTargetGoal<Player> {
		
		
		public NearestAttackablePlayerGoal(Mob p_26060_, boolean p_26062_) {
			super(p_26060_, Player.class, p_26062_);
		}
		
		@Override
		protected void findTarget() {
			super.findTarget();
			if (this.target != null && ((AbstractSlimeEntity) this.mob).isSurfaceSlime() && Evaluations.COOL_WITH_SURFACE_SLIMES.test((Player) this.target)) {
                this.target = null;
			}
		}
	}
}
