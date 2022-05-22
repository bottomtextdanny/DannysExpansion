package bottomtextdanny.de_json_generator.types.base;

import bottomtextdanny.de_json_generator.types.extension.IGenExtension;

import java.io.IOException;

public interface IGenerator<T extends IGenerator<T>> extends GeneratorUtils {

    void generate() throws IOException;

    default T with(IGenExtension<? super T> generator) throws IOException {
        generator.generate((T) this);
        return (T) this;
    }

}
