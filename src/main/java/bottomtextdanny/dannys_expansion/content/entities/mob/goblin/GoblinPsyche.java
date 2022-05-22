package bottomtextdanny.dannys_expansion.content.entities.mob.goblin;

import bottomtextdanny.dannys_expansion.content.entities.Shared;
import bottomtextdanny.dannys_expansion.tables.DEEntities;
import bottomtextdanny.dannys_expansion.tables.DESounds;
import bottomtextdanny.braincell.base.BCMath;
import bottomtextdanny.braincell.base.FloatRandomPicker;
import bottomtextdanny.braincell.base.scheduler.IntScheduler;
import bottomtextdanny.braincell.base.value_mapper.FloatMapper;
import bottomtextdanny.braincell.base.value_mapper.FloatMappers;
import bottomtextdanny.braincell.base.value_mapper.RandomIntegerMapper;
import bottomtextdanny.braincell.base.vector.DistanceCalc3;
import bottomtextdanny.braincell.mod.entity.modules.animatable.SimpleAnimation;
import bottomtextdanny.braincell.mod.entity.modules.variable.Form;
import bottomtextdanny.braincell.mod.entity.psyche.Action;
import bottomtextdanny.braincell.mod.entity.psyche.MarkedTimer;
import bottomtextdanny.braincell.mod.entity.psyche.Psyche;
import bottomtextdanny.braincell.mod.entity.psyche.actions.*;
import bottomtextdanny.braincell.mod.entity.psyche.actions.target.LookForAttackTargetAction;
import bottomtextdanny.braincell.mod.entity.psyche.actions.target.TargetBullyAction;
import bottomtextdanny.braincell.mod.entity.psyche.extras.BCAABBs;
import bottomtextdanny.braincell.mod.entity.psyche.extras.TargetConsumers;
import bottomtextdanny.braincell.mod.entity.psyche.extras.TargetSideReaction;
import bottomtextdanny.braincell.mod.entity.psyche.input.ActionInputKey;
import bottomtextdanny.braincell.mod.entity.psyche.input.UnbuiltActionInputs;
import bottomtextdanny.braincell.mod.entity.psyche.pos_finder.MobPosProcessor;
import bottomtextdanny.braincell.mod.entity.psyche.targeting.*;
import bottomtextdanny.braincell.mod.world.helpers.CombatHelper;
import bottomtextdanny.braincell.mod.world.helpers.ReachHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.function.Function;

import static bottomtextdanny.braincell.mod.entity.psyche.pos_finder.MobPosProcessors.*;

