package net.bottomtextdanny.braincell.mod.entity.modules.animatable;

import com.google.common.collect.Lists;
import net.bottomtextdanny.braincell.mod.entity.modules.EntityModule;
import net.minecraft.world.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAnimatableModule<P extends BaseAnimatableProvider<?>> extends EntityModule<Entity, P> {
    private final ArrayList<IAnimation> animationsIndexed;
    private final ArrayList<AnimationHandler<?>> animationHandlersIndexed;

    public BaseAnimatableModule(Entity entity) {
        super(entity);
        this.animationsIndexed = Lists.newArrayList();
        this.animationHandlersIndexed = Lists.newArrayList();
    }

    public void tick() {
        if (!this.entity.isRemoved()) {
            animationHandlerList().forEach(handler -> {
                IAnimation animation = handler.get();

                if (!animation.isNull()) {
                    if (animation.goal(handler.getTick())) {
                        this.provider.animationEndCallout(handler, animation);
                        handler.trySleep();
                    } else {
                        handler.update(animation.progressTick(handler.getTick()));
                    }
                }
            });
        }
    }

    public List<IAnimation> animationList() {
        return this.animationsIndexed;
    }

    public List<AnimationHandler<?>> animationHandlerList() {
        return this.animationHandlersIndexed;
    }
}
