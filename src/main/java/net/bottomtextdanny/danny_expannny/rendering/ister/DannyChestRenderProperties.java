package net.bottomtextdanny.danny_expannny.rendering.ister;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraftforge.client.IItemRenderProperties;

public class DannyChestRenderProperties implements IItemRenderProperties {

    @Override
    public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
        return new DEChestISR();
    }
}