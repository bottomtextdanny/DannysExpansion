package bottomtextdanny.dannys_expansion.content.entities.mob.cursed_skull;

import bottomtextdanny.braincell.mod.entity.psyche.actions.RandomStrollAction;
import bottomtextdanny.dannys_expansion._util.DEFloatMappers;
import bottomtextdanny.dannys_expansion.content.entities.Shared;
import bottomtextdanny.dannys_expansion.content.entities.mob.ice_elemental.IceElemental;
import bottomtextdanny.dannys_expansion.content.entities.projectile.CursedFireball;
import bottomtextdanny.dannys_expansion.tables.DEEntities;
import bottomtextdanny.dannys_expansion.tables.DESounds;
import bottomtextdanny.braincell.base.BCMath;
import bottomtextdanny.braincell.base.FloatRandomPicker;
import bottomtextdanny.braincell.base.scheduler.IntScheduler;
import bottomtextdanny.braincell.base.value_mapper.FloatMapper;
import bottomtextdanny.braincell.base.value_mapper.RandomIntegerMapper;
import bottomtextdanny.braincell.mod.entity.modules.animatable.SimpleAnimation;
import bottomtextdanny.braincell.mod.entity.psyche.MarkedTimer;
import bottomtextdanny.braincell.mod.entity.psyche.Psyche;
import bottomtextdanny.braincell.mod.entity.psyche.actions.AnimationAction;
import bottomtextdanny.braincell.mod.entity.psyche.actions.ConstantThoughtAction;
import bottomtextdanny.braincell.mod.entity.psyche.actions.MoveReactionToEntityAction;
import bottomtextdanny.braincell.mod.entity.psyche.actions.target.LookForAttackTargetAction;
import bottomtextdanny.braincell.mod.entity.psyche.actions.target.TargetBullyAction;
import bottomtextdanny.braincell.mod.entity.psyche.input.ActionInputKey;
import bottomtextdanny.braincell.mod.entity.psyche.input.UnbuiltActionInputs;
import bottomtextdanny.braincell.mod.entity.psyche.pos_finder.MobPosPredicate;
import bottomtextdanny.braincell.mod.entity.psyche.pos_finder.MobPosPredicates;
import bottomtextdanny.braincell.mod.entity.psyche.pos_finder.MobPosProcessor;
import bottomtextdanny.braincell.mod.entity.psyche.targeting.SearchNearestPredicates;
import bottomtextdanny.braincell.mod.entity.psyche.targeting.TargetPredicate;
import bottomtextdanny.braincell.mod.entity.psyche.targeting.TargetRange;
import bottomtextdanny.braincell.mod.entity.psyche.targeting.Targeter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.function.Function;

import static bottomtextdanny.braincell.mod.entity.psyche.pos_finder.MobPosProcessors.*;

public class CursedSkullPsyche extends Psyche<CursedSkull> {
    public static final TargetPredicate TARGET_PREDICATE =
            Targeter.Builder.start(TargetRange.followRange())
                    .isForCombat()
                    .targetRangeForInvisible(TargetRange.followRangeMultiplied(0.75F))
                    .hasToBeOnSight()
                    .build();
    public static final MobPosProcessor<Object> STROLL = compose(
            (pos, mob, r, extra) -> {
                FloatMapper view = DEFloatMappers.range(mob.getYHeadRot() * BCMath.FRAD, 20.0F * BCMath.FRAD, FloatRandomPicker.normal());

                MobPosPredicate<Object> stable = MobPosPredicates.isStable().and(MobPosPredicates.noFluid());
                MobPosPredicate<Object> downIf = MobPosPredicates.isStable().negate().or(MobPosPredicates.noFluid());

                return testNull(stack()
                        .push(advanceHorizontal(view, FloatMapper.from(6, 8, FloatRandomPicker.linear())))
                        .push(sample(10, stack()
                                        .push(randomOffset(5, 5, 5)),
                                evaluatePos(mob))
                        ).push(find(10, stack()
                        .push(randomOffset(4, 3, 4))
                        .push(testNull(stack()
                                .push(moveIf(Direction.DOWN, 7, downIf))
                                .push(pred(stable))
                                .push(move(Direction.UP, FloatMapper.from(6, 8, FloatRandomPicker.normal()))))
                        ).push(pred(MobPosPredicates.isStable().negate().and(MobPosPredicates.noFluid()))))
                        ));
            });
    public static final MobPosProcessor<LivingEntity> AVOID_POS = compose((blockPos, mob, randomGenerator, target) -> {
        Vec3 posDiff = mob.position().subtract(target.position());
        float oppositeFromTargetRad = (float) Mth.atan2(posDiff.x, posDiff.y);

        FloatMapper angleAwayFromTarget = FloatMapper.from(
                oppositeFromTargetRad - 20 * BCMath.FRAD,
                oppositeFromTargetRad + 20 * BCMath.FRAD,
                FloatRandomPicker.normal());

        FloatMapper stepAway = FloatMapper.of(14);

        return stack().generic(LivingEntity.class)
                .push(advanceHorizontal(angleAwayFromTarget, stepAway))
                .push(sample(5, randomOffset(3, 4, 3), Shared.evaluateAvoidingPos(mob, 1.0F)));
    });

