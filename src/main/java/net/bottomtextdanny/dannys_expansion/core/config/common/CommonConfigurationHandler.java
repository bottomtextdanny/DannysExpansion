package net.bottomtextdanny.dannys_expansion.core.config.common;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommonConfigurationHandler {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static Config CONFIG = new Config(BUILDER);

    public static class Config {
        public static List<MobSpawnConfiguration> spawnList = new ArrayList<MobSpawnConfiguration>();

        public final ForgeConfigSpec.BooleanValue sporerOnlySpawnInRain;
	    public final ForgeConfigSpec.BooleanValue curiosCompatibility;

        public final ForgeConfigSpec.ConfigValue<List<? extends String>> validIDs;

        Config(ForgeConfigSpec.Builder builder) {
            builder.push("General");
            builder.comment("Allowed mod biomes where the mobs can spawn, by ID like: minecraft, biomesoplenty, etc.");
            this.validIDs = builder.defineList("modIDs", Arrays.asList("minecraft", "biomesoplenty"), a -> true);
            builder.comment("Sporers should only spawn in rain?");
            this.sporerOnlySpawnInRain = builder.define("sporerOnlySpawnInRain", true);
            this.curiosCompatibility = builder.define("curios compatibility (if active)", false);
            builder.pop();

            spawnList.add(new MobSpawnConfiguration.Builder()
                    .start("dannys_expansion:monstrous_scorpion", builder)
                    .setValues(1, 1, 25)
                    .setDictionaries("SANDY", "JUNGLE")
                    .build());

            spawnList.add(new MobSpawnConfiguration.Builder()
                    .start("dannys_expansion:cursed_skull", builder)
                    .setValues(1, 1, 0)
                    .setDictionaries("SWAMP:OVERWORLD")
                    .setBlacklistDictionaries("MUSHROOM")
                    .build());
	
	        spawnList.add(new MobSpawnConfiguration.Builder()
		        .start("dannys_expansion:desertic_slime", builder)
		        .setValues(1, 1, 20)
		        .setDictionaries("SANDY")
		        .build());

            spawnList.add(new MobSpawnConfiguration.Builder()
                    .start("dannys_expansion:ender_beast_archer", builder)
                    .setValues(1, 1, 0)
                    .setDictionaries("END")
                    .setBlacklistBiomes("minecraft:the_end")
                    .build());

            spawnList.add(new MobSpawnConfiguration.Builder()
                    .start("dannys_expansion:ender_beast_lancer", builder)
                    .setValues(1, 1, 0)
                    .setDictionaries("END")
                    .setBlacklistBiomes("minecraft:the_end")
                    .build());

            spawnList.add(new MobSpawnConfiguration.Builder()
                    .start("dannys_expansion:frozen_ghoul", builder)
                    .setValues(1, 1, 25)
                    .setDictionaries("SNOWY:OVERWORLD")
                    .build());

            spawnList.add(new MobSpawnConfiguration.Builder()
                    .start("dannys_expansion:frozen_slime", builder)
                    .setValues(1, 1, 20)
                    .setDictionaries("SNOWY:OVERWORLD")
                    .build());

            spawnList.add(new MobSpawnConfiguration.Builder()
                    .start("dannys_expansion:grand_rammer", builder)
                    .setValues(1, 1, 0)
                    .setDictionaries("FOREST")
                    .build());


            spawnList.add(new MobSpawnConfiguration.Builder()
                    .start("dannys_expansion:ghoul", builder)
                    .setValues(1, 3, 20)
                    .setDictionaries("OVERWORLD")
                    .setBlacklistDictionaries("MUSHROOM")
                    .build());

            spawnList.add(new MobSpawnConfiguration.Builder()
                    .start("dannys_expansion:ice_elemental", builder)
                    .setValues(1, 1, 20)
                    .setBiomes("minecraft:ice_spikes")
                    .build());

            spawnList.add(new MobSpawnConfiguration.Builder()
                    .start("dannys_expansion:jemosselly", builder)
                    .setValues(1, 1, 1)
                    .setDictionaries("END")
                    .build());

            spawnList.add(new MobSpawnConfiguration.Builder()
                    .start("dannys_expansion:jungle_golem", builder)
                    .setValues(1, 1, 14)
                    .setDictionaries("JUNGLE")
                    .build());

            spawnList.add(new MobSpawnConfiguration.Builder()
                    .start("dannys_expansion:jungle_slime", builder)
                    .setValues(1, 1, 30)
                    .setDictionaries("JUNGLE")
                    .build());

            spawnList.add(new MobSpawnConfiguration.Builder()
                    .start("dannys_expansion:magma_gulper", builder)
                    .setValues(1, 1, 10)
                    .setDictionaries("NETHER")
                    .setBlacklistBiomes("minecraft:warped_forest")
                    .build());

            spawnList.add(new MobSpawnConfiguration.Builder()
                    .start("dannys_expansion:magma_slime", builder)
                    .setValues(1, 1, 8)
                    .setDictionaries("NETHER")
                    .setBlacklistBiomes("minecraft:warped_forest")
                    .build());

//            spawnList.add(new MobSpawnConfiguration.Builder()
//                    .start("dannys_expansion:manhunter", builder)
//                    .setValues(1, 1, 0)
//                    .setDictionaries("PlAINS")
//                    .build());

            spawnList.add(new MobSpawnConfiguration.Builder()
                    .start("dannys_expansion:mummy", builder)
                    .setValues(1, 1, 15)
                    .setDictionaries("SANDY")
                    .build());

            spawnList.add(new MobSpawnConfiguration.Builder()
                    .start("dannys_expansion:mundane_slime", builder)
                    .setValues(1, 1, 13)
                    .setDictionaries("FOREST", "PLAINS")
                    .build());


            spawnList.add(new MobSpawnConfiguration.Builder()
                    .start("dannys_expansion:nyctoid", builder)
                    .setValues(1, 1, 0)
                    .setDictionaries("OVERWORLD")
                    .setBlacklistDictionaries("MUSHROOM")
                    .build());

            spawnList.add(new MobSpawnConfiguration.Builder()
                    .start("dannys_expansion:hollow_armor", builder)
                    .setValues(1, 1, 7)
                    .setDictionaries("OVERWORLD")
                    .setBlacklistDictionaries("MUSHROOM")
                    .build());

//            spawnList.add(new MobSpawnConfiguration.Builder()
//                    .start("dannys_expansion:purpolio", builder)
//                    .setValues(1, 1, 1)
//                    .setDictionaries("END")
//                    .build());

            spawnList.add(new MobSpawnConfiguration.Builder()
                    .start("dannys_expansion:rammer", builder)
                    .setValues(1, 1, 1)
                    .setDictionaries("FOREST", "PLAINS")
                    .build());

            spawnList.add(new MobSpawnConfiguration.Builder()
                    .start("dannys_expansion:sporer", builder)
                    .setValues(1, 1, 4)
                    .setDictionaries("OVERWORLD")
                    .setBlacklistDictionaries("MUSHROOM")
                    .build());

            spawnList.add(new MobSpawnConfiguration.Builder()
                    .start("dannys_expansion:squig", builder)
                    .setValues(1, 1, 10)
                    .setDictionaries("OCEAN")
                    .build());

            spawnList.add(new MobSpawnConfiguration.Builder()
                    .start("dannys_expansion:tumefend", builder)
                    .setValues(1, 1, 1)
                    .setDictionaries("END")
                    .build());
        }
    }


    public static final ForgeConfigSpec forgeConfigSpec = BUILDER.build();
}