public class GoblinPsyche extends Psyche<Goblin> {
    public static final MobPosProcessor<LivingEntity> AVOID_POS = compose((blockPos, mob, randomGenerator, target) -> {
        Vec3 posDiff = mob.position().subtract(target.position());
        float oppositeFromTargetRad = (float) Mth.atan2(posDiff.z, posDiff.x) + BCMath.FPI;

        FloatMapper angleAwayFromTarget = FloatMapper.from(
                oppositeFromTargetRad - 20 * BCMath.FRAD,
                oppositeFromTargetRad + 20 * BCMath.FRAD,
                FloatRandomPicker.normal());

        FloatMapper stepAway = FloatMapper.of(14);

        return stack().generic(LivingEntity.class)
                .push(advanceHorizontal(angleAwayFromTarget, stepAway))
                .push(sample(10, randomOffset(3, 4, 3), evaluateAvoidingPos(mob)));
    });
    public static final MobPosProcessor<LivingEntity> SEEK_PEER = compose((blockPos, mob, randomGenerator, peer) -> {
        return (blockPos1, mob1, randomGenerator1, peer1) -> peer1.blockPosition();
    });
    private static final MobMatchPredicate<LivingEntity> OPPOSITE_GOBLIN = (mob, target) -> {
        return target instanceof Goblin targetGoblin
                && targetGoblin.variableModule().getForm() != ((Goblin)mob).variableModule().getForm()
                && Shared.DEFAULT_TARGET_PARAMETERS.test(mob, target);
    };
    private static final MobMatchPredicate<LivingEntity> NOT_SAME_GOBLIN = (mob, target) -> {
        return !(target instanceof Goblin targetGoblin && targetGoblin.variableModule().getForm() == ((Goblin)mob).variableModule().getForm())
                && Shared.DEFAULT_TARGET_PARAMETERS.test(mob, target);
    };
    private static final MobMatchPredicate<LivingEntity> MOUNTABLE_PEER = (mob, target) -> {
        if (target instanceof Goblin targetGoblin) {

            if (!CombatHelper.isValidAttackTarget(target)) return false;

            if (target.getPassengers().size() > Goblin.MAX_MOUNT_CHAIN_SIZE) return false;

            if (!target.isPassenger() && target.getVehicle() instanceof Goblin) return false;

            if (mob.getRandom().nextFloat() < 0.2F) {
                float height = (float) target.getPassengersRidingOffset() + mob.getBbHeight();

                for (Entity entity : target.getPassengers()) {
                    height += (float) entity.getPassengersRidingOffset();
                }

                BlockPos targetPosOffset = new BlockPos(target.position()
                        .add(0.0F, height, 0.0F));

                if (mob.level.getBlockState(targetPosOffset).getMaterial().isSolid())
                    return false;
            }

            return targetGoblin.variableModule().getForm() == ((Goblin)mob).variableModule().getForm();
        }

        return false;
    };
    private static final TargetSideReaction<Goblin> AGGRO_PEERS = (mob, targeter, target) -> {
        if (!(targeter instanceof Goblin goblinTargeter)) return;

        Form<?> variant = mob.variableModule().getForm();

        if (variant != null && variant == goblinTargeter.variableModule().getForm()) {
            LivingEntity actualTarget = mob.getTarget();

            if (!CombatHelper.isValidAttackTarget(actualTarget)) {
                mob.setAggressive(true);
                mob.setTarget(target);
            }
        }
    };
    public static final int
            ANIMATION_ACTIONS_MODULE = 1,
            MOUNT_MODULE = 2,
            AVOID_MODULE = 3,
            MELEE_MODULE = 4,
            IDLE_ACTIONS_MODULE = 5;
    private final MarkedTimer unseenTimer;
    private LookRandomlyAction lookRandomlyAction;
    private RandomStrollAction randomStrollAction;
    private MoveReactionToEntityAction<Goblin, LivingEntity> avoidTarget;
    private MoveReactionToEntityAction<Goblin, LivingEntity> mountPeerAction;
    private FollowTargetAction<Goblin> followTargetAction;
    private ThrowAttackAction throwAction;
    private BumpAttackAction bumpAction;
    private DoubleSlashAction doubleSlashAction;
    private SlashAction slashAction;
    private StabAction stabAction;

    public GoblinPsyche(Goblin mob) {
        super(mob);
        this.unseenTimer = new MarkedTimer(IntScheduler.simple(100));

        //allocate all the indices you will need for your AI, we call each "module".
        allocateModules(6);
    }

    @Override
    protected void populateInputs(UnbuiltActionInputs inputs) {
        //this input means the time the mob will take before forgetting its last-seen target.
        inputs.put(ActionInputKey.MARKED_UNSEEN, () -> this.unseenTimer);

        //this input is what will be run each time a new target is set by running actions.
        inputs.put(ActionInputKey.SET_TARGET_CALL, () -> {
            getMob().getMeleeAttackDelay().end();
        });
    }

