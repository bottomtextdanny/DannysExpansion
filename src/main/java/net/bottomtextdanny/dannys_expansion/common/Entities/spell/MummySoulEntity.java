package net.bottomtextdanny.dannys_expansion.common.Entities.spell;

import net.bottomtextdanny.braincell.mod.world.builtin_entities.ModuledMob;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.EntityUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class MummySoulEntity extends SpellEntity {
    boolean playerMode;

    public MummySoulEntity(EntityType<? extends SpellEntity> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
        setLifeTime(20);
    }

    @Override
    public void onLifeEnd() {
        for(int i = 0; i < 6; ++i) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextFloat() * -0.1D;
            double d2 = this.random.nextGaussian() * 0.02D;

            EntityUtil.particleAt(this.level, ParticleTypes.POOF, 1, this.getRandomX(0.6D), this.getRandomY(), this.getRandomZ(0.6D), d0, d1, d2, 0.1F);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (getLifeTick() == 10) {
            playSound(DESounds.ES_POSSESSED_ARMOR_SWISH.get(), 0.3F + this.random.nextFloat() * 0.4F, 0.4F + this.random.nextFloat() * 0.2F);
        } else if (getLifeTick() == 13) {

            Vec3 vec = DEMath.fromPitchYaw(0, getYRot());

            if (this.playerMode) {
                List<Mob> list = getCaster().level.getEntitiesOfClass(Mob.class, this.getBoundingBox().inflate(1.4), e -> e.getTarget() == getCaster());
                if (!list.isEmpty()) {
                    Mob target = list.get(0);

                    target.push(5.0F * vec.x, 0F, 5.0F * vec.z);
                    target.hurtMarked = true;
                }
            } else if (getCaster() instanceof Mob && ((Mob)getCaster()).getTarget() != null && ((Mob)getCaster()).getTarget().getBoundingBox().intersects(this.getBoundingBox().inflate(5))) {
                if (((Mob)getCaster()).getTarget() instanceof Player) {
                    ModuledMob.disableShield(((Mob) getCaster()).getTarget(), 10);
                }

                ((Mob)getCaster()).getTarget().push(5.0F * vec.x, 0F, 5.0F * vec.z);
                ((Mob)getCaster()).getTarget().hurtMarked = true;
            }
            
        }
    }
    
    public void setPlayerMode(boolean playerMode) {
        this.playerMode = playerMode;
    }
}
