package bottomtextdanny.dannys_expansion.content.entities.mob.squig;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion._base.particle.DannyParticleData;
import bottomtextdanny.dannys_expansion.content._client.rendering.entity.mob.SquigRenderer;
import bottomtextdanny.dannys_expansion.content.entities.ai.controllers.MGLookController;
import bottomtextdanny.dannys_expansion.content.entities.mob.SmartyMob;
import bottomtextdanny.dannys_expansion.content._client.particles.SquigCrossParticle;
import bottomtextdanny.dannys_expansion.content._client.particles.SquigRingParticle;
import bottomtextdanny.dannys_expansion.tables.DESounds;
import bottomtextdanny.dannys_expansion.tables._client.DEParticles;
import bottomtextdanny.braincell.base.ObjectFetcher;
import bottomtextdanny.braincell.base.scheduler.IntScheduler;
import bottomtextdanny.braincell.mod._base.serialization.WorldPacketData;
import bottomtextdanny.braincell.mod._base.serialization.builtin.BuiltinSerializers;
import bottomtextdanny.braincell.mod._mod.client_sided.variant_data.SimpleVariantRenderingData;
import bottomtextdanny.braincell.mod._mod.client_sided.variant_data.VariantRenderingData;
import bottomtextdanny.braincell.mod.entity.modules.additional_motion.ExternalMotion;
import bottomtextdanny.braincell.mod.entity.modules.additional_motion.ExtraMotionModule;
import bottomtextdanny.braincell.mod.entity.modules.additional_motion.ExtraMotionProvider;
import bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationGetter;
import bottomtextdanny.braincell.mod.entity.modules.animatable.SimpleAnimation;
import bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManager;
import bottomtextdanny.braincell.mod.entity.modules.variable.Form;
import bottomtextdanny.braincell.mod.entity.modules.variable.IndexedFormManager;
import bottomtextdanny.braincell.mod.entity.modules.variable.IndexedVariableModule;
import bottomtextdanny.braincell.mod.entity.psyche.Psyche;
import bottomtextdanny.braincell.mod.entity.serialization.EntityData;
import bottomtextdanny.braincell.mod.entity.serialization.EntityDataReference;
import bottomtextdanny.braincell.mod.entity.serialization.RawEntityDataReference;
import bottomtextdanny.braincell.mod.network.Connection;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class Squig extends SmartyMob implements ExtraMotionProvider {
    private static final int UPDATE_YAW_OFFSET_FLAG = 0;
    private static final int IMPULSE_FLAG = 1;
    public static final SquigForm BLUE = new SquigForm(
            new ResourceLocation(DannysExpansion.ID, "textures/entity/squig/freedom_squig.png"),
            SquigCrossParticle.BLUE_IDX, SquigRingParticle.BLUE_IDX);
    public static final SquigForm GREEN = new SquigForm(
            new ResourceLocation(DannysExpansion.ID, "textures/entity/squig/harmony_squig.png"),
            SquigCrossParticle.GREEN_IDX, SquigRingParticle.GREEN_IDX);
    public static final SquigForm RED = new SquigForm(
            new ResourceLocation(DannysExpansion.ID, "textures/entity/squig/courage_squig.png"),
            SquigCrossParticle.RED_IDX, SquigRingParticle.RED_IDX);
    public static final SquigForm PURPLE = new SquigForm(
            new ResourceLocation(DannysExpansion.ID, "textures/entity/squig/luxury_squig.png"),
            SquigCrossParticle.PURPLE_IDX, SquigRingParticle.PURPLE_IDX);
    public static final IndexedFormManager FORMS = IndexedFormManager
            .builder()
            .add(BLUE)
            .add(GREEN)
            .add(RED)
            .add(PURPLE)
            .create();
    public static final EntityDataReference<BlockPos> HOME_REF =
            BCDataManager.attribute(Squig.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.BLOCK_POS,
                            () -> BlockPos.ZERO,
                            "home")
            );
    public static final EntityDataReference<Boolean> UPDATED_HOME_REF =
            BCDataManager.attribute(Squig.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.BOOLEAN,
                            () -> false,
                            "updated_home")
            );
    public static final SimpleAnimation IMPULSE = new SimpleAnimation(15);
    private final EntityData<BlockPos> home;
    private final EntityData<Boolean> updatedHome;
    private ExtraMotionModule extraMotionModule;
    private ExternalMotion impulseMotion;
    private final IntScheduler.Simple changeTargetThreshold;
    private final IntScheduler.Ranged impulseDelay;
    private float xTarget;
    private float yTarget;
    private float offsetYawTarget;
    private float offsetYaw;
    private float offsetYawO;

    public Squig(EntityType<? extends Squig> type, Level worldIn) {
        super(type, worldIn);
        home = bcDataManager().addNonSyncedData(EntityData.of(HOME_REF));
        updatedHome = bcDataManager().addNonSyncedData(EntityData.of(UPDATED_HOME_REF));
        changeTargetThreshold = IntScheduler.simple(60);
        impulseDelay = IntScheduler.ranged(60, 80);
        lookControl = new MGLookController(this);
    }

    public static AttributeSupplier.Builder Attributes() {
        return createMobAttributes()
                .add(Attributes.MAX_HEALTH, 60.0D)
                .add(Attributes.ARMOR, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FOLLOW_RANGE, 40.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.9D);
    }

    public static boolean spawnPlacement(EntityType<Squig> type, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, Random randomIn) {
        if (worldIn.getDifficulty() == Difficulty.PEACEFUL) return false;
        if (pos.getY() < worldIn.getSeaLevel()) return false;
        if (pos.getY() > worldIn.getSeaLevel() + 20) return false;
        if (Biome.getBiomeCategory(worldIn.getBiome(pos)) != Biome.BiomeCategory.OCEAN) return false;
        return worldIn.getLevelData().isRaining();
    }

    @Override
    protected void commonInit() {
        super.commonInit();
        variableModule = new IndexedVariableModule(this, FORMS);
        extraMotionModule = new ExtraMotionModule(this);
        impulseMotion = addCustomMotion(new ExternalMotion(0.96F));
    }

    @Override
    public Psyche<?> makePsyche() {
        return new SquigPsyche(this);
    }

    @Override
    protected BodyRotationControl createBodyControl() {
        return new Body(this);
    }

    @Override
    public LookControl getLookControl() {
        return lookControl;
    }

    @Override
    public AnimationGetter getAnimations() {
        return IMPULSE;
    }

    @Override
    public Form<?> chooseVariant() {
        int rng = this.random.nextInt(100);

        if (rng <= 50) {
            return BLUE;
        } else if (rng <= 69) {
            return RED;
        } else if (rng <= 88) {
            return GREEN;
        } else if (rng <= 95) {
            return PURPLE;
        } else {
            return BLUE;
        }
    }

    @Override
    public ExtraMotionModule extraMotionModule() {
        return extraMotionModule;
    }

    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide) {
            checkHome();
            if (offsetYawO != offsetYaw) {
                syncYawOffsetTarget();
            }
        }

        Connection.doClientSide(() -> clientTick());
    }

    public void checkHome() {
        if (!updatedHome.get()) {
            home.set(blockPosition());
            updatedHome.set(true);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void clientTick() {
        offsetYawO = offsetYaw;
        offsetYaw = Mth.lerp(0.1F, offsetYaw, offsetYawTarget);
    }

    public void syncYawOffsetTarget() {
        offsetYawO = offsetYaw;
        sendClientMsg(UPDATE_YAW_OFFSET_FLAG, WorldPacketData.of(BuiltinSerializers.FLOAT, offsetYawTarget));
    }

    @OnlyIn(Dist.CLIENT)
    private void updateYawOffset(int serverTarget) {
        offsetYawTarget = serverTarget;
    }

    public void tellClientToDoImpulse() {
        sendClientMsg(IMPULSE_FLAG);
    }

    @OnlyIn(Dist.CLIENT)
    private void clientImpulse() {
        ClientLevel clientLevel = (ClientLevel) this.level;
        SquigForm form = getForm();

        if (form == null) return;

        Vec3 pos = position();
        Vec3 lookVec = Vec3.directionFromRotation(getXRot(), getYHeadRot()).scale(-1.5);
        Vec3 ringPos = pos.add(lookVec).add(0.0, 0.31, 0.0);

        DannyParticleData ringParticle = new DannyParticleData(DEParticles.SQUIG_RING, form.getRingSprite(), getYHeadRot() - 180.0F, -getXRot());
        DannyParticleData crossData = new DannyParticleData(DEParticles.SQUIG_CROSS, form.getCrossSprite());

        clientLevel.addParticle(ringParticle, ringPos.x, ringPos.y, ringPos.z, lookVec.x * 0.2, lookVec.y * 0.2, lookVec.z * 0.2);

        int rgn = this.random.nextInt(4) + 3;

        for (int i = 0; i < rgn; i++) {
            Vec3 move = Vec3.directionFromRotation(getXRot() + 30.0F * (float) this.random.nextGaussian(), getYRot() + 30.0F * (float) this.random.nextGaussian()).scale(-1.0);
            Vec3 mot = move.scale(0.25 + 0.15 * this.random.nextGaussian());

            clientLevel.addParticle(crossData, pos.x + move.x, pos.y + 0.31 + move.y, pos.z + move.z, mot.x, mot.y, mot.z);
        }

        this.offsetYawTarget += 340.0F;

        clientLevel.playLocalSound(getX(), getY() + getBbHeight() / 2.0, getZ(), DESounds.ES_SQUIG_JUMP.get(), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientCallOutHandler(int flag, ObjectFetcher fetcher) {
        if (flag == UPDATE_YAW_OFFSET_FLAG) {
            updateYawOffset(fetcher.get(0));
        } else if (flag == IMPULSE_FLAG) {
            clientImpulse();
        }
    }

    public void setTargetLook(float x, float y) {
        this.xTarget = x;
        this.yTarget = y;
    }

    private SquigForm getForm() {
        return variableModule.getForm() instanceof SquigForm squigForm ? squigForm : null;
    }

    public BlockPos getHome() {
        return home.get();
    }

    public IntScheduler getChangeTargetThreshold() {
        return changeTargetThreshold;
    }

    public IntScheduler getImpulseDelay() {
        return impulseDelay;
    }

    public float getXTarget() {
        return xTarget;
    }

    public float getYTarget() {
        return yTarget;
    }

    public float getOffsetYaw() {
        return offsetYaw;
    }

    public float getOffsetYawO() {
        return offsetYawO;
    }

    public ExternalMotion getImpulseMotion() {
        return impulseMotion;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return DESounds.ES_SQUIG_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return DESounds.ES_SQUIG_HURT.get();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return DESounds.ES_SQUIG_IDLE.get();
    }

    @Override
    public boolean removeWhenFarAway(double v) {
        return false;
    }

    public static class Body extends BodyRotationControl {
        private final Mob mob;
        private static final int HEAD_STABLE_ANGLE = 15;
        private static final int DELAY_UNTIL_STARTING_TO_FACE_FORWARD = 10;
        private static final int HOW_LONG_IT_TAKES_TO_FACE_FORWARD = 10;
        private int headStableTime;
        private float lastStableYHeadRot;

        public Body(Mob mob) {
            super(mob);
            this.mob = mob;
        }

        public void clientTick() {
            if (this.isMoving()) {
                this.mob.yBodyRot = this.mob.getYRot();
                this.rotateHeadIfNecessary();
                this.lastStableYHeadRot = this.mob.yHeadRot;
                this.headStableTime = 0;
            } else {
                if (this.notCarryingMobPassengers()) {
                    if (Math.abs(this.mob.yHeadRot - this.lastStableYHeadRot) > 15.0F) {
                        this.headStableTime = 0;
                        this.lastStableYHeadRot = this.mob.yHeadRot;
                        this.rotateBodyIfNecessary();
                    } else {
                        ++this.headStableTime;
                    }
                }

            }
        }

        private void rotateBodyIfNecessary() {
            this.mob.yBodyRot = Mth.rotateIfNecessary(this.mob.yBodyRot, this.mob.yHeadRot, (float)this.mob.getMaxHeadYRot());
        }

        private void rotateHeadIfNecessary() {
            this.mob.yHeadRot = Mth.rotateIfNecessary(this.mob.yHeadRot, this.mob.yBodyRot, (float)this.mob.getMaxHeadYRot());
        }

        private void rotateHeadTowardsFront() {
            int i = this.headStableTime - 10;
            float f = Mth.clamp((float)i / 10.0F, 0.0F, 1.0F);
            float f1 = (float)this.mob.getMaxHeadYRot() * (1.0F - f);
            this.mob.yBodyRot = Mth.rotateIfNecessary(this.mob.yBodyRot, this.mob.yHeadRot, f1);
        }

        private boolean notCarryingMobPassengers() {
            return !(this.mob.getFirstPassenger() instanceof Mob);
        }

        private boolean isMoving() {
            double d0 = this.mob.getX() - this.mob.xo;
            double d1 = this.mob.getZ() - this.mob.zo;
            return d0 * d0 + d1 * d1 > (double)2.5000003E-7F;
        }
    }

    public static class SquigForm extends Form<Squig> {
        private final ResourceLocation texture;
        private final int crossSprite;
        private final int ringSprite;

        public SquigForm(ResourceLocation texture, int crossSprite, int ringSprite) {
            super();
            this.texture = Connection.makeClientSideOrElse(() -> texture, () -> texture);
            this.crossSprite = crossSprite;
            this.ringSprite = ringSprite;
        }

        public int getCrossSprite() {
            return crossSprite;
        }

        public int getRingSprite() {
            return ringSprite;
        }

        @OnlyIn(Dist.CLIENT)
        @Override
        protected VariantRenderingData<Squig> createRenderingHandler() {
            return new SimpleVariantRenderingData<>(texture, SquigRenderer.MODEL);
        }
    }
}
