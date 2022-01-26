package net.bottomtextdanny.dannys_expansion.common.Entities.projectile;

import com.mojang.math.Vector3f;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;

public class VomitEntity extends AbstractArrow {

    public VomitEntity(EntityType<? extends VomitEntity> type, Level worldIn) {
        super(type, worldIn);
        setSoundEvent(SoundEvents.HONEY_BLOCK_BREAK);
    }

    public static VomitEntity vomitEntity(EntityType<? extends VomitEntity> type, Level worldIn) {
        return new VomitEntity(type, worldIn);
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
        this.level.addParticle(new DustParticleOptions(new Vector3f((float) 89/255 ,(float)176/255 , (float) 33/255), 0.5F), this.getX(), this.getY() + 0.15625F, this.getZ(), 0.0D, 0.0D, 0.0D);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);

            for(int i = 0; i < 20; ++i) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;
                this.level.addParticle(new DustParticleOptions(new Vector3f((float) 89/255 ,(float)176/255 , (float) 33/255), 0.8F), this.getRandomX(1.0D), this.getRandomY(), this.getRandomZ(1.0D), d0, d1, d2);
            }
        this.remove(RemovalReason.KILLED);
    }

    @Override
    protected void onHitEntity(EntityHitResult p_213868_1_) {
        p_213868_1_.getEntity().hurt(DamageSource.mobAttack((LivingEntity) getOwner()), 8);
        if (p_213868_1_.getEntity() instanceof  LivingEntity) {
            ((LivingEntity)p_213868_1_.getEntity()).addEffect(new MobEffectInstance(MobEffects.POISON, 40, 0, false, false, false));
        }
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
