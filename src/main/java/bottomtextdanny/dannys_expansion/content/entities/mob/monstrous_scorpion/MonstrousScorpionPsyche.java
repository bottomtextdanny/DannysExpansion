package bottomtextdanny.dannys_expansion.content.entities.mob.monstrous_scorpion;

import bottomtextdanny.dannys_expansion.content.entities.Shared;
import bottomtextdanny.dannys_expansion.tables.DEEffects;
import bottomtextdanny.dannys_expansion.tables.DESounds;
import bottomtextdanny.braincell.base.scheduler.IntScheduler;
import bottomtextdanny.braincell.base.value_mapper.RandomIntegerMapper;
import bottomtextdanny.braincell.mod.entity.modules.animatable.SimpleAnimation;
import bottomtextdanny.braincell.mod.entity.psyche.Action;
import bottomtextdanny.braincell.mod.entity.psyche.Psyche;
import bottomtextdanny.braincell.mod.entity.psyche.actions.*;
import bottomtextdanny.braincell.mod.entity.psyche.actions.target.LookForAttackTargetAction;
import bottomtextdanny.braincell.mod.entity.psyche.actions.target.TargetBullyAction;
import bottomtextdanny.braincell.mod.entity.psyche.input.UnbuiltActionInputs;
import bottomtextdanny.braincell.mod.entity.psyche.targeting.SearchNearestPredicates;
import bottomtextdanny.braincell.mod.world.helpers.CombatHelper;
import bottomtextdanny.braincell.mod.world.helpers.ReachHelper;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.ToDoubleFunction;

public class MonstrousScorpionPsyche extends Psyche<MonstrousScorpion> {
    public static final int
            MAIN_MODULE = 1,
            ANIMATION_ACTIONS_MODULE = 2,
            IDLE_ACTIONS_MODULE = 3;
    private RandomStrollAction randomStrollAction;
    private FollowTargetAction<MonstrousScorpion> followTargetAction;
    private StingAttackAction stingAttackAction;
    private ClawAttackAction clawAttackAction;

    public MonstrousScorpionPsyche(MonstrousScorpion mob) {
        super(mob);
        allocateModules(3);
    }

    @Override
    protected void populateInputs(UnbuiltActionInputs inputs) {

    }

    @Override
    protected void initialize() {
        this.followTargetAction = new LocalFollowTargetAction(getMob(), target -> MonstrousScorpion.COMBAT_SPEED).setRefreshRate(10);
        this.followTargetAction.addBlockedModule(IDLE_ACTIONS_MODULE);
        this.randomStrollAction = new WaterAvoidingRandomStrollAction(getMob(), Shared.LAND_STROLL, RandomIntegerMapper.of(120, 190));
        this.randomStrollAction.addModule(IDLE_ACTIONS_MODULE);
        this.stingAttackAction = new StingAttackAction(getMob());
        this.stingAttackAction.addBlockedModule(MAIN_MODULE);
        this.clawAttackAction = new ClawAttackAction(getMob());
        this.clawAttackAction.addBlockedModule(MAIN_MODULE);

        ConstantThoughtAction<MonstrousScorpion> globalCheck = ConstantThoughtAction.withUpdateCallback(getMob(), mobo -> {
            if (mobo.getTarget() != null) {
                float reach = ReachHelper.reachSqr(mobo, mobo.getTarget());
                if (getMob().mainHandler.isPlayingNull() && reach < MonstrousScorpion.ATTACK_RANGE) {
                    if (getMob().attackDelay.get().hasEnded()) {
                        IntScheduler.Ranged clawAttackTimer = getMob().clawAttackDelay.get();

                        if (clawAttackTimer.hasEnded() && reach < MonstrousScorpion.CLAW_ATTACK_RANGE) {
                            tryAddRunningAction(ANIMATION_ACTIONS_MODULE, this.clawAttackAction);
                            clawAttackTimer.reset();
                        } else {
                            tryAddRunningAction(ANIMATION_ACTIONS_MODULE, this.stingAttackAction);
                        }
                        getMob().attackDelay.get().reset();

                    } else {
                        getMob().attackDelay.get().advance();
                    }
                }
                getMob().clawAttackDelay.get().advance();
                tryAddRunningAction(MAIN_MODULE, this.followTargetAction);
            } else {
                tryAddRunningAction(MAIN_MODULE, this.randomStrollAction);
            }
        });
        tryAddRunningAction(CHECKS_MODULE, globalCheck);
        tryAddRunningAction(CHECKS_MODULE, new TargetBullyAction(getMob(), Shared.DEFAULT_TARGET_PARAMETERS));
        tryAddRunningAction(CHECKS_MODULE, new LookForAttackTargetAction<>(
                getMob(), IntScheduler.simple(4),
                Shared.DEFAULT_TARGET_PARAMETERS, SearchNearestPredicates.nearestPlayer()));
        tryAddRunningAction(CHECKS_MODULE, new LookForAttackTargetAction<>(
                getMob(), IntScheduler.simple(12),
                Shared.GOODIE.and(Shared.DEFAULT_TARGET_PARAMETERS), SearchNearestPredicates.nearestLiving()));
        tryAddRunningAction(IDLE_ACTIONS_MODULE, new FloatAction(getMob(), 0.3F));
    }

