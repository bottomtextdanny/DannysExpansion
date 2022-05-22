package bottomtextdanny.dannys_expansion._util.tooltip;

import bottomtextdanny.dannys_expansion._util.DEStringUtil;
import bottomtextdanny.dannys_expansion._util.DescriptionUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public interface TooltipWriter {

    void write(int spacing, @Nullable ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag);

    static MutableComponent spacing(int spacing, Component other) {
        return new TextComponent(DEStringUtil.repeatChar(' ', spacing)).append(other);
    }

    static <T> TooltipWriter data(TooltipData<T> data, T input) {
        return (spacing, stack, level, tooltip, flag) -> tooltip.add(spacing(spacing, data.message(input)));
    }

    static TooltipWriter componentSupplier(Supplier<Component> component) {
        return (spacing, stack, level, tooltip, flag) -> tooltip.add(spacing(spacing, component.get()));
    }

    static TooltipWriter component(Component component) {
        return (spacing, stack, level, tooltip, flag) -> tooltip.add(spacing(spacing, component));
    }

    static TooltipWriter trans(String key, MutableComponent object, Style objectStyle, Style textStyle) {

        TranslatableComponent translation = new TranslatableComponent(key);

        return (spacing, stack, level, tooltip, flag) -> {
            String[] split = translation.getString().split(DescriptionUtil.INPUT_KEY);

            MutableComponent out;

            if (split.length > 0) {
                out = new TextComponent(split[0]).withStyle(ChatFormatting.GREEN);

                Component translatedShift = object
                        .withStyle(objectStyle);

                for (int i = 1; i < split.length; i++) {
                    out.append(translatedShift);
                    out.append(new TextComponent(split[i]).withStyle(textStyle));
                }
            }
            else out = translation;

            tooltip.add(spacing(spacing, out));
        };
    }
}
