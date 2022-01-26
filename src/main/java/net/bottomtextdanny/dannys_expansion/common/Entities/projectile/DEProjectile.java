package net.bottomtextdanny.dannys_expansion.common.Entities.projectile;

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
import net.bottomtextdanny.dannys_expansion.core.Util.DannyRayTraceHelper;
import net.bottomtextdanny.braincell.underlying.misc.ObjectFetcher;
import net.bottomtextdanny.dannys_expansion.core.interfaces.entity.ClientManager;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nullable;

public abstract class DEProjectile extends Projectile implements BCDataManagerProvider, ClientManager, AnimatableProvider {
    private static final int BASE_UPDATE_TICK_CLIENT_FLAG = 0;
    public static final EntityDataReference<Entity> CASTER_REF =
            BCDataManager.attribute(DEProjectile.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.ENTITY_REFERENCE,
                            () -> null,
                            "caster")
            );
    public static final EntityDataReference<Integer> ACTUAL_TICK_REF =
            BCDataManager.attribute(DEProjectile.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.INTEGER,
                            () -> 0,
                            "actual_tick")
            );
    private BCDataManager deDataManager;
    private AnimatableModule animatableModule;
    protected final EntityData<Integer> actualTick;
    protected final EntityData<Entity> caster;
    public final AnimationHandler<DEProjectile> mainModule;
    private int lifetime;

    public DEProjectile(EntityType<? extends Projectile> type, Level level) {
        super(type, level);
        this.mainModule = addAnimationHandler(new AnimationHandler<>(this));
        this.caster = bcDataManager().addSyncedData(EntityData.of(CASTER_REF));
        this.actualTick = bcDataManager().addSyncedData(EntityData.of(ACTUAL_TICK_REF));
    }

    @Override
    protected void defineSynchedData() {
        this.lifetime = baseLifetime();
        this.deDataManager = new BCDataManager(this);
        this.animatableModule = new AnimatableModule(this);
        commonInit();
    }

    public abstract void commonInit();

    @Override
    public AnimatableModule animatableModule() {
        return this.animatableModule;
    }

    @Override
    public BCDataManager bcDataManager() {
        return this.deDataManager;
    }

    @Override
    public void tick() {
        super.tick();
        final Vec3 delta = getDeltaMovement();

        if (this.actualTick.get() < this.lifetime) {
            this.actualTick.set(this.actualTick.get() + 1);
        } else {
            if (!this.level.isClientSide) {
                removeProjectile();
            }
        }

        if (!this.level.isClientSide) {
            HitResult raytraceresult = rayTraceResultType();
            onHit(raytraceresult);

            sendClientMsg(BASE_UPDATE_TICK_CLIENT_FLAG, PacketDistributor.TRACKING_ENTITY.with(() -> this), WorldPacketData.of(BuiltinSerializers.VEC3, delta));
        }

        move(MoverType.SELF, getDeltaMovement());

        checkInsideBlocks();
    }

    @Override
    protected void onHit(HitResult result) {
        HitResult.Type type = result.getType();

        if (this.caster.get() != null) {
            if (type == HitResult.Type.ENTITY) {
                this.onEntityHit((EntityHitResult) result, ((EntityHitResult) result).getEntity());
            } else if (type == HitResult.Type.BLOCK) {
                this.onBlockHit((BlockHitResult) result);
            }
        } else {
            removeProjectile();
        }
    }

    protected void onBlockHit(BlockHitResult result) {
    }

    protected void onEntityHit(EntityHitResult result, Entity entity) {
    }

    @Override
    public void clientCallOutHandler(int flag, ObjectFetcher fetcher) {
        if (flag == BASE_UPDATE_TICK_CLIENT_FLAG) {
            Vec3 serverDeltaMovement = fetcher.get(0, Vec3.class);
            setDeltaMovement(serverDeltaMovement);
        }
    }

    public void removeProjectile() {
        removeCallOut();
        remove(RemovalReason.DISCARDED);
    }

    public HitResult rayTraceResultType() {
        return DannyRayTraceHelper.bulletRaytrace(this, this::collisionParameters, getDeltaMovement(), ClipContext.Fluid.NONE);
    }

    public boolean collisionParameters(Entity entityIn) {
        if (!entityIn.isAlive() || entityIn.isRemoved()) return false;
        if (!entityIn.isPickable()) return false;
        return EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(entityIn) && entityIn != this.caster.get();
    }

    protected void removeCallOut() {}

    public void setCaster(LivingEntity livingEntity) {
        this.caster.set(livingEntity);
        setOwner(livingEntity);
    }

    public void setLifetime(int lifetime) {
        this.lifetime = lifetime;
    }

    public int baseLifetime() {
        return 10;
    }

    public LivingEntity getCaster() {
        return (LivingEntity) this.caster.get();
    }

    protected double getCasterAttribute(Attribute attribute) {
        return getCaster().getAttribute(attribute).getValue();
    }

    public int getLifeTick() {
        return this.actualTick.get();
    }

    @Nullable
    @Override
    public Entity getOwner() {
        return this.caster.get();
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
