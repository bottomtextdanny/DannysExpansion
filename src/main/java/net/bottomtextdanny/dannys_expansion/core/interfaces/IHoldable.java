package net.bottomtextdanny.dannys_expansion.core.interfaces;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface IHoldable {

    void rightClickUnpress(Level world, Player player, ItemStack stack, int progress);

    boolean canBePressed(Level world, Player player, ItemStack stack);

    void rightClickProgressUpdate(Level world, Player player, ItemStack stack);

    int rightClickDuration();

    int getRightClickProgress(Player player);

    void onRightClickHold(Player player);

    float holdMovementSpeed();

    void setRightClickProgress(Player player, int newProg);

    void addRightClickProgress(Player player, int newProg);

    boolean automatic();

}
