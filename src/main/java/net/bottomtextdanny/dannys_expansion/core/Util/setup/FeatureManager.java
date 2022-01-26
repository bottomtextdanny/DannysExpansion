package net.bottomtextdanny.dannys_expansion.core.Util.setup;

import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.Wrap;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.function.Supplier;

public abstract class FeatureManager<FC extends FeatureConfiguration> extends Wrap<Feature<FC>> {
	public static final UnsupportedOperationException CALL_WHEN_NOT_DEFERRED_EX = new UnsupportedOperationException("Cannot call any dependant object because this wrapper is not deferred.");
	public static final UnsupportedOperationException PRE_SOLVING_CALL_EX = new UnsupportedOperationException("Cannot call any dependant object before mod loading.");

	protected FeatureManager(ResourceLocation name, Supplier<Feature<FC>> feature) {
		super(name, feature);
	}
	
	@Override
	public void solve() {
		super.solve();
	}
	
	public Feature<FC> getFeature() {
		return this.obj;
	}

	protected ConfiguredFeature<FC, ?> makeConfiguration(ResourceLocation key, FC featureConfiguration) {
		ConfiguredFeature<FC, ?> configuredFeature = this.obj.configured(featureConfiguration);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, key, configuredFeature);
		return configuredFeature;
	}

	protected PlacedFeature makePlacement(ResourceLocation key, ConfiguredFeature<FC, ?> configuredFeature, PlacementModifier... modifiers) {
		PlacedFeature placedFeature = configuredFeature.placed(modifiers);
		Registry.register(BuiltinRegistries.PLACED_FEATURE, key, placedFeature);
		return placedFeature;
	}

	protected final void checkSolvingState() {
		if (getModSolvingState() == null) {
			throw CALL_WHEN_NOT_DEFERRED_EX;
		} else if (!getModSolvingState().isOpen()) {
			throw PRE_SOLVING_CALL_EX;
		}
	}
}
