package net.bottomtextdanny.braincell.mod.entity.modules.animatable;

import net.bottomtextdanny.braincell.mod.entity.modules.ModuleProvider;
import net.minecraft.world.entity.Entity;

public interface BaseAnimatableProvider<B extends BaseAnimatableModule<?>> extends ModuleProvider {

    B animatableModule();

    default boolean operateAnimatableModule() {
        return animatableModule() != null && animatableModule().animationManager() != null;
    }

    default <A extends AnimationHandler<?>> A addAnimationHandler(A module) {
        if (!operateAnimatableModule()) throw new UnsupportedOperationException("Tried to setup an animation handler on deactivated AnimatableModule, entity:" + ((Entity)this).getType().getRegistryName().toString());
        else {
            module.setIndex(animatableModule().animationHandlerList().size(), new BCAnimationToken());
            animatableModule().animationHandlerList().add(module);
        }
        return module;
    }

    default void animationEndCallout(AnimationHandler<?> module, Animation animation) {}
}
