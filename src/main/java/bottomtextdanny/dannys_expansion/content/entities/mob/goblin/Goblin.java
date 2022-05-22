package bottomtextdanny.dannys_expansion.content.entities.mob.goblin;

import bottomtextdanny.dannys_expansion.content._client.rendering.entity.mob.goblin.GoblinRenderer;
import bottomtextdanny.dannys_expansion.content.entities.mob.SmartyMob;
import bottomtextdanny.dannys_expansion.tables.DESounds;
import bottomtextdanny.braincell.base.ObjectFetcher;
import bottomtextdanny.braincell.base.scheduler.IntScheduler;
import bottomtextdanny.braincell.mod._base.serialization.WorldPacketData;
import bottomtextdanny.braincell.mod._base.serialization.builtin.BuiltinSerializers;
import bottomtextdanny.braincell.mod._mod.client_sided.variant_data.SimpleVariantRenderingData;
import bottomtextdanny.braincell.mod._mod.client_sided.variant_data.VariantRenderingData;
import bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationArray;
import bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationGetter;
import bottomtextdanny.braincell.mod.entity.modules.animatable.SimpleAnimation;
import bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManager;
import bottomtextdanny.braincell.mod.entity.modules.looped_walk.LoopedWalkModule;
import bottomtextdanny.braincell.mod.entity.modules.variable.Form;
import bottomtextdanny.braincell.mod.entity.modules.variable.IndexedFormManager;
import bottomtextdanny.braincell.mod.entity.modules.variable.IndexedVariableModule;
import bottomtextdanny.braincell.mod.entity.psyche.Psyche;
import bottomtextdanny.braincell.mod.entity.serialization.EntityData;
import bottomtextdanny.braincell.mod.entity.serialization.EntityDataReference;
import bottomtextdanny.braincell.mod.entity.serialization.RawEntityDataReference;
import bottomtextdanny.braincell.mod.serialization.BCSerializers;
import bottomtextdanny.braincell.mod.world.helpers.CombatHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class Goblin extends SmartyMob {
    private static final int SYNC_WEAPONS_FLAG = 0;
    public static final float ATTACK_REACH_SQUARE = 1.1F;
    public static final float MOUNT_SPEED_REDUCTION = 0.3F;
    public static final float SOLDIER_ANGRY_SPEED_MULTIPLIER = 1.5F;
    public static final float ANGRY_SPEED_MULTIPLIER = 1.3F;
    public static final float RUN_AWAY_SPEED_MULTIPLIER = 1.6F;
    public static final float MOUNT_PEER_RANGE = 1.5F;
    public static final float AVOID_RANGE = 2.5F;
    public static final float THROW_RANGE = 6.5F;
    public static final float BARE_HANDS_PROB = 0.25F;
    public static final float SWORD_TO_SPEAR_PROB = 0.45F;
    public static final int MAX_MOUNT_CHAIN_SIZE = 5;
    public static final int MAX_STONES = 5;
    public static final Form<Goblin> GREEN = new Form<>() {
        @OnlyIn(Dist.CLIENT)
        @Override
        protected VariantRenderingData<Goblin> createRenderingHandler() {
            return new SimpleVariantRenderingData<>(
                    GoblinRenderer.TEXTURES_GREEN,
                    GoblinRenderer.MODEL
            );
        }
    };
    public static final Form<Goblin> PURPLE = new Form<>() {
        @OnlyIn(Dist.CLIENT)
        @Override
        protected VariantRenderingData<Goblin> createRenderingHandler() {
            return new SimpleVariantRenderingData<>(
                    GoblinRenderer.TEXTURES_PURPLE,
                    GoblinRenderer.MODEL
            );
        }
    };
    public static final Form<Goblin> RED = new Form<>() {
        @OnlyIn(Dist.CLIENT)
        @Override
        protected VariantRenderingData<Goblin> createRenderingHandler() {
            return new SimpleVariantRenderingData<>(
                    GoblinRenderer.TEXTURES_RED,
                    GoblinRenderer.MODEL
            );
        }
    };
    public static final Form<Goblin> BLUE = new Form<>() {
        @OnlyIn(Dist.CLIENT)
        @Override
        protected VariantRenderingData<Goblin> createRenderingHandler() {
            return new SimpleVariantRenderingData<>(
                    GoblinRenderer.TEXTURES_BLUE,
                    GoblinRenderer.MODEL
            );
        }
    };
    public static final IndexedFormManager FORMS =
            IndexedFormManager.builder()
                    .add(GREEN)
                    .add(PURPLE)
                    .add(RED)
                    .add(BLUE)
                    .create();
    public static final EntityDataReference<IntScheduler.Ranged> DEMOUNT_DELAY_REF =
            BCDataManager.attribute(Goblin.class,
                    RawEntityDataReference.of(
                            BCSerializers.RANGED_INT_SCHEDULER,
                            () -> IntScheduler.ranged(200, 240),
                            "demount_delay")
            );
    public static final EntityDataReference<IntScheduler.Ranged> THROW_DELAY_REF =
            BCDataManager.attribute(Goblin.class,
                    RawEntityDataReference.of(
                            BCSerializers.RANGED_INT_SCHEDULER,
                            () -> IntScheduler.ranged(40, 70),
                            "throw_delay")
            );
    public static final EntityDataReference<IntScheduler.Simple> STONE_READDITION_DELAY_REF =
            BCDataManager.attribute(Goblin.class,
                    RawEntityDataReference.of(
                            BCSerializers.INT_SCHEDULER,
                            () -> IntScheduler.simple(100),
                            "stone_delay")
            );
    public static final EntityDataReference<Integer> STONES_REF =
            BCDataManager.attribute(Goblin.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.INTEGER,
                            () -> 4,
                            "stones")
            );
    public static final EntityDataReference<Byte> LEFT_WEAPON_REF =
            BCDataManager.attribute(Goblin.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.BYTE,
                            () -> (byte) -1,
                            "left_weapon")
            );
    public static final EntityDataReference<Byte> RIGHT_WEAPON_REF =
            BCDataManager.attribute(Goblin.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.BYTE,
                            () -> (byte) -1,
                            "right_weapon")
            );
    public static final EntityDataReference<Boolean> WEAPONS_SETTLED_REF =
            BCDataManager.attribute(Goblin.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.BOOLEAN,
                            () -> false,
                            "updated_weapons")
            );
    public static final SimpleAnimation DOUBLE_SLASH = new SimpleAnimation(21);
    public static final SimpleAnimation SLASH_LEFT = new SimpleAnimation(16);
    public static final SimpleAnimation SLASH_RIGHT = new SimpleAnimation(16);
    public static final SimpleAnimation STAB_LEFT = new SimpleAnimation(17);
    public static final SimpleAnimation STAB_RIGHT = new SimpleAnimation(17);
    public static final SimpleAnimation THROW = new SimpleAnimation(18);
    public static final SimpleAnimation BUMP = new SimpleAnimation(17);
    public static final AnimationArray ANIMATIONS = new AnimationArray(
            DOUBLE_SLASH, SLASH_LEFT, SLASH_RIGHT, STAB_LEFT, STAB_RIGHT, THROW, BUMP);
    private final EntityData<Byte> leftWeapon;
    private final EntityData<Byte> rightWeapon;
    private final EntityData<Boolean> updatedWeapons;
    public final EntityData<Integer> stones;
    public final EntityData<IntScheduler.Ranged> demountDelay;
    public final EntityData<IntScheduler.Ranged> throwDelay;
    public final EntityData<IntScheduler.Simple> stoneDelay;
    private final IntScheduler meleeAttackDelay;
    private final IntScheduler mountDelay;
    private final IntScheduler waitForAvoid;
    private final IntScheduler waitForMount;
    private final IntScheduler waitForThrow;

    public Goblin(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        this.xpReward = 4;
        this.updatedWeapons = bcDataManager().addNonSyncedData(EntityData.of(WEAPONS_SETTLED_REF));
        this.demountDelay = bcDataManager().addNonSyncedData(EntityData.of(DEMOUNT_DELAY_REF));
        this.throwDelay = bcDataManager().addNonSyncedData(EntityData.of(THROW_DELAY_REF));
        this.stoneDelay = bcDataManager().addNonSyncedData(EntityData.of(STONE_READDITION_DELAY_REF));
        this.stones = bcDataManager().addNonSyncedData(EntityData.of(STONES_REF));
        this.leftWeapon = bcDataManager().addSyncedData(EntityData.of(LEFT_WEAPON_REF));
        this.rightWeapon = bcDataManager().addSyncedData(EntityData.of(RIGHT_WEAPON_REF));
        this.meleeAttackDelay = IntScheduler.ranged(3, 15);
        this.mountDelay = IntScheduler.ranged(30, 130);
        this.waitForAvoid = IntScheduler.simple(30);
        this.waitForMount = IntScheduler.simple(50);
        this.waitForThrow = IntScheduler.ranged(4, 6);
    }

    public static AttributeSupplier.Builder attributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ARMOR, 1.5D)
                .add(Attributes.FOLLOW_RANGE, 40.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.22D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    public static boolean spawningParameters(EntityType<? extends Goblin> entityType,
                                             ServerLevelAccessor level, MobSpawnType reason,
                                             BlockPos pos, Random random) {
        return level.getDifficulty() != Difficulty.PEACEFUL
                && Monster.isDarkEnoughToSpawn(level, pos, random) && checkMobSpawnRules(entityType, level, reason, pos, random);
    }

    @Override
    protected void commonInit() {
        super.commonInit();
        this.loopedWalkModule = new LoopedWalkModule(this);
        this.variableModule = new IndexedVariableModule(this, FORMS);
    }

    @Override
    public AnimationGetter getAnimations() {
        return ANIMATIONS;
    }

    @Override
    public Psyche<?> makePsyche() {
        return new GoblinPsyche(this);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level,
                                        DifficultyInstance difficulty,
                                        MobSpawnType spawnType,
                                        @Nullable SpawnGroupData group,
                                        @Nullable CompoundTag tag) {
        super.finalizeSpawn(level, difficulty, spawnType, group, tag);
        GoblinGroupData data;

        if (group instanceof GoblinGroupData goblinData) data = goblinData;
        else data = new GoblinGroupData(chooseVariant());

        if (data.variant() != null) variableModule().setForm(data.variant());

        return data;
    }

    @Override
    protected void onAnyPossibleSpawn() {
        if (!this.updatedWeapons.get()) {
            float bareHandsProb = BARE_HANDS_PROB * this.level.getDifficulty().getId();

            if (bareHandsProb > 0.0F) {
                chooseWeapon(bareHandsProb, true);
                chooseWeapon(bareHandsProb, false);
            }
            this.updatedWeapons.set(true);
        }
    }

    protected void chooseWeapon(float bareHandsProb, boolean leftHand) {
        if (random.nextFloat() < bareHandsProb) {
            if (random.nextFloat() < SWORD_TO_SPEAR_PROB) GoblinWeapon.SWORD.arm(this, leftHand);
            else GoblinWeapon.SPEAR.arm(this, leftHand);
        }
    }

    @Override
    public Form<?> chooseVariant() {
        return FORMS.getForm(random.nextInt(0, 4));
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getEntity() != null && source.getEntity() instanceof LivingEntity livingTarget) {
            this.waitForThrow.incrementFreely(random.nextInt(20, 50));
        }
        return super.hurt(source, amount);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientCallOutHandler(int flag, ObjectFetcher fetcher) {
        super.clientCallOutHandler(flag, fetcher);
        if (flag == SYNC_WEAPONS_FLAG) {
            byte serverLeftWeapon = fetcher.get(0, Byte.class);
            byte serverRightWeapon = fetcher.get(1, Byte.class);

            this.leftWeapon.set(serverLeftWeapon);
            this.rightWeapon.set(serverRightWeapon);
        }
    }

    private void syncWeaponsToClient() {
        sendClientMsg(SYNC_WEAPONS_FLAG,
                WorldPacketData.of(BuiltinSerializers.BYTE, this.leftWeapon.get()),
                WorldPacketData.of(BuiltinSerializers.BYTE, this.rightWeapon.get()));
    }

    @Override
    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset() + 0.115F;
    }

    @Override
    public float getLoopWalkMultiplier() {
        return 0.77F;
    }

    public void setLeftWeapon(GoblinWeapon weapon) {
        this.leftWeapon.set((byte) weapon.getIndex());
    }

    public void setRightWeapon(GoblinWeapon weapon) {
        this.rightWeapon.set((byte) weapon.getIndex());
    }

    public GoblinWeapon getLeftWeapon() {
        return GoblinWeapon.values()[(int)this.leftWeapon.get() + 1];
    }

    public GoblinWeapon getRightWeapon() {
        return GoblinWeapon.values()[(int)this.rightWeapon.get() + 1];
    }

    public boolean hasLeftWeapon() {
        return (int)this.leftWeapon.get() != GoblinWeapon.NONE.getIndex()
                && (int)this.leftWeapon.get() < GoblinWeapon.values().length;
    }

    public boolean hasRightWeapon() {
        return (int)this.rightWeapon.get() != GoblinWeapon.NONE.getIndex()
                && (int)this.rightWeapon.get() < GoblinWeapon.values().length;
    }

    public boolean hasTwoWeapons() {
        return hasLeftWeapon() && hasRightWeapon();
    }

    public boolean hasWeapon(GoblinWeapon weapon) {
        return (int)this.leftWeapon.get() == weapon.getIndex()
                || (int)this.rightWeapon.get() == weapon.getIndex();
    }

    public IntScheduler getMeleeAttackDelay() {
        return meleeAttackDelay;
    }

    public IntScheduler getMountDelay() {
        return mountDelay;
    }

    public IntScheduler getWaitForAvoid() {
        return waitForAvoid;
    }

    public IntScheduler getWaitForMount() {
        return waitForMount;
    }

    public IntScheduler getWaitForThrow() {
        return waitForThrow;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return CombatHelper.isValidAttackTarget(getTarget()) ? null : DESounds.ES_GOBLIN_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return DESounds.ES_GOBLIN_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return DESounds.ES_GOBLIN_HURT.get();
    }

    @Override
    public boolean removeWhenFarAway(double v) {
        return true;
    }

    protected record GoblinGroupData(Form<?> variant) implements SpawnGroupData {}
}
