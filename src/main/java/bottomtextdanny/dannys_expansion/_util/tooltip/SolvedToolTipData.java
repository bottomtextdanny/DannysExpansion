package bottomtextdanny.dannys_expansion._util.tooltip;

import net.minecraft.network.chat.Component;

public record SolvedToolTipData<T>(TooltipData<T> data, T input) {
    public Component message() {
        return data.message(input);
    }
}
