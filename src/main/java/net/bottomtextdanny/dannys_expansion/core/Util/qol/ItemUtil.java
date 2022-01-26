package net.bottomtextdanny.dannys_expansion.core.Util.qol;

import com.mojang.blaze3d.platform.InputConstants;
import net.bottomtextdanny.danny_expannny.objects.items.AccessoryItem;
import net.bottomtextdanny.danny_expannny.objects.accessories.CoreAccessory;
import net.bottomtextdanny.danny_expannny.capabilities.item.DEStackCapability;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Consumer;

public class ItemUtil {

    public static void createDescription(Item item, List<Component> tooltip) {
        createDescription(item, tooltip, false);
    }

    public static void createDescription(String str, List<Component> tooltip) {
        createDescription(str, tooltip, false);
    }

    public static void createDescription(Item item, List<Component> tooltip, boolean fullSet) {
        if (Minecraft.getInstance().screen != null) {
            if (InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), Minecraft.getInstance().options.keyShift.getKey().getValue())) {
                if (fullSet) {
                    tooltip.add(new TranslatableComponent("description.dannys_expansion.full_set").withStyle(ChatFormatting.RED).append(" "));
                }
                tooltip.add(new TranslatableComponent("description.dannys_expansion." + item.getRegistryName().getPath()).withStyle(ChatFormatting.GREEN));
            } else {
                tooltip.add(new TranslatableComponent("description.dannys_expansion.text1").withStyle(ChatFormatting.GRAY)
                        .append(" ").withStyle(ChatFormatting.GRAY)
                        .append(new TranslatableComponent(Minecraft.getInstance().options.keyShift.getKey().getName())).withStyle(ChatFormatting.AQUA)
                        .append(" ").withStyle(ChatFormatting.GRAY)
                        .append(new TranslatableComponent("description.dannys_expansion.text2").withStyle(ChatFormatting.GRAY)));
            }
        }
    }

    public static void createDescription(String key, List<Component> tooltip, boolean fullSet) {
        if (Minecraft.getInstance().screen != null) {
            if (InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), Minecraft.getInstance().options.keyShift.getKey().getValue())) {
                if (fullSet) {
                    tooltip.add(new TranslatableComponent("description.dannys_expansion.full_set").withStyle(ChatFormatting.RED).append(" "));
                }
                tooltip.add(new TranslatableComponent("description.dannys_expansion." + key).withStyle(ChatFormatting.GREEN));

            } else {
                tooltip.add(new TranslatableComponent("description.dannys_expansion.text1").withStyle(ChatFormatting.GRAY)
                        .append(" ").withStyle(ChatFormatting.GRAY)
                        .append(new TranslatableComponent(Minecraft.getInstance().options.keyShift.getKey().getName())).withStyle(ChatFormatting.AQUA)
                        .append(" ").withStyle(ChatFormatting.GRAY)
                        .append(new TranslatableComponent("description.dannys_expansion.text2").withStyle(ChatFormatting.GRAY)));
            }
        }
    }



    public static TranslatableComponent descComp(String key) {
        return new TranslatableComponent("description.dannys_expansion." + key);
    }

    public static CoreAccessory getAccessory(Player player, Item item) {
        CoreAccessory accessory;
        
        if (item instanceof AccessoryItem) {
            accessory = ((AccessoryItem)item).accessoryKey.create(player);
        } else {
            accessory = CoreAccessory.EMPTY;
        }

        return accessory;
    }
	
	@Nonnull
	public static void ifDannyCap(ItemStack stack, Consumer<DEStackCapability> action) {
    	LazyOptional<DEStackCapability> lazyCap = stack.getCapability(DEStackCapability.CAP);
    	if (lazyCap.isPresent()) {
    		action.accept(lazyCap.orElse(null));
	    }
	}
	
	@Nonnull
	public static DEStackCapability cap(ItemStack stack) {
		return stack.getCapability(DEStackCapability.CAP).orElse(null);
	}
}
