package net.bottomtextdanny.de_json_generator.types.extension;

import net.bottomtextdanny.de_json_generator.types.base.GeneratorUtils;
import net.bottomtextdanny.de_json_generator.types.base.IGenerator;

import java.io.IOException;

public interface IGenExtension<B extends IGenerator> extends GeneratorUtils {

    void generate(B base) throws IOException;
}
