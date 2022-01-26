package net.bottomtextdanny.braincell.mod.entity.modules.animatable;

import net.bottomtextdanny.braincell.mod.entity.modules.ModuleProvider;
import net.minecraft.world.entity.Entity;

public interface BaseAnimatableProvider<B extends BaseAnimatableModule<?>> extends ModuleProvider {

    B animatableModule();

    default boolean operateAnimatableModule() {
        return animatableModule() != null;
    }

    default <A extends IAnimation> A addAnimation(A animation) {
        if (!operateAnimatableModule()) throw new UnsupportedOperationException("Tried to setup an animation on deactivated AnimationModule, entity:" + ((Entity)this).getType().getRegistryName().toString());

        animation.setIndex(animatableModule().animationList().size());
        animatableModule().animationList().add(animation);
        return animation;
    }

    default <A extends AnimationHandler<?>> A addAnimationHandler(A module) {
        if (!operateAnimatableModule()) throw new UnsupportedOperationException("Tried to setup an animation handler on deactivated AnimatableModule, entity:" + ((Entity)this).getType().getRegistryName().toString());
        else {
            
            module.setIndex(animatableModule().animationHandlerList().size());
            animatableModule().animationHandlerList().add(module);
        }
        return module;
    }

    default void animationEndCallout(AnimationHandler<?> module, IAnimation animation) {}
}
