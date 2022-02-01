package net.bottomtextdanny.braincell.mod.entity.modules.animatable;

import java.util.Collections;

public final class AnimationManager implements AnimationGetter {
    private static final BCAnimationToken TOKEN = new BCAnimationToken();
    private final Animation<?>[] animations;

    public AnimationManager(Animation<?>... animations) {
        super();
        this.animations = animations;
        for (int i = 0; i < animations.length; i++) {
            animations[i].setIndex(i, TOKEN);
        }
    }

    public static AnimationManager marge(AnimationManager manager, Animation<?>... animations) {
        Animation<?>[] merge = new Animation<?>[manager.animations.length + animations.length];
        System.arraycopy(manager.animations, 0, merge, 0, manager.animations.length);
        System.arraycopy(animations, 0, merge, manager.animations.length, animations.length);
        return new AnimationManager(merge);
    }

    public Animation<?> getAnimation(int index) {
        return this.animations[index];
    }

    public int size() {
        return this.animations.length;
    }
}
