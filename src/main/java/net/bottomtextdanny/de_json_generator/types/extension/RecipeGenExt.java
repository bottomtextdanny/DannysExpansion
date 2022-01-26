package net.bottomtextdanny.de_json_generator.types.extension;

import net.bottomtextdanny.de_json_generator.GenUtils;
import net.bottomtextdanny.de_json_generator.jsonBakers.JsonUtilsFrontend;
import net.bottomtextdanny.de_json_generator.jsonBakers._recipehelper.RecipeMaster;
import net.bottomtextdanny.de_json_generator.types.base.Generator;

import java.io.IOException;

public class RecipeGenExt extends GenUtils implements IGenExtension<Generator> {
	RecipeMaster master;

    public RecipeGenExt(RecipeMaster master) {
        this.master = master;
    }

    @Override
    public void generate(Generator base) throws IOException {
    	if (this.master.getResult() == null) this.master.result(JsonUtilsFrontend.result());
		doRecipe(this.master.bake());
    }
}