    @Override
    protected void initialize() {
        this.throwAction = new ThrowAttackAction(this.getMob());
        this.bumpAction = new BumpAttackAction(this.getMob());
        this.doubleSlashAction = new DoubleSlashAction(this.getMob());
        this.slashAction = new SlashAction(this.getMob());
        this.stabAction = new StabAction(this.getMob());

        this.mountPeerAction = new MoveReactionToEntityAction<>(getMob(), MOUNTABLE_PEER, SEEK_PEER)
                .speedByTarget(target -> Goblin.RUN_AWAY_SPEED_MULTIPLIER)
                .setRefreshRate(25)
                .searchRange(() -> getMob().getAttribute(Attributes.FOLLOW_RANGE).getValue() * 0.5F)
                .searchBy(SearchNearestPredicates.nearestOfType(Goblin.class));

        this.avoidTarget = new MoveReactionToEntityAction<>(getMob(), TargetRange.followRangeMultiplied(0.3F), AVOID_POS)
                .speedByTarget(target -> Goblin.RUN_AWAY_SPEED_MULTIPLIER)
                .setRefreshRate(15)
                //we are not really searching for any entity around, just checking if
                //actual combat target is close enough.
                .searchRange(() -> 0.0F)
                .searchBy(searchTargetSpecifically());
        this.avoidTarget.addBlockedModule(IDLE_ACTIONS_MODULE);

        this.followTargetAction = new FollowTargetAction<>(getMob(), target -> {
            float speed = getMob().hasTwoWeapons() ? Goblin.SOLDIER_ANGRY_SPEED_MULTIPLIER
                    : Goblin.ANGRY_SPEED_MULTIPLIER;

            if (getMob().isVehicle()) {
                speed -= Goblin.MOUNT_SPEED_REDUCTION;
            }

            return speed;
        }).setRefreshRate(16);
        //when goblin is following a target, every action running on IDLE_ACTIONS_MODULE will be ignored.
        this.followTargetAction.addBlockedModule(IDLE_ACTIONS_MODULE);

        //goblin stroll position finder.
        MobPosProcessor<?> strollPositionCalculator = Shared.LAND_STROLL;
        this.randomStrollAction = new RandomStrollAction(getMob(), strollPositionCalculator, RandomIntegerMapper.of(60, 120));
        this.randomStrollAction.addModule(IDLE_ACTIONS_MODULE);

        this.lookRandomlyAction = new LookRandomlyAction(getMob(), RandomIntegerMapper.of(120, 160))
                .vertical(FloatMappers.of(-0.68F, 0.2F));
        this.lookRandomlyAction.addModule(IDLE_ACTIONS_MODULE);

        //this action is processed before everything else, it handles all the transient action tasks.
        ConstantThoughtAction<Goblin> globalCheck = ConstantThoughtAction.withUpdateCallback(getMob(), mobo -> {
            LivingEntity target = mobo.getTarget();

            checkStoneReaddition(mobo);

            if (CombatHelper.isValidAttackTarget(target)
                    && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(target)) {
                onTargetFound(mobo, target);
            } else {
                idle(mobo);
            }
        });

        //these are the permanent-running actions, note that globalCheck is the first to be added, and therefore, is the first to be processed each tick.
        tryAddRunningAction(CHECKS_MODULE, globalCheck);

        tryAddRunningAction(CHECKS_MODULE, new FloatAction(getMob(), 0.2F));

        tryAddRunningAction(CHECKS_MODULE, new TargetBullyAction(getMob(), NOT_SAME_GOBLIN)
                .findTargetCallOut(TargetConsumers.entityGroupAction(
                        Goblin.class,
                        targeter -> BCAABBs.livingEntityRange(targeter, 30, 20),
                        AGGRO_PEERS)));

        tryAddRunningAction(CHECKS_MODULE, new LookForAttackTargetAction<>(
                getMob(), IntScheduler.simple(4),
                OPPOSITE_GOBLIN, SearchNearestPredicates.nearestOfType(Goblin.class)));
    }

    private void checkStoneReaddition(Goblin mob) {
        int stones = mob.stones.get();

        if (stones > Goblin.MAX_STONES) return;

        IntScheduler stoneDelay = mob.stoneDelay.get();
        stoneDelay.incrementFreely(1);

        if (stoneDelay.hasEnded()) {
            mob.stones.set(stones + 1);
            stoneDelay.reset();
        }
    }

