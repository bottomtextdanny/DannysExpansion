package bottomtextdanny.dannys_expansion._util.tooltip;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public interface TooltipPredicate {
    TooltipPredicate TRUE = (stack, level, tooltip, flag) -> true;

    boolean test(@Nullable ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag);
}
