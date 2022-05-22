package bottomtextdanny.dannys_expansion._util.tooltip;

import bottomtextdanny.dannys_expansion._util.DescriptionUtil;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class TooltipCondition implements TooltipWriter, TooltipPredicate {
    public static final TooltipCondition TRUE = new TooltipCondition(TooltipPredicate.TRUE);
    public static final TooltipCondition HOLD_SHIFT = Util.make(() -> {
        TooltipCondition con = new TooltipCondition((stack, level, tooltip, flag) -> {
            Minecraft mc = Minecraft.getInstance();
            return InputConstants.isKeyDown(mc.getWindow().getWindow(), mc.options.keyShift.getKey().getValue());
        });

        TranslatableComponent translation = new TranslatableComponent("description.dannys_expansion.hold_key_for_info");

        con.hiddenMessage((int spacing, @Nullable ItemStack stack, @Nullable Level level,
                           List<Component> tooltip, TooltipFlag flag) -> {
            String[] split = translation.getString().split(DescriptionUtil.INPUT_KEY);

            MutableComponent out;

            if (split.length > 0) {
                out = new TextComponent(split[0]).withStyle(ChatFormatting.GREEN);

                Component translatedShift = new TranslatableComponent(Minecraft.getInstance().options.keyShift.getKey().getName())
                        .withStyle(ChatFormatting.AQUA);

                for (int i = 1; i < split.length; i++) {
                    out.append(translatedShift);
                    out.append(new TextComponent(split[i]).withStyle(ChatFormatting.GREEN));
                }
            }
            else out = translation;

            tooltip.add(TooltipWriter.spacing(spacing, out));
        });

        return con;
    });
    private final TooltipPredicate predicate;
    @Nullable
    public TooltipWriter hiddenMessage;

    public TooltipCondition(TooltipPredicate predicate) {
        this.predicate = predicate;
    }

    public TooltipCondition hiddenMessage(TooltipWriter hiddenMessage) {
        this.hiddenMessage = hiddenMessage;
        return this;
    }

    @Override
    public void write(int spacing, @Nullable ItemStack stack, @Nullable Level level,
                      List<Component> tooltip, TooltipFlag flag) {
        if (hiddenMessage != null) {
            hiddenMessage.write(spacing, stack, level, tooltip, flag);
        }
    }

    @Override
    public boolean test(@Nullable ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        return predicate.test(stack, level, tooltip, flag);
    }
}
