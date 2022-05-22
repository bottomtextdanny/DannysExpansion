package bottomtextdanny.dannys_expansion.content.blocks;

import bottomtextdanny.dannys_expansion.tables.DEBlockProperties;
import bottomtextdanny.dannys_expansion.tables.DEBlocks;
import bottomtextdanny.dannys_expansion.content.block_entities.WorkbenchBlockEntity;
import bottomtextdanny.dannys_expansion.content.block_properties.WorkbenchPart;
import bottomtextdanny.dannys_expansion.content.containers.lazy_workstation.WorkbenchMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class WorkbenchBlock extends Block implements EntityBlock, SimpleWaterloggedBlock {
    private static final VoxelShape SHAPE = Shapes.box(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<WorkbenchPart> PART = DEBlockProperties.WORKBENCH_PART;

    public WorkbenchBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(PART, WorkbenchPart.MAIN).setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.FALSE));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PART, FACING, WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new WorkbenchBlockEntity(p_153215_, p_153216_);
    }

    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (worldIn.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            player.openMenu(state.getMenuProvider(worldIn, pos));
            return InteractionResult.CONSUME;
        }
    }

    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
        return new SimpleMenuProvider((id, inventory, player) -> {
            return new WorkbenchMenu(id, inventory);
        }, new TranslatableComponent("container.workbench"));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
        state.setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);

        if (world.getBlockState(pos.relative(state.getValue(FACING).getClockWise())).canBeReplaced(context)) {
            return state;
        }

        state = this.defaultBlockState().setValue(FACING, context.getHorizontalDirection()).setValue(PART, WorkbenchPart.OFF);

        if (world.getBlockState(pos.relative(state.getValue(FACING).getCounterClockWise())).canBeReplaced(context)) {
            return state;
        }

        return null;
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (state.getValue(PART) == WorkbenchPart.MAIN) {
            worldIn.setBlock(pos.relative(state.getValue(FACING).getClockWise()), state.setValue(PART, WorkbenchPart.OFF), 3);
        } else {
            worldIn.setBlock(pos.relative(state.getValue(FACING).getCounterClockWise()), state.setValue(PART, WorkbenchPart.MAIN), 3);
        }
    }


    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }

        if (stateIn.getValue(PART) == WorkbenchPart.MAIN) {
            if (worldIn.getBlockState(currentPos.relative(stateIn.getValue(FACING).getClockWise())).getBlock() != DEBlocks.WORKBENCH.get()) {
                worldIn.destroyBlock(currentPos, false);
            }
        } else {
            if (worldIn.getBlockState(currentPos.relative(stateIn.getValue(FACING).getCounterClockWise())).getBlock() != DEBlocks.WORKBENCH.get()) {
                worldIn.destroyBlock(currentPos, false);
            }
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    public FluidState getFluidState(BlockState blockstate) {
        return blockstate.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockstate);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
