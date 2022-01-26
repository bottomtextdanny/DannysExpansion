package net.bottomtextdanny.dannys_expansion.core.Registries;

import com.google.common.collect.Lists;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.objects.world_gen.structures.anglerstreasure.AnglersTreasurePiece;
import net.bottomtextdanny.danny_expannny.objects.world_gen.structures.anglerstreasure.AnglersTreasureStructure;
import net.bottomtextdanny.dannys_expansion.core.Util.setup.DEStructureRegistry;
import net.bottomtextdanny.dannys_expansion.core.Util.setup.DimensionEnum;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.fml.common.Mod;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.Map;

@Mod.EventBusSubscriber(modid = DannysExpansion.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DannyStructures {
    public static final Map<DimensionEnum, NoiseGeneratorSettings> DIMENSION_CONFIG_MAP = new EnumMap<>(DimensionEnum.class);
    public static final LinkedList<DEStructureRegistry<?>> REGISTRY_LIST = Lists.newLinkedList();

    public static final DEStructureRegistry<NoneFeatureConfiguration> ANGLERS_TREASURE = defer(DEStructureRegistry.Builder.start("anglers_treasure", new AnglersTreasureStructure(NoneFeatureConfiguration.CODEC))
            .seed(83471234)
            .spacing(17, 28)
            .noiseSettings(DimensionEnum.OVERWORLD)
            .deferPieces(AnglersTreasurePiece::new)
    );
//
//    public static final DEStructureRegister<CaveTreasureStructure> CAVE_TREASURE =
//            start("cave_treasure", new CaveTreasureStructure(NoneFeatureConfiguration.CODEC), CaveTreasurePiece::new)
//                    .spacing(17, 28).seed(46434644);
//
//    public static final DEStructureRegister<KlifourSpawnStructure> KLIFOUR_CLUSTER =
//            start("klifour_cluster", new KlifourSpawnStructure(NoneFeatureConfiguration.CODEC), KlifourSpawnPiece::new)
//                    .spacing(5, 8).seed(92574644);
//
//    public static final DEStructureRegister<EnderBeastRegionStructure> ENDER_BEAST_REGION =
//            start("ender_beast_region", new EnderBeastRegionStructure(NoneFeatureConfiguration.CODEC), EnderBeastRegionPiece::new)
//	            .settingsOf(DimensionEnum.END).spacing(5, 8).seed(66592924);
//
//    public static final DEStructureRegister<GiantEmossencePatchStructure> GIANT_EMOSSENCE_PATCH =
//            start("giant_emossence_patch", new GiantEmossencePatchStructure(NoneFeatureConfiguration.CODEC), GiantEmossencePatchPiece::new)
//                    .settingsOf(DimensionEnum.END).spacing(5, 8).seed(83572636);
//


    public static <T extends FeatureConfiguration> DEStructureRegistry<T> defer(DEStructureRegistry.Builder<T> builder) {
        DEStructureRegistry<T> reg = builder.build();
        REGISTRY_LIST.add(reg);
        return reg;
    }

    public static void loadClass() {
    }
}
