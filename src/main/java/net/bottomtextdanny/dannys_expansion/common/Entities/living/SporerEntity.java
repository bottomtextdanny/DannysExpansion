package net.bottomtextdanny.dannys_expansion.common.Entities.living;

import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationGetter;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.SimpleAnimation;
import net.bottomtextdanny.braincell.mod.world.builtin_entities.ModuledMob;
import net.bottomtextdanny.danny_expannny.object_tables.DEEntities;
import net.bottomtextdanny.danny_expannny.object_tables.DEParticles;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.common.Entities.projectile.SporeEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.Timer;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.EntityUtil;
import net.bottomtextdanny.dannys_expansion.core.interfaces.ISummoner;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class SporerEntity extends ModuledMob implements ISummoner {
    public static final SimpleAnimation BOING = new SimpleAnimation(30);
    public Timer sporeTimer;

    public SporerEntity(EntityType<? extends SporerEntity> type, Level worldIn) {
        super(type, worldIn);
        this.sporeTimer = new Timer(150, baseBound -> baseBound + Mth.floor(this.random.nextGaussian() * 0.2 * baseBound));
    }

    public static AttributeSupplier.Builder Attributes() {
        return createMobAttributes()
                .add(Attributes.MAX_HEALTH, 60.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.9D);
    }

    protected void registerExtraGoals() {
        this.goalSelector.addGoal(1, new SporerEntity.BoingGoal());
    }

    @Override
    public AnimationGetter getAnimations() {
        return BOING;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return true;
    }
    
    @Override
    public void tick() {
        super.tick();
        this.sporeTimer.tryUp();
        if (this.mainHandler.isPlaying(BOING)) {
            if (this.mainHandler.getTick() >= 6) {
                EntityUtil.particleAt(this.level, ParticleTypes.MYCELIUM, 1, this.getX(), this.getY() - 1.0D, this.getZ(),0.0D, 0.0D, 0.0D, 0.0F);
                if (this.mainHandler.getTick() == 6) {
                    this.playSound(DESounds.ES_SPORER_SQUISH.get(), 1.0F, 1.0F + this.random.nextFloat() * 0.2F);
                }
            }
        }
    }

    @Override
    public void travel(Vec3 p_213352_1_) {
        super.travel(p_213352_1_);
        if (this.isEffectiveAi()) {
            AttributeInstance gravity = this.getAttribute(net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY.get());
            double d0 = gravity.getValue();
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, d0 * 0.9424D, 0.0D));
        }
        this.fallDistance = 0;
    }

    @Override
    public ParticleOptions getDespawnParticle() {
        return DEParticles.DEATH_SPORER.get();
    }

    class BoingGoal extends Goal {

        @Override
        public void start() {
            super.start();
            SporerEntity.this.mainHandler.play(BOING);
        }

        @Override
        public void tick() {
            super.tick();
            if (SporerEntity.this.mainHandler.getTick() >= 0 && SporerEntity.this.mainHandler.getTick() < 6) {
                float prog = (float) SporerEntity.this.mainHandler.getTick() / 6;
                setDeltaMovement(0, -0.1 * prog, 0);
            }
            if (SporerEntity.this.mainHandler.getTick() >= 6 && SporerEntity.this.mainHandler.getTick() < 10) {
                float prog = (float)(SporerEntity.this.mainHandler.getTick() - 6) / 4;
                float invProg = (float) (1.0 - prog);
                Random rand = new Random();
                setDeltaMovement(0, 0.7 * invProg, 0);

                if (SporerEntity.this.mainHandler.getTick() == 6) {
                    SporeEntity sporeEntity = new SporeEntity(DEEntities.SPORE.get(), SporerEntity.this.level);
                    sporeEntity.setOwner(SporerEntity.this);
                    sporeEntity.setPos(getX(), getY() + 0.5, getZ());
                    sporeEntity.shootFromRotation(SporerEntity.this, getXRot() - 50 + rand.nextFloat() * -30, rand.nextFloat() * 360, 0.0F,  0.5F, 1.0F);
                    SporerEntity.this.level.addFreshEntity(sporeEntity);
                }
            }
        }

        @Override
        public boolean canContinueToUse() {
            return SporerEntity.this.mainHandler.isPlaying(BOING);
        }

        @Override
        public boolean canUse() {
            return SporerEntity.this.mainHandler.isPlayingNull() && (airBelow(SporerEntity.this.level, new BlockPos(getX(), getY(), getZ())) < 5 || SporerEntity.this.verticalCollision);
        }

        protected int airBelow(BlockGetter worldIn, BlockPos pos) {
            int i = 0;
            for(; i < 5 && worldIn.getBlockState(pos.below(i)).isAir(); ++i);
            return i;
        }
    }
}
