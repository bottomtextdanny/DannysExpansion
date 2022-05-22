package bottomtextdanny.dannys_expansion.content.entities.projectile.arrow;

import bottomtextdanny.dannys_expansion.content.items.arrow.DEArrowItem;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import bottomtextdanny.braincell.base.BCMath;
import bottomtextdanny.braincell.base.ObjectFetcher;
import bottomtextdanny.braincell.mod._base.serialization.WorldPacketData;
import bottomtextdanny.braincell.mod._base.serialization.builtin.BuiltinSerializers;
import bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManager;
import bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManagerProvider;
import bottomtextdanny.braincell.mod.entity.serialization.EntityData;
import bottomtextdanny.braincell.mod.entity.serialization.EntityDataReference;
import bottomtextdanny.braincell.mod.entity.serialization.RawEntityDataReference;
import bottomtextdanny.braincell.mod.world.entity_utilities.EntityClientMessenger;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class DEArrow extends AbstractArrow implements BCDataManagerProvider, EntityClientMessenger {
    public static final int SET_TEXTURE_FLAG = 0;
    public static final EntityDataReference<ItemStack> PICK_ITEM_REF =
            BCDataManager.attribute(DEArrow.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.ITEM_STACK,
                            () -> Items.ARROW.getDefaultInstance(),
                            "pick_item")
            );
    public static final EntityDataReference<Float> DAMAGE_REF =
            BCDataManager.attribute(DEArrow.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.FLOAT,
                            () -> 2.0F,
                            "dmg")
            );
    public static final EntityDataReference<Float> WATER_INERTIA_REF =
            BCDataManager.attribute(DEArrow.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.FLOAT,
                            () -> 1.0F,
                            "water_vel")
            );
    public static final EntityDataReference<Float> VELOCITY_FACTOR_REF =
            BCDataManager.attribute(DEArrow.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.FLOAT,
                            () -> 1.0F,
                            "vel")
            );
    public static final EntityDataReference<Float> KNOCKBACK_REF =
            BCDataManager.attribute(DEArrow.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.FLOAT,
                            () -> 1.0F,
                            "kb")
            );
    private final BCDataManager bcDataManager;
    @Nullable
    private IntOpenHashSet piercedEntities;
    @Nullable
    private List<Entity> hitEntities;
    private final EntityData<ItemStack> pickItem;
    private final EntityData<Float> damage;
    private final EntityData<Float> knockback;
    private final EntityData<Float> velocityFactor;
    private final EntityData<Float> waterInertia;

    public DEArrow(EntityType<? extends AbstractArrow> type, Level level) {
        super(type, level);
        this.bcDataManager = new BCDataManager(this);
        this.pickItem = bcDataManager().addSyncedData(EntityData.of(PICK_ITEM_REF));
        this.damage = bcDataManager().addSyncedData(EntityData.of(DAMAGE_REF));
        this.knockback = bcDataManager().addSyncedData(EntityData.of(KNOCKBACK_REF));
        this.velocityFactor = bcDataManager().addSyncedData(EntityData.of(VELOCITY_FACTOR_REF));
        this.waterInertia = bcDataManager().addSyncedData(EntityData.of(WATER_INERTIA_REF));
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        if (!level.isClientSide) {
            syncPickItem();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity target = result.getEntity();
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

            this.piercedEntities.add(target.getId());
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
                ((LivingEntity)entity1).setLastHurtMob(target);
            }
        }

        boolean flag = target.getType() == EntityType.ENDERMAN;
        int k = target.getRemainingFireTicks();
        if (this.isOnFire() && !flag) {
            target.setSecondsOnFire(5);
        }

        if (target.hurt(damagesource, (float)i)) {
            if (pickItem.get().getItem() instanceof DEArrowItem arrowItem) {
                arrowItem.onEntityHurt(this, target, i);
            }

            if (flag) {
                return;
            }

            if (target instanceof LivingEntity livingTarget) {
                float knockbackFactor = getKnockbackFactor();

                if (knockbackFactor > 0) {
                    Vec3 vector3d = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale(knockbackFactor * 0.6D);
                    if (vector3d.lengthSqr() > 0.0D) {
                        livingTarget.push(vector3d.x, 0.1D, vector3d.z);
                    }
                }

                if (!this.level.isClientSide && entity1 instanceof LivingEntity) {
                    EnchantmentHelper.doPostHurtEffects(livingTarget, entity1);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity)entity1, livingTarget);
                }

                this.doPostHurtEffects(livingTarget);
                if (entity1 != null && livingTarget != entity1 && livingTarget instanceof Player && entity1 instanceof ServerPlayer && !this.isSilent()) {
                    ((ServerPlayer)entity1).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
                }

                if (!target.isAlive() && this.hitEntities != null) {
                    this.hitEntities.add(livingTarget);
                }

                if (!this.level.isClientSide && entity1 instanceof ServerPlayer) {
                    ServerPlayer serverplayerentity = (ServerPlayer)entity1;
                    if (this.hitEntities != null && this.shotFromCrossbow()) {
                        CriteriaTriggers.KILLED_BY_CROSSBOW.trigger(serverplayerentity, this.hitEntities);
                    } else if (!target.isAlive() && this.shotFromCrossbow()) {
                        CriteriaTriggers.KILLED_BY_CROSSBOW.trigger(serverplayerentity, Arrays.asList(target));
                    }
                }
            }

            this.playSound(getDefaultHitGroundSoundEvent(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            if (this.getPierceLevel() <= 0) {
                this.remove(RemovalReason.KILLED);
            }
        } else {
            target.setRemainingFireTicks(k);
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
        float f = -BCMath.sin(rotationYaw * ((float)Math.PI / 180F)) * BCMath.cos(rotationPitch * ((float)Math.PI / 180F));
        float f1 = -BCMath.sin((rotationPitch + pitchDelta) * ((float)Math.PI / 180F));
        float f2 = BCMath.cos(rotationYaw * ((float)Math.PI / 180F)) * BCMath.cos(rotationPitch * ((float)Math.PI / 180F));
        this.shoot(f, f1, f2, velocity, inaccuracy);
        Vec3 vector3d = shooter.getDeltaMovement();
        this.setDeltaMovement(this.getDeltaMovement().add(vector3d.x, shooter.isOnGround() ? 0.0D : vector3d.y, vector3d.z));
    }

    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        super.shoot(x, y, z, velocity * getVelocityFactor(), inaccuracy);
    }

    @Override
    public void clientCallOutHandler(int flag, ObjectFetcher fetcher) {
        if (flag == SET_TEXTURE_FLAG) {
            pickItem.set(fetcher.get(0));
        }
    }

    public void setPickItem(ItemStack item) {
        if (item == null) item = ItemStack.EMPTY;
        pickItem.set(item);
    }

    public void syncPickItem() {
        if (isAddedToWorld())
            sendClientMsg(SET_TEXTURE_FLAG, WorldPacketData.of(BuiltinSerializers.ITEM_STACK, getPickupItem()));
    }

    public void setDamage(float value) {
        damage.set(value);
    }

    public void setKnockback(float value) {
        knockback.set(value);
    }

    public void setVelocityFactor(float value) {
        velocityFactor.set(value);
    }

    public void setWaterInertia(float value) {
        waterInertia.set(value);
    }

    public ItemStack getPickItem() {
        return pickItem.get();
    }

    @Override
    public double getBaseDamage() {
        return getDamage();
    }

    public float getDamage() {
        return damage.get();
    }

    public float getKnockbackFactor() {
        return knockback.get();
    }

    public float getVelocityFactor() {
        return velocityFactor.get();
    }

    public float getWaterInertia() {
        return waterInertia.get();
    }

    @Override
    protected ItemStack getPickupItem() {
        return getPickItem();
    }

    @Override
    public BCDataManager bcDataManager() {
        return this.bcDataManager;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