    private void onTargetFound(Goblin mob, @Nonnull LivingEntity target) {
        IntScheduler mountDelay = mob.getMountDelay();
        IntScheduler meleeAttackDelay = mob.getMeleeAttackDelay();
        IntScheduler throwDelay = mob.throwDelay.get();
        
        //block idling activities instantly while targeting.
        blockModule(IDLE_ACTIONS_MODULE);

        meleeAttackDelay.incrementFreely(1);
        throwDelay.incrementFreely(1);

        if (mob.isPassenger()) {
            blockModule(MELEE_MODULE);
            blockModule(AVOID_MODULE);

            onTargetFoundWhileOnVehicle(mob, target);
            return;
        }

        if (!mob.hasTwoWeapons()) {
            if (mob.isPassenger() || mob.isVehicle()) mountDelay.reset();
            else mountDelay.incrementFreely(1);

            if (mountDelay.hasEnded()) {
                if (tryMountPeer(mob, target)) {
                    blockModule(MELEE_MODULE);
                    blockModule(AVOID_MODULE);
                    return;
                }
                mountDelay.reset();
            }
        }

        blockModule(MOUNT_MODULE);

        //if goblin should throw stone and stone is thrown successfully, skip melee behaviour.
        if (!(mob.stones.get() > 0 && throwDelay.hasEnded() && !tryAvoidAndThrowStoneToTarget(mob, target))) {
            goMeleeOnTarget(mob, target);
        }
    }

    private boolean tryMountPeer(Goblin mob, @Nonnull LivingEntity target) {
        IntScheduler waitForMount = mob.getWaitForMount();

        waitForMount.incrementFreely(1);

        tryAddRunningAction(MOUNT_MODULE, this.mountPeerAction);
        LivingEntity mountFocus = this.mountPeerAction.getFocus();

        //if the wait for reaching peer hasn't ended we check if our goblin's peer is close enough, along other parameters.
        if (waitForMount.hasEnded()) return false;
        
        if (mountFocus instanceof Goblin peer && isValidPeer(mob, peer)) {
            waitForMount.reset();
            mountPeer(mob, peer);
            return true;
        } 
        
        blockModule(MOUNT_MODULE);
        return false;
    }

    private boolean isValidPeer(Goblin mob, @Nonnull Goblin peer) {
        double distance = ReachHelper.reach3(mob, peer, DistanceCalc3.MANHATTAN);
        return MOUNTABLE_PEER.test(mob, peer) && distance < Goblin.MOUNT_PEER_RANGE;
    }
    
    private void mountPeer(Goblin mob, @Nonnull Goblin peer) {
        peer.startRiding(mob);
    }
    
    private void onTargetFoundWhileOnVehicle(Goblin mob, @Nonnull LivingEntity target) {
        double distance = mob.position().distanceTo(target.position());

        tryDismount(mob, 1);

        if (distance < Goblin.THROW_RANGE && mob.getSensing().hasLineOfSight(target)) {
            tryThrowStone(mob, target, 2);
        }

        mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
        mob.setYRot(mob.yHeadRot);
    }

    private void tryDismount(Goblin mob, int step) {
        IntScheduler demountDelay = mob.demountDelay.get();
        int dismountStep = mob.getVehicle() == mob.getRootVehicle() ? step * 2 : step;

        demountDelay.incrementFreely(dismountStep);

        if (mob.hasTwoWeapons()
                || (mob.getVehicle() instanceof Goblin goblinMount && !MOUNTABLE_PEER.test(mob, goblinMount))
                || demountDelay.hasEnded()) {
            mob.stopRiding();
            demountDelay.reset();
        }
    }

    private boolean tryAvoidAndThrowStoneToTarget(Goblin mob, @Nonnull LivingEntity target) {
        double distance = mob.position().distanceTo(target.position());
        IntScheduler waitForAvoid = mob.getWaitForAvoid();

        if (distance > Goblin.THROW_RANGE) return true;

        if (mob.hasTwoWeapons()) return true;

        blockModule(MELEE_MODULE);

        //if goblin is in distance to throw stone, cancel avoiding behaviour.
        if ((waitForAvoid.hasEnded() || distance > Goblin.AVOID_RANGE) && mob.getSensing().hasLineOfSight(target)) {
            blockModule(AVOID_MODULE);
            tryThrowStone(mob, target, 1);

            return true;
        }

        waitForAvoid.incrementFreely(1);

        tryAddRunningAction(AVOID_MODULE, this.avoidTarget);
        return false;
    }

