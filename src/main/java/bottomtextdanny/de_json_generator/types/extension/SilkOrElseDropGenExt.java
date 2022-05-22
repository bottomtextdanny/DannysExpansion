package bottomtextdanny.de_json_generator.types.extension;

import bottomtextdanny.de_json_generator.jsonBakers.JsonUtilsMiddleEnd;
import bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.*;
import bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.conditions.item.MatchToolCondition;
import bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.conditions.item.TLItemPred;
import bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.functions.EnchantRandomlyFunction;
import bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.functions.EnchantWithLevelsFunction;
import bottomtextdanny.de_json_generator.types.base.Generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SilkOrElseDropGenExt implements IGenExtension<Generator> {
    String auxiliary;
    int minimum = 1, maximum = 1;

    public SilkOrElseDropGenExt bounds(int minimum, int maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
        return this;
    }

    public SilkOrElseDropGenExt aux(String auxiliary) {
        this.auxiliary = auxiliary;
        return this;
    }

    @Override
    public void generate(Generator base) throws IOException {

        new LootTableMaster(LootTableType.BLOCK)
                .pools(new LTPool(JsonUtilsMiddleEnd.constant(1))
                        .entries(new LTEntry()
                                .type(EntryType.ALTERNATIVE)
                                .children(
                                        new LTEntry()
                                                .itemID("minecraft:diamond")
                                                .conditions(
                                                        new MatchToolCondition(
                                                                new TLItemPred()
                                                                        .item("minecraft:iron_axe")
                                                                        .enchantments(
                                                                                new EnchantJson("minecraft:silk_touch")
                                                                                        .min(1)
                                                                        )
                                                        )
                                                )
                                                .functions(
                                                        new EnchantRandomlyFunction(
                                                                "minecraft:frost_walker",
                                                                "minecraft:sharpness"
                                                        ),
                                                        new EnchantWithLevelsFunction(JsonUtilsMiddleEnd.uniform(1, 3))
                                                )
                                        ,
                                        new LTEntry()
                                                .itemID("minecraft:wheat_seeds")
                                )
                        )
                ).bake();

        String fileName = "silk_touch_or_else";

        File json = Generator.createBlockLootTable(base.getName());
        String         template = Generator.getTemplate("loottables", fileName);
        BufferedWriter writer = new BufferedWriter(new FileWriter(json));

        writer.write(template
                .replaceAll("_Objname", base.getName())
                .replaceAll("_minimum", String.valueOf(this.minimum))
                .replaceAll("_maximum", String.valueOf(this.maximum))
                .replaceAll("_else", this.auxiliary)
        );
        writer.close();

        this.minimum = 1;
        this.maximum = 1;
    }
}
