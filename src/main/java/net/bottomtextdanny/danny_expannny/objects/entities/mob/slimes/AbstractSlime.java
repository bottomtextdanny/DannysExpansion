package net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes;

import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationGetter;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationManager;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.SimpleAnimation;
import net.bottomtextdanny.braincell.mod.entity.psyche.Psyche;
import net.bottomtextdanny.danny_expannny.object_tables.items.DEItems;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.ai.DESlimePsyche;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.data.SlimeHopAnimationInput;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.data.SlimeHopMovementInput;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.data.SlimeHopSoundInput;
import net.bottomtextdanny.danny_expannny.objects.items.GelItem;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.SmartyMob;
import net.bottomtextdanny.dannys_expansion.core.Util.Timer;
import net.bottomtextdanny.dannys_expansion.core.Util.mixin.IItemEntityExt;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.RandomFloatMapper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;

public abstract class AbstractSlime extends SmartyMob {
    public static final float DEFAULT_HORIZONTAL_HOP_SPEED = 0.1F;
    public static final float DEFAULT_HOP_HEIGHT = 0.7F;
    public static final int DEFAULT_ATTACK_DELAY = 40;
	public static final SimpleAnimation JUMP = new SimpleAnimation(17);
	public static final SimpleAnimation DEATH = new SimpleAnimation(10);
    public static final AnimationManager BASE_ANIMATIONS = new AnimationManager(JUMP, DEATH);
    public static final SlimeHopSoundInput DEFAULT_SOUND_INPUT = new SlimeHopSoundInput(
            SoundEvents.HONEY_BLOCK_BREAK,
            SoundEvents.HONEY_BLOCK_BREAK,
            RandomFloatMapper.of(1.0F, 1.2F)
    );
    public static final SlimeHopAnimationInput DEFAULT_HOP_ANIMATION_INPUT = new SlimeHopAnimationInput(
            JUMP,
            2,
            5
    );
    public int timeCollidedHorizontally;
    public Timer attackDelay;

    protected AbstractSlime(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        refreshDimensions();
        this.attackDelay = new Timer(DEFAULT_ATTACK_DELAY);
    }

    @Override
    public AnimationGetter getAnimations() {
        return BASE_ANIMATIONS;
    }

    @Override
    public Psyche<?> makePsyche() {
        return new DESlimePsyche(this);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        refreshDimensions();
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
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
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.InFluid()) {
            this.push(0, 0.05, 0);
        }
    }

    // TODO: 12/10/2021 data-drive this
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

    public abstract GelItem.Model getGelVariant();

    public abstract SlimeHopSoundInput hopSoundsInput();

    public abstract SlimeHopAnimationInput hopAnimationInput();

    public abstract SlimeHopMovementInput hopInput();
	
	public boolean isSurfaceSlime() {
		return false;
	}
}
