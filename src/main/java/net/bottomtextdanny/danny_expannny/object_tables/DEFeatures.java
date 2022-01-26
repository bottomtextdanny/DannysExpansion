package net.bottomtextdanny.danny_expannny.object_tables;

import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.BCRegistry;
import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.RegistryHelper;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.objects.features.GiantFoamshroomManager;
import net.minecraft.world.level.levelgen.feature.Feature;

public final class DEFeatures {
	public static final BCRegistry<Feature<?>> ENTRIES = new BCRegistry<>(true);
	public static final RegistryHelper<Feature<?>> HELPER = new RegistryHelper<>(DannysExpansion.solvingState, ENTRIES);

	public static final GiantFoamshroomManager GIANT_FOAMSHROOM = HELPER.deferWrap(new GiantFoamshroomManager());

//	public static final DEFeatureRegistry EMOSSENCE_SWARD_PATCH = defer("emossence_sward_patch", new DEFeatureRegistry(new GrassPatchFeature((world) -> new GrassPatchDeco(world, DEUtil.SS_RANDOM.split(), 12, DETags.EMOSSENCE_PLACEABLE_ON.getValues(),
//		ItemWeightAccess.<Block>builder(Block.class)
//			.add(20, DannyBlocks.EMOSSENCE_SWARD)
//			.add(4, DannyBlocks.EMOSSENCE_PLANT)
//			.add(4, DannyBlocks.TALL_EMOSSENCE_SWARD)
//			.add(1, DannyBlocks.FOAMSHROOM)
//			.build())))
//		.chance(1)
//		.placement(Feature.HEIGH.count(6))
//		.config(NoneFeatureConfiguration.INSTANCE)
//	);
//
//	public static final DEFeatureRegistry EMOSSENCE_SWARD_SINGLE = defer("emossence_sward_single", new DEFeatureRegistry(new SingleGrassFeature((world) -> new GrassSingleDeco(world, DEUtil.SS_RANDOM.split(), 12, DETags.EMOSSENCE_PLACEABLE_ON.getValues(),
//		ItemWeightAccess.<Block>builder(Block.class)
//			.add(20, DannyBlocks.EMOSSENCE_SWARD)
//			.add(4, DannyBlocks.EMOSSENCE_PLANT)
//			.add(4, DannyBlocks.TALL_EMOSSENCE_SWARD)
//			.add(1, DannyBlocks.FOAMSHROOM)
//			.build())))
//		.countRandom(4)
//		.placement(Features.Decorators.TOP_SOLID_HEIGHTMAP_SQUARE.count(12))
//		.config(NoneFeatureConfiguration.INSTANCE)
//	);
}
