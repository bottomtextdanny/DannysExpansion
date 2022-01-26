package net.bottomtextdanny.dannys_expansion.common.Entities.spell;

import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class EnderArrowEntity extends SpellEntity {

    public EnderArrowEntity(EntityType<? extends EnderArrowEntity> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
        setLifeTime(150);
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 motionSum = Vec3.ZERO;
        motionSum = motionSum.add(4 * this.forward.x, 4 * this.forward.y, 4 * this.forward.z);

        setDeltaMovement(motionSum);
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
            Vec3 vec = DEMath.fromPitchYaw(0, getYRot());

            p_213868_1_.getEntity().push(1.0F * vec.x, 0F, 1.0F * vec.z);
            castersDamage((LivingEntity)p_213868_1_.getEntity(),10);
        }
        setDeath();
    }
}
