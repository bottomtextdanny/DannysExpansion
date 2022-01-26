package net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util;

public class DELayers {
//	public static <R extends BigContext<LazyArea>> LazyArea createEndBiomeLayer(Registry<Biome> lookupRegistry, LongFunction<R> contextFactory) {
//		AreaFactory<LazyArea> biomesFactory = new EndBiomesLayer(lookupRegistry).run(contextFactory.apply(100L));
//
//		for (int i = 0; i < 5; i++) {
//			biomesFactory = ZoomLayer.NORMAL.run(contextFactory.apply(1000L + (long) i), biomesFactory);
//		}
//
//		return biomesFactory.make();
//	}
//
//	static class EndBiomesLayer implements AreaTr {
//		private final Registry<Biome> lookupRegistry;
//		private final SplittableRandom mapper = DEUtil.SS_RANDOM.split();
//
//		EndBiomesLayer(Registry<Biome> lookupRegistry) {
//			this.lookupRegistry = lookupRegistry;
//		}
//
//		@Override
//		public int applyPixel(Context random, int x, int z) {
//			if (mapper.nextInt(7) != 2) return -1;
//			return this.lookupRegistry.getId(DEBiomes.getEndBiomes().map(random.nextRandom(DEBiomes.getEndBiomes().accessMapSize)).getBiome(lookupRegistry));
//		}
//	}
}
