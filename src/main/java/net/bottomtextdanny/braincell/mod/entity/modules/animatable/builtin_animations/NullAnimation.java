package net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations;

import net.bottomtextdanny.braincell.mod.entity.modules.animatable.IAnimation;

public class NullAnimation implements IAnimation {
    public static final NullAnimation UNI = new NullAnimation();

    public NullAnimation() {
    }
	
	@Override
	public boolean isNull() {
		return true;
	}
	
	@Override
    public int progressTick(int duration) {
        return 0;
    }

    @Override
    public boolean goal(int prog) {
        return false;
    }

    @Override
    public int getDuration() {
        return 0;
    }
	
	@Override
	public void setIndex(int index) {
    	
	}
	
	@Override
	public boolean isWoke() {
		return false;
	}
	
	@Override
	public void setWoke(boolean val) {
	}
	
	@Override
	public boolean from(int identifier) {
		return false;
	}
	
	@Override
	public int getIndex() {
		return 0;
	}
	
	public void resetInstanceValues() {}
}
