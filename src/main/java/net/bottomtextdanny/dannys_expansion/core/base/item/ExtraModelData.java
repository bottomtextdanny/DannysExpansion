package net.bottomtextdanny.dannys_expansion.core.base.item;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;

public record ExtraModelData(int index, ModelResourceLocation location) {

    public static ExtraModelData of(Enum<?> entry, ModelResourceLocation location) {
        return new ExtraModelData(entry.ordinal(), location);
    }

    public static ExtraModelData of(int index, ModelResourceLocation location) {
        return new ExtraModelData(index, location);
    }
}
