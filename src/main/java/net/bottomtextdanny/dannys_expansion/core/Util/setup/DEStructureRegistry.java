package net.bottomtextdanny.dannys_expansion.core.Util.setup;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.dannys_expansion.core.Registries.DannyStructures;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraftforge.event.RegistryEvent;

import java.util.List;
import java.util.Map;

public class DEStructureRegistry<FC extends FeatureConfiguration> {
    private final Map<FC, ConfiguredStructureFeature<FC, ?>> configuredMap = Maps.newHashMap();
    private final List<StructurePieceType> pieceIndexedList;
    private final StructureFeature<FC> structure;
    public ResourceLocation name;

    private DEStructureRegistry(ResourceLocation name, StructureFeature<FC> structure, StructurePieceType[] types) {
        this.pieceIndexedList = Lists.newArrayList(types);
        this.structure = structure;
        this.name = name;
    }

    public StructurePieceType getPieceByIndex(int index) {
        return this.pieceIndexedList.get(index);
    }

    public ConfiguredStructureFeature<FC, ?> config(FC configuration) {
        if (!this.configuredMap.containsKey(configuration)) {
            this.configuredMap.put(configuration, this.structure.configured(configuration));
        }
        return this.configuredMap.get(configuration);
    }

    public void customRegistry(RegistryEvent.Register<StructureFeature<?>> event) {
        StructureRegistryData data = Builder.REGISTRY_DATA.get(this);
        NoiseGeneratorSettings dimemsionSettings = DannyStructures.DIMENSION_CONFIG_MAP.get(data.noiseSettings);

        if (dimemsionSettings == null) {
            dimemsionSettings = NoiseGeneratorSettings.bootstrap();
        }

        dimemsionSettings.structureSettings().structureConfig().put(this.structure, data.featureConfig);

        event.getRegistry().register(this.structure);

        StructureFeature.STRUCTURES_REGISTRY.put(this.structure.getRegistryName().toString(), this.structure);
    }

    public static class Builder<S extends FeatureConfiguration> {
        public static final Map<DEStructureRegistry<?>, StructureRegistryData> REGISTRY_DATA = Maps.newHashMap();
        private DimensionEnum noiseSettings = DimensionEnum.OVERWORLD;
        private int minimumSpacing = 2;
        private int maximumSpacing = 4;
        private int generationSeed;
        private final StructureFeature<S> feature;
        private final ResourceLocation key;
        private StructurePieceType[] pieces;

        private Builder(ResourceLocation key, StructureFeature<S> feature) {
            this.key = key;
            this.feature = feature;
        }

        public static <S1 extends FeatureConfiguration> Builder<S1> start(String name, StructureFeature<S1> structure) {
            return new Builder<>(new ResourceLocation(DannysExpansion.ID, name), structure);
        }

        public Builder<S> spacing(int minimum, int maximum) {
            this.minimumSpacing = minimum;
            this.maximumSpacing = maximum;
            return this;
        }

        public Builder<S> seed(int generationSeed) {
            this.generationSeed = generationSeed;
            return this;
        }

        public Builder<S> noiseSettings(DimensionEnum noiseSettings) {
            this.noiseSettings = noiseSettings;
            return this;
        }

        public Builder<S> deferPieces(StructurePieceType.ContextlessType... pieces) {
            this.pieces = pieces;
            return this;
        }

        public DEStructureRegistry<S> build() {
            DEStructureRegistry<S> reg = new DEStructureRegistry<S>(this.key, this.feature, this.pieces);
            this.feature.setRegistryName(this.key);
            REGISTRY_DATA.put(reg, new StructureRegistryData(new StructureFeatureConfiguration(this.maximumSpacing, this.minimumSpacing, this.generationSeed), this.noiseSettings, this.pieces));
            StructureRegistryData data = REGISTRY_DATA.get(reg);
            for (int i = 0; i < data.pieces.length; i++) {
                Registry.register(Registry.STRUCTURE_PIECE, this.key, data.pieces[i]);
            }
            return reg;
        }
    }

    public record StructureRegistryData(StructureFeatureConfiguration featureConfig, DimensionEnum noiseSettings, StructurePieceType[] pieces) {}
}