    private void tryThrowStone(Goblin mob, @Nonnull LivingEntity target, int delayStep) {
        IntScheduler wait = mob.getWaitForThrow();

        wait.incrementFreely(delayStep);

        if (wait.hasEnded()) {
            tryAddRunningAction(ANIMATION_ACTIONS_MODULE, this.throwAction);
        }

        mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
        mob.setYRot(mob.yHeadRot);
    }

    private void goMeleeOnTarget(Goblin mob, @Nonnull LivingEntity target) {
        tryAddRunningAction(MELEE_MODULE, this.followTargetAction);

        if (mob.getMeleeAttackDelay().hasEnded() && mob.mainHandler.isPlayingNull() && ReachHelper.reachSqr(mob, target) < Goblin.ATTACK_REACH_SQUARE) {
            Action<?> attack = getMeleeAttack(mob);

            tryAddRunningAction(ANIMATION_ACTIONS_MODULE, attack);
        }
    }

    private Action<?> getMeleeAttack(Goblin mob) {
        boolean useLeftHand = mob.getRandom().nextBoolean();
        boolean canDoDoubleSlash = canDoubleSlash(mob);

        float doubleSlashProb = 0.25F;
        float bumpProbability = 0.15F;

        float gen = mob.getRandom().nextFloat();

        if (gen < bumpProbability) return this.bumpAction;
        else if (canDoDoubleSlash && gen - bumpProbability < doubleSlashProb) return this.doubleSlashAction;

        float handedAttackGen = mob.getRandom().nextFloat();

        if (useLeftHand) {
            if (mob.hasLeftWeapon())
                return getHandedAttack(mob, true, handedAttackGen);
        } else if (mob.hasRightWeapon())
            return getHandedAttack(mob, false, handedAttackGen);

        return this.bumpAction;
    }

    private GoblinHandedAttackAction getHandedAttack(Goblin mob, boolean useLeftHand, float gen) {
        if (GoblinWeapon.SPEAR.hasOnHand(mob, useLeftHand)) {
            this.stabAction.setUsingLeftAnimation(useLeftHand);
            return this.stabAction;
        }
        else if (GoblinWeapon.SWORD.hasOnHand(mob, useLeftHand)) {
            if (gen < 0.50F) {
                this.stabAction.setUsingLeftAnimation(useLeftHand);
                return this.stabAction;
            } else {
                this.slashAction.setUsingLeftAnimation(useLeftHand);
                return this.slashAction;
            }
        }
        return null;
    }

    private boolean canDoubleSlash(Goblin mob) {
        return mob.getLeftWeapon() == GoblinWeapon.SWORD
                && mob.getRightWeapon() == GoblinWeapon.SWORD;
    }

    private static SearchNearestPredicate searchTargetSpecifically() {
        return (mob, serverLevel, rangeTest, lazy, targetPredicate) -> {
            LivingEntity target = mob.getTarget();
            if (target == null || !target.isAlive()
                    || !targetPredicate.test(mob, target)) return null;
            return target;
        };
    }

    private static Function<LivingEntity, Comparator<BlockPos>> evaluateAvoidingPos(Mob mob) {
        return (target) -> mob instanceof PathfinderMob pf ? Comparator.comparingDouble(pos -> {
            double value = Math.min(1.0, pf.getWalkTargetValue(pos));
            value -= mob.position().distanceTo(target.position()) / 8;

            return value;
        }) : null;
    }

