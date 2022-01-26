package net.bottomtextdanny.dannys_expansion.common.Entities.spell;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class PlasmaProjectileEntity extends SpellEntity {
    public int spriteTimmer;

    public PlasmaProjectileEntity(EntityType<? extends PlasmaProjectileEntity> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
        setLifeTime(120);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level.isClientSide) {
            if (this.spriteTimmer > 4) {
                this.spriteTimmer = 0;
            } else {
                ++this.spriteTimmer;
            }
        }

        setDeltaMovement(getDeltaMovement().add(0.9 * this.forward.x, 0.9 * this.forward.y, 0.9 * this.forward.z));
    }

    @Override
    public void onLifeEnd() {
        super.onLifeEnd();
    }

    @Override
    protected void onBlockHit(BlockHitResult p_230299_1_) {
        super.onBlockHit(p_230299_1_);
        setDeath();
    }

    @Override
    protected void onEntityHit(EntityHitResult p_213868_1_) {
        super.onEntityHit(p_213868_1_);
        if (p_213868_1_.getEntity() instanceof LivingEntity) {
            castersDamage((LivingEntity)p_213868_1_.getEntity(),8);
        }
        setDeath();
    }
}
