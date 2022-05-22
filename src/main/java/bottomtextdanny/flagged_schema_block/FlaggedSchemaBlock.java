package bottomtextdanny.flagged_schema_block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.GameMasterBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class FlaggedSchemaBlock extends BaseEntityBlock implements GameMasterBlock {

    public FlaggedSchemaBlock(Properties properties) {
        super(properties);
    }

    public BlockEntity newBlockEntity(BlockPos position, BlockState blockState) {
        return new FlaggedSchemaBlockEntity(position, blockState);
    }

    public InteractionResult use(BlockState blockState, Level level,
                                 BlockPos position, Player player,
                                 InteractionHand hand,
                                 BlockHitResult hitResult) {
        BlockEntity blockentity = level.getBlockEntity(position);
        if (blockentity instanceof FlaggedSchemaBlockEntity be) {
            return be.usedBy(player) ?
                    InteractionResult.sidedSuccess(level.isClientSide) :
                    InteractionResult.PASS;
        }
        return InteractionResult.PASS;
    }

    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }
}
