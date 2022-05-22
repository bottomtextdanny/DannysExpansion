package bottomtextdanny.dannys_expansion.content.items.bow;

import bottomtextdanny.braincell.mod._base.registry.item_extensions.ExtraItemModelLoader;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ForgeModelBakery;

public interface IBigBowModelLoader extends ExtraItemModelLoader {

    @Override
    default void bake(ModelRegistryEvent event) {
        if (getRegistryName() != null) {
            String path = getRegistryName().getPath();
            ForgeModelBakery.addSpecialModel(new ModelResourceLocation("dannys_expansion:" + path + "_handheld", "inventory"));
            ForgeModelBakery.addSpecialModel(new ModelResourceLocation("dannys_expansion:" + path + "_handheld_pulling_0", "inventory"));
            ForgeModelBakery.addSpecialModel(new ModelResourceLocation("dannys_expansion:" + path + "_handheld_pulling_1", "inventory"));
            ForgeModelBakery.addSpecialModel(new ModelResourceLocation("dannys_expansion:" + path + "_handheld_pulling_2", "inventory"));
	        ForgeModelBakery.addSpecialModel(new ModelResourceLocation("dannys_expansion:" + path + "_gui", "inventory"));
	        ForgeModelBakery.addSpecialModel(new ModelResourceLocation("dannys_expansion:" + path + "_gui_pulling_0", "inventory"));
	        ForgeModelBakery.addSpecialModel(new ModelResourceLocation("dannys_expansion:" + path + "_gui_pulling_1", "inventory"));
	        ForgeModelBakery.addSpecialModel(new ModelResourceLocation("dannys_expansion:" + path + "_gui_pulling_2", "inventory"));
        }
    }
}
