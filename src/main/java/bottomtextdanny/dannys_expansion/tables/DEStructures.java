package bottomtextdanny.dannys_expansion.tables;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content.structures.anglerstreasure.AnglersTreasurePiece;
import bottomtextdanny.dannys_expansion.content.structures.anglerstreasure.AnglersTreasureStructure;
import bottomtextdanny.braincell.mod._base.registry.BCStructureRegistry;
import bottomtextdanny.braincell.mod._base.registry.managing.BCRegistry;
import bottomtextdanny.braincell.mod._base.registry.managing.RegistryHelper;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static bottomtextdanny.dannys_expansion.DannysExpansion.id;

@Mod.EventBusSubscriber(modid = DannysExpansion.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DEStructures {
    public static final BCRegistry<StructureFeature<?>> ENTRIES = new BCRegistry<>(false);
    public static final RegistryHelper<StructureFeature<?>> HELPER = new RegistryHelper<>(DannysExpansion.DE_REGISTRY_MANAGER, ENTRIES);


    public static final BCStructureRegistry<AnglersTreasureStructure> ANGLERS_TREASURE = HELPER.deferWrap(BCStructureRegistry.builder(id("anglers_treasure"),
                    AnglersTreasureStructure::new,
                    GenerationStep.Decoration.SURFACE_STRUCTURES)
            .piece("DE_ang", AnglersTreasurePiece::new)
            .configured(id("anglers_treasure"), s -> s.get().configured(NoneFeatureConfiguration.NONE, BiomeTags.HAS_OCEAN_MONUMENT))
            .set(id("anglers_treasure"), r -> new StructureSet(
                    List.of(StructureSet.entry(r.getConfigured(0))),
                    new RandomSpreadStructurePlacement(
                            12,
                            3,
                            RandomSpreadType.LINEAR,
                            847892794)
                    )
            ).build());

    public static void loadClass() {}
}
