package net.bottomtextdanny.dannys_expansion.core.Util.setup;

import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.Wrap;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public final class DEBiomeRegistry extends Wrap<Biome> {
	private BiomeDictionary.Type[] catArray;
	private ResourceKey<Biome> biomeKey;
	
	private DEBiomeRegistry(ResourceLocation name, Supplier<Biome> sup, BiomeDictionary.Type[] categories) {
		super(name, sup);
		this.catArray = categories;
	}
	
	public void inferBiomeKey(ResourceKey<Biome> biomeKey) {
		if (this.biomeKey == null) this.biomeKey = biomeKey;
	}
	
	@Override
	public void solve() {
		super.solve();
		ResourceKey<Biome> biomeKey = ResourceKey.create(ForgeRegistries.Keys.BIOMES, this.key);
		inferBiomeKey(biomeKey);
		DannysExpansion.COMMON_SETUP_CALLS.add(() -> {
			ResourceKey<Biome> biomeKey1 = ResourceKey.create(ForgeRegistries.Keys.BIOMES, ForgeRegistries.BIOMES.getKey(this.obj));
			inferBiomeKey(biomeKey1);
            this.obj = ForgeRegistries.BIOMES.getValue(biomeKey1.location());
		});
		BiomeDictionary.addTypes(biomeKey, this.catArray);
        this.catArray = null;
	}
	
	public ResourceKey<Biome> getBiomeKey() {
		return this.biomeKey;
	}
	
	public static class Builder {
		private BiomeDictionary.Type[] catArray;
		private final Supplier<Biome> sup;
		private final ResourceLocation key;
		
		private Builder(ResourceLocation key, Supplier<Biome> sup) {
			this.key = key;
			this.sup = sup;
		}
		
		public static Builder start(String name, Supplier<Biome> sup) {
			return new Builder(new ResourceLocation(DannysExpansion.ID, name), sup);
		}
		
		public Builder putTypes(BiomeDictionary.Type... types) {
            this.catArray = types;
			return this;
		}
		
		public DEBiomeRegistry build() {
			DEBiomeRegistry reg = new DEBiomeRegistry(this.key, this.sup, this.catArray);
			return reg;
		}
	}
}
