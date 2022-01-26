package net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations;

import net.bottomtextdanny.dannys_expansion.core.interfaces.InstancedAnimation;

@InstancedAnimation
public class CuttedAnimation extends Animation {
    private final int cut;
    private boolean pass;

    public CuttedAnimation(int duration, int cut) {
        super(duration);
        this.cut = cut;
    }

    @Override
    public int progressTick(int progress) {
        return this.pass || progress < this.cut ? progress + 1 : progress;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    @Override
    public void resetInstanceValues() {
        this.pass = false;
    }
}
