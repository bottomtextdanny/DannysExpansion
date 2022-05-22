package bottomtextdanny.dannys_expansion.content.entities.mob.slimes;

import bottomtextdanny.dannys_expansion.content.entities.mob.slimes.data.SlimeHopAnimationInput;
import bottomtextdanny.dannys_expansion.content.entities.mob.slimes.data.SlimeHopMovementInput;
import bottomtextdanny.dannys_expansion.content.entities.mob.slimes.data.SlimeHopSoundInput;
import bottomtextdanny.dannys_expansion.tables.items.DEItems;
import bottomtextdanny.dannys_expansion.content.entities.mob.slimes.ai.DESlimePsyche;
import bottomtextdanny.dannys_expansion.content.items.material.GelItem;
import bottomtextdanny.dannys_expansion.content.entities.mob.SmartyMob;
import bottomtextdanny.braincell.base.scheduler.IntScheduler;
import bottomtextdanny.braincell.base.value_mapper.FloatMappers;
import bottomtextdanny.braincell.mixin_support.ItemEntityExtensor;
import bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationArray;
import bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationGetter;
import bottomtextdanny.braincell.mod.entity.modules.animatable.SimpleAnimation;
import bottomtextdanny.braincell.mod.entity.psyche.Psyche;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;

public abstract class AbstractSlime extends SmartyMob implements Enemy {
    public static final float DEFAULT_HORIZONTAL_HOP_SPEED = 0.1F;
    public static final float DEFAULT_HOP_HEIGHT = 0.7F;
    public static final int DEFAULT_ATTACK_DELAY = 40;
	public static final SimpleAnimation JUMP = new SimpleAnimation(17);
	public static final SimpleAnimation DEATH = new SimpleAnimation(10);
    public static final AnimationArray BASE_ANIMATIONS = new AnimationArray(JUMP, DEATH);
    public static final SlimeHopSoundInput DEFAULT_SOUND_INPUT = new SlimeHopSoundInput(
            SoundEvents.HONEY_BLOCK_BREAK,
            SoundEvents.HONEY_BLOCK_BREAK,
            FloatMappers.of(1.0F, 1.2F)
    );
    public static final SlimeHopAnimationInput DEFAULT_HOP_ANIMATION_INPUT = new SlimeHopAnimationInput(
            JUMP,
            2,
            5
    );
    public int timeCollidedHorizontally;
    public IntScheduler.Simple attackDelay;

    protected AbstractSlime(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        refreshDimensions();
        this.attackDelay = IntScheduler.simple(DEFAULT_ATTACK_DELAY);
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
        if (this.horizontalCollision) {
            this.timeCollidedHorizontally++;
        }

        super.tick();

        this.fallDistance = 0;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (isInWater() || isInLava()) {
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

                ((ItemEntityExtensor)itemEntity).setShowingModel(getGelVariant());

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
