package bottomtextdanny.de_json_generator.types.extension;

import bottomtextdanny.de_json_generator.types.base.GeneratorUtils;
import bottomtextdanny.de_json_generator.types.base.IGenerator;

import java.io.IOException;

public interface IGenExtension<B extends IGenerator> extends GeneratorUtils {

    void generate(B base) throws IOException;
}
