package net.bottomtextdanny.braincell.mod.minecraft_front_rendering;

import net.bottomtextdanny.braincell.mod.world.builtin_block_entities.BCChestBlockEntity;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.chest.BCBaseLeftChestModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.chest.BCBaseRightChestModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.chest.BCBaseSingleChestModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.LidBlockEntity;

public class BCSimpleChestRenderer<T extends BCChestBlockEntity & LidBlockEntity> extends BCBaseChestRenderer<T> {

    public BCSimpleChestRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
        super(rendererDispatcherIn, new BCBaseSingleChestModel(), new BCBaseLeftChestModel(), new BCBaseRightChestModel());
    }
}
