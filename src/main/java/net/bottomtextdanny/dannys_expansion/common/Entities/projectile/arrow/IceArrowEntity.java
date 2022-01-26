package net.bottomtextdanny.dannys_expansion.common.Entities.projectile.arrow;

import net.bottomtextdanny.danny_expannny.object_tables.items.DEItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class IceArrowEntity extends DannyArrowEntity {

    public IceArrowEntity(EntityType<? extends AbstractArrow> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    protected float getWaterInertia() {
        return 0.8F;
    }

    @Override
    public float baseDamage() {
        return 2.125F;
    }

    @Override
    public float velocityMult() {
        return 1.0F;
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(DEItems.ICE_ARROW.get());
    }
}
