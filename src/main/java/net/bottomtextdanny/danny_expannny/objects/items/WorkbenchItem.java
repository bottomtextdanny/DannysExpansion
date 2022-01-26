package net.bottomtextdanny.danny_expannny.objects.items;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.object_tables.DEBlocks;
import net.bottomtextdanny.danny_expannny.rendering.DERenderProperties;
import net.bottomtextdanny.danny_expannny.vertex_models.guns.WorkbenchModel;
import net.bottomtextdanny.dannys_expansion.core.interfaces.ISpecialModel;
import net.minecraft.client.model.Model;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;

import java.util.function.Consumer;

public class WorkbenchItem extends BlockItem implements ISpecialModel {

    public WorkbenchItem(Properties builder) {
        super(DEBlocks.WORKBENCH.get(), builder);
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(DERenderProperties.SPECIAL_MODELS);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public Model model() {
        return new WorkbenchModel();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ResourceLocation modelTexture() {
        return new ResourceLocation(DannysExpansion.ID, "textures/models/item/workbench.png");
    }
}
