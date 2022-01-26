package net.bottomtextdanny.dannys_expansion.common.Entities.projectile.arrow;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import java.util.Arrays;
import java.util.List;

// TODO: 12/20/2021 hook custom arrow crit particles
public abstract class DannyArrowEntity extends AbstractArrow {
    public float knockback;
    private IntOpenHashSet piercedEntities;
    private List<Entity> hitEntities;

    public DannyArrowEntity(EntityType<? extends AbstractArrow> type, Level worldIn) {
        super(type, worldIn);
        setBaseDamage(baseDamage());
    }

    @Override
    protected void onHitEntity(EntityHitResult p_213868_1_) {
        Entity entity = p_213868_1_.getEntity();
        float f = (float)this.getDeltaMovement().length();
        int i = Mth.ceil(Mth.clamp((double)f * getBaseDamage(), 0.0D, 2.147483647E9D));
        if (this.getPierceLevel() > 0) {
            if (this.piercedEntities == null) {
                this.piercedEntities = new IntOpenHashSet(5);
            }

            if (this.hitEntities == null) {
                this.hitEntities = Lists.newArrayListWithCapacity(5);
            }

            if (this.piercedEntities.size() >= this.getPierceLevel() + 1) {
                this.remove(RemovalReason.KILLED);
                return;
            }

            this.piercedEntities.add(entity.getId());
        }

        if (this.isCritArrow()) {
            long j = this.random.nextInt(i / 2 + 2);
            i = (int)Math.min(j + (long)i, 2147483647L);
        }

        Entity entity1 = this.getOwner();
        DamageSource damagesource;
        if (entity1 == null) {
            damagesource = DamageSource.arrow(this, this);
        } else {
            damagesource = DamageSource.arrow(this, entity1);
            if (entity1 instanceof LivingEntity) {
                ((LivingEntity)entity1).setLastHurtMob(entity);
            }
        }

        boolean flag = entity.getType() == EntityType.ENDERMAN;
        int k = entity.getRemainingFireTicks();
        if (this.isOnFire() && !flag) {
            entity.setSecondsOnFire(5);
        }

        if (entity.hurt(damagesource, (float)i)) {
            if (flag) {
                return;
            }

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity;

                if (this.knockback > 0) {
                    Vec3 vector3d = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale((double)this.knockback * 0.6D);
                    if (vector3d.lengthSqr() > 0.0D) {
                        livingentity.push(vector3d.x, 0.1D, vector3d.z);
                    }
                }

                if (!this.level.isClientSide && entity1 instanceof LivingEntity) {
                    EnchantmentHelper.doPostHurtEffects(livingentity, entity1);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity)entity1, livingentity);
                }

                this.doPostHurtEffects(livingentity);
                if (entity1 != null && livingentity != entity1 && livingentity instanceof Player && entity1 instanceof ServerPlayer && !this.isSilent()) {
                    ((ServerPlayer)entity1).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
                }

                if (!entity.isAlive() && this.hitEntities != null) {
                    this.hitEntities.add(livingentity);
                }

                if (!this.level.isClientSide && entity1 instanceof ServerPlayer) {
                    ServerPlayer serverplayerentity = (ServerPlayer)entity1;
                    if (this.hitEntities != null && this.shotFromCrossbow()) {
                        CriteriaTriggers.KILLED_BY_CROSSBOW.trigger(serverplayerentity, this.hitEntities);
                    } else if (!entity.isAlive() && this.shotFromCrossbow()) {
                        CriteriaTriggers.KILLED_BY_CROSSBOW.trigger(serverplayerentity, Arrays.asList(entity));
                    }
                }
            }

            this.playSound(getDefaultHitGroundSoundEvent(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            if (this.getPierceLevel() <= 0) {
                this.remove(RemovalReason.KILLED);
            }
        } else {
            entity.setRemainingFireTicks(k);
            this.setDeltaMovement(this.getDeltaMovement().scale(-0.1D));
            this.setYRot(getYRot() + 180.0F);
            this.yRotO += 180.0F;
            if (!this.level.isClientSide && this.getDeltaMovement().lengthSqr() < 1.0E-7D) {
                if (this.pickup == AbstractArrow.Pickup.ALLOWED) {
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }

                this.remove(RemovalReason.KILLED);
            }
        }
    }


    public void shootFromRotation(Entity shooter, float rotationPitch, float rotationYaw, float pitchDelta, float velocity, float inaccuracy) {
        float f = -DEMath.sin(rotationYaw * ((float)Math.PI / 180F)) * DEMath.cos(rotationPitch * ((float)Math.PI / 180F));
        float f1 = -DEMath.sin((rotationPitch + pitchDelta) * ((float)Math.PI / 180F));
        float f2 = DEMath.cos(rotationYaw * ((float)Math.PI / 180F)) * DEMath.cos(rotationPitch * ((float)Math.PI / 180F));
        this.shoot(f, f1, f2, velocity, inaccuracy);
        Vec3 vector3d = shooter.getDeltaMovement();
        this.setDeltaMovement(this.getDeltaMovement().add(vector3d.x, shooter.isOnGround() ? 0.0D : vector3d.y, vector3d.z));
    }

    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        super.shoot(x, y, z, velocity * velocityMult(), inaccuracy);
    }

    public abstract float velocityMult();

    public abstract float baseDamage();

    @Override
    protected ItemStack getPickupItem() {
        return null;
    }

    @Override
    protected float getWaterInertia() {
        return 0.99F;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
