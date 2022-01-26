package net.bottomtextdanny.dannys_expansion.core.Util;

import java.util.function.Consumer;

public class Timer {
    public static IBoundPredicate NORMAL = baseBound -> baseBound;
    private final Timer.IBoundPredicate nextBoundMath;
    private int boundBase;
    private int currentBound;
    private int count;

    public Timer(int boundBase, Timer.IBoundPredicate nextBoundMath) {
        this.boundBase = boundBase;
        this.nextBoundMath = nextBoundMath;
        this.currentBound = nextBoundMath.nextBound(boundBase);
    }



    public Timer(int boundBase) {
        this.boundBase = boundBase;
        this.nextBoundMath = NORMAL;
        this.currentBound = this.nextBoundMath.nextBound(boundBase);
    }

    public Timer() {
        this.boundBase = 0;
        this.nextBoundMath = baseBound -> baseBound;
        this.currentBound = this.nextBoundMath.nextBound(this.boundBase);
    }

    public Timer ended() {
        end();
        return this;
    }

    public void end() {
        this.count = this.currentBound;
    }

    public void tryUp() {
        if (this.count < this.currentBound) this.count++;
    }
	
	
	public void tryUpOrElse(Consumer<Timer> elseDo) {
		if (this.count < this.currentBound)
            this.count++;
		else
			elseDo.accept(this);
			
	}

    public void reset() {
        this.currentBound = this.nextBoundMath.nextBound(this.boundBase);
        this.count = 0;
    }
    
    public float prog() {
    	return (float) this.count / (float) this.currentBound;
    }

    public boolean hasEnded() {
        return this.count >= this.currentBound;
    }

    public void setBoundBase(int boundBase) {
        this.boundBase = boundBase;
    }

    public void setCurrentBound(int currentBound) {
        this.currentBound = currentBound;
    }

    public void setCount(int timer) {
        this.count = timer;
    }

    public int getBoundBase() {
        return this.boundBase;
    }

    public int getCurrentBound() {
        return this.currentBound;
    }

    public int getCount() {
        return this.count;
    }

    public IBoundPredicate getNextBoundMath() {
        return this.nextBoundMath;
    }

    @FunctionalInterface
    public interface IBoundPredicate {
        int nextBound(int baseBound);
    }
}
