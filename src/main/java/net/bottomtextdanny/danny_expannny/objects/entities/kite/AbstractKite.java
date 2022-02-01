package net.bottomtextdanny.danny_expannny.objects.entities.kite;

import net.bottomtextdanny.braincell.mod.entity.modules.additional_motion.ExtraMotionModule;
import net.bottomtextdanny.braincell.mod.entity.modules.additional_motion.ExtraMotionProvider;
import net.bottomtextdanny.danny_expannny.capabilities.world.LevelWindModule;
import net.bottomtextdanny.dannys_expansion.core.Util.ExternalMotion;
import net.bottomtextdanny.danny_expannny.capabilities.CapabilityHelper;
import net.bottomtextdanny.danny_expannny.capabilities.world.LevelCapability;
import net.bottomtextdanny.dannys_expansion.core.interfaces.entity.EntityClientMessenger;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.network.NetworkHooks;

public class AbstractKite extends PartEntity<KiteKnotEntity> implements EntityClientMessenger, ExtraMotionProvider {
    private ExtraMotionModule extraMotionModule;
    private ExternalMotion forwardMotion;
    private ExternalMotion retrayMotion;
    private ExternalMotion stopMotion;
    private Vec3 clientExtraMotion;
    private boolean valid;

    public AbstractKite(KiteKnotEntity parent) {
        super(parent);
    }

    @Override
    public ExtraMotionModule extraMotionModule() {
        return this.extraMotionModule;
    }

    @Override
    protected void defineSynchedData() {
        this.extraMotionModule = new ExtraMotionModule(this);
        this.forwardMotion = addCustomMotion(new ExternalMotion(0.9F));
        this.retrayMotion = addCustomMotion(new ExternalMotion(0.84F));
        this.stopMotion = addCustomMotion(new ExternalMotion(3, 0.995F));
    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag p_20052_) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag p_20139_) {
    }

    @Override
    public void tick() {
        super.tick();
        LevelWindModule windModule = CapabilityHelper.get(this.level, LevelCapability.CAPABILITY).getWindModule();

//        if (!level.isClientSide() && !validExistence) {
//            remove(RemovalReason.DISCARDED);
//        }
//
//        float customTicksExisted =  tickCount;
//        float kiteAngle = Mth.wrapDegrees(windModule.getWindDirection());
//
//        float difference = (float) DEOp.LOGIC_DIST3D_UTIL.start(this).get(xo, yo, zo);
//
//        prevRenderDifference = renderDifference;
//        renderDifference += (difference - renderDifference) / 15;
//
//        if (!level.isClientSide()) {
//            if (knot != null && knot.isAlive()) {
//                knot.kite_position.set(position());
//                knot.kite_yaw.set(getYRot());
//                knot.kite_pitch.set(getXRot());
//
//                Vec3 knotForward = DEMath.fromPitchYaw(DEMath.getTargetPitch(knot, this), DEMath.getTargetYaw(knot, this));
//                Vec3 windForward = DEMath.fromPitchYaw(0, getYRot());
//
//                if (knot.distanceTo(this) < avgDistance * 2) {
//                    float forwardVec = -(knot.distanceTo(this) - avgDistance * 2);
//                    forwardMotion.setMotion(windForward.x * 0.1 * forwardVec, (DEMath.sin(customTicksExisted * 0.04F) * 0.05) + 0.18, windForward.z * 0.1 * forwardVec);
//                }
//
//                if (knot.distanceTo(this) > avgDistance) {
//                    float retrayVec = (knot.distanceTo(this) - avgDistance);
//                    retrayMotion.setMotion(-knotForward.x * 0.1 * retrayVec, -knotForward.y * 0.1 * retrayVec, -knotForward.z * 0.1 * retrayVec);
//                }
//
//                if (worldCap.isWindStopped()) {
//                    stopMotion.setMotion(-windForward.x * 0.05  * (worldCap.windStopMagnitude),  0.026 * (worldCap.windStopMagnitude), -windForward.z * 0.05 * (worldCap.windStopMagnitude));
//                }
//
//
//            } else {
//
//                ItemEntity itementity = new ItemEntity(level, getX(), getY(), getZ(), itemstack);
//                level.addFreshEntity(itementity);
//                remove(RemovalReason.DISCARDED);
//            }
//
//            absoluteMotion = getDeltaMovement()
//                    .add(forwardMotion.getAcceleratedMotion())
//                    .add(retrayMotion.getAcceleratedMotion())
//                    .add(stopMotion.getAcceleratedMotion());
//
//            setYRot(kiteAngle);
//
//            sendClientMsg(1, PCTData.of(PNSerial.VECTOR3D, absoluteMotion), PCTData.of(PNSerial.FLOAT, getYRot()));
//        }
//
//        move(MoverType.SELF, absoluteMotion);
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return this.valid;
    }

    @Override
    public void move(MoverType type, Vec3 vec) {
        super.move(type, moveHook(vec));
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
