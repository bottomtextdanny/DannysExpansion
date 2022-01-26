package net.bottomtextdanny.danny_expannny.objects.blocks;

import net.bottomtextdanny.braincell.mod.base.util.Connection;
import net.bottomtextdanny.braincell.mod.world.block_utilities.AmbientWeightProvider;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.danny_expannny.rendering.ambiances.DEAmbiance;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;
import java.util.function.Supplier;

public class AnchorBlock extends Block implements AmbientWeightProvider {
	public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
	@OnlyIn(Dist.CLIENT)
	private Supplier<?> ambiance;
	
	public AnchorBlock(Properties properties, Supplier<?> ambiance) {
		super(properties.lightLevel(state -> state.getValue(ACTIVE) ? 10 : 0));
		Connection.doClientSide(() -> {
			this.ambiance = ambiance;
		});

		this.registerDefaultState(this.stateDefinition.any().setValue(ACTIVE, false));
	}

	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		state = state.setValue(ACTIVE, !state.getValue(ACTIVE));
		worldIn.setBlock(pos, state, 10);
		worldIn.playSound(null, pos, state.getValue(ACTIVE) ? DESounds.BS_UPHOLDER_ON.get() : DESounds.BS_UPHOLDER_OFF.get(), SoundSource.BLOCKS, 0.6F, 1.0F);
		
		//worldIn.playSound(null, state.get(ACTIVE) ? DannySounds.BS_UPHOLDER_ON : DannySounds.BS_UPHOLDER_OFF, pos, 0);
		return InteractionResult.sidedSuccess(worldIn.isClientSide);
	}
	
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(ACTIVE);
	}
	
	public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, Random rand) {
		if (state.getValue(ACTIVE)) {
			worldIn.setBlock(pos, state.cycle(ACTIVE), 2);
		}
		
	}
	
	@Override
	public boolean triggerEvent(BlockState state, Level worldIn, BlockPos pos, int id, int param) {
		return super.triggerEvent(state, worldIn, pos, id, param);
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public DEAmbiance ambiance() {
		return (DEAmbiance)this.ambiance.get();
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public int weightOnAmbiance(BlockState state, BlockPos pos, Level world) {
		return state.getValue(ACTIVE) ? 99999 : 0;
	}
}
