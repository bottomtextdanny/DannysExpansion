package net.bottomtextdanny.dannys_expansion.common.Entities.living;

import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.Animation;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.NullAnimation;
import net.bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManager;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityData;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityDataReference;
import net.bottomtextdanny.braincell.mod.entity.serialization.RawEntityDataReference;
import net.bottomtextdanny.braincell.mod.serialization.serializers.builtin.BuiltinSerializers;
import net.bottomtextdanny.braincell.mod.world.builtin_entities.ModuledMob;
import net.bottomtextdanny.danny_expannny.object_tables.DEEntities;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.common.Entities.ai.goals.FollowTargetGoal;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.BarrenOrbEntity;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.DeserticFangEntity;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.MummySoulEntity;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.SandScarabEggEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.Timer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class MummyEntity extends ModuledMob implements Enemy {
    public static final EntityDataReference<ItemStack> FOREHEAD_ITEM_REF =
            BCDataManager.attribute(MummyEntity.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.ITEM_STACK,
                            () -> ItemStack.EMPTY,
                            "forehead")
            );
    private final EntityData<ItemStack> forehead_item;
    public final Animation riseSpike = addAnimation(new Animation(26));
    public final Animation throwOrb = addAnimation(new Animation(26));
    public final Animation throwEgg = addAnimation(new Animation(26));
    public final Animation summonAbomination = addAnimation(new Animation(30));
    public Vec3 rightHandVec = Vec3.ZERO;
    public Vec3 leftHandVec = Vec3.ZERO;
    public Timer riseSpikeTimer;
    public Timer mummySoulTimer;
    public Timer eggTimer;
    public Timer summonTimer;

    public MummyEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        this.forehead_item = bcDataManager().addSyncedData(EntityData.of(FOREHEAD_ITEM_REF));
        if (getForeheadItem() == ItemStack.EMPTY) {
            float f = this.random.nextFloat();
            if (f < 0.003F) {
                setForeheadItem(new ItemStack(Items.MUSIC_DISC_MALL));
            } else if (f < 0.01F) {
                setForeheadItem(new ItemStack(Items.DIAMOND));
            } else if (f < 0.015F) {
                setForeheadItem(new ItemStack(Items.HEART_OF_THE_SEA));
            }else if (f < 0.03F) {
                setForeheadItem(new ItemStack(Items.EMERALD));
            } else if (f < 0.04F) {
                setForeheadItem(new ItemStack(Items.GOLD_BLOCK));
            } else if (f < 0.12F) {
                setForeheadItem(new ItemStack(Items.GOLD_INGOT));
            } else if (f < 0.16F) {
                setForeheadItem(new ItemStack(Items.CLOCK));
            } else if (f < 0.2F) {
                setForeheadItem(new ItemStack(Items.QUARTZ));
            } else if (f < 0.3F) {
                setForeheadItem(new ItemStack(Items.LAPIS_LAZULI));
            } else if (f < 0.35F) {
                setForeheadItem(new ItemStack(Items.BONE));
            } else if (f < 0.4F) {
                setForeheadItem(new ItemStack(Items.RED_DYE));
            } else if (f < 0.45F) {
                setForeheadItem(new ItemStack(Items.GUNPOWDER));
            } else if (f < 0.5F) {
                setForeheadItem(new ItemStack(Items.FEATHER));
            } else if (f < 0.55F) {
                setForeheadItem(new ItemStack(Items.FLINT));
            } else if (f < 0.6F) {
                setForeheadItem(new ItemStack(Items.COAL));
            } else if (f < 0.65F) {
                setForeheadItem(new ItemStack(Items.CLAY_BALL));
            } else {
                setForeheadItem(new ItemStack(Items.GOLD_NUGGET));
            }
        }
        this.rangedTimer.setBoundBase(60);
        this.riseSpikeTimer = new Timer(170, baseBound -> baseBound + (int)(this.random.nextGaussian() * baseBound * 0.3F));
        this.mummySoulTimer = new Timer(80);
        this.eggTimer = new Timer(700);
        this.summonTimer = new Timer(250);
        this.summonTimer.setCount(70);
        this.eggTimer.setCount(200);
    }

    @Override
    public void onDeathAnimationStart() {
        super.onDeathAnimationStart();
        if (getForeheadItem() != ItemStack.EMPTY) {
            ItemEntity item = new ItemEntity(this.level, getX(), getY(), getZ(),getForeheadItem());
            this.level.addFreshEntity(item);
        }
    }

    protected void registerExtraGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MummyEntity.SummonAbominationGoal());
        this.goalSelector.addGoal(1, new MummyEntity.ThrowEggGoal());
        this.goalSelector.addGoal(1, new MummyEntity.RiseSpikeGoal());
        this.goalSelector.addGoal(1, new MummyEntity.ThrowOrbGoal());
        this.goalSelector.addGoal(2, new FollowTargetGoal(this, 1.2d, 100));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1d));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, false));
    }

    public static AttributeSupplier.Builder Attributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 120.0D)
                .add(Attributes.FOLLOW_RANGE, 35.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return true;
    }


    @Override
    public void tick() {
        super.tick();

        if (isEffectiveAi()) {

            if (getTarget() != null) {
                LivingEntity livingEntity = getTarget();

                this.mummySoulTimer.tryUp();
                this.riseSpikeTimer.tryUp();
                this.eggTimer.tryUp();
                this.summonTimer.tryUp();

                if (this.getHealth() < this.getMaxHealth() * 0.75F && this.reachTo(livingEntity) < 2F && this.mummySoulTimer.getCount() >= 50) {
                    MummySoulEntity mummySoulEntity = new MummySoulEntity(DEEntities.MUMMY_SOUL.get(), this.level);
                    float yawToTarget = DEMath.getTargetYaw(this, getTarget());
                    float f0 = DEMath.sin(yawToTarget * ((float) Math.PI / 180F));
                    float f1 = DEMath.cos(yawToTarget * ((float) Math.PI / 180F));
                    float f2 = DEMath.sin((Mth.wrapDegrees(yawToTarget) + -90) * ((float) Math.PI / 180F));
                    float f3 = DEMath.cos((Mth.wrapDegrees(yawToTarget) + -90) * ((float) Math.PI / 180F));

                    mummySoulEntity.absMoveTo(getX() - 0.9 * -f0 - 0.5 * -f2, getY() + 1, getZ() - 0.9 * f1 - 0.5 * f3, yawToTarget, 0);
                    mummySoulEntity.setCaster(this);
                    this.level.addFreshEntity(mummySoulEntity);
                    this.mummySoulTimer.reset();
                }
            }

            if (this.mainAnimationHandler.isPlaying(this.summonAbomination)) {
                if (this.mainAnimationHandler.getTick() == 2) {
                    playSound(DESounds.ES_MUMMY_SUMMON_ABOMINATION.get(), 1.0F, 1.0F + this.random.nextFloat() * 0.1F);
                }
            }

        } else {
            if (this.mainAnimationHandler.isPlaying(this.summonAbomination)) {

                this.level.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, Blocks.OAK_PLANKS.defaultBlockState()), this.rightHandVec.x, this.rightHandVec.y, this.rightHandVec.z, 0, -0.1, 0);
                this.level.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, Blocks.OAK_PLANKS.defaultBlockState()), this.leftHandVec.x, this.leftHandVec.y, this.leftHandVec.z, 0, -0.1, 0);

            }
        }

        if (this.mainAnimationHandler.isPlaying(this.throwEgg)) {

            if (!this.level.isClientSide()) {

                if (hasAttackTarget()) {
                    getLookControl().setLookAt(getTarget(), 999.0F, 30.0F);

                    if (this.mainAnimationHandler.getTick() == 6) {

                        playSound(DESounds.ES_SWOOSH.get(), 1.0F, 0.6F);
                    }
                }
            }

            setYRot(this.yHeadRot);
            this.yBodyRot = this.yHeadRot;
        }
    }

    public void setForeheadItem(ItemStack stack) {
        this.forehead_item.set(stack);
    }

    public ItemStack getForeheadItem() {
        return this.forehead_item.get();
    }

    @Nullable
    @Override
    public SoundEvent getLivingSound() {
        return DESounds.ES_MUMMY_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return DESounds.ES_MUMMY_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return DESounds.ES_MUMMY_DEATH.get();
    }

    class ThrowOrbGoal extends Goal {

        public void start() {
            super.start();
            MummyEntity.this.mainAnimationHandler.play(MummyEntity.this.throwOrb);
            MummyEntity.this.rangedTimer.reset();
            MummyEntity.this.sleepPathSchedule.setSleep(MummyEntity.this.mainAnimationHandler.get().getDuration());
        }

        @Override
        public void tick() {
            super.tick();
            if (hasAttackTarget()) {

                getLookControl().setLookAt(getTarget(), 30.0F, 30.0F);

                if (MummyEntity.this.mainAnimationHandler.getTick() == 5) {
                    BarrenOrbEntity barrenOrbEntity = DEEntities.BARREN_ORB.get().create(MummyEntity.this.level);

                    if (barrenOrbEntity != null) {

                        barrenOrbEntity.setPos(getX(), getY() + 2.5F, getZ());
                        float yaw = DEMath.getTargetYaw(barrenOrbEntity, getTarget());
                        float pitch = DEMath.getTargetPitch(barrenOrbEntity, getTarget());
                        barrenOrbEntity.setRotations(yaw, pitch);
                        barrenOrbEntity.setCaster(MummyEntity.this);
                        MummyEntity.this.level.addFreshEntity(barrenOrbEntity);
                    }

                    if (MummyEntity.this.mainAnimationHandler.getTick() == 16) {
                        playSound(DESounds.ES_SWOOSH.get(), 0.6F, 1.0F + MummyEntity.this.random.nextFloat() * 0.1F);
                    }
                }
            }
        }

        @Override
        public boolean canContinueToUse() {
            return MummyEntity.this.mainAnimationHandler.isPlaying(MummyEntity.this.throwOrb);
        }

        @Override
        public boolean canUse() {
            return hasAttackTarget() && MummyEntity.this.mainAnimationHandler.isPlayingNull() && hasLineOfSight(getTarget()) && MummyEntity.this.rangedTimer.hasEnded();
        }

        @Override
        public void stop() {
            super.stop();
        }
    }

    class ThrowEggGoal extends Goal {

        public void start() {
            super.start();
            MummyEntity.this.mainAnimationHandler.play(MummyEntity.this.throwEgg);
            MummyEntity.this.eggTimer.reset();
            MummyEntity.this.sleepPathSchedule.setSleep(MummyEntity.this.mainAnimationHandler.get().getDuration());

        }

        @Override
        public void tick() {
            super.tick();
            if (MummyEntity.this.mainAnimationHandler.getTick() == 9 && hasAttackTarget()) {
                SandScarabEggEntity egg = DEEntities.SAND_SCARAB_EGG.get().create(MummyEntity.this.level);

                if (egg != null) {
                    Vec3 vec0 = DEMath.fromPitchYaw(-15, getYRot()).multiply(0.6, 0.6, 0.6);

                    egg.setPos(getX(), getY() + 1.1F, getZ());
                    float yaw;

                    if (hasAttackTarget()) {

                        yaw = DEMath.getTargetYaw(egg, getTarget());
                    } else {

                        yaw = getYRot();
                    }

                    float pitch = DEMath.getTargetPitch(egg, getTarget());
                    egg.setRotations(yaw, pitch);
                    egg.setCaster(MummyEntity.this);
                    egg.push(vec0.x, vec0.y, vec0.z);
                    MummyEntity.this.level.addFreshEntity(egg);
                }
            }
        }

        @Override
        public boolean canContinueToUse() {
            return MummyEntity.this.mainAnimationHandler.isPlaying(MummyEntity.this.throwEgg);
        }

        @Override
        public boolean canUse() {
            return hasAttackTarget() && MummyEntity.this.mainAnimationHandler.isPlayingNull() && hasLineOfSight(getTarget()) && MummyEntity.this.eggTimer.hasEnded();
        }

        @Override
        public void stop() {
            super.stop();
        }
    }

    class SummonAbominationGoal extends Goal {

        public void start() {
            super.start();
            MummyEntity.this.mainAnimationHandler.play(MummyEntity.this.summonAbomination);
            MummyEntity.this.summonTimer.reset();
            MummyEntity.this.sleepPathSchedule.setSleep(MummyEntity.this.mainAnimationHandler.get().getDuration());
        }

        @Override
        public void tick() {
            super.tick();
            if (MummyEntity.this.mainAnimationHandler.getTick() == 10 && hasAttackTarget()) {
                AridAbominationEntity abomination = DEEntities.ARID_ABOMINATION.get().create(MummyEntity.this.level);

                if (abomination != null) {
                    Vec3 vec0 = DEMath.fromPitchYaw(-15, getYRot()).multiply(0.6, 0.6, 0.6);

                    float yaw;

                    if (hasAttackTarget()) {

                        yaw = DEMath.getTargetYaw(abomination, getTarget());
                    } else {

                        yaw = getYRot();
                    }

                    float pitch = DEMath.getTargetPitch(abomination, getTarget());

                    abomination.setTamed(true);
                    abomination.setSummoner(MummyEntity.this);
                    abomination.absMoveTo(getX(), getY(), getZ(), yaw, pitch);
                    abomination.push(vec0.x, vec0.y, vec0.z);
                    MummyEntity.this.level.addFreshEntity(abomination);
                }
            }
        }

        @Override
        public boolean canContinueToUse() {
            return MummyEntity.this.mainAnimationHandler.isPlaying(MummyEntity.this.summonAbomination);
        }

        @Override
        public boolean canUse() {
            return hasAttackTarget() && MummyEntity.this.mainAnimationHandler.isPlayingNull() && hasLineOfSight(getTarget()) && MummyEntity.this.summonTimer.hasEnded();
        }

        @Override
        public void stop() {
            super.stop();
        }
    }

    class RiseSpikeGoal extends Goal {
        double lastTickPozX;
        double lastTickPozZ;

        public void start() {
            super.start();
            MummyEntity.this.mainAnimationHandler.play(MummyEntity.this.riseSpike);
            MummyEntity.this.riseSpikeTimer.reset();
            MummyEntity.this.sleepPathSchedule.setSleep(MummyEntity.this.mainAnimationHandler.get().getDuration());
        }

        @Override
        public void tick() {
            super.tick();
            if (hasAttackTarget()) {
                if (MummyEntity.this.mainAnimationHandler.getTick() == 11) {
                    this.lastTickPozX = getTarget().xOld;
                    this.lastTickPozZ = getTarget().zOld;

                }
                if (MummyEntity.this.mainAnimationHandler.getTick() == 12) {
                    DeserticFangEntity deserticFangEntity = DEEntities.DESERTIC_FANG.get().create(MummyEntity.this.level);
                    if (deserticFangEntity != null) {
                        double lastTickDifX = Mth.clamp(getTarget().getX() - this.lastTickPozX, -0.5, 0.5);
                        double lastTickDifZ = Mth.clamp(getTarget().getZ() - this.lastTickPozZ, -0.5, 0.5);
                        BlockPos blockpos = new BlockPos(getTarget().getX() + lastTickDifX * 6, getTarget().getY(), getTarget().getZ() + lastTickDifZ * 6);
                        boolean flag = false;
                        double d0 = 0;

                        deserticFangEntity.setCaster(MummyEntity.this);

                        do {
                            BlockPos blockpos1 = blockpos.below();
                            BlockState blockstate = MummyEntity.this.level.getBlockState(blockpos1);
                            if (blockstate.isFaceSturdy(MummyEntity.this.level, blockpos1, Direction.UP)) {
                                if (!MummyEntity.this.level.isEmptyBlock(blockpos)) {
                                    BlockState blockstate1 = MummyEntity.this.level.getBlockState(blockpos);
                                    VoxelShape voxelshape = blockstate1.getCollisionShape(MummyEntity.this.level, blockpos);
                                    if (!voxelshape.isEmpty()) {
                                        d0 = voxelshape.max(Direction.Axis.Y);
                                    }
                                }

                                flag = true;
                                break;
                            }

                            blockpos = blockpos.below();
                        } while(blockpos.getY() >= Mth.floor(d0) - 1);

                        if (flag) {
                            deserticFangEntity.absMoveTo(getTarget().getX() + lastTickDifX * 6, blockpos.getY() + d0, getTarget().getZ() + lastTickDifZ * 6, DEMath.getTargetYaw(deserticFangEntity, MummyEntity.this), 0);
                            MummyEntity.this.level.addFreshEntity(deserticFangEntity);
                        }
                    }
            }

            }
        }

        @Override
        public boolean canContinueToUse() {
            return MummyEntity.this.mainAnimationHandler.isPlaying(MummyEntity.this.riseSpike);
        }

        @Override
        public boolean canUse() {
            return hasAttackTarget() && MummyEntity.this.mainAnimationHandler.isPlayingNull() && hasLineOfSight(getTarget()) && MummyEntity.this.riseSpikeTimer.hasEnded();
        }

        @Override
        public void stop() {
            super.stop();
            MummyEntity.this.sleepPathSchedule.wake();
            MummyEntity.this.mainAnimationHandler.play(NullAnimation.UNI);
        }
    }
}
