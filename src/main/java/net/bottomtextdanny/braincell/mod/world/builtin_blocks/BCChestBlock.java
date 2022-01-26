package net.bottomtextdanny.braincell.mod.world.builtin_blocks;

import net.bottomtextdanny.braincell.mod.world.block_utilities.ChestMaterialProvider;
import net.bottomtextdanny.braincell.mod.world.builtin_block_entities.BCChestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public abstract class BCChestBlock extends ChestBlock implements ChestMaterialProvider {
    private final SoundEvent openSound;
    private final SoundEvent closeSound;
    private int materialSlot = -1;
    
    public BCChestBlock(Properties builder, Supplier<BlockEntityType<? extends ChestBlockEntity>> tileEntityTypeIn, SoundEvent openSound, SoundEvent closeSound) {
        super(builder, tileEntityTypeIn);
        this.openSound = openSound;
        this.closeSound = closeSound;
    }

    public BCChestBlock(Properties builder, Supplier<BlockEntityType<? extends ChestBlockEntity>> tileEntityTypeIn) {
        this(builder, tileEntityTypeIn, SoundEvents.CHEST_OPEN, SoundEvents.CHEST_CLOSE);
    }

    @Override
    public abstract BCChestBlockEntity newBlockEntity(BlockPos pos, BlockState state);

    public SoundEvent getOpenSound() {
        return this.openSound;
    }

    public SoundEvent getCloseSound() {
        return this.closeSound;
    }

    @Override
    public int getChestMaterialSlot() {
        return this.materialSlot;
    }

    @Override
    public void setChestMaterialSlot(int newSlot) {
        if (this.materialSlot < 0) this.materialSlot = newSlot;
    }
}