    public static final MobPosProcessor<LivingEntity> APPROACH_POS = compose((blockPos, mob, randomGenerator, target) -> {
        Vec3 posDiff = mob.position().subtract(target.position());
        float oppositeFromTargetRad = (float) Mth.atan2(posDiff.x, posDiff.z) + BCMath.FPI;

        FloatMapper angleAwayFromTarget = FloatMapper.from(
                oppositeFromTargetRad - 20 * BCMath.FRAD,
                oppositeFromTargetRad + 20 * BCMath.FRAD,
                FloatRandomPicker.normal());

        FloatMapper stepAway = FloatMapper.of(14);

        return stack().generic(LivingEntity.class)
                .push(advanceHorizontal(angleAwayFromTarget, stepAway))
                .push(sample(5, randomOffset(3, 4, 3), Shared.evaluateApproachingPos(mob, 1.0F)));
    });
    public static final int
            COMBAT_MODULE = 1,
            AVOID_MODULE = 2,
            IDLE_MODULE = 3,
            ATTACK_MODULE = 4;
    private final MarkedTimer unseenTimer;
    private RandomStrollAction wanderAction;
    private MoveReactionToEntityAction<CursedSkull, LivingEntity> flyAwayFromLookAction;
    private MoveReactionToEntityAction<CursedSkull, LivingEntity> flyToLookAction;
    private SpitAction spitAction;

    public CursedSkullPsyche(CursedSkull mob) {
        super(mob);
        this.unseenTimer = new MarkedTimer(IntScheduler.simple(100));
        allocateModules(5);
    }

    @Override
    protected void populateInputs(UnbuiltActionInputs inputs) {
        inputs.put(ActionInputKey.MARKED_UNSEEN, () -> this.unseenTimer);
    }

