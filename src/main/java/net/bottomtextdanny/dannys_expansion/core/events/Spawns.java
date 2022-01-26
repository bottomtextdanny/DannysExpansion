package net.bottomtextdanny.dannys_expansion.core.events;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.dannys_expansion.core.config.common.CommonConfigurationHandler;
import net.bottomtextdanny.dannys_expansion.core.config.common.MobSpawnConfiguration;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber(modid = DannysExpansion.ID)
public class Spawns {

    @SubscribeEvent(priority =  EventPriority.HIGH)
    public static void addSpawn(BiomeLoadingEvent event) {
        Biome biome = ForgeRegistries.BIOMES.getValue(event.getName());
        List<MobSpawnConfiguration> spawns = CommonConfigurationHandler.Config.spawnList;

        if (biome != null) {
            ResourceKey<Biome> biomeKey = ResourceKey.create(Registry.BIOME_REGISTRY, event.getName());
            CommonConfigurationHandler.Config config = CommonConfigurationHandler.CONFIG;
            Set<BiomeDictionary.Type> biomeDic = BiomeDictionary.getTypes(biomeKey);

//	        event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DannyFeatures.GIANT_FOAMSHROOM.getPlaced());
//	        event.getGeneration().addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, CavePlacements.AMETHYST_GEODE);
//	        event.getGeneration().addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, CavePlacements.DRIPSTONE_CLUSTER);
//
	        if (config.validIDs.get().contains(event.getName().getNamespace())) {

            
//
//                if (biome.getCategory() == Biome.BiomeCategory.DESERT) {
//                    event.getGeneration().withStructure(DannyStructures.DESERT_DUNGEON);
//                }

//                if (biome.getBiomeCategory() == Biome.BiomeCategory.THEEND) {
//                    event.getGeneration().addStructureStart(DannyStructures.ENDER_BEAST_REGION.noConfig());
//                    event.getGeneration().addStructureStart(DannyStructures.GIANT_EMOSSENCE_PATCH.noConfig());
//                }
//
//
//                if (biome.getBiomeCategory() == Biome.BiomeCategory.JUNGLE) {
//                    event.getGeneration().addStructureStart(DannyStructures.KLIFOUR_CLUSTER.noConfig());
//                }

                for (MobSpawnConfiguration spawn : spawns) {
                    modSpawn: {
                        EntityType<?> type = spawn.getEntityType();

                        if (type == null) break modSpawn;

                        for (String str : spawn.blacklistBiomeDictionaries.get()) {
                            List<String> subStr = MobSpawnConfiguration.decodeNode(str);

                            for (String s : subStr) {
                                if (biomeDic.stream().anyMatch(dic -> dic.getName().equals(s))) {
                                    break modSpawn;
                                }
                            }
                        }

                        for (String str : spawn.blacklistBiomes.get()) {
                            List<String> subStr = MobSpawnConfiguration.decodeNode(str);

                            for (String s : subStr) {
                                if (biomeKey.getRegistryName().toString().equals(s)) {
                                    break modSpawn;
                                }
                            }
                        }

                        boolean biomeFlag = spawn.biomes.get()
                                .stream()
                                .anyMatch(o -> o.equals(biomeKey.getRegistryName().toString()));

                        if (biomeFlag && spawn.weight.get() > 0) {
                            event.getSpawns().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(type, spawn.weight.get(), spawn.min.get(), spawn.max.get()));
                        } else if (spawn.biomeDictionaries.get().stream().anyMatch(dicNode -> {
                            List<String> dictionaries = MobSpawnConfiguration.decodeNode(dicNode);

                            return biomeDic.stream().anyMatch(dictionaries::contains);
                        })) {
                            if (spawn.weight.get() > 0) {
                                event.getSpawns().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(type, spawn.weight.get(), spawn.min.get(), spawn.max.get()));
                            }
                        }
                    }
                }
            }
        }
    }
}
