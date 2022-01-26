package net.bottomtextdanny.dannys_expansion.common.Entities.spell;

import net.bottomtextdanny.danny_expannny.object_tables.DEEntities;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class SporeBombEntity extends SpellEntity {

    public SporeBombEntity(EntityType<? extends SporeBombEntity> type, Level worldIn) {
        super(type, worldIn);
        setLifeTime(200);
    }

    @Override
    public void tick() {
        super.tick();
        setDeltaMovement(getDeltaMovement().add(0.0, -0.12, 0.0));

        if (this.horizontalCollision || this.verticalCollision) {
            setDeath();
        }
    }

    @Override
    public void onLifeEnd() {
        super.onLifeEnd();
        if (!this.level.isClientSide) {
            playSound(DESounds.ES_SPORE_BOMB_LAND.get(), 2.0F, 1.0F + this.random.nextFloat() * 0.05F);

            SporeCloudEntity sporeCloudEntity = new SporeCloudEntity(DEEntities.SPORE_CLOUD.get(), this.level);
            sporeCloudEntity.setPos(getX(), getY(), getZ());
            this.level.addFreshEntity(sporeCloudEntity);
        } else {

            this.level.addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult result) {
        super.onEntityHit(result);
        if (getLifeTick() > 1) {
            if (result.getEntity() != this && result.getEntity() != getCaster()) {
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
