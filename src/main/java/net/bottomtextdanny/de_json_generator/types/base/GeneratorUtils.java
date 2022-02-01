package net.bottomtextdanny.de_json_generator.types.base;

import net.bottomtextdanny.danny_expannny.DannysExpansion;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

public interface GeneratorUtils {
    String PATH = "C:/Users/pc/minecraft mod/DannysExpansion/src/main/resources";
    String JAVA_PATH = "C:/Users/pc/minecraft mod/DannysExpansion/src/main/java/";
    String TEMPLATE_PATH = "C:/Users/pc/minecraft mod/DannysExpansion/src/main/resources/datagentemplates";
    String MOD_ID = DannysExpansion.ID;
    String MC_ID = "minecraft";
    Pattern P_NAME = Pattern.compile("_name", Pattern.LITERAL);

    static String getClassSource(Class<?> clazz) {
        try {
            String path = JAVA_PATH + clazz.getPackage().getName().replaceAll("\\.","/") + "/" + clazz.getSimpleName() + ".java";
            return Files.readString(Path.of(path));
        } catch (Exception e) {}
        return null;
    }
}
