package net.bottomtextdanny.danny_expannny.objects.items.arrow;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class WoodenArrowItem extends DannyArrowItem {

    public WoodenArrowItem(Properties builder) {
        super(builder);
    }

    @Override
    public AbstractArrow createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
      //  WoodenArrowEntity arrowentity = new WoodenArrowEntity(DannyEntities.WOODEN_ARROW.get(), worldIn, shooter);
        return null;
    }
}
