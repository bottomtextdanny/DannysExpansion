package bottomtextdanny.de_json_generator._gen;

import bottomtextdanny.de_json_generator.jsonBakers._wb_recipe.LazyType;
import bottomtextdanny.de_json_generator.types.base.Generator;
import bottomtextdanny.de_json_generator.types.base.GeneratorUtils;

import static bottomtextdanny.de_json_generator.GenUtils.*;

public class LazyRecipesGenerator {
    
    public static void gen() {
        try {
            Generator.trashAgentDir(str -> str.contains("lazy_recipes"), GeneratorUtils.PATH + "\\data\\dannys_expansion\\lazy_recipes");
            workbench();
            engineeringStation();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void workbench() {
        int counter = 0;

        doLazyRecipe(wb_recipe().type(LazyType.WORKBENCH)
                        .index(counter++)
                        .add(lezy_item("minecraft:planks", 8, '#'))
                        .add(lezy_item("minecraft:hay_block", 5))
                        .result(lezy_item("dannys_expansion:test_dummy", 1))
        );

        doLazyRecipe(wb_recipe().type(LazyType.WORKBENCH)
                .index(counter++)
                .add(lezy_item("minecraft:hay_block", 4))
                .result(lezy_item("dannys_expansion:thatch", 16))
        );

        doLazyRecipe(wb_recipe().type(LazyType.WORKBENCH)
                .index(counter++)
                .add(lezy_item("minecraft:packed_ice", 24))
                .add(lezy_item("dannys_expansion:ice_shard", 1))
                .result(lezy_item("dannys_expansion:ice_bricks", 16))
        );
    }
    
    public static void engineeringStation() {
        int counter = 0;

        doLazyRecipe(wb_recipe().type(LazyType.ENGINEERING_STATION)
                .index(counter++)
                .add(lezy_item("minecraft:iron_ingot", 3))
                .add(lezy_item("minecraft:gold_ingot", 3))
                .add(lezy_item("minecraft:redstone", 4))
                .add(lezy_item("minecraft:gunpowder", 4))
                .result(lezy_item("dannys_expansion:impulsor", 1))
        );

        doLazyRecipe(wb_recipe().type(LazyType.ENGINEERING_STATION)
                .index(counter++)
                .add(lezy_item("minecraft:totem_of_undying", 1))
                .result(lezy_item("dannys_expansion:life_essence", 5))
        );

        doLazyRecipe(wb_recipe().type(LazyType.ENGINEERING_STATION)
                .index(counter++)
                .add(lezy_item("minecraft:blue_ice", 128))
                .add(lezy_item("dannys_expansion:ice_shard", 16))
                .result(lezy_item("dannys_expansion:frozen_bow", 1))
        );

        doLazyRecipe(wb_recipe().type(LazyType.ENGINEERING_STATION)
                .index(counter++)
                .add(lezy_item("minecraft:glass_bottle", 1))
                .add(lezy_item("dannys_expansion:scorpion_gland", 1))
                .result(lezy_item("dannys_expansion:venom_vial", 1))
        );

        doLazyRecipe(wb_recipe().type(LazyType.ENGINEERING_STATION)
                .index(counter++)
                .add(lezy_item("minecraft:iron_ingot", 5))
                .add(lezy_item("minecraft:planks", 2, '#'))
                .result(lezy_item("dannys_expansion:handgun", 1))
        );

        doLazyRecipe(wb_recipe().type(LazyType.ENGINEERING_STATION)
                .index(counter++)
                .add(lezy_item("minecraft:iron_ingot", 10))
                .add(lezy_item("minecraft:planks", 5, '#'))
                .result(lezy_item("dannys_expansion:shotgun", 1))
        );

        doLazyRecipe(wb_recipe().type(LazyType.ENGINEERING_STATION)
                .index(counter++)
                .add(lezy_item("minecraft:iron_ingot", 10))
                .add(lezy_item("minecraft:planks", 10, '#'))
                .result(lezy_item("dannys_expansion:musket", 1))
        );

        doLazyRecipe(wb_recipe().type(LazyType.ENGINEERING_STATION)
                .index(counter++)
                .add(lezy_item("minecraft:planks", 6, '#'))
                .result(lezy_item("dannys_expansion:wooden_arrow", 2))
        );

        doLazyRecipe(wb_recipe().type(LazyType.ENGINEERING_STATION)
                .index(counter++)
                .add(lezy_item("minecraft:arrow", 12))
                .add(lezy_item("minecraft:blue_ice", 5))
                .add(lezy_item("dannys_expansion:ice_shard", 1))
                .result(lezy_item("dannys_expansion:ice_arrow", 12))
        );

        doLazyRecipe(wb_recipe().type(LazyType.ENGINEERING_STATION)
                .index(counter++)
                .add(lezy_item("minecraft:arrow", 12))
                .add(lezy_item("dannys_expansion:venom_vial", 1))
                .result(lezy_item("dannys_expansion:venom_arrow", 12))
        );

        doLazyRecipe(wb_recipe().type(LazyType.ENGINEERING_STATION)
                .index(counter++)
                .add(lezy_item("minecraft:iron_ingot", 8))
                .add(lezy_item("minecraft:gunpowder", 8))
                .result(lezy_item("dannys_expansion:bullet", 12))
        );

        doLazyRecipe(wb_recipe().type(LazyType.ENGINEERING_STATION)
                .index(counter++)
                .add(lezy_item("dannys_expansion:bullet", 16))
                .add(lezy_item("dannys_expansion:impulsor", 1))
                .result(lezy_item("dannys_expansion:high_velocity_bullet", 16))
        );

        doLazyRecipe(wb_recipe().type(LazyType.ENGINEERING_STATION)
                .index(counter++)
                .add(lezy_item("dannys_expansion:bullet", 12))
                .add(lezy_item("minecraft:prismarine_shard", 20))
                .result(lezy_item("dannys_expansion:aquatic_bullet", 12))
        );
    }
}
