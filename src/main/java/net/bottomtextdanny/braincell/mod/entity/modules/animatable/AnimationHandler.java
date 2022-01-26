package net.bottomtextdanny.braincell.mod.entity.modules.animatable;

import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.NullAnimation;
import net.bottomtextdanny.braincell.mod.packet_front.server_to_client.MSGEntityAnimation;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.MutableLatch;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;

public class AnimationHandler<E extends Entity> {
	protected MutableLatch<Integer> indexLookable = MutableLatch.empty();
	protected IAnimation animation = NullAnimation.UNI;
	protected int prevTick;
	protected int tick;
	protected E entity;
	
	public AnimationHandler(E entity) {
		this.entity = entity;
	}
	
	public IAnimation get() {
		return this.animation;
	}
	
	public void play(IAnimation animation) {
		if (!this.entity.level.isClientSide()) {
			new MSGEntityAnimation(this.entity.getId(), this.indexLookable.get(), animation.getIndex())
				.sendTo(PacketDistributor.ALL.with(() -> null));
		}

        this.tick = 0;
		this.animation.resetInstanceValues();
		this.animation.setWoke(false);
		this.animation = animation;
		this.animation.setWoke(true);
	}
	
	public boolean isPlaying(IAnimation animation) {
		return this.animation == animation;
	}
	
	public boolean isPlayingNull() {
		return this.animation.isNull();
	}
	
	public boolean inactive() {
		return this.animation == NullAnimation.UNI;
	}
	
	public void setIndex(int index) {
        this.indexLookable.setLocked(index);
	}
	
	public int getIndex() {
		return this.indexLookable.get();
	}
	
	public void update(int tick) {
		this.prevTick = this.tick;
		this.tick = tick;
	}
	
	public int getTick() {
		return this.tick;
	}
	
	public void trySleep() {
        this.tick = 0;
		this.animation.resetInstanceValues();
		this.animation.setWoke(false);
		this.animation = NullAnimation.UNI;
	}
	
	@OnlyIn(Dist.CLIENT)
	public float linearProgress() {
		return this.tick + DEUtil.PARTIAL_TICK;
	}
	
	@OnlyIn(Dist.CLIENT)
	public float e_dynamic() {
		return Mth.lerp(DEUtil.PARTIAL_TICK, this.prevTick, this.tick);
	}
}
