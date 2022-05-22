package bottomtextdanny.dannys_expansion._base.sensitive_hooks;

import bottomtextdanny.dannys_expansion.tables.DEEntities;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;

public final class SpawnHooks {

    public static void applySpawnToBiomes(BiomeLoadingEvent event) {
        Biome biome = ForgeRegistries.BIOMES.getValue(event.getName());

        if (biome != null && event.getName() != null) {
            ResourceKey<Biome> biomeKey = ResourceKey.create(Registry.BIOME_REGISTRY, event.getName());
            List<MobSpawnSettings.SpawnerData> monsterSpawns = event.getSpawns().getSpawner(MobCategory.MONSTER);
            Set<BiomeDictionary.Type> forgeDictionary = BiomeDictionary.getTypes(biomeKey);

            if (forgeDictionary.contains(BiomeDictionary.Type.OVERWORLD)
                    && !forgeDictionary.contains(BiomeDictionary.Type.MUSHROOM)) {
                if (forgeDictionary.contains(BiomeDictionary.Type.FOREST)) {
                    monsterSpawns.add(spawn(DEEntities.GOBLIN.get(), 15, 7, 9));
                }

                monsterSpawns.add(spawn(DEEntities.HOLLOW_ARMOR.get(), 15, 1, 1));

                monsterSpawns.add(spawn(DEEntities.GHOUL.get(), 45, 1, 3));

                monsterSpawns.add(spawn(DEEntities.CURSED_SKULL.get(), 18, 1, 2));

                monsterSpawns.add(spawn(DEEntities.MUNDANE_SLIME.get(), 20, 2, 4));

                if (forgeDictionary.contains(BiomeDictionary.Type.SNOWY)) {
                    monsterSpawns.add(spawn(DEEntities.ICE_ELEMENTAL.get(), 35, 1, 2));
                }

                if (forgeDictionary.contains(BiomeDictionary.Type.OCEAN)) {
                    monsterSpawns.add(spawn(DEEntities.SQUIG.get(), 15, 2, 4));
                }

                if (forgeDictionary.contains(BiomeDictionary.Type.SANDY)) {
                    monsterSpawns.add(spawn(DEEntities.DESERTIC_SLIME.get(), 35, 2, 4));
                }

                if (forgeDictionary.contains(BiomeDictionary.Type.JUNGLE)
                        || forgeDictionary.contains(BiomeDictionary.Type.SANDY)) {
                    monsterSpawns.add(spawn(DEEntities.MONSTROUS_SCORPION.get(), 35, 2, 4));
                }
            } else if (forgeDictionary.contains(BiomeDictionary.Type.NETHER)) {
                monsterSpawns.add(spawn(DEEntities.MAGMA_SLIME.get(), 25, 1, 3));
            }
        }
    }

    private static MobSpawnSettings.SpawnerData spawn(EntityType<?> entityType, int weight, int minGroup, int maxGroup) {
        return new MobSpawnSettings.SpawnerData(entityType, weight, minGroup, maxGroup);
    }

    private SpawnHooks() {}
}
