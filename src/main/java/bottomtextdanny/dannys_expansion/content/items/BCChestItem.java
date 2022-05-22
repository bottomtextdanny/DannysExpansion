package bottomtextdanny.dannys_expansion.content.items;

import bottomtextdanny.dannys_expansion.content._client.rendering.ister.DEChestISR;
import bottomtextdanny.braincell.mod.world.builtin_blocks.BCChestBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.client.IItemRenderProperties;

import java.util.function.Consumer;

public class BCChestItem extends BlockItem {

    public BCChestItem(BCChestBlock blockIn, Properties properties) {
        super(blockIn, properties);
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(DEChestISR.AS_PROPERTY);
    }
}
