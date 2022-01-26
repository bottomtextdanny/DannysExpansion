package net.bottomtextdanny.dannys_expansion.core.interfaces;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IDannyHoldable {

    boolean automatic();

    float holdMovementSpeed();

    int getTrueMaxCount(ItemStack stack);
}
