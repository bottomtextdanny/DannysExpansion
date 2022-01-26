package net.bottomtextdanny.danny_expannny.objects.items.sword;

import net.bottomtextdanny.danny_expannny.objects.containers.DannyAccessoriesContainer;
import net.bottomtextdanny.dannys_expansion.core.data.DannyItemTier;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;

public class KlifourBladeItem extends SwordItem {

    public KlifourBladeItem(Properties builderIn) {
        super(DannyItemTier.KLIFOUR, 3, -2.5F, builderIn.fireResistant());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (!playerIn.level.isClientSide) playerIn.openMenu(new SimpleMenuProvider(DannyAccessoriesContainer::new, new TranslatableComponent("container.danny_accessories")));

        return super.use(worldIn, playerIn, handIn);
    }
}
