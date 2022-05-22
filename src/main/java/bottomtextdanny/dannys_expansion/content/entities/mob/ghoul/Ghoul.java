package bottomtextdanny.dannys_expansion.content.entities.mob.ghoul;

import bottomtextdanny.dannys_expansion.content._client.rendering.entity.mob.ghoul.GhoulRenderer;
import bottomtextdanny.dannys_expansion.content.entities.mob.SmartyMob;
import bottomtextdanny.dannys_expansion.tables.DESounds;
import bottomtextdanny.braincell.base.ObjectFetcher;
import bottomtextdanny.braincell.mod._base.serialization.WorldPacketData;
import bottomtextdanny.braincell.mod._base.serialization.builtin.BuiltinSerializers;
import bottomtextdanny.braincell.mod._mod.client_sided.variant_data.SimpleVariantRenderingData;
import bottomtextdanny.braincell.mod._mod.client_sided.variant_data.VariantRenderingData;
import bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationArray;
import bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationGetter;
import bottomtextdanny.braincell.mod.entity.modules.animatable.SimpleAnimation;
import bottomtextdanny.braincell.mod.entity.modules.looped_walk.LoopedWalkModule;
import bottomtextdanny.braincell.mod.entity.modules.motion_util.MotionUtilProvider;
import bottomtextdanny.braincell.mod.entity.modules.variable.Form;
import bottomtextdanny.braincell.mod.entity.modules.variable.IndexedFormManager;
import bottomtextdanny.braincell.mod.entity.modules.variable.IndexedVariableModule;
import bottomtextdanny.braincell.mod.entity.psyche.Psyche;
import bottomtextdanny.braincell.mod.world.helpers.CombatHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class Ghoul extends SmartyMob implements Enemy, MotionUtilProvider {
    private static final int USING_LEFT_HAND_FLAG = 0;
    public static final int RAISE_HAND_FLAG = 1;
    public static final int LOWER_HAND_FLAG = 2;
    public static final float COMBAT_MOVE_SPEED = 1.3F;
    public static final float HAND_RAISE = 0.05F;
    public static final float MELEE_REACH = 0.8F;
    public static final Form<Ghoul> GHOUL = new Form<>() {
        @OnlyIn(Dist.CLIENT)
        @Override
        protected VariantRenderingData<Ghoul> createRenderingHandler() {
            return new SimpleVariantRenderingData<>(
                    GhoulRenderer.GHOUL_TEXTURES,
                    GhoulRenderer.MODEL
            );
        }
    };
    public static final Form<Ghoul> FROZEN_GHOUL = new Form<>() {
        @OnlyIn(Dist.CLIENT)
        @Override
        protected VariantRenderingData<Ghoul> createRenderingHandler() {
            return new SimpleVariantRenderingData<>(
                    GhoulRenderer.FROZEN_GHOUL_TEXTURES,
                    GhoulRenderer.MODEL
            );
        }
    };
    public static final IndexedFormManager FORMS =
            IndexedFormManager.builder()
                    .add(GHOUL)
                    .add(FROZEN_GHOUL)
                    .create();
    public static final SimpleAnimation PUNCH = new SimpleAnimation(20);
    public static final SimpleAnimation UP_PUNCH = new SimpleAnimation(20);
    public static final SimpleAnimation TRAP = new SimpleAnimation(20);
    public static final AnimationArray ANIMATIONS =
            new AnimationArray(PUNCH, UP_PUNCH, TRAP);
    private boolean usingLeftHand;
    protected float handRaiseO;
    protected float handRaise;

    public Ghoul(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        xpReward = 5;
    }

    public static AttributeSupplier.Builder Attributes() {
        return createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.ARMOR, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.26D)
                .add(Attributes.FOLLOW_RANGE, 40.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.9D)
                .add(Attributes.ATTACK_DAMAGE, 4.5D);
    }

    public static boolean spawnPlacement(EntityType<? extends Ghoul> entityType,
                                             ServerLevelAccessor level, MobSpawnType reason,
                                             BlockPos pos, Random random) {
        return level.getDifficulty() != Difficulty.PEACEFUL
                && Monster.isDarkEnoughToSpawn(level, pos, random)
                && checkMobSpawnRules(entityType, level, reason, pos, random);
    }

    @Override
    protected void commonInit() {
        super.commonInit();
        this.loopedWalkModule = new LoopedWalkModule(this);
        this.variableModule = new IndexedVariableModule(this, FORMS);
    }

    @Override
    public Psyche<?> makePsyche() {
        return new GhoulPsyche(this);
    }

    @Override
    public AnimationGetter getAnimations() {
        return ANIMATIONS;
    }

    @Override
    public Form<?> chooseVariant() {
        //Biome.BiomeCategory biomeCategory = Biome.getBiomeCategory(this.level.getBiome(blockPosition()));
        Biome biome = level.getBiome(blockPosition()).value();

        if (biome.coldEnoughToSnow(blockPosition()))
            return FROZEN_GHOUL;
        return GHOUL;
    }

    @Override
    public void tick() {
        if (level.isClientSide) {
            handRaiseO = handRaise;
        }

        super.tick();

        if (!level.isClientSide) {
            if (CombatHelper.isValidAttackTarget(getTarget()))
                sendClientMsg(RAISE_HAND_FLAG);
            else sendClientMsg(LOWER_HAND_FLAG);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientCallOutHandler(int flag, ObjectFetcher fetcher) {
        if (flag == USING_LEFT_HAND_FLAG) {
            usingLeftHand = fetcher.get(0);
        } else if (flag == RAISE_HAND_FLAG) {
            handRaise = Math.min(handRaise + HAND_RAISE, 1.0F);
        } else if (flag == LOWER_HAND_FLAG) {
            handRaise = Math.max(handRaise - HAND_RAISE, 0.0F);
        }
    }

    public void setLeftHandlingAndSync(boolean state) {
        usingLeftHand = state;
        sendClientMsg(USING_LEFT_HAND_FLAG, WorldPacketData.of(BuiltinSerializers.BOOLEAN, state));
    }

    public boolean isUsingLeftHand() {
        return usingLeftHand;
    }

    @Override
    public float getLoopWalkMultiplier() {
        return 0.4F;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return DESounds.ES_GHOUL_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return DESounds.ES_GHOUL_DEATH.get();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return DESounds.ES_GHOUL_IDLE.get();
    }

    public float getHandRaise() {
        return handRaise;
    }

    public float getHandRaiseO() {
        return handRaiseO;
    }

    @Override
    public boolean removeWhenFarAway(double v) {
        return true;
    }

    @Override
    public float inputMovementMultiplier() {
        return 1;
    }

    @Override
    public void setInputMovementMultiplier(float v) {

    }
}
