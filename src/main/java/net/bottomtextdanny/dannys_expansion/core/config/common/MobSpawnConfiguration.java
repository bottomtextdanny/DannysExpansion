package net.bottomtextdanny.dannys_expansion.core.config.common;

import com.google.common.collect.Lists;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MobSpawnConfiguration {
    static Map<String, BiomeDictionary.Type> type = BiomeDictionary.Type.getAll().stream().collect(Collectors.toMap(BiomeDictionary.Type::getName, Function.identity()));
    public ForgeConfigSpec.Builder configBuilder;
    public String name;
    public ForgeConfigSpec.IntValue min;
    public ForgeConfigSpec.IntValue max;
    public ForgeConfigSpec.IntValue weight;
    public ForgeConfigSpec.ConfigValue<List<? extends String>> biomes;
    public ForgeConfigSpec.ConfigValue<List<? extends String>> blacklistBiomes;
    public ForgeConfigSpec.ConfigValue<List<? extends String>> biomeDictionaries;
    public ForgeConfigSpec.ConfigValue<List<? extends String>> blacklistBiomeDictionaries;
    public Optional<EntityType<?>> entity;

    public MobSpawnConfiguration(int min, int max, int weight, List<? extends String> biomes, List<? extends String> blacklistBiomes, List<? extends String> biomeDictionaries, List<? extends String> blacklistBiomeDictionaries, String creature, ForgeConfigSpec.Builder builder) {
        this.name = creature;
        builder.push(this.name + "_spawn_parameters");
        this.min = builder.defineInRange("MinGroupSize", min, 0, 32);
        this.max = builder.defineInRange("MaxGroupSize", max, 0, 32);
        this.weight = builder.defineInRange("Weight", weight, 0, 500);
        this.biomes = builder.defineList("Biomes", biomes, o -> true);
        this.blacklistBiomes = builder.defineList("BlacklistBiomes", blacklistBiomes, o -> true);
        this.biomeDictionaries = builder.defineList("Dictionaries", biomeDictionaries, s -> dictionaryValidator(s, this.name));
        this.blacklistBiomeDictionaries = builder.defineList("BlacklistDictionaries", blacklistBiomeDictionaries, s -> dictionaryValidator(s, this.name));
        builder.pop();
    }

    @Nullable
    public EntityType<?> getEntityType() {
        if (this.entity == null) {
            this.entity = EntityType.byString(this.name);
        }

        if (this.entity.isPresent()) {
            return this.entity.get();
        } else {
            DannysExpansion.LOGGER.error(String.format("Danny's Expansion couldn't find entity type %s, skipping its spawn config...", this.name));
            return null;
        }
    }

    public MobSpawnConfiguration() {
    }

    public static class Builder {
        private MobSpawnConfiguration spawn;


        public Builder start(String creature, ForgeConfigSpec.Builder builder) {
            this.spawn = new MobSpawnConfiguration(0, 0, 0, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), creature, builder);
            this.spawn.configBuilder = builder;
            this.spawn.name = creature;
            builder.push(this.spawn.name + "_spawn_parameters");
            return this;
        }

        public Builder setValues(int min, int max, int weight) {
            this.spawn.min = this.spawn.configBuilder.defineInRange("MinGroupSize", min, 0, 32);
            this.spawn.max = this.spawn.configBuilder.defineInRange("MaxGroupSize", max, 0, 32);
            this.spawn.weight = this.spawn.configBuilder.defineInRange("Weight", weight, 0, 500);
            return this;
        }

        public Builder setBiomes(String... biomes) {
            this.spawn.biomes = this.spawn.configBuilder.defineList("Biomes", Lists.newArrayList(biomes), o -> true);
            return this;
        }

        public Builder setBlacklistBiomes(String... biomes) {
            this.spawn.blacklistBiomes = this.spawn.configBuilder.defineList("BlacklistBiomes", Lists.newArrayList(biomes), o -> true);
            return this;
        }

        public Builder setDictionaries(String... dictionaries) {
            this.spawn.biomeDictionaries = this.spawn.configBuilder.defineList("Dictionaries", Lists.newArrayList(dictionaries), s -> dictionaryValidator(s, this.spawn.name));
            return this;
        }

        public Builder setBlacklistDictionaries(String... dictionaries) {
            this.spawn.blacklistBiomeDictionaries = this.spawn.configBuilder.defineList("BlacklistDictionaries", Lists.newArrayList(dictionaries), s -> dictionaryValidator(s, this.spawn.name));
            return this;
        }

        public MobSpawnConfiguration build() {
            this.spawn.configBuilder.pop();
            return this.spawn;
        }
    }


    public static boolean dictionaryValidator(Object obj, String name) {
        List<String> strs = decodeNode(String.valueOf(obj));

        strs.forEach(s -> {
            if (!BiomeDictionary.Type.getAll().contains(type.get(s))) {
                DannysExpansion.LOGGER.error(String.format("Danny's Expansion couldn't recognize the biome dictionary >>%s<< for node >>%s<< from spawn configuration >>%s<<, please remove it", s, obj, name + "_spawn_parameters"));
            }
        });


        return true;
    }

    public static List<String> decodeNode(String s) {
        return Arrays.asList(s.split(":"));
    }
}
