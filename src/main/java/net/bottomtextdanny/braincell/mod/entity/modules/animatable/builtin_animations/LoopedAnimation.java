package net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations;

import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationHandler;

import java.util.function.Supplier;

public class LoopedAnimation extends DEAnimation<LoopedAnimationData> {

    public LoopedAnimation(int duration) {
        super(duration);
    }

    @Override
    public Supplier<LoopedAnimationData> dataForPlay() {
        return LoopedAnimationData::new;
    }

    @Override
    public int progressTick(int progress, AnimationHandler<?> handler) {
        return (progress + 1) % getDuration();
    }
	
	@Override
	public boolean goal(int progression, AnimationHandler<?> handler) {
		return getData(handler).stop;
	}
	
	public void setStop(AnimationHandler<?> handler, boolean pass) {
        getData(handler).stop = pass;
    }
}
