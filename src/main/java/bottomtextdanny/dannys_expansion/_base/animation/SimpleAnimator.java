package bottomtextdanny.dannys_expansion._base.animation;

public abstract class SimpleAnimator<T> implements ISimpleAnimator<T> {
    private boolean lock;
    protected float actual;
    protected float keyframe;
    protected float prevKeyframe;
    protected T prevGoal = fallback(null);
    protected T goal = fallback(null);

    @Override
    public void addPoint(float ticksAh, T newGoal) {
        if (!this.lock) {
            updateFrame(ticksAh);
            this.prevGoal = this.goal;
            this.goal = newGoal;
            if (this.actual >= this.prevKeyframe && this.actual < this.keyframe) {
                this.lock = true;
            }
        }
    }

    private void updateFrame(float ticksAh) {
        this.prevKeyframe = this.keyframe;
        this.keyframe += ticksAh;
    }

    @Override
    public void reset(T start) {
        this.prevKeyframe = this.keyframe = 0;
        this.prevGoal = this.goal = fallback(start);
        this.lock = false;

    }

    @Override
    public void setActual(float newActual) {
        this.actual = newActual;
    }

    @Override
    public T getGoal() {
        return this.goal;
    }

    @Override
    public void setGoal(T goal) {
        this.goal = goal;
    }

    @Override
    public void setPrevGoal(T prevGoal) {
        this.prevGoal = prevGoal;
    }
}
