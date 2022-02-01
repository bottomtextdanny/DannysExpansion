package net.bottomtextdanny.braincell.mod.entity.modules.animatable;

import net.bottomtextdanny.braincell.mod.packet_front.server_to_client.MSGEntityAnimation;
import net.bottomtextdanny.braincell.mod.structure.BCStaticData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;

public class AnimationHandler<E extends Entity> {
	private int index;
	protected final E entity;
	protected AnimationData animationMetadata = AnimationData.NULL;
	protected Animation<?> animation = Animation.NULL;
	protected int prevTick;
	protected int tick;

	public AnimationHandler(E entity) {
		this.entity = entity;
	}
	
	public void play(Animation<?> animation) {
		if (!this.entity.level.isClientSide()) {
			new MSGEntityAnimation(this.entity.getId(), this.index, animation.getIndex())
				.sendTo(PacketDistributor.ALL.with(() -> null));
		}
        this.tick = 0;
		this.animation.onEnd(this);
		this.animation = animation;
		this.animationMetadata = animation.dataForPlay().get();
		this.animation.onStart(this);
	}

	public void update(int tick) {
		this.prevTick = this.tick;
		this.tick = tick;
	}

	public void deactivate() {
		this.tick = 0;
		this.animation.onEnd(this);
		this.animation = Animation.NULL;
	}

	public void setIndex(int index, BCAnimationToken token) {
		if (token == null) {
			throw new IllegalArgumentException("Invalid token.");
		}
        this.index = index;
	}

	public Animation<?> getAnimation() {
		return this.animation;
	}

	public AnimationData getData() {
		return this.animationMetadata;
	}

	public int getIndex() {
		return this.index;
	}
	
	public int getTick() {
		return this.tick;
	}

	public E getEntity() {
		return entity;
	}

	public boolean isPlaying(Animation<?> animation) {
		return this.animation == animation;
	}

	public boolean isPlayingNull() {
		return this.animation == Animation.NULL;
	}

	@OnlyIn(Dist.CLIENT)
	public float linearProgress() {
		return this.tick + BCStaticData.partialTick();
	}
	
	@OnlyIn(Dist.CLIENT)
	public float dynamicProgress() {
		return Mth.lerp(BCStaticData.partialTick(), this.prevTick, this.tick);
	}
}
