package net.bottomtextdanny.dannys_expansion.mixin;

//import net.bottomtextdanny.dannys_expansion.common.Level.structures.util.DELayers;
//import net.minecraft.core.Registry;
//import net.minecraft.world.level.biome.Biome;
//import net.minecraft.world.level.biome.BiomeSource;
//import net.minecraft.world.level.biome.TheEndBiomeSource;
//import net.minecraft.world.level.levelgen.synth.SimplexNoise;
//import net.minecraft.world.level.newbiome.area.LazyArea;
//import net.minecraft.world.level.newbiome.context.LazyAreaContext;
//import org.spongepowered.asm.mixin.Final;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//
//import java.util.List;


import net.bottomtextdanny.dannys_expansion.common.Level.end_biome_generation.EndBiomeChooser;
import net.minecraft.core.Registry;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.TheEndBiomeSource;
import net.minecraft.world.level.levelgen.synth.SimplexNoise;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = TheEndBiomeSource.class, priority = 99)
public abstract class EndBiomeProviderMixinsd extends BiomeSource {
	@Shadow @Final private Registry<Biome> biomes;
	@Shadow @Final private Biome end;
	@Shadow @Final private Biome highlands;
	@Shadow @Final private Biome midlands;
	@Shadow @Final private Biome islands;
	@Shadow @Final private Biome barrens;
	@Shadow @Final private SimplexNoise islandNoise;
	
	protected EndBiomeProviderMixinsd(List<Biome> biomes) {
		super(biomes);
	}
//
//	@Inject(at = @At("RETURN"), method = "<init>")
//	private void init(Registry<Biome> lookupRegistry, long seed, CallbackInfo ci) {
//		this.noiseMapper = DELayers.createEndBiomeLayer(lookupRegistry, (seedModifier) -> new LazyAreaContext(25, seed, seedModifier));
//	}
//
	@Inject(at = @At("HEAD"), method = "getNoiseBiome", cancellable = true, remap = false)
	private void DE_putBiomes(int x, int y, int z, Climate.Sampler climate, CallbackInfoReturnable<Biome> cir) {
		int i = x >> 1;
		int j = z >> 1;
		if ((long)i * (long)i + (long)j * (long)j > 4096L && EndBiomeChooser.chooseHeightForBlock(this.islandNoise, x, z) >= 0.0F) {
			Biome biome = EndBiomeChooser.getBiomeForCoord(x, y, z, this.biomes);
			if (biome != null) {
				cir.setReturnValue(biome);
			}
		}
	}
//
//	private Biome DE_getNoiseBio(int x, int z) {
//		return biomes.byId(this.noiseMapper.get(x, z));
//	}
}
