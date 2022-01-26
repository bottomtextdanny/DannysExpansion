package net.bottomtextdanny.danny_expannny.objects.items;

import net.bottomtextdanny.braincell.mod.world.builtin_blocks.BCChestBlock;
import net.bottomtextdanny.danny_expannny.rendering.DERenderProperties;
import net.bottomtextdanny.danny_expannny.rendering.ister.DEChestISR;
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
