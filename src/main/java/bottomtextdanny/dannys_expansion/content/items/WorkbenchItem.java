package bottomtextdanny.dannys_expansion.content.items;

import bottomtextdanny.dannys_expansion.content._client.model.block_entities.WorkbenchModel;
import bottomtextdanny.dannys_expansion.tables.DEBlocks;
import bottomtextdanny.dannys_expansion.tables._client.DERenderProperties;
import bottomtextdanny.dannys_expansion.content._client.rendering.block_entity.WorkbenchTileEntityRenderer;
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
        return WorkbenchTileEntityRenderer.TEXTURES;
    }
}
