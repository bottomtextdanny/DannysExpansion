package net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.block_extensions;

import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.BCRegistry;
import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.DeferrorType;
import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.ModDeferringManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public interface ExtraPottedPlantRegisterer<T extends Block, U extends FlowerPotBlock> extends ExtraBlockRegisterer<T> {

	@Override
	default void registerExtra(T object, ModDeferringManager solving) {
		Objects.requireNonNull(object);
		Optional<BCRegistry<Block>> registryOp = solving.getRegistryDeferror(DeferrorType.BLOCK);
		registryOp.ifPresent(registry -> {
			U newPotBlock = pottedFactory(object).get();
			newPotBlock.setRegistryName(new ResourceLocation(solving.getModID(), "potted_" + object.getRegistryName().getPath()));
			solving.doHooksForObject(DeferrorType.BLOCK, newPotBlock);
			registry.addDeferredRegistry(() -> newPotBlock);
			decPotted(newPotBlock);
		});
	}
    
    void decPotted(U value);

    default Supplier<U> pottedFactory(T base) {
    	return () -> (U) new FlowerPotBlock(base, BlockBehaviour.Properties.of(Material.DECORATION).instabreak().noOcclusion());
    }
	
	@Override
	default boolean executeSideRegistry() {
		return true;
	}
	
	U potted();
}
