package bottomtextdanny.dannys_expansion.content.items.bow;

import net.minecraft.world.item.ItemStack;

public interface DEBow {

    boolean automatic();

    float holdMovementSpeed();

    int getTrueMaxCount(ItemStack stack);
}
