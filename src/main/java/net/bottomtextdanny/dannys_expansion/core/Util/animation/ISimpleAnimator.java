package net.bottomtextdanny.dannys_expansion.core.Util.animation;

public interface ISimpleAnimator<T> {

    default ISimpleAnimator<T> start(T start) {
        setPrevGoal(start);
        setGoal(start);
        return this;
    }

    default ISimpleAnimator<T> actual(float newActual) {
        setActual(newActual);
        return this;
    }


    void addPoint(float tickAh, T newGoal);

    void reset(T start);

    T fallback(T obj);

    default void reset() {
        reset(null);
    }

    T get(Easing easing, float ptc);

    default T get() {
        return get(Easing.LINEAR, 0);
    }

    void setActual(float newActual);

    T getGoal();

    void setGoal(T obj);

    void setPrevGoal(T obj);
}
