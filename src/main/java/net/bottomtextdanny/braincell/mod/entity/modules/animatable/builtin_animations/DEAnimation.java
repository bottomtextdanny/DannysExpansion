package net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations;

import net.bottomtextdanny.braincell.mod.entity.modules.animatable.Animation;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationData;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationHandler;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.BCAnimationToken;

public abstract class DEAnimation<T extends AnimationData> implements Animation<T> {
    public static final int NO_IDENTIFIER = -1;
    private int auxId = NO_IDENTIFIER;
    private int index = 0;
    private final int tickDuration;

    public DEAnimation(int duration) {
        this.tickDuration = duration;
    }

    @SuppressWarnings("unchecked")
    public <A extends SimpleAnimation> A identifier(int identifier) {
        this.auxId = identifier;
        return (A)this;
    }

    public boolean isFrom(int identifier) {
        return this.auxId == identifier;
    }

    @Override
    public int progressTick(int progress, AnimationHandler<?> handler) {
        return progress + 1;
    }

    @Override
    public boolean goal(int progression, AnimationHandler<?> handler) {
        return progression > this.tickDuration;
    }

    public int getIndex() {
        return this.index;
    }

    @Override
    public void setIndex(int index, BCAnimationToken token) {
        if (token == null) {
            throw new IllegalStateException("Invalid token.");
        }
        this.index = index;
    }

    @Override
    public int getDuration() {
        return this.tickDuration;
    }
}
