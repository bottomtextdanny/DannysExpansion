package bottomtextdanny.de_json_generator._gen;


import bottomtextdanny.de_json_generator.inner.CommonTextureProviders;
import bottomtextdanny.de_json_generator.jsonBakers._recipehelper.RecipeShapedMaster;

import java.io.IOException;

import static bottomtextdanny.de_json_generator.ExtraModelGen.*;

public class BlockGenerator {

    public static void gen() throws IOException {
        flaggedSchemaBlock();
        stations();
        thatch();
    }

    private static void flaggedSchemaBlock() throws IOException {
        g_simple_block("flagged_schema_block", m_cube_all(
                de_b("flagged_schema_block")
        )).generate();
    }

    private static void stations() throws IOException {
        g_simple_block_h_rotable("engineering_station", m_custom_cube(
                de_b("engineering_station_front"),
                de_b("engineering_station_front"),
                de_b("engineering_station_back"),
                de_b("engineering_station_right"),
                de_b("engineering_station_left"),
                de_b("engineering_station_top"),
                de_b("engineering_station_bottom"))
        ).with(ext_recipe(new RecipeShapedMaster("XXX", "WWW", "WWW")
                .keys(keyItem('X', "minecraft:iron_ingot"),
                        keyTag('W', "minecraft:planks")
                )
                .result(result("dannys_expansion:" + CURRENT_NAME)
                        .count(1)
                ).bake())
        ).with(ext_loottable("blocks", lt_classic())).generate();
    }

    private static void thatch() throws IOException {
        g_simple_block("thatch", m_cube_all(
                de_b("thatch"))
        ).with(ext_loottable("blocks", lt_classic())).generate();

        g_stairs("thatch_stairs", CommonTextureProviders.tp_all(de_b("thatch")))
                .with(ext_loottable("blocks", lt_classic()))
                .with(ext_recipe(rs_stairs(de("thatch")))).generate();
        g_slab("thatch_slab", "thatch", CommonTextureProviders.tp_all(de_b("thatch")))
                .with(ext_loottable("blocks", lt_classic()))
                .with(ext_recipe(rs_slab(de("thatch")))).generate();
    }

    private static String de(String name) {
        return "dannys_expansion:" + name;
    }

    private static String de_b(String name) {
        return "dannys_expansion:block/" + name;
    }
}
