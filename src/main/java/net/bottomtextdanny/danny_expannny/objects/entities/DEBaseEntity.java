package net.bottomtextdanny.danny_expannny.objects.entities;

import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimatableModule;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimatableProvider;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationGetter;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationHandler;
import net.bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManager;
import net.bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManagerProvider;
import net.bottomtextdanny.braincell.mod.serialization.WorldPacketData;
import net.bottomtextdanny.braincell.mod.serialization.serializers.builtin.BuiltinSerializers;
import net.bottomtextdanny.braincell.underlying.misc.ObjectFetcher;
import net.bottomtextdanny.dannys_expansion.core.interfaces.entity.EntityClientMessenger;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

public abstract class DEBaseEntity extends Entity implements EntityClientMessenger, BCDataManagerProvider, AnimatableProvider {
	public static final int CLIENT_BASE_FLAG = 256;
	public final AnimationHandler<DEBaseEntity> mainAnimationHandler;
	private BCDataManager deDataManager;
	private final AnimatableModule animatableModule;
	
	public DEBaseEntity(EntityType<? extends Entity> entityTypeIn, Level worldIn) {
		super(entityTypeIn, worldIn);
		this.animatableModule = new AnimatableModule(this, getAnimations());
		this.mainAnimationHandler = addAnimationHandler(new AnimationHandler<>(this));
	}

	public AnimationGetter getAnimations() {
		return null;
	}
	
	@Override
	protected void defineSynchedData() {
		this.deDataManager = new BCDataManager(this);
		commonInit();
	}

	@Override
	public AnimatableModule animatableModule() {
		return this.animatableModule;
	}

	protected abstract void commonInit();

	@Override
	public void tick() {
		super.tick();

		setDeltaMovement(getDeltaMovement().scale(motionMult()));
		sendClientMsg(CLIENT_BASE_FLAG,
				WorldPacketData.of(BuiltinSerializers.VEC3, getDeltaMovement()),
				WorldPacketData.of(BuiltinSerializers.FLOAT, getYRot()),
				WorldPacketData.of(BuiltinSerializers.FLOAT, getXRot()));
	}
	
	public float motionMult() {
		return 0.0F;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void clientCallOutHandler(int flag, ObjectFetcher fetcher) {
		if (flag == CLIENT_BASE_FLAG) {
			Vec3 pctMotion = fetcher.get(0, Vec3.class);
			float pctYaw = fetcher.get(1, Float.class);
			float pctPitch = fetcher.get(2, Float.class);
			setDeltaMovement(pctMotion);
			setYRot(pctYaw);
			setXRot(pctPitch);
		}
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

