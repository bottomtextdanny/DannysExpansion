package net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations;

import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationData;

import java.util.function.Supplier;

public class SimpleAnimation extends DEAnimation<AnimationData> {

    public SimpleAnimation(int duration) {
        super(duration);
    }

    @Override
    public Supplier<AnimationData> dataForPlay() {
        return () -> AnimationData.NULL;
    }
}
