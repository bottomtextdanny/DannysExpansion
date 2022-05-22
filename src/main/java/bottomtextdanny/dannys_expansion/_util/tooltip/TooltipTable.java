package bottomtextdanny.dannys_expansion._util.tooltip;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TooltipTable implements TooltipWriter {
    private final List<TooltipWriter> data;

    private TooltipTable(List<TooltipWriter> data) {
        super();
        this.data = data;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public void write(int spacing, @Nullable ItemStack stack, @Nullable Level level,
                      List<Component> tooltip, TooltipFlag flag) {
        int size = data.size();

        for (TooltipWriter writer : data) {
            writer.write(spacing, stack, level, tooltip, flag);
        }
    }

    public static class Builder {
        private final List<TooltipWriter> data;

        private Builder() {
            super();
            data = Lists.newArrayList();
        }

        public Builder block(TooltipWriter readable) {
            data.add(readable);
            return this;
        }

        public TooltipTable build() {
            return new TooltipTable(data);
        }
    }
}
