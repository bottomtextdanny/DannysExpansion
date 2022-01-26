package net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations;

import net.bottomtextdanny.braincell.mod.entity.modules.animatable.IAnimation;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.MutableLatch;

public class Animation implements IAnimation {
	public static final int NO_IDENTIFIER = 0;
	private final MutableLatch<Integer> indexLockable = MutableLatch.empty();
	private int auxIdentifier = NO_IDENTIFIER;
    private final int tickDuration;
    private boolean active;
    
    public Animation(int duration) {
        this.tickDuration = duration;
    }
    
    @SuppressWarnings("unchecked")
    public <A extends Animation> A identifier(int identifier) {
        this.auxIdentifier = identifier;
    	
    	return (A)this;
    }
    
    public boolean from(int identifier) {
    	return this.auxIdentifier == identifier;
    }

    /**
     * This means activating and running the animation until its satisfies its {@link Animation#goal(int)}.
     */
    public void wake() {
    }

    /**
     * when the animation finally satisfies its goal, this method is called to rest the animation.
     */
    public void sleep() {
    }
	
	@Override
	public void setIndex(int index) {
        this.indexLockable.setLocked(index);
	}
	
	public boolean isWoke() {
        return this.active;
    }
	
	@Override
	public void setWoke(boolean val) {
        if (val) wake();
        else sleep();
        this.active = val;
	}
	
	@Override
	public boolean isNull() {
		return false;
	}
	
	@Override
    public int progressTick(int progress) {
        return progress + 1;
    }

    @Override
    public boolean goal(int progression) {
        return progression > this.tickDuration;
    }
    

    public int getIndex() {
        return this.indexLockable.get();
    }

    @Override
    public int getDuration() {
        return this.tickDuration;
    }

    @Override
    public void resetInstanceValues() {}
}
