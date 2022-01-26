package net.bottomtextdanny.danny_expannny.objects.entities.kite;

import net.bottomtextdanny.braincell.mod.serialization.WorldPacketData;
import net.bottomtextdanny.braincell.mod.serialization.serializers.builtin.BuiltinSerializers;
import net.bottomtextdanny.danny_expannny.ClientInstance;
import net.bottomtextdanny.danny_expannny.objects.sound_instances.KiteLoopSound;
import net.bottomtextdanny.danny_expannny.objects.items.KiteItem;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.ExternalMotion;
import net.bottomtextdanny.braincell.underlying.misc.ObjectFetcher;
import net.bottomtextdanny.danny_expannny.capabilities.CapabilityHelper;
import net.bottomtextdanny.danny_expannny.capabilities.world.LevelCapability;
import net.bottomtextdanny.danny_expannny.capabilities.world.LevelWindModule;
import net.bottomtextdanny.dannys_expansion.core.interfaces.entity.ClientManager;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

public class KiteEntity extends Entity implements ClientManager {
    public ItemStack itemstack;
    public String cachedDesign = "none";
    public boolean cachedIsDyableDesign;
    public int cachedKiteColor;
    public int cachedDesignColor;
    public Vec3 designColor = Vec3.ZERO;

    private boolean validExistence;
    private KiteKnotEntity knot;

    private final ExternalMotion forwardMotion;
    private final ExternalMotion retrayMotion;
    private final ExternalMotion stopMotion;
    private Vec3 absoluteMotion = Vec3.ZERO;
    @OnlyIn(Dist.CLIENT)
    KiteLoopSound fooooo;
    public float prevRenderDifference;
    public float renderDifference;
    public float kiteYaw;
    public float prevKiteYaw;
    public float kitePitch;
    public float prevKitePitch;
    public float tickOffset;
    private float avgDistance;
    private float angleOffset;

    public KiteEntity(EntityType<? extends  Entity> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
        float f0 = 5.25F + this.random.nextFloat() * 0.5F;
        float f1 = (float) (4 * this.random.nextGaussian());
        float f2 = this.random.nextFloat() * 20000;

        this.avgDistance = f0;
        this.angleOffset = f1;
        this.tickOffset = f2;

        this.forwardMotion = new ExternalMotion(0.9F);
        this.retrayMotion = new ExternalMotion(0.84F);
        this.stopMotion = new ExternalMotion(3, 0.995F);
    }

    public static KiteEntity provide(EntityType<? extends Entity> entityTypeIn, Level worldIn, ItemStack kiteItemstack) {
        KiteEntity kiteInst = new KiteEntity(entityTypeIn, worldIn);

        kiteInst.itemstack = kiteItemstack;

        if (kiteInst.itemstack != null) {

            kiteInst.updateData();


        }

        return kiteInst;
    }

    protected void defineSynchedData() {
    }

    @Override
    public boolean save(CompoundTag p_20224_) {
        return false;
    }

