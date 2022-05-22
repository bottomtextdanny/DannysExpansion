package bottomtextdanny.dannys_expansion.content.items;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class MaterialItem extends Item {

    public MaterialItem(Properties properties) {
        super(properties.tab(DannysExpansion.TAB));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(new TranslatableComponent("description.dannys_expansion.material").withStyle(ChatFormatting.DARK_GREEN));
    }
}
