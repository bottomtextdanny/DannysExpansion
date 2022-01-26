package net.bottomtextdanny.danny_expannny.objects.items.arrow;

import net.bottomtextdanny.danny_expannny.object_tables.DEEntities;
import net.bottomtextdanny.dannys_expansion.common.Entities.projectile.arrow.IceArrowEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class IceArrowItem extends DannyArrowItem {

    public IceArrowItem(Properties builder) {
        super(builder);
    }

    @Override
    public AbstractArrow createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
        IceArrowEntity arrowentity = new IceArrowEntity(DEEntities.ICE_ARROW.get(), worldIn);
        arrowentity.setOwner(shooter);
	    arrowentity.setPos(shooter.getX(), shooter.getEyeY() - (double)0.1F, shooter.getZ());
	    
        return arrowentity;
    }
}