    public static class StingAttackAction extends AnimationAction<MonstrousScorpion, SimpleAnimation> {

        public StingAttackAction(MonstrousScorpion mob) {
            super(mob, MonstrousScorpion.STING, mob.mainHandler);
        }

        @Override
        public void update() {
            super.update();
            LivingEntity livingEntity = this.mob.getTarget();

           if (livingEntity != null) this.mob.getLookControl().setLookAt(this.mob.getTarget(), 30.0F, 20.0F);

            if (this.animationHandler.getTick() == 2) {
                this.mob.level.playSound(null, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ(), DESounds.ES_SWOOSH.get(), SoundSource.HOSTILE, 0.6F + 0.07F * this.mob.getRandom().nextFloat(), 1.0F + 0.2F * this.mob.getRandom().nextFloat());
            } else if (this.animationHandler.getTick() == 6) {
                if (livingEntity != null && livingEntity.isAlive() && ReachHelper.reachSqr(this.mob, livingEntity) < MonstrousScorpion.ATTACK_RANGE * 1.2F) {
                    CombatHelper.attackWithMultiplier(this.mob, livingEntity, 1.0F);
                    this.mob.playSound(DESounds.ES_BLACK_SCORPION_STING.get(), 1.0F, 1.0F + this.mob.getRandom().nextFloat() * 0.2F);
                    if (this.mob.level.getDifficulty().getId() != 0 && this.mob.doHurtTarget(this.mob.getTarget())) {
                        this.mob.getTarget().addEffect(new MobEffectInstance(DEEffects.VENOM.get(), 50 * this.mob.level.getDifficulty().getId(), 0));

                    }
                }
            }
        }
    }

    public static class ClawAttackAction extends Action<MonstrousScorpion> {

        public ClawAttackAction(MonstrousScorpion mob) {
            super(mob);
        }

        @Override
        public boolean canStart() {
            return active();
        }

        @Override
        protected void start() {
            super.start();
            int rgn = this.mob.getRandom().nextInt(3);
            this.mob.mainHandler.play(MonstrousScorpion.getClawAttackAnimations()[rgn]);
        }

        @Override
        public void update() {
            super.update();
            LivingEntity livingEntity = this.mob.getTarget();

            if (livingEntity != null) this.mob.getLookControl().setLookAt(this.mob.getTarget(), 30.0F, 20.0F);

            if (this.mob.mainHandler.getTick() == 5) {
                this.mob.level.playSound(null, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ(), DESounds.ES_SWOOSH.get(), SoundSource.HOSTILE, 0.6F + 0.07F * this.mob.getRandom().nextFloat(), 1.0F + 0.2F * this.mob.getRandom().nextFloat());
            } else if (this.mob.mainHandler.getTick() == 7) {
                if (livingEntity != null && livingEntity.isAlive() && ReachHelper.reachSqr(this.mob, livingEntity) < MonstrousScorpion.CLAW_ATTACK_RANGE * 1.2F) {
                    CombatHelper.attackWithMultiplier(this.mob, livingEntity, 1.0F);
                    this.mob.playSound(DESounds.ES_MONSTROUS_SCORPION_CLAW_ATTACK.get(), 1.0F, 1.0F + this.mob.getRandom().nextFloat() * 0.2F);

                    if (this.mob.level.getDifficulty().getId() != 0 && this.mob.doHurtTarget(this.mob.getTarget())) {
                        this.mob.getTarget().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40 * this.mob.level.getDifficulty().getId(), 0));
                    }
                }
            }
        }

        @Override
        public boolean shouldKeepGoing() {
            return active() && this.mob.mainHandler.getAnimation().isFrom(MonstrousScorpion.CLAW_ATTACK_IDENTIFIER);
        }
    }

    public static class LocalFollowTargetAction extends FollowTargetAction<MonstrousScorpion> {

        public LocalFollowTargetAction(MonstrousScorpion mob, ToDoubleFunction<LivingEntity> moveSpeed) {
            super(mob, moveSpeed);
        }

        @Override
        protected void start() {
            super.start();
            this.mob.clawAttackDelay.get().reset();
            this.mob.attackDelay.get().end();
        }
    }
}
