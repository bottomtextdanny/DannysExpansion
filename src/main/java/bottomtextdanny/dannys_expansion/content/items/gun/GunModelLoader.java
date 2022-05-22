package bottomtextdanny.dannys_expansion.content.items.gun;

import bottomtextdanny.braincell.mod._base.registry.item_extensions.ExtraItemModelLoader;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ForgeModelBakery;

public interface GunModelLoader extends ExtraItemModelLoader {

    @Override
    default void bake(ModelRegistryEvent event) {
        if (getRegistryName() != null) {
	        ForgeModelBakery.addSpecialModel(new ModelResourceLocation("dannys_expansion:" + getRegistryName().getPath() + "_gui", "inventory"));
        }
    }
}
