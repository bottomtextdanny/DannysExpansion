package net.bottomtextdanny.danny_expannny.objects.blocks;

import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.world.builtin_block_entities.BCChestBlockEntity;
import net.bottomtextdanny.braincell.mod.world.builtin_blocks.BCChestBlock;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.objects.block_entities.DEChestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public class DEChestBlock extends BCChestBlock implements DETrappedChestRegisterer {
    private DETrappedChestBlock trapped;

    public DEChestBlock(Properties builder, Supplier<BlockEntityType<? extends ChestBlockEntity>> tileEntityTypeIn, SoundEvent openSound, SoundEvent closeSound) {
        super(builder, tileEntityTypeIn, openSound, closeSound);
    }

    public DEChestBlock(Properties builder, Supplier<BlockEntityType<? extends ChestBlockEntity>> tileEntityTypeIn) {
        super(builder, tileEntityTypeIn);
    }

    public DEChestBlock overrideRecipe(ResourceLocation materialID) {
        Braincell.common().getChestOverriderManager().put(materialID, () -> new ItemStack(this));
        return this;
    }

    @Override
    public BCChestBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DEChestBlockEntity(pos, state);
    }

    @Override
    public void setTrapped(DETrappedChestBlock newTrapped) {
        this.trapped = newTrapped;
    }

    @Override
    public DETrappedChestBlock trapped() {
        return this.trapped;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ResourceLocation[] chestTexturePaths() {
        String name = getRegistryName().getPath();
        return new ResourceLocation[] {
                new ResourceLocation(DannysExpansion.ID, "entity/chest/" + name),
                new ResourceLocation(DannysExpansion.ID, "entity/chest/" + name + "_left"),
                new ResourceLocation(DannysExpansion.ID, "entity/chest/" + name + "_right")
        };
    }
}