    private void idle(Goblin mob) {

        if (mob.isPassenger() || mob.isVehicle()) {
            if (mob.isPassenger()) tryDismount(mob, 2);
            mob.getMountDelay().reset();
        } else {
            tryAddRunningAction(IDLE_ACTIONS_MODULE, this.randomStrollAction);
            tryAddRunningAction(IDLE_ACTIONS_MODULE, this.lookRandomlyAction);
            mob.demountDelay.get().reset();
        }
    }

    protected static final class ThrowAttackAction extends AnimationAction<Goblin, SimpleAnimation> {

        public ThrowAttackAction(Goblin mob) {
            super(mob, Goblin.THROW, mob.mainHandler);
        }

        @Override
        protected void update() {
            super.update();
            int animationTick = this.animationHandler.getTick();
            LivingEntity target = this.mob.getTarget();

            if (CombatHelper.isValidAttackTarget(target)) {

                if (animationTick == 6)
                    this.mob.playSound(DESounds.ES_GOBLIN_ATTACK.get(), 1.0F, 0.9F + RANDOM.nextFloat() * 0.2F);

                if (animationTick == 7) {
                   this.mob.throwDelay.get().reset();
                   this.mob.stones.set(this.mob.stones.get() - 1);
                   this.mob.getWaitForThrow().reset();
                   this.mob.getWaitForAvoid().reset();

                   StoneProjectile projectile = new StoneProjectile(DEEntities.STONE_PROJECTILE.get(), this.mob.level);
                   projectile.setOwner(this.mob);
                   projectile.setPos(this.mob.position().add(0.0F, 0.5F, 0.0F));

                   Vec2 rotations = Shared.rotationsToTarget(
                           projectile.position(),
                           target.position().add(0.0F, target.getBbHeight() / 2.0F, 0.0F)
                   );

                   projectile.shootFromRotation(this.mob, rotations.x, rotations.y - 90, -1.5F,  2.0F, 0.6F);
                   this.mob.level.addFreshEntity(projectile);
                }
            }
        }

        @Override
        public boolean canStart() {
            return super.canStart() && this.animationHandler.isPlayingNull();
        }
    }

    protected static final class BumpAttackAction extends AnimationAction<Goblin, SimpleAnimation> {

        public BumpAttackAction(Goblin mob) {
            super(mob, Goblin.BUMP, mob.mainHandler);
        }

        @Override
        protected void update() {
            super.update();
            int animationTick = this.animationHandler.getTick();
            LivingEntity target = this.mob.getTarget();

            if (CombatHelper.isValidAttackTarget(target)) {

                if (animationTick == 6)
                    this.mob.playSound(DESounds.ES_GOBLIN_ATTACK.get(), 1.0F, 0.9F + RANDOM.nextFloat() * 0.2F);

                if (animationTick == 8 && ReachHelper.reachSqr(this.mob, target) < Goblin.ATTACK_REACH_SQUARE * 1.2F) {
                    CombatHelper.attackWithMultiplier(this.mob, target, 1.0F + RANDOM.nextFloat() * 0.15F);
                    this.mob.getMeleeAttackDelay().reset();
                }

                mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
                mob.setYRot(mob.yHeadRot);
            }
        }

        @Override
        public boolean canStart() {
            return super.canStart() && this.animationHandler.isPlayingNull();
        }
    }

    protected static final class DoubleSlashAction extends AnimationAction<Goblin, SimpleAnimation> {

        public DoubleSlashAction(Goblin mob) {
            super(mob, Goblin.DOUBLE_SLASH, mob.mainHandler);
        }

        @Override
        protected void update() {
            super.update();
            int animationTick = this.animationHandler.getTick();
            LivingEntity target = this.mob.getTarget();

            if (CombatHelper.isValidAttackTarget(target)) {

                if (animationTick == 6)
                    this.mob.playSound(DESounds.ES_GOBLIN_ATTACK.get(), 1.0F, 0.9F + RANDOM.nextFloat() * 0.2F);

                if (animationTick == 8 && ReachHelper.reachSqr(this.mob, target) < Goblin.ATTACK_REACH_SQUARE * 1.2F) {
                    CombatHelper.attackWithMultiplier(this.mob, target, 1.5F * this.mob.getLeftWeapon().getDamageMultiplier() + RANDOM.nextFloat() * 0.15F);
                    this.mob.getMeleeAttackDelay().reset();
                }

                mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
                mob.setYRot(mob.yHeadRot);
            }
        }

