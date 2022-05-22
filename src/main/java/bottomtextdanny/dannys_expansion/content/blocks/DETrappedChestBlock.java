package bottomtextdanny.dannys_expansion.content.blocks;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content.block_entities.DETrappedChestBlockEntity;
import bottomtextdanny.braincell.mod.world.builtin_block_entities.BCChestBlockEntity;
import bottomtextdanny.braincell.mod.world.builtin_blocks.BCChestBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class DETrappedChestBlock extends BCChestBlock {

    public DETrappedChestBlock(Properties builder, Supplier<BlockEntityType<? extends ChestBlockEntity>> tileEntityTypeIn) {
        super(builder, tileEntityTypeIn);
    }

    public DETrappedChestBlock(Properties builder, Supplier<BlockEntityType<? extends ChestBlockEntity>> tileEntityTypeIn, SoundEvent openSound, SoundEvent closeSound) {
        super(builder, tileEntityTypeIn, openSound, closeSound);
    }


    @Override
    public BCChestBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DETrappedChestBlockEntity(pos, state);
    }

    protected Stat<ResourceLocation> getOpenChestStat() {
        return Stats.CUSTOM.get(Stats.TRIGGER_TRAPPED_CHEST);
    }

    public boolean isSignalSource(BlockState state) {

        return true;
    }

    public int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
        return Mth.clamp(ChestBlockEntity.getOpenCount(blockAccess, pos), 0, 15);
    }

    public int getDirectSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
        return side == Direction.UP ? blockState.getSignal(blockAccess, pos, side) : 0;
    }

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
