package bottomtextdanny.dannys_expansion._util;

import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.Arrays;

public final class DEStringUtil {

    public static String repeatChar(char character, int times) {
        char[] chars = new char[times];

        Arrays.fill(chars, character);

        return new String(chars);
    }

    public static String normalizeKey(String key) {
        StringBuilder stringBuilder = new StringBuilder(key);
        int length = stringBuilder.length();
        for (int i = 0; i < length; i++) {
            if (i == 0 || stringBuilder.charAt(i - 1) == '_') {
                if (i != 0) stringBuilder.setCharAt(i - 1, ' ');
                stringBuilder.setCharAt(i, Character.toUpperCase(stringBuilder.charAt(i)));
            }
        }
        return stringBuilder.toString();
    }

    public static TextComponent translationWithReplacements(String translationKey, String... changes) {
        String translationString = new TranslatableComponent(translationKey).getString();
        int replacements = changes.length / 2;

        for (int i = 1; i <= replacements; i++) {
            translationString = translationString.replace(changes[i-1], changes[1]);
        }

        return new TextComponent(translationString);
    }

    private DEStringUtil() {}
}