        @Override
        public boolean canStart() {
            return super.canStart() && this.animationHandler.isPlayingNull();
        }
    }

    protected static abstract class GoblinHandedAttackAction extends Action<Goblin> {
        private final SimpleAnimation left;
        private final SimpleAnimation right;
        protected boolean usingLeftAnimation;

        public GoblinHandedAttackAction(Goblin mob, SimpleAnimation left, SimpleAnimation right) {
            super(mob);
            this.left = left;
            this.right = right;
        }

        @Override
        public boolean canStart() {
            return super.active() && this.mob.mainHandler.isPlayingNull();
        }

        protected void start() {
            super.start();
            this.mob.mainHandler.play(getAnimation());
        }

        public boolean shouldKeepGoing() {
            return this.active() && this.mob.mainHandler.isPlaying(getAnimation());
        }

        @Override
        protected void update() {
            super.update();
            int animationTick = this.mob.mainHandler.getTick();
            LivingEntity target = this.mob.getTarget();
            MobMatchPredicate<LivingEntity> validator = getPsyche().getInputs().getOfDefault(ActionInputKey.TARGET_VALIDATOR);

            if (validator != null && target != null && validator.test(this.mob, target)) {

                handleTarget(animationTick, target);

                mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
                mob.setYRot(mob.yHeadRot);
            }
        }

        public abstract void handleTarget(int animationTick, LivingEntity target);

        protected SimpleAnimation getAnimation() {
            return usingLeftAnimation ? left : right;
        }

        public void setUsingLeftAnimation(boolean usingLeftAnimation) {
            this.usingLeftAnimation = usingLeftAnimation;
        }
    }

    protected static final class SlashAction extends GoblinHandedAttackAction {

        public SlashAction(Goblin mob) {
            super(mob, Goblin.SLASH_LEFT, Goblin.SLASH_RIGHT);
        }

        @Override
        public void handleTarget(int animationTick, LivingEntity target) {

            if (animationTick == 6)
                this.mob.playSound(DESounds.ES_GOBLIN_ATTACK.get(), 1.0F, 0.9F + RANDOM.nextFloat() * 0.2F);

            if (animationTick == 8 && ReachHelper.reachSqr(this.mob, target) < Goblin.ATTACK_REACH_SQUARE * 1.2F) {
                GoblinWeapon weapon = this.usingLeftAnimation ? this.mob.getLeftWeapon()
                        : this.mob.getRightWeapon();

                CombatHelper.attackWithMultiplier(this.mob, target, 1.2F * weapon.getDamageMultiplier() + RANDOM.nextFloat() * 0.15F);
                this.mob.getMeleeAttackDelay().reset();
            }
        }
    }

    protected static final class StabAction extends GoblinHandedAttackAction {

        public StabAction(Goblin mob) {
            super(mob, Goblin.STAB_LEFT, Goblin.STAB_RIGHT);
        }

        @Override
        public void handleTarget(int animationTick, LivingEntity target) {

            if (animationTick == 6)
                this.mob.playSound(DESounds.ES_GOBLIN_ATTACK.get(), 1.0F, 0.9F + RANDOM.nextFloat() * 0.2F);

            if (animationTick == 8 && ReachHelper.reachSqr(this.mob, target) < Goblin.ATTACK_REACH_SQUARE * 1.4F) {
                GoblinWeapon weapon = this.usingLeftAnimation ? this.mob.getLeftWeapon()
                        : this.mob.getRightWeapon();

                CombatHelper.attackWithMultiplier(this.mob, target, 1.1F * weapon.getDamageMultiplier() + RANDOM.nextFloat() * 0.15F);
                this.mob.getMeleeAttackDelay().reset();
            }
        }
    }
}
