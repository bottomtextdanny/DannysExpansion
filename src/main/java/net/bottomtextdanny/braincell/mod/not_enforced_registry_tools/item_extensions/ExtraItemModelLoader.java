package net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.item_extensions;

import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;

public interface ExtraItemModelLoader extends IForgeRegistryEntry<Item> {

    @OnlyIn(Dist.CLIENT)
    void bake(ModelRegistryEvent event);
}
