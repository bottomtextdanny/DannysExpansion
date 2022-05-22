package bottomtextdanny.de_json_generator.types.blockmodel;

import bottomtextdanny.de_json_generator.types.base.GeneratorUtils;

import java.io.IOException;

public interface IGenBlockModel<T extends IGenBlockModel<T>> extends GeneratorUtils {
    void generate() throws IOException;

    String getName();

    T setNameRemote(String newName);
}
