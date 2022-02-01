package net.bottomtextdanny.braincell.mod.entity.modules.animatable;

import com.google.common.collect.Lists;
import net.bottomtextdanny.braincell.mod.entity.modules.EntityModule;
import net.minecraft.world.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAnimatableModule<P extends BaseAnimatableProvider<?>> extends EntityModule<Entity, P> {
    private final AnimationGetter animationsIndexed;
    private final ArrayList<AnimationHandler<?>> animationHandlersIndexed;

    public BaseAnimatableModule(Entity entity, AnimationGetter manager) {
        super(entity);
        this.animationsIndexed = manager;
        this.animationHandlersIndexed = Lists.newArrayList();
    }

    public void tick() {
        if (!this.entity.isRemoved()) {
            animationHandlerList().forEach(handler -> {
                Animation<?> animation = handler.getAnimation();

                if (animation != Animation.NULL) {
                    if (animation.goal(handler.getTick(), handler)) {
                        this.provider.animationEndCallout(handler, animation);
                        handler.deactivate();
                    } else {
                        handler.update(animation.progressTick(handler.getTick(), handler));
                    }
                }
            });
        }
    }

    public AnimationGetter animationManager() {
        return this.animationsIndexed;
    }

    public List<AnimationHandler<?>> animationHandlerList() {
        return this.animationHandlersIndexed;
    }
}
