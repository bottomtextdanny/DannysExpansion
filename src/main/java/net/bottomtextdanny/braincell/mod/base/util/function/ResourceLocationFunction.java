package net.bottomtextdanny.braincell.mod.base.util.function;

import net.minecraft.resources.ResourceLocation;

@FunctionalInterface
public interface ResourceLocationFunction<T> {

    T apply(ResourceLocation value);
}
