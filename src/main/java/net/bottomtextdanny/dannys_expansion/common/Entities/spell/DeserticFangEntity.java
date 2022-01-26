package net.bottomtextdanny.dannys_expansion.common.Entities.spell;

import net.bottomtextdanny.danny_expannny.object_tables.DEParticles;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.EntityUtil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class DeserticFangEntity extends SpellEntity {

    public DeserticFangEntity(EntityType<? extends DeserticFangEntity> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
        setLifeTime(30);
    }

    @Override
    public void onLifeStart() {
        super.onLifeStart();
        playSound(DESounds.ES_MUMMY_SPIKE.get(), 1.0F, 0.9F + this.random.nextFloat() * 0.1F);
    }

    @Override
    public void tick() {
        super.tick();
        if (getLifeTick() == 1) {
            for(int i = 0; i < 3; ++i) {
                double d0 = this.random.nextGaussian() * 0.03D;
                double d1 = this.random.nextFloat() * 0.2D + 0.4D;
                double d2 = this.random.nextGaussian() * 0.03D;
                EntityUtil.particleAt(this.level, DEParticles.SANDY_DUST.get(), 1, this.getRandomX(0.6D), this.getRandomY(), this.getRandomZ(0.6D),d0, d1, d2, 1.0F);
            }
        } else if (getLifeTick() == 5 && casterHasAttackTarget()) {
            if (getCasterTarget().getBoundingBox().intersects(getBoundingBox().inflate(0.5))) {
                castersDamage(getCasterTarget(), 7);
            }
        }
    }
}
