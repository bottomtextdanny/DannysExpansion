package net.bottomtextdanny.danny_expannny.objects.entities.mob.monstrous_scorpion;

import net.bottomtextdanny.braincell.mod.base.misc.timer.IntScheduler;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.Animation;
import net.bottomtextdanny.braincell.mod.entity.psyche.Psyche;
import net.bottomtextdanny.braincell.mod.entity.psyche.actions.*;
import net.bottomtextdanny.braincell.mod.entity.psyche.actions.target.LookForAristocratAction;
import net.bottomtextdanny.braincell.mod.entity.psyche.actions.target.LookForAttackTargetAction;
import net.bottomtextdanny.braincell.mod.entity.psyche.actions.target.TargetBullyAction;
import net.bottomtextdanny.braincell.mod.entity.psyche.data.UnbuiltActionInputs;
import net.bottomtextdanny.braincell.mod.world.helpers.ReachHelper;
import net.bottomtextdanny.danny_expannny.object_tables.DEEffects;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.core.Util.Timer;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.RandomIntegerMapper;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

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
        initializeActionMap(MAIN_MODULE, ANIMATION_ACTIONS_MODULE, IDLE_ACTIONS_MODULE);
    }

    @Override
    protected void populateInputs(UnbuiltActionInputs inputs) {}

    @Override
    protected void initialize() {
        this.followTargetAction = new LocalFollowTargetAction(getMob(), target -> MonstrousScorpion.COMBAT_SPEED).setRefreshRate(10);
        this.followTargetAction.addBlockedModule(IDLE_ACTIONS_MODULE);
        this.randomStrollAction = new WaterAvoidingRandomStrollAction(getMob(), RandomIntegerMapper.of(120, 190));
        this.randomStrollAction.addModule(IDLE_ACTIONS_MODULE);
        this.stingAttackAction = new StingAttackAction(getMob());
        this.stingAttackAction.addBlockedModule(MAIN_MODULE);
        this.clawAttackAction = new ClawAttackAction(getMob());
        this.clawAttackAction.addBlockedModule(MAIN_MODULE);
        ConstantThoughtAction<MonstrousScorpion> globalCheck = ConstantThoughtAction.withUpdateCallback(getMob(), mobo -> {
            if (mobo.getTarget() != null) {
                float reach = mobo.reachTo(mobo.getTarget());
                if (getMob().mainAnimationHandler.isPlayingNull() && reach < MonstrousScorpion.ATTACK_RANGE) {
                    if (getMob().attackDelay.get().hasEnded()) {
                        IntScheduler.Variable clawAttackTimer = getMob().clawAttackDelay.get();
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
        tryAddRunningAction(CHECKS_MODULE, new TargetBullyAction(getMob()));
        tryAddRunningAction(CHECKS_MODULE, new LookForAttackTargetAction<>(getMob(), Player.class).setUnseeTimer(new Timer(4)));
        tryAddRunningAction(CHECKS_MODULE, new LookForAristocratAction<>(getMob()).setUnseeTimer(new Timer(0)));
        tryAddRunningAction(IDLE_ACTIONS_MODULE, new FloatAction(getMob(), 0.3F));
    }

    public static class StingAttackAction extends AnimationAction<MonstrousScorpion, Animation> {

        public StingAttackAction(MonstrousScorpion mob) {
            super(mob, mob.getStingAnimation(), mob.mainAnimationHandler);
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
                    this.mob.attackWithMultiplier(livingEntity, 1.0F);
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
            this.mob.mainAnimationHandler.play(this.mob.getClawAttackAnimations()[rgn]);
        }

        @Override
        public void update() {
            super.update();
            LivingEntity livingEntity = this.mob.getTarget();

            if (livingEntity != null) this.mob.getLookControl().setLookAt(this.mob.getTarget(), 30.0F, 20.0F);

            if (this.mob.mainAnimationHandler.getTick() == 5) {
                this.mob.level.playSound(null, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ(), DESounds.ES_SWOOSH.get(), SoundSource.HOSTILE, 0.6F + 0.07F * this.mob.getRandom().nextFloat(), 1.0F + 0.2F * this.mob.getRandom().nextFloat());
            } else if (this.mob.mainAnimationHandler.getTick() == 7) {
                if (livingEntity != null && livingEntity.isAlive() && ReachHelper.reachSqr(this.mob, livingEntity) < MonstrousScorpion.CLAW_ATTACK_RANGE * 1.2F) {
                    this.mob.attackWithMultiplier(livingEntity, 1.0F);
                    this.mob.playSound(DESounds.ES_MONSTROUS_SCORPION_CLAW_ATTACK.get(), 1.0F, 1.0F + this.mob.getRandom().nextFloat() * 0.2F);

                    if (this.mob.level.getDifficulty().getId() != 0 && this.mob.doHurtTarget(this.mob.getTarget())) {
                        this.mob.getTarget().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40 * this.mob.level.getDifficulty().getId(), 0));
                    }
                }
            }
        }

        @Override
        public boolean shouldKeepGoing() {
            return active() && this.mob.mainAnimationHandler.get().from(MonstrousScorpion.CLAW_ATTACK_IDENTIFIER);
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
