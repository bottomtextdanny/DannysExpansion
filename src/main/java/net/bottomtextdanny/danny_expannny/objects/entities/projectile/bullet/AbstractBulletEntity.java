package net.bottomtextdanny.danny_expannny.objects.entities.projectile.bullet;

import net.bottomtextdanny.braincell.mod.base.misc.timer.IntScheduler;
import net.bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManager;
import net.bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManagerProvider;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityData;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityDataReference;
import net.bottomtextdanny.braincell.mod.entity.serialization.RawEntityDataReference;
import net.bottomtextdanny.braincell.mod.serialization.WorldPacketData;
import net.bottomtextdanny.braincell.mod.serialization.serializers.builtin.BuiltinSerializers;
import net.bottomtextdanny.braincell.mod.serialization.serializers.braincell.BCSerializers;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.common.Entities.projectile.DEProjectile;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.DannyRayTraceHelper;
import net.bottomtextdanny.dannys_expansion.core.Util.ExternalMotion;
import net.bottomtextdanny.braincell.underlying.misc.ObjectFetcher;
import net.bottomtextdanny.danny_expannny.capabilities.player.MiniAttribute;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerAccessoryModule;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerHelper;
import net.bottomtextdanny.dannys_expansion.core.data.DEDamageSources;
import net.bottomtextdanny.dannys_expansion.core.interfaces.entity.ClientManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nullable;