    @Override
    protected void initialize() {
        this.spitAction = new SpitAction(getMob());
        this.flyAwayFromLookAction = new MoveReactionToEntityAction<>(getMob(), TargetRange.followRangeMultiplied(CursedSkull.FLEE_RANGE).cast(), AVOID_POS)
                .speedByTarget(target -> CursedSkull.COMBAT_MOVE_SPEED)
                .setRefreshRate(15)
                .searchRange(() -> 0.0F)
                .searchBy(Shared.searchTargetSpecifically());
        this.flyToLookAction = new MoveReactionToEntityAction<>(getMob(), TargetRange.followRange().cast(), APPROACH_POS)
                .speedByTarget(target -> CursedSkull.COMBAT_MOVE_SPEED)
                .setRefreshRate(15)
                .searchRange(() -> 0.0F)
                .searchBy(Shared.searchTargetSpecifically());
        this.wanderAction = new RandomStrollAction(getMob(), STROLL, RandomIntegerMapper.of(1)) {
            @Override
            protected void update() {
                super.update();
                Path path = this.mob.getNavigation().getPath();
                if (path != null && path.getNextNodeIndex() <= path.getNodeCount()) {
                    Vec3 vec = Vec3.atBottomCenterOf(path.getNextNodePos()).add(0.0, mob.getEyeHeight(), 0.0);
                    this.mob.getLookControl().setLookAt(vec.x, vec.y, vec.z, 180.0F, 30.0F);
                }
            }
        };
        ConstantThoughtAction<CursedSkull> globalCheck = ConstantThoughtAction.withUpdateCallback(getMob(), mobo -> {
            LivingEntity target = mobo.getTarget();

            if (getMob().isCombatMode()) {
                blockModule(IDLE_MODULE);

                final boolean seen = mobo.getSensing().hasLineOfSight(target);

                IntScheduler attackDelay = getMob().attackDelay.get();
                getMob().getLookControl().setLookAt(mobo.getTarget(), 180.0F, 50.0F);

                if (seen && TargetRange.followRangeMultiplied(IceElemental.FLEE_RANGE).test(mobo, target)) {
                    tryAddRunningAction(AVOID_MODULE, this.flyAwayFromLookAction);
                } else {
                    tryAddRunningAction(COMBAT_MODULE, this.flyToLookAction);
                }

                if (attackDelay.hasEnded()) {
                    if (getMob().getSensing().hasLineOfSight(mobo)) {
                        tryAddRunningAction(ATTACK_MODULE, this.spitAction);
                    }
                    attackDelay.reset();
                } else {
                    attackDelay.advance();
                }
            } else {
                tryAddRunningAction(IDLE_MODULE, this.wanderAction);
            }
        });

        tryAddRunningAction(CHECKS_MODULE, globalCheck);
        tryAddRunningAction(CHECKS_MODULE, new TargetBullyAction(getMob(), TARGET_PREDICATE));
        tryAddRunningAction(CHECKS_MODULE, new LookForAttackTargetAction<>(
                getMob(), IntScheduler.simple(4),
                TARGET_PREDICATE, SearchNearestPredicates.nearestPlayer()));
        tryAddRunningAction(CHECKS_MODULE, new LookForAttackTargetAction<>(
                getMob(), IntScheduler.simple(12),
                Shared.GOODIE.and(TARGET_PREDICATE), SearchNearestPredicates.nearestLiving()));
    }

    public static Function<Object, Comparator<BlockPos>> evaluatePos(Mob mob) {
        return bad -> Comparator.comparingDouble(pos -> {
            double value = 0;
            BlockPos l = pos.subtract(mob.blockPosition());

            double dif = 1 - (Mth.degreesDifference(mob.getYHeadRot(), (float)(Mth.atan2(l.getX(), l.getZ()) * (double)(180F / (float)Math.PI)) - 90.0F) / 180.0);

            dif *= 1.5;

            double heightValue = (1 - (Mth.clamp(l.getY(), -5, 5) / 5.0)) * 1.5;

            if (GoalUtils.isNotStable(mob.getNavigation(), pos) || !mob.level.getBlockState(pos).getFluidState().isEmpty()) {
                heightValue *= 0.5;
                value += 8;
            }

            return value + dif + heightValue;
        });
    }

    public static class SpitAction extends AnimationAction<CursedSkull, SimpleAnimation> {
        private boolean stop;

        public SpitAction(CursedSkull mob) {
            super(mob, CursedSkull.SPIT, mob.mainHandler);
        }

        @Override
        protected void start() {
            super.start();
            this.stop = this.mob.getRandom().nextFloat() < 0.3F;
        }

        @Override
        public void update() {
            super.update();
            if (this.stop) {
                getPsyche().blockModule(COMBAT_MODULE);
                getPsyche().blockModule(AVOID_MODULE);
            }
            if (this.animationHandler.getTick() == 4) {
                this.mob.level.playSound(null, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ(), DESounds.ES_CURSED_SKULL_SPIT.get(), SoundSource.HOSTILE, 1.0F + 0.07F * this.mob.getRandom().nextFloat(), 1.0F + 0.2F * this.mob.getRandom().nextFloat());
            } else if (this.animationHandler.getTick() == 9) {
                float heightDelta = this.mob.getBbHeight() / 2.0F;
                CursedFireball fireball = new CursedFireball(DEEntities.CURSED_FIREBALL.get(), this.mob.level);
                fireball.setCaster(this.mob);
                fireball.setPos(this.mob.getX(), this.mob.getY() + heightDelta, this.mob.getZ());
                fireball.setXRot(this.mob.getXRot());
                fireball.setYRot(this.mob.getYRot());
                this.mob.level.addFreshEntity(fireball);
            }
        }
    }
}
