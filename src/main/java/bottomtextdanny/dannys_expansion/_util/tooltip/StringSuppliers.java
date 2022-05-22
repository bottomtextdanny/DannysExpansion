package bottomtextdanny.dannys_expansion._util.tooltip;

import net.minecraft.network.chat.TranslatableComponent;

import java.util.function.Supplier;

public final class StringSuppliers {

    public static TranslatableComponent translatable(String key) {
        return new TranslatableComponent(key);
    }

    public static Supplier<String> seconds(float value) {
        return () -> String.valueOf(value) + 's';
    }

    public static Supplier<String> ticksToSeconds(float value) {
        return () -> String.valueOf(value / 20.0F) + 's';
    }

    public static Supplier<String> milis(float value) {
        return () -> String.valueOf(value) + "ms";
    }

    public static Supplier<String> float_(float value) {
        return () -> String.valueOf(value);
    }

    public static Supplier<String> double_(double value) {
        return () -> String.valueOf(value);
    }

    public static Supplier<String> int_(int value) {
        return () -> String.valueOf(value);
    }

    private StringSuppliers() {}
}