public abstract class AbstractBulletEntity extends DEProjectile implements BCDataManagerProvider, ClientManager {
    public static final int HIT_CLIENT_CALLOUT_FLAG = 0, UPDATE_TICK_CALLOUT_FLAG = 1;
    public static final EntityDataReference<IntScheduler.Simple> LIFE_REF =
            BCDataManager.attribute(AbstractBulletEntity.class,
                    RawEntityDataReference.of(
                            BCSerializers.INT_SCHEDULER,
                            () -> IntScheduler.simple(0),
                            "life")
            );
    public static final EntityDataReference<Float> DAMAGE_REF =
            BCDataManager.attribute(AbstractBulletEntity.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.FLOAT,
                            () -> 0.0F,
                            "damage")
            );
    public static final EntityDataReference<Float> SPEED_REF =
            BCDataManager.attribute(AbstractBulletEntity.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.FLOAT,
                            () -> 0.0F,
                            "speed")
            );
    public static final EntityDataReference<Float> SPEED_MULT_REF =
            BCDataManager.attribute(AbstractBulletEntity.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.FLOAT,
                            () -> 0.0F,
                            "speed_mult")
            );
    private final BCDataManager deDataManager;
    protected final EntityData<Float> damage;
    protected final EntityData<Float> speed;
    protected final EntityData<Float> speed_mult;
    public ExternalMotion acceleration = new ExternalMotion(0.95F);
    public boolean soundPlayed;
    public boolean waterSplashSoundPlayer;
    public float prevDifference;
    public float difference;

    public AbstractBulletEntity(EntityType<? extends AbstractBulletEntity> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
        this.deDataManager = new BCDataManager(this);
        this.damage = bcDataManager().addSyncedData(EntityData.of(DAMAGE_REF));
        this.speed = bcDataManager().addSyncedData(EntityData.of(SPEED_REF));
        this.speed_mult = bcDataManager().addSyncedData(EntityData.of(SPEED_MULT_REF));
    }

    public AbstractBulletEntity(EntityType<? extends AbstractBulletEntity> entityTypeIn, Level worldIn, int lifetime) {
        this(entityTypeIn, worldIn);
        setLifetime(lifetime);
    }

    @Override
    public void commonInit() {}

    protected void defineSynchedData() {}

    @Override
    public void tick() {
        super.tick();

        this.noPhysics = true;
        this.acceleration.tick();
        this.prevDifference = this.difference;
        this.difference = DEMath.getDistance(getX(), getY(), getZ(), this.xo, this.yo, this.zo);

        if (!canceledMovement()) {
            Vec3 forward = Vec3.directionFromRotation(getXRot(), getYRot());
            Vec3 nextPosition = position().add(getRealSpeed() * forward.x, getRealSpeed() * forward.y, getRealSpeed() * forward.z);
            BlockPos nextBlockPosition = new BlockPos((int)nextPosition.x, (int)nextPosition.y, (int)nextPosition.z);
			
		    if (!touchingUnloadedChunk() && this.level.isLoaded(nextBlockPosition)) {
			    this.move(MoverType.SELF, this.acceleration.getAcceleratedMotion());
		    } else if (!this.level.isClientSide()) {
			    remove(RemovalReason.DISCARDED);
		    }
			
            if (!this.onGround) {
                this.acceleration.setMotion(getRealSpeed() * forward.x, getRealSpeed() * forward.y, getRealSpeed() * forward.z);
            }
        } else {
            setDeltaMovement(0, 0, 0);
        }

        sendClientMsg(UPDATE_TICK_CALLOUT_FLAG, PacketDistributor.TRACKING_ENTITY.with(() -> this), WorldPacketData.of(BuiltinSerializers.VEC3, getDeltaMovement()));
        HitResult raytraceresult = rayTraceResultType();
        onHit(raytraceresult);
        checkInsideBlocks();
    }

    @Override
    protected void onHit(HitResult result) {
	    HitResult.Type type = result.getType();

        if (!this.level.isClientSide) {
            if (type == HitResult.Type.ENTITY && this.caster.get() != null && ((EntityHitResult) result).getEntity().getId() != this.caster.get().getId()) {
                this.onHitEntity((EntityHitResult) result);
            } else if (type == HitResult.Type.BLOCK) {
                this.onBlockHit((BlockHitResult) result);
            }
        }
    }

    public void onDeath() {
    }

    protected void onBlockHit(BlockHitResult result) {
        Block block = this.level.getBlockState(result.getBlockPos()).getBlock();
        this.onHitBlock(result);

        SoundEvent breakSound = this.level.getBlockState(result.getBlockPos()).getSoundType().getBreakSound();
        if (this.level.getFluidState(result.getBlockPos()).getType() == Fluids.WATER) {
            this.level.playSound(null, this.getX(), this.getY(), this.getZ(), DESounds.ES_BULLET_IMPACT_WATER.get(), SoundSource.NEUTRAL, 0.6F, 1.0F + 0.2F * this.random.nextFloat());
            this.waterSplashSoundPlayer = true;
        } else if (breakSound == SoundEvents.STONE_BREAK || block.getTags().stream().anyMatch(res -> res.equals(Tags.Blocks.STONE.getName()))) {
            this.level.playSound(null, this.getX(), this.getY(), this.getZ(), DESounds.ES_BULLET_IMPACT_STONE.get(), SoundSource.NEUTRAL, 0.6F, 1.0F + 0.2F * this.random.nextFloat());
            this.soundPlayed = true;
        } else if (breakSound == SoundEvents.GLASS_BREAK || block.getTags().stream().anyMatch(res -> res.equals(Tags.Blocks.GLASS.getName()))) {
            this.level.playSound(null, this.getX(), this.getY(), this.getZ(), DESounds.ES_BULLET_IMPACT_GLASS.get(), SoundSource.NEUTRAL, 0.6F, 1.0F + 0.2F * this.random.nextFloat());
            this.soundPlayed = true;
        } else if (breakSound == SoundEvents.GRASS_BREAK || breakSound == SoundEvents.GRAVEL_BREAK || block.getTags().stream().anyMatch(res -> res == Tags.Blocks.GRAVEL)) {
            this.level.playSound(null, this.getX(), this.getY(), this.getZ(), DESounds.ES_BULLET_IMPACT_DIRT.get(), SoundSource.NEUTRAL, 0.6F, 1.0F + 0.2F * this.random.nextFloat());
            this.soundPlayed = true;
        } else if (breakSound == SoundEvents.WOOD_BREAK) {
            this.level.playSound(null, this.getX(), this.getY(), this.getZ(), DESounds.ES_BULLET_IMPACT_WOOD.get(), SoundSource.NEUTRAL, 0.6F, 1.0F + 0.2F * this.random.nextFloat());
            this.soundPlayed = true;
        }
	    if (!this.level.isClientSide()) {
		    removeAfterChangingDimensions();
	    }
    }

    protected void onHitEntity(EntityHitResult result) {
        result.getEntity().invulnerableTime = 0;
        castersDamage(result.getEntity(), getBulletDamage());
        if (!this.level.isClientSide()) {
	        removeAfterChangingDimensions();
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientCallOutHandler(int flag, ObjectFetcher fetcher) {
        if (flag == HIT_CLIENT_CALLOUT_FLAG) {
            clientHitCallout(fetcher.get(0));
        } else if (flag == UPDATE_TICK_CALLOUT_FLAG) {
            setDeltaMovement(fetcher.get(0));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public abstract void clientHitCallout(Vec3 hitPosition);

    public void sendHitMsg(Vec3 hitPos) {
        sendClientMsg(HIT_CLIENT_CALLOUT_FLAG, WorldPacketData.of(BuiltinSerializers.VEC3, hitPos));
    }

    @Override
    protected void removeAfterChangingDimensions() {
        onDeath();
        super.removeAfterChangingDimensions();
    }

    public boolean canceledMovement() {
        return false;
    }

    public HitResult rayTraceResultType() {
        return DannyRayTraceHelper.bulletRaytrace(this, this::collisionParameters, DEMath.fromPitchYaw(getXRot(), getYRot()).multiply(getRealSpeed(), getRealSpeed(), getRealSpeed()), ClipContext.Fluid.ANY);
    }

    public boolean collisionParameters(Entity entityIn) {
        return !entityIn.isSpectator() && entityIn != this.caster.get();
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    protected void updateRotation() {
    }

    //UTILS
    //----*

    public void castersDamage(Entity livingEntity, float amount) {
        livingEntity.hurt(DEDamageSources.causeBulletDamage(this, this.caster.get()), amount);
    }

    //SETTERS
    //------*

    @Nullable
    @Override
    public Entity getOwner() {
        return this.caster.get();
    }

    @Override
    public void setOwner(@Nullable Entity p_37263_) {
    }

    @Override
    protected boolean ownedBy(Entity p_150172_) {
        return p_150172_ == this.caster.get() || super.ownedBy(p_150172_);
    }

    public void setBulletDamage(float damage) {
        this.damage.set(damage);
    }

    public void addBulletDamage(float damage) {
        this.damage.set(getBulletDamage() + damage);
    }

    public void setBulletSpeedMult(float speedMult) {
        this.speed_mult.set(speedMult);
    }

    public void addBulletSpeedMult(float speedMult) {
        this.speed_mult.set(getBulletSpeed() + speedMult);
    }

    public void setBulletSpeed(float speed) {
        this.speed.set(speed);
    }

    public void addBulletSpeed(float speed) {
        this.speed.set(getBulletSpeed() + speed);
    }

    public void setOrigin(LivingEntity livingEntity) {
        this.caster.set(livingEntity);
        if (livingEntity instanceof Player player) {
            PlayerAccessoryModule cap = PlayerHelper.accessoryModule(player);
            addBulletSpeedMult((cap.getLesserModifier(MiniAttribute.BULLET_SPEED_MLT) - 100.0F) / 100.0F);
        }
    }

    //GETTERS
    //------*

    public float getBulletDamage() {
        return this.damage.get();
    }

    public float getBulletSpeedMult() {
        return this.speed_mult.get();
    }

    public float getBulletSpeed() {
        return this.speed.get();
    }

    public float getRealSpeed() {return getBulletSpeed() * getBulletSpeedMult();}

	public void setupForGun(Player player, float dispersion) {
		setupForGunPellet(player, dispersion, 0.0F, 0.0F);
	}
	
	public void setupForGunPellet(Player player, float dispersion, float globalDispersionX, float globalDispersionY) {
        PlayerAccessoryModule cap = PlayerHelper.accessoryModule(player);
		
		addBulletDamage(cap.getLesserModifier(MiniAttribute.GUN_DAMAGE_ADD));
		setBulletDamage(getBulletDamage() * cap.getLesserModifier(MiniAttribute.GUN_DAMAGE_MLT) / 100.0F);
		
		absMoveTo(player.getX(), player.getEyeY(), player.getZ(), player.yHeadRot + (float)(dispersion * this.random.nextGaussian()) + globalDispersionY, player.getXRot() - PlayerHelper.gunModule(player).getRecoil() + (float)(dispersion / 2.0 * this.random.nextGaussian()) + globalDispersionX);
		setOrigin(player);
		player.level.addFreshEntity(this);
	}
	
    @Override
    public BCDataManager bcDataManager() {
        return this.deDataManager;
    }
}
