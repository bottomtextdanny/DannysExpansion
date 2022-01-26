package net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations;

import net.bottomtextdanny.dannys_expansion.core.interfaces.InstancedAnimation;

@InstancedAnimation
public class LoopAnimation extends Animation {
    private boolean stop;

    public LoopAnimation(int duration) {
        super(duration);
    }

    @Override
    public int progressTick(int progress) {
        return (progress + 1) % getDuration();
    }
	
	@Override
	public boolean goal(int progression) {
		return this.stop;
	}
	
	public void setStop(boolean pass) {
        this.stop = pass;
    }

    @Override
    public void resetInstanceValues() {
        this.stop = false;
    }
}
