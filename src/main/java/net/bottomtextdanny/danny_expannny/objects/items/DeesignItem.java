package net.bottomtextdanny.danny_expannny.objects.items;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class DeesignItem extends Item {
    public String designName;
    private final boolean dyable;

    public DeesignItem(String design, boolean dyable, Properties properties) {
        super(properties);
        this.designName = design;
        this.dyable = dyable;
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = new ItemStack(this);
        stack.getOrCreateTagElement("Design");
        CompoundTag compoundNBT = stack.getTagElement("Design");
        compoundNBT.putString("DesignName", this.designName);
        return super.getDefaultInstance();
    }

    public boolean isDyable() {
        return this.dyable;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);

        if (this.dyable) {
            tooltip.add(new TranslatableComponent("description.dannys_expansion.design.dyable").withStyle(ChatFormatting.GREEN));
        }
    }
}
