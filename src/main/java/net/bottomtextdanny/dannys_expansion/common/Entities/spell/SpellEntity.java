package net.bottomtextdanny.dannys_expansion.common.Entities.spell;

import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimatableModule;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimatableProvider;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationHandler;
import net.bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManager;
import net.bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManagerProvider;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityData;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityDataReference;
import net.bottomtextdanny.braincell.mod.entity.serialization.RawEntityDataReference;
import net.bottomtextdanny.braincell.mod.serialization.WorldPacketData;
import net.bottomtextdanny.braincell.mod.serialization.serializers.builtin.BuiltinSerializers;
import net.bottomtextdanny.dannys_expansion.core.Util.ExternalMotion;
import net.bottomtextdanny.braincell.underlying.misc.ObjectFetcher;
import net.bottomtextdanny.dannys_expansion.core.interfaces.entity.ClientManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

@Deprecated
public abstract class SpellEntity extends Entity implements AnimatableProvider, ClientManager, BCDataManagerProvider {
    public static final int CLIENT_BASE_FLAG_START = 256;
    public static final int TICK_INFO_SYNC_CALL = CLIENT_BASE_FLAG_START;
    public static final EntityDataReference<Entity> CASTER_REF =
            BCDataManager.attribute(SpellEntity.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.ENTITY_REFERENCE,
                            () -> null,
                            "caster")
            );
    public static final EntityDataReference<Integer> ACTUAL_TICK_REF =
            BCDataManager.attribute(SpellEntity.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.INTEGER,
                            () -> 0,
                            "actual_tick")
            );
	public final AnimationHandler<SpellEntity> mainHandler;
	private final BCDataManager deDataManager;
    protected final EntityData<Integer> actualTick;
    protected final EntityData<Entity> caster;
    protected Vec3 forward = Vec3.ZERO;
    protected ExternalMotion externalMotion;
	protected int maxLife;
    private AnimatableModule animatableModule;
	
    public SpellEntity(EntityType<? extends SpellEntity> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
        this.deDataManager = new BCDataManager(this);
        this.caster = bcDataManager().addSyncedData(EntityData.of(CASTER_REF));
        this.actualTick = bcDataManager().addSyncedData(EntityData.of(ACTUAL_TICK_REF));
        this.animatableModule = new AnimatableModule(this);
        this.mainHandler = addAnimationHandler(new AnimationHandler<>(this));
        this.externalMotion = new ExternalMotion(0.0F);
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void addAdditionalSaveData(CompoundTag p_20139_) {}

    @Override
    protected void readAdditionalSaveData(CompoundTag p_20052_) {}

    @Override
    public AnimatableModule animatableModule() {
        return this.animatableModule;
    }

    public void setCaster(LivingEntity livingEntity) {
        this.caster.set(livingEntity);
    }

    @Override
    public void tick() {
        super.tick();
        
        setupRaytrace();

        this.forward = Vec3.directionFromRotation(getXRot(), getYRot());

        if (this.actualTick.get() == 0) {
            onLifeStart();
        } else if (this.actualTick.get() >= this.maxLife) {
            setDeath();
        }
        this.actualTick.set(this.actualTick.get() + 1);

        setDeltaMovement(getDeltaMovement().scale(getMotionDeceleration()));
        sendClientMsg(TICK_INFO_SYNC_CALL,
                WorldPacketData.of(BuiltinSerializers.VEC3, getDeltaMovement()),
                WorldPacketData.of(BuiltinSerializers.FLOAT, getYRot()),
                WorldPacketData.of(BuiltinSerializers.FLOAT, getXRot()));
        this.externalMotion.tick();
        if (movable()) {
            this.move(MoverType.SELF, finalMotion());
        }
    }
    
    public float getMotionDeceleration() {
    	return 0.96F;
    }
    
    public Vec3 finalMotion() {
	    return getDeltaMovement().add(this.externalMotion.getAcceleratedMotion());
    }

    protected void setupRaytrace() {
        HitResult raytraceresult = rayTraceResultType();
        onImpact(raytraceresult);
    }

    @Nullable
    public LivingEntity getCaster() {
        if (this.caster.get() instanceof LivingEntity) {
            return (LivingEntity) this.caster.get();
        }
        return null;
    }

    public void setDeath() {
        onLifeEnd();
        this.remove(RemovalReason.KILLED);
    }

    public void onLifeEnd() {}

    public void onLifeStart() {
    }

    protected void onImpact(HitResult result) {
        HitResult.Type type = result.getType();

        if (type == HitResult.Type.ENTITY && ((EntityHitResult)result).getEntity() != getCaster()) {
            this.onEntityHit((EntityHitResult)result);
        } else if (type == HitResult.Type.BLOCK) {
            this.onBlockHit((BlockHitResult)result);
        }
    }

    public int getLifeTime() {
        return this.maxLife;
    }

    public int getLifeTick() {
        return this.actualTick.get();
    }

    public void setLifeTime(int life) {
        this.maxLife = life;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientCallOutHandler(int flag, ObjectFetcher fetcher) {
        if (flag == TICK_INFO_SYNC_CALL) {
            Vec3 pctMotion = fetcher.get(0, Vec3.class);
            float pctYaw = fetcher.get(1, Float.class);
            float pctPitch = fetcher.get(2, Float.class);
            setDeltaMovement(pctMotion);
            setYRot(pctYaw);
            setXRot(pctPitch);
        }
    }
	
	public HitResult rayTraceResultType() {
        return ProjectileUtil.getHitResult(this, this::collisionParameters);
    }

    public boolean collisionParameters(Entity entityIn) {
    	if (entityIn instanceof SpellEntity) {
    		SpellEntity spellEntity = (SpellEntity) entityIn;
    		if (spellEntity.getCaster() != null || spellEntity.getCaster() != getCaster()) {
    			return false;
		    }
	    }
        return !entityIn.isSpectator() && entityIn != getCaster();
    }

    protected void onEntityHit(EntityHitResult result) {}

    protected void onBlockHit(BlockHitResult result) {}

    public void setRotations(float yaw, float pitch) {
        setYRot(yaw);
        setXRot(pitch);
    }

    @Deprecated
    public boolean casterHasAttackTarget() {
    	if (getCaster() instanceof Mob) {
    		return ((Mob) getCaster()).getTarget() != null;
    	}
        return false;
    }

    public LivingEntity getCasterTarget() {
        if (getCaster() != null) {
            if (getCaster() instanceof Mob) {
                return ((Mob) getCaster()).getTarget();
            }
        }
        return null;
    }

    public boolean castersDamage(LivingEntity livingEntity, float amount) {
        if (getCaster() != null) {
            return livingEntity.hurt(DamageSource.mobAttack(getCaster()), amount);
        } else {
            return livingEntity.hurt(DamageSource.MAGIC, amount);
        }
    }

    public boolean movable() {
        return true;
    }

    public void setSimpleMotion(double x, double y, double z) {
        this.externalMotion.setMotion(x, y, z);
    }

    public Vec3 getSimpleMotion() {
        return this.externalMotion.getAcceleratedMotion();
    }

    @Override
    public BCDataManager bcDataManager() {
        return this.deDataManager;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
