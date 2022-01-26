package net.bottomtextdanny.dannys_expansion.core.interfaces.item;

import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.item_extensions.ExtraItemModelLoader;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ForgeModelBakery;

public interface IBigItemModelLoader extends ExtraItemModelLoader {

    @Override
    default void bake(ModelRegistryEvent event) {
        if (getRegistryName() != null) {
            String path = getRegistryName().getPath();
            ForgeModelBakery.addSpecialModel(new ModelResourceLocation("dannys_expansion:" + path + "_gui", "inventory"));
            ForgeModelBakery.addSpecialModel(new ModelResourceLocation("dannys_expansion:" + path + "_handheld", "inventory"));
        }
    }
}
