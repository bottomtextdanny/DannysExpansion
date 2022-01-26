package net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations;

public class StartCalloutAnimation extends Animation {
    private final Runnable startCallOut;

    public StartCalloutAnimation(int duration, Runnable startCallOut) {
        super(duration);
        this.startCallOut = startCallOut;
    }

    @Override
    public void wake() {
        this.startCallOut.run();
    }
}
