package bottomtextdanny.de_json_generator.types.base;

import java.util.regex.Pattern;

public abstract class GeneratorWMother<T extends GeneratorWMother<T>> extends Generator<T> {
    public static final Pattern P_WHOLE = Pattern.compile("_whole", Pattern.LITERAL);
    protected final String motherName;

    public GeneratorWMother(String name, String motherName) {
        super(name);
        this.motherName = motherName;
    }

    public String getMotherName() {
        return this.motherName;
    }
}