    @Override
    public void load(CompoundTag compound) {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {

    }

    public void updateData() {
        this.cachedKiteColor = getItem().getColor();

        if (this.itemstack.getTagElement("Design") != null) {

            CompoundTag compoundNBT = this.itemstack.getTagElement("Design");

            this.cachedDesign = compoundNBT.getString("DesignName");
            this.cachedIsDyableDesign = compoundNBT.getBoolean("DyableDesign");

            if (this.cachedIsDyableDesign) {
                this.cachedDesignColor = compoundNBT.getInt("Color");
                updateDesignColor();
            }
        }
    }


    @Override
    public void tick() {
        super.tick();

        if (!this.level.isClientSide() && !this.validExistence) {
            remove(RemovalReason.DISCARDED);
        }


        this.retrayMotion.tick();
        this.stopMotion.tick();
        this.forwardMotion.tick();

        LevelCapability worldCapability = CapabilityHelper.get(this.level, LevelCapability.CAPABILITY);
        LevelWindModule windModule = worldCapability.getWindModule();
        //        float customTicksExisted =  tickCount + tickOffset;
        float kiteAngle = Mth.wrapDegrees(windModule.getWindDirection() + this.angleOffset);

        float difference = DEMath.getDistance(getX(), getY(), getZ(), this.xo, this.yo, this.zo);

        this.prevRenderDifference = this.renderDifference;
        this.renderDifference += (difference - this.renderDifference) / 15;

        if (!this.level.isClientSide()) {
            if (this.knot != null && !this.knot.isRemoved()) {
                this.knot.kite_position.set(position());
                this.knot.kite_yaw.set(getYRot());
                this.knot.kite_pitch.set(getXRot());

                Vec3 knotForward = DEMath.fromPitchYaw(DEMath.getTargetPitch(this.knot, this),
                        DEMath.getTargetYaw(this.knot, this));
                Vec3 windForward = DEMath.fromPitchYaw(0, getYRot());

                if (this.knot.distanceTo(this) < this.avgDistance * 2) {
                    float forwardVec = -(this.knot.distanceTo(this) - this.avgDistance * 2);
                    this.forwardMotion.setMotion(
                            windForward.x * 0.1 * forwardVec,
                            DEMath.sin(this.tickOffset + this.tickCount * 0.04F) * 0.05 + 0.18,
                            windForward.z * 0.1 * forwardVec);
                }
                if (this.knot.distanceTo(this) > this.avgDistance) {
                    float retrayVec = this.knot.distanceTo(this) - this.avgDistance;
                    this.retrayMotion.setMotion(
                            -knotForward.x * 0.1 * retrayVec,
                            -knotForward.y * 0.1 * retrayVec,
                            -knotForward.z * 0.1 * retrayVec);
                }
                if (windModule.isWindStopped()) {
                    this.stopMotion.setMotion(
                            -windForward.x * 0.05  * windModule.getWindStrength(),
                            0.026 * windModule.getWindStrength(),
                            -windForward.z * 0.05 * windModule.getWindStrength());
                }
            } else {
                remove(RemovalReason.DISCARDED);
            }

            this.absoluteMotion = getDeltaMovement()
                    .add(this.forwardMotion.getAcceleratedMotion())
                    .add(this.retrayMotion.getAcceleratedMotion())
                    .add(this.stopMotion.getAcceleratedMotion());

            setYRot(kiteAngle);

            sendClientMsg(1, WorldPacketData.of(BuiltinSerializers.VEC3, this.absoluteMotion), WorldPacketData.of(BuiltinSerializers.FLOAT, getYRot()));
        }

        move(MoverType.SELF, this.absoluteMotion);
    }



    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientCallOutHandler(int flag, ObjectFetcher fetcher) {
        if (flag == 0) {
            this.avgDistance = fetcher.get(0, Float.class);
            this.angleOffset = fetcher.get(1, Float.class);
            this.tickOffset = fetcher.get(2, Float.class);
            this.itemstack = fetcher.get(3, ItemStack.class);
            Entity mabyKnot = fetcher.get(4, Entity.class);

            if (mabyKnot instanceof KiteKnotEntity) {
                this.knot = (KiteKnotEntity) mabyKnot;
            }
            updateData();
        }
        else if (flag == 1) {
            if (ClientInstance.player() != null) {
                if (this.fooooo == null || this.fooooo.isStopped() && Minecraft.getInstance().player.distanceTo(this) < 20) {
                    this.fooooo = new KiteLoopSound(this);
                    Minecraft.getInstance().getSoundManager().play(this.fooooo);
                }
            }
            this.absoluteMotion = fetcher.get(0, Vec3.class);
            setYRot(fetcher.get(1, Float.class));
        }
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();

    }

    public void knowDotDotDotLaugh(KiteKnotEntity newKnot) {
        this.knot = newKnot;
        newKnot.kiteEntity = this;
        if (!this.level.isClientSide()) {
            if (this.itemstack != null && this.knot != null) {
                sendClientMsg(0,
                        WorldPacketData.of(BuiltinSerializers.FLOAT, this.avgDistance),
                        WorldPacketData.of(BuiltinSerializers.FLOAT, this.angleOffset),
                        WorldPacketData.of(BuiltinSerializers.FLOAT, this.tickOffset),
                        WorldPacketData.of(BuiltinSerializers.ITEM_STACK, this.itemstack),
                        WorldPacketData.of(BuiltinSerializers.ENTITY_REFERENCE, this.knot));
            }
        }
    }

    public void validate() {
        this.validExistence = true;
    }

    public void invalidate() {
        this.validExistence = false;
    }

    public KiteItem getItem() {
        return (KiteItem) this.itemstack.getItem();
    }

    public KiteKnotEntity getKnot() {
        return this.knot;
    }

    public void updateDesignColor() {
        if (!this.cachedIsDyableDesign) {
            this.designColor = new Vec3(255, 255, 255);
            return;
        }
        switch (this.cachedDesignColor) {
            case 0:
                this.designColor = new Vec3(240, 240, 255);break;
            case 1:
                this.designColor = new Vec3(240, 172, 55);break;
            case 2:
                this.designColor = new Vec3(222, 53, 231);break;
            case 3:
                this.designColor = new Vec3(71, 169, 246);break;
            case 4:
                this.designColor = new Vec3(255, 231, 31);break;
            case 5:
                this.designColor = new Vec3(71, 223, 71);break;
            case 6:
                this.designColor = new Vec3(230, 90, 160);break;
            case 7:
                this.designColor = new Vec3(91, 96, 102);break;
            case 8:
                this.designColor = new Vec3(158, 168, 173);break;
            case 9:
                this.designColor = new Vec3(0, 209, 185);break;
            case 10:
                this.designColor = new Vec3(138, 71, 209);break;
            case 11:
                this.designColor = new Vec3(39, 59, 171);break;
            case 12:
                this.designColor = new Vec3(110, 55, 0);break;
            case 13:
                this.designColor = new Vec3(20, 115, 37);break;
            case 14:
                this.designColor = new Vec3(199, 28, 39);break;
            default:
                this.designColor = new Vec3(5, 3, 12);
        }
    }

    @Override
    public boolean shouldRender(double x, double y, double z) {
        return true;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
