package net.bottomtextdanny.dannys_expansion.common.Entities.spell;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class VenomEntity extends SpellEntity {

    public VenomEntity(EntityType<? extends SpellEntity> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
        setLifeTime(120);
    }

    @Override
    public void tick() {
        super.tick();
        push(0, -0.03, 0);

        if (this.horizontalCollision || this.verticalCollision) {
            setDeath();
        }
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        double d0 = this.getBoundingBox().getSize() * 10.0D;
        if (Double.isNaN(d0)) {
            d0 = 1.0D;
        }

        d0 = d0 * 64.0D * getViewScale();
        return distance < d0 * d0;
    }

    @Override
    protected void onEntityHit(EntityHitResult result) {
        super.onEntityHit(result);
        if (getLifeTick() > 1) {
            if (result.getEntity() != this && result.getEntity() != getCaster()) {
                result.getEntity().invulnerableTime = 0;
                result.getEntity().hurt(DamageSource.mobAttack(getCaster()), 3.0F);
                setDeath();
            }
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult result) {
        super.onBlockHit(result);
        setDeath();
    }
}
