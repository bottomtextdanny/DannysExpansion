package net.bottomtextdanny.dannys_expansion.common.Entities.projectile;

import com.mojang.math.Vector3f;
import net.bottomtextdanny.danny_expannny.object_tables.DEEntities;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.SporerEntity;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.SporeSlime;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import java.util.Random;

public class SporeEntity extends AbstractArrow {

    public SporeEntity(EntityType<? extends SporeEntity> type, Level worldIn) {
        super(type, worldIn);
        setSoundEvent(SoundEvents.HONEY_BLOCK_BREAK);
    }

    public static SporeEntity sporeEntity(EntityType<? extends SporeEntity> type, Level worldIn) {
        return new SporeEntity(type, worldIn);
    }

    @Override
    protected ItemStack getPickupItem() {
        return null;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.horizontalCollision || this.verticalCollision) {
            this.remove(RemovalReason.KILLED);
        }
        this.level.addParticle(new DustParticleOptions(new Vector3f(255F/89 , 0F, 255F/242), 0.5F), this.getX(), this.getY() + 0.15625F, this.getZ(), 0.0D, 0.0D, 0.0D);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (new Random().nextFloat() <= 0.36) {
            SporeSlime sporeSlimeEntity = new SporeSlime(DEEntities.SPORE_SLIME.get(), this.level);
            sporeSlimeEntity.absMoveTo(getX(), getY(), getZ(), this.getYRot(), 0);
            sporeSlimeEntity.setTamed(true);
            if (this.getOwner() instanceof SporerEntity) {
                sporeSlimeEntity.setSummoner((SporerEntity)this.getOwner());
            }

            this.level.addFreshEntity(sporeSlimeEntity);
        }

        for(int i = 0; i < 20; ++i) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            this.level.addParticle(new DustParticleOptions(new Vector3f(255/89 , 0, 255/242), 0.8F), this.getRandomX(1.0D), this.getRandomY(), this.getRandomZ(1.0D), d0, d1, d2);
        }
        this.remove(RemovalReason.KILLED);
    }

    @Override
    public void move(MoverType typeIn, Vec3 pos) {
        super.move(typeIn, pos);

    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
