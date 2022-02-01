package net.bottomtextdanny.danny_expannny.objects.entities.animal.rammer;

import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationGetter;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.SimpleAnimation;
import net.bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManager;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityData;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityDataReference;
import net.bottomtextdanny.braincell.mod.entity.serialization.RawEntityDataReference;
import net.bottomtextdanny.braincell.mod.serialization.serializers.builtin.BuiltinSerializers;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.common.Entities.ai.goals.FollowTargetGoal;
import net.bottomtextdanny.dannys_expansion.common.Entities.ai.goals.PlayAnimationGoal;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.OwnableMountEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class GrandRammerEntity extends OwnableMountEntity {
    public static final EntityDataReference<Float> SIZE_REF =
            BCDataManager.attribute(GrandRammerEntity.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.FLOAT,
                            () -> 0.0F,
                            "size")
            );
    public static final EntityDataReference<Boolean> SADDLED_REF =
            BCDataManager.attribute(GrandRammerEntity.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.BOOLEAN,
                            () -> false,
                            "saddled")
            );
    public static final SimpleAnimation RAM = new SimpleAnimation(20);
    public final EntityData<Float> size;
    public final EntityData<Boolean> saddled;
    private boolean updatedSize;

    public GrandRammerEntity(EntityType<? extends GrandRammerEntity> type, Level worldIn) {
        super(type, worldIn);
        this.maxUpStep = 1.2F;
        this.size = bcDataManager().addNonSyncedData(EntityData.of(SIZE_REF));
        this.saddled = bcDataManager().addSyncedData(EntityData.of(SADDLED_REF));
    }

    @Override
    protected void commonInit() {
    }

    @Override
    public AnimationGetter getAnimations() {
        return RAM;
    }

    protected void registerExtraGoals() {
        this.goalSelector.addGoal(1, new PlayAnimationGoal(this, RAM, o -> hasAttackTarget() && this.mainHandler.isPlayingNull() && distanceTo(getTarget()) < targetDistance(1.0F)));
        this.goalSelector.addGoal(2, new FollowTargetGoal(this, 1.25d));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1d));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this) {
            @Override
            public boolean canUse() {
                return super.canUse() && !isTamed();
            }
        });
    }

    public static AttributeSupplier.Builder Attributes() {
        return createMobAttributes()
                .add(Attributes.MAX_HEALTH, 60.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 3.0D);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.updatedSize) {
            refreshDimensions();
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(getAttribute(Attributes.MAX_HEALTH).getBaseValue() * this.getSize());
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() * this.getSize());
            this.updatedSize = true;
        }

        if (this.mainHandler.isPlaying(RAM)) {

            if (this.mainHandler.getTick() == 4) {
                this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), DESounds.ES_POSSESSED_ARMOR_SLASH.get(), this.getSoundSource(), 1.0F, this.random.nextFloat() * 0.3F + 0.7F, false);
            }

            if (isVehicle()) {

                if (this.mainHandler.getTick() == 9) {
                    float f1 = DEMath.sin(this.getYRot() * ((float)Math.PI / 180F));
                    float f2 = DEMath.cos(this.getYRot() * ((float)Math.PI / 180F));
                    List<LivingEntity> entities = this.level.getEntitiesOfClass(LivingEntity.class, new AABB(this.getX() - 1.2 * getSize() + -f1 * 2 * getSize(), this.getY() - 0.3 * getSize(), this.getZ() - 1.2 * getSize() + f2 * 2 * getSize(), this.getX() + 1.2F * getSize() + -f1 * 2 * getSize(), this.getY() + getBbHeight() + 0.3 * getSize(), this.getZ() + 1.2F * getSize() + f2 * 2 * getSize()));

                    for (LivingEntity livingEntity : entities) {
                        if (!(livingEntity == this || livingEntity == getControllingPassenger())) {
                            double d1 = livingEntity.getX() - this.getX();
                            double d2 = livingEntity.getZ() - this.getZ();
                            double d3 = Math.max(d1 * d1 + d2 * d2, 0.001D);

                            livingEntity.push(d1 / d3 * 4.0D, 0.3D, d2 / d3 * 4.0D);

                            if (getControllingPassenger() instanceof LivingEntity) {
                                livingEntity.hurt(DamageSource.indirectMobAttack(this, (LivingEntity)getControllingPassenger()), (float) getAttribute(Attributes.ATTACK_DAMAGE).getValue());

                            } else {
                                livingEntity.hurt(DamageSource.indirectMobAttack(this, null), (float) getAttribute(Attributes.ATTACK_DAMAGE).getValue());
                            }
                        }
                    }
                }
            } else {
                getNavigation().stop();
                setYRot(this.yHeadRot);

                if (hasAttackTarget()) {
                    getLookControl().setLookAt(getTarget(), 30.0F, 30.0F);

                    if (this.mainHandler.getTick() == 7 && distanceTo(getTarget()) <= 11) doHurtTarget(getTarget());
                }
            }
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        setSize(1 + this.random.nextFloat() * 0.3F);
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);


        if (itemstack.getItem() == Items.GOLDEN_APPLE && !isTamed()) {
            setTamedBy(player);
            this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EAT, this.getSoundSource(), 1.0F, this.random.nextFloat() * 0.3F + 0.7F, false);

            consumeItemFromStack(player, itemstack);

            return InteractionResult.CONSUME;
        }

        if (itemstack.getItem() == Items.SADDLE && isTamed() && !isSaddled()) {
            this.saddled.set(true);
            this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.HORSE_SADDLE, this.getSoundSource(), 1.0F + this.random.nextFloat(), this.random.nextFloat() * 0.7F + 0.3F, false);

            consumeItemFromStack(player, itemstack);

            return InteractionResult.CONSUME;
        }

        if (!isVehicle() && this.saddled.get()) {
            if (!this.level.isClientSide() && isOwner(player)) {
                this.setRiddenBy(player);
            }
        }

        return super.interactAt(player, vec, hand);
    }



    @Override
    public void travel(Vec3 p_213352_1_) {
        if (this.isAlive() && getControllingPassenger() instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity) this.getControllingPassenger();
            if (this.isVehicle() && livingentity != null) {
                this.setYRot(livingentity.getYRot());
                this.yRotO = this.getYRot();
                this.setXRot(livingentity.getXRot() * 0.5F);
                this.setRot(this.getYRot(), this.getXRot());
                this.yBodyRot = this.getYRot();
                this.yHeadRot = this.yBodyRot;
                float f = livingentity.xxa * 0.15F;
                float f1 = livingentity.zza * 0.2F;

                if (f1 < 0.0F) {
                    f1 = f1 / 2;
                }

                if (!onGround() || onGround() && isInWater()) {
                    f = (float) (livingentity.xxa * 0.03);
                    f1 = (float) (livingentity.zza * 0.04);
                } else if (this.mainHandler.isPlaying(RAM) && this.mainHandler.getTick() < 10){
                    f = (float) (livingentity.xxa * 0.075);
                    f1 = (float) (livingentity.zza * 0.1);
                }


                float f2 = DEMath.sin(this.getYRot() * ((float)Math.PI / 180F));
                float f3 = DEMath.cos(this.getYRot() * ((float)Math.PI / 180F));
                float f4 = DEMath.sin(this.rotate(Rotation.COUNTERCLOCKWISE_90) * ((float)Math.PI / 180F));
                float f5 = DEMath.cos(this.rotate(Rotation.COUNTERCLOCKWISE_90) * ((float)Math.PI / 180F));

                this.setDeltaMovement(this.getDeltaMovement().add(f2 * -f1 + f4 * -f, 0.0D, f3 * f1 + f5 * f));


            }

        }
        super.travel(p_213352_1_);

    }

    public void positionRider(Entity passenger) {
        super.positionRider(passenger);

        passenger.setPos(passenger.getX(), getY() + getBoundingBox().getYsize() - 0.625, passenger.getZ());


        if (this.mainHandler.isPlaying(RAM)) {
            float f3 = DEMath.sin(this.yBodyRot * ((float)Math.PI / 180F));
            float f = DEMath.cos(this.yBodyRot * ((float)Math.PI / 180F));
            float f1 = 0;

            f1 += DEMath.freeAnimator(0.7F * getSize(), 0F, 0, 5, Easing.LINEAR, this.mainHandler.getTick());
            f1 += DEMath.freeAnimator(-1.3F * getSize(), 0.7F * getSize(), 5, 9, Easing.LINEAR, this.mainHandler.getTick());
            f1 += DEMath.freeAnimator(0.6F * getSize(), -0.6F * getSize(), 9, 15, Easing.LINEAR, this.mainHandler.getTick());
            passenger.setPos(this.getX() + f1 * f3, passenger.getY(), this.getZ() - f1 * f);
        }
    }

    @Override
    protected int calculateFallDamage(float p_225508_1_, float p_225508_2_) {
        return super.calculateFallDamage(p_225508_1_, p_225508_2_) / 2;
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return 1.2F;
    }

    public void setSize(float sizeIn) {
        this.size.set(sizeIn);
        this.updatedSize = false;
    }

    public float getSize() {return this.size.get(); }

    public EntityDimensions getDimensions(Pose poseIn) {return super.getDimensions(poseIn).scale(getSize());}

    public boolean isSaddled() {return this.saddled.get(); }
    
    @Override
    public void doAct() {
        this.mainHandler.play(RAM);
    }

    @Override
    public int progressAddition() {
        return 1000;
    }

    @Override
    public int progressSubtraction() {
        return -30;
    }

    @Override
    public boolean usesAbilityBar() {
        return false;
    }

    protected boolean shouldDespawnInPeaceful() {
        return false;
    }
}
