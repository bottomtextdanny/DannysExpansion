package net.bottomtextdanny.danny_expannny.objects.items;

import net.bottomtextdanny.danny_expannny.rendering.DERenderProperties;
import net.bottomtextdanny.danny_expannny.objects.entities.kite.KiteEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.client.RenderProperties;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public abstract class BaseKiteItem extends Item {
    public Random random = new Random();

    public BaseKiteItem(Properties properties) {
        super(properties);
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(DERenderProperties.KITE);
    }


    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslatableComponent("description.dannys_expansion.kite").withStyle(ChatFormatting.GRAY));
    }

    public abstract KiteEntity createKite(Level world, ItemStack stack);
}
