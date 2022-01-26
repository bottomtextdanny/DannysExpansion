package net.bottomtextdanny.danny_expannny.objects.entities.mob.slime;

import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.Animation;
import net.bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManager;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityData;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityDataReference;
import net.bottomtextdanny.braincell.mod.entity.serialization.RawEntityDataReference;
import net.bottomtextdanny.braincell.mod.serialization.serializers.builtin.BuiltinSerializers;
import net.bottomtextdanny.danny_expannny.object_tables.items.DEItems;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.OwnableMountEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.FloatAnimator;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.ISimpleAnimator;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.UUID;

public class BlueSlimeEntity extends OwnableMountEntity {
	public final int PROTECTIVE_TIME = 300;
    public static final EntityDataReference<Integer> PROTECTIVE_TICKS_REF =
            BCDataManager.attribute(BlueSlimeEntity.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.INTEGER,
                            () -> 0,
                            "protective_ticks")
            );
    public final Animation backToItem = addAnimation(new Animation(20));
    public final Animation fromItem = addAnimation(new Animation(20));
    public final Animation jump = addAnimation(new Animation(13));
    private final EntityData<Integer> protective_ticks;
    private final ISimpleAnimator<Float> passengerMover = new FloatAnimator(0);
    private boolean wasJumping;

    public BlueSlimeEntity(EntityType<? extends BlueSlimeEntity> type, Level worldIn) {
        super(type, worldIn);
        this.maxUpStep = 1.2F;
        this.protective_ticks = bcDataManager().addSyncedData(EntityData.of(PROTECTIVE_TICKS_REF));
    }

    public static AttributeSupplier.Builder Attributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.12D);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        AttributeModifier attributemodifier = new AttributeModifier(UUID.fromString("ECfA401C-4A16-11EB-B378-0242AC130002"), "BlueSlimeProtection", 120F, AttributeModifier.Operation.ADDITION);
        this.fallDistance = 0;

        if (!this.mainAnimationHandler.isPlayingNull()) {
            if (this.mainAnimationHandler.isPlaying(this.backToItem)) {
                if (this.mainAnimationHandler.getTick() == 20) {

                    ItemStack stack = new ItemStack(DEItems.BLUE_SLIME.get());
                    stack.getOrCreateTagElement("BlueSlimeTags");
                    CompoundTag compoundNBT = stack.getTagElement("BlueSlimeTags");

                    if (this.hasCustomName()) {
                        compoundNBT.putString("name", getName().getString());
                    } else {
                        compoundNBT.putString("name", new TranslatableComponent("entity.dannys_expansion.blue_slime").getString());
                    }

                    compoundNBT.putFloat("health", getHealth());

                    if (getOwner() != null) {
                        compoundNBT.putUUID("owner", getOwner().getUUID());
                    }

                    ItemEntity itemEntity = new ItemEntity(this.level, position().x, position().y, position().z, stack);

                    itemEntity.setDeltaMovement(0.0F, 0.5F, 0.0F);

                    this.level.addFreshEntity(itemEntity);
                    remove(RemovalReason.DISCARDED);
                }
            }
        }

        if (getProtectiveTicks() > -1) {
            setProtectiveTicks(getProtectiveTicks() + 1);
            push(0, -0.05, 0);

            if (!getAttribute(Attributes.ARMOR).hasModifier(attributemodifier)) {
                getAttribute(Attributes.ARMOR).addPermanentModifier(attributemodifier);
            }

            if (getProtectiveTicks() == this.PROTECTIVE_TIME - 5) {
                playSound(DESounds.ES_BLUE_SLIME_EXIT.get(), 1.0F, 1.0F);
            } else if (getProtectiveTicks() > this.PROTECTIVE_TIME) {
                setProtectiveTicks(-1);
            }

            if (!getDimensions(getPose()).equals(new EntityDimensions(1.9125F, 1.4875F, super.getDimensions(getPose()).fixed))) {
                refreshDimensions();
            }
        } else {
            if (!getDimensions(getPose()).equals(new EntityDimensions(1.125F, 0.875F, super.getDimensions(getPose()).fixed))) {
                refreshDimensions();
            }

            if (getAttribute(Attributes.ARMOR).hasModifier(attributemodifier)) {
                getAttribute(Attributes.ARMOR).removeModifier(attributemodifier);
            }
        }

        if (!this.level.isClientSide) {
            if (this.wasJumping) {
                if (onGround()) {
                    playSound(SoundEvents.HONEY_BLOCK_BREAK, 1.4F, 0.6F + this.random.nextFloat() * 0.2F);
                    this.wasJumping = false;
                }
            }
        }


    }

    @Override
    public boolean isCustomNameVisible() {
        return false;
    }

    @Override
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand) {
        if (getOwner() == null) {
            setTamedBy(player);
        }

        if (isOwner(player) && this.mainAnimationHandler.isPlayingNull() && getControllingPassenger() != player) {
            if (player.isShiftKeyDown() && getProtectiveTicks() < 0) {
                this.mainAnimationHandler.play(this.backToItem);
            } else {
                setRiddenBy(player);
            }
        }

        return super.interactAt(player, vec, hand);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.isAlive() && getControllingPassenger() instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity) this.getControllingPassenger();
            float f = livingentity.xxa * 0.5F;
            float f1 = livingentity.zza;
            if (this.isVehicle() && livingentity != null) {
                setYRot(livingentity.getYRot());
                this.yRotO = this.getYRot();
                this.setRot(this.getYRot(), this.getXRot());
                this.yBodyRot = this.getYRot();
                this.yHeadRot = this.yBodyRot;

                float speed = (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED);

                if (f1 < 0.0F) {
                    f1 /= 2;
                }

                if (onGround()) {
                    if (getProgress01() > 0 && getProgressDifference() >= 0 && getProtectiveTicks() < 0){
                        speed *= 0.6F;
                    }

                    if (isInWater()) {
                        speed *= 0.5;
                    }
                } else {
                    speed *= 1.55;
                }

                if (getProtectiveTicks() > -1) {
                    speed *= 2.75;
                }

                setSpeed(speed);
                setZza(f1);
                setXxa(f);
                this.flyingSpeed = 0.23F * speed;
            }
            super.travel(new Vec3(f, travelVector.y, f1));
        } else {
            this.flyingSpeed = 0.02F;
            super.travel(travelVector);
        }
    }

    public void positionRider(Entity passenger) {
        super.positionRider(passenger);

        passenger.setPos(passenger.getX(), getY() + getBoundingBox().getYsize() - 0.625, passenger.getZ());
        float f1 = 0;

        if (getProgress01() > 0) {
            this.passengerMover.reset();
            this.passengerMover.actual(getProgress01());
            this.passengerMover.addPoint(1, -0.3F);
            f1 += this.passengerMover.get();

        }

        if (this.mainAnimationHandler.isPlaying(this.jump)) {
            this.passengerMover.reset();
            this.passengerMover.actual(this.mainAnimationHandler.getTick());
            this.passengerMover.addPoint(3, 0.4F);
            this.passengerMover.addPoint(10, 0.0F);
            f1 += this.passengerMover.get();

        }

        if (getProtectiveTicks() > -1) {
            final float f = -0.7F;
            this.passengerMover.reset();
            this.passengerMover.actual(getProtectiveTicks());
            this.passengerMover.addPoint(10, f);
            this.passengerMover.addPoint(280, this.passengerMover.getGoal());
            this.passengerMover.addPoint(10, 0.0F);
            f1 += this.passengerMover.get();

            f1 -= 0.6125;
        }

        passenger.setPos(this.getX(), passenger.getY() + f1, this.getZ());
    }

    @Override
    protected float getWaterSlowDown() {
        return 0.5F;
    }

    @Override
    public boolean isAttackable() {
        return super.isAttackable() && getProtectiveTicks() < 0;
    }

    @Override
    public void doAct() {

        if (onGround() || this.wasTouchingWater) {
            this.mainAnimationHandler.play(this.jump);
            playSound(SoundEvents.HONEY_BLOCK_BREAK, 1.2F, 0.6F + this.random.nextFloat() * 0.2F);
            setDeltaMovement(getDeltaMovement().x * 3.5 * getProgress01(), 2.0F * getProgress01() + 0.5F, getDeltaMovement().z * 3.5 * getProgress01());
            this.wasJumping = true;
        }
    }

    @Override
    public void doAbility() {
        super.doAbility();
        playSound(DESounds.ES_BLUE_SLIME_ENTER.get(), 1.0F, 1.0F);
        setProtectiveTicks(0);
    }

    @Override
    public boolean progressIncreasingParams() {
        return super.progressIncreasingParams() && this.mainAnimationHandler.isPlayingNull() && getProtectiveTicks() < 0;
    }

    @Override
    public int abilityTimerAddition() {
        return 10;
    }

    @Override
    public EntityDimensions getDimensions(Pose poseIn) {
        if (getProtectiveTicks() > -1) {
            return new EntityDimensions(1.9125F, 1.4875F, super.getDimensions(poseIn).fixed);
        }
        return super.getDimensions(poseIn);
    }

    public int getProtectiveTicks() {
        return this.protective_ticks.get();
    }

    public void setProtectiveTicks(int i) {
        this.protective_ticks.set(i);
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.HONEY_BLOCK_SLIDE;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.HONEY_BLOCK_FALL;
    }
}
