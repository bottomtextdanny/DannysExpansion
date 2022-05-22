package bottomtextdanny.dannys_expansion._util.tooltip;

import com.google.common.collect.Lists;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class TooltipBlock implements TooltipWriter {
    private final List<TooltipWriter> data;
    @Nullable
    private final TooltipCondition condition;
    @Nullable
    private final TooltipWriter header;

    public TooltipBlock(List<TooltipWriter> data, @Nullable TooltipCondition condition,
                        @Nullable TooltipWriter header) {
        super();
        this.data = data;
        this.condition = condition;
        this.header = header;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public void write(int spacing, @Nullable ItemStack stack, @Nullable Level level,
                      List<Component> tooltip, TooltipFlag flag) {

        if (condition == null || condition.test(stack, level, tooltip, flag)) {
            if (header != null) {
                header.write(spacing, stack, level, tooltip, flag);
                data.forEach(readable -> readable.write(spacing + 1, stack, level, tooltip, flag));
            } else data.forEach(readable -> readable.write(spacing, stack, level, tooltip, flag));
        } else if (condition != null) {
            condition.hiddenMessage.write(spacing, stack, level, tooltip, flag);
        }
    }

    public static class Builder {
        private final List<TooltipWriter> data;
        @Nullable
        private TooltipCondition condition;
        @Nullable
        private TooltipWriter header;

        private Builder() {
            super();
            data = Lists.newArrayList();
        }

        public Builder add(TooltipWriter component) {
            this.data.add(component);

            return this;
        }

        public <T> Builder add(TooltipData<T> data, T input) {
            this.data.add(TooltipWriter.data(data, input));

            return this;
        }

        public <T> Builder add(Component component) {
            this.data.add(TooltipWriter.component(component));

            return this;
        }

        public <T> Builder add(Supplier<Component> componentSupplier) {
            this.data.add(TooltipWriter.componentSupplier(componentSupplier));

            return this;
        }

        public Builder header(TooltipWriter header) {
            this.header = header;

            return this;
        }

        public <T> Builder header(TooltipData<T> data, T input) {
            this.header = TooltipWriter.data(data, input);

            return this;
        }

        public <T> Builder header(Component component) {
            this.header = TooltipWriter.component(component);

            return this;
        }

        public <T> Builder header(Supplier<Component> componentSupplier) {
            this.header = TooltipWriter.componentSupplier(componentSupplier);

            return this;
        }

        public Builder condition(TooltipCondition condition) {
            this.condition = condition;

            return this;
        }

        public TooltipBlock build() {
            return new TooltipBlock(Collections.unmodifiableList(data), condition, header);
        }
    }
}
