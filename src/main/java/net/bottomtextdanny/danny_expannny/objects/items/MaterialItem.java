package net.bottomtextdanny.danny_expannny.objects.items;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
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
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslatableComponent("description.dannys_expansion.material").withStyle(ChatFormatting.DARK_GREEN));
    }
}
