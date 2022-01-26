package net.bottomtextdanny.danny_expannny.objects.entities.mob.ghouls;

import net.bottomtextdanny.braincell.mod.base.misc.timer.IntScheduler;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.Animation;
import net.bottomtextdanny.braincell.mod.entity.psyche.Psyche;
import net.bottomtextdanny.braincell.mod.entity.psyche.actions.*;
import net.bottomtextdanny.braincell.mod.entity.psyche.actions.target.LookForAristocratAction;
import net.bottomtextdanny.braincell.mod.entity.psyche.actions.target.LookForAttackTargetAction;
import net.bottomtextdanny.braincell.mod.entity.psyche.actions.target.TargetBullyAction;
import net.bottomtextdanny.braincell.mod.entity.psyche.data.UnbuiltActionInputs;
import net.bottomtextdanny.braincell.mod.world.helpers.ReachHelper;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.ghouls.PetrifiedGhoul;
import net.bottomtextdanny.dannys_expansion.core.Util.Timer;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.RandomFloatMapper;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.RandomIntegerMapper;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.ToDoubleFunction;

public class PetrifiedGhoulPsyche extends Psyche<PetrifiedGhoul> {
    public static final int
            MAIN_MODULE = 1,
            ANIMATION_ACTIONS_MODULE = 2,
            IDLE_ACTIONS_MODULE = 3;
    private LookRandomlyAction lookRandomlyAction;
    private RandomStrollAction randomStrollAction;
    private SideAttackAction sideAttackAction;
    private StrongAttackAction strongAttackAction;
    private FollowTargetAction<PetrifiedGhoul> followTargetAction;

    public PetrifiedGhoulPsyche(PetrifiedGhoul mob) {
        super(mob);
        initializeActionMap(MAIN_MODULE, ANIMATION_ACTIONS_MODULE, IDLE_ACTIONS_MODULE);
    }

    @Override
    protected void populateInputs(UnbuiltActionInputs inputs) {}

    @Override
    protected void initialize() {
        this.sideAttackAction = new SideAttackAction(getMob());
        this.sideAttackAction.addBlockedModule(MAIN_MODULE);
        this.strongAttackAction = new StrongAttackAction(getMob());
        this.strongAttackAction.addBlockedModule(MAIN_MODULE);
        this.followTargetAction = new LocalFollowTargetAction(getMob(), target -> PetrifiedGhoul.COMBAT_SPEED_MULTIPLIER).setRefreshRate(10);
        this.followTargetAction.addBlockedModule(IDLE_ACTIONS_MODULE);
        this.randomStrollAction = new WaterAvoidingRandomStrollAction(getMob(), RandomIntegerMapper.of(220, 320));
        this.randomStrollAction.addModule(IDLE_ACTIONS_MODULE);
        this.lookRandomlyAction = new LookRandomlyAction(getMob(), RandomIntegerMapper.of(120, 160)).vertical(RandomFloatMapper.of(-0.28F, 0.15F));
        this.lookRandomlyAction.addModule(IDLE_ACTIONS_MODULE);
        ConstantThoughtAction<PetrifiedGhoul> globalCheck = ConstantThoughtAction.withUpdateCallback(getMob(), mobo -> {
            if (mobo.getTarget() != null) {
                if (getMob().mainAnimationHandler.isPlayingNull() && mobo.reachTo(mobo.getTarget()) < PetrifiedGhoul.ATTACK_RANGE) {
                    if (getMob().attackDelay.get().hasEnded()) {
                        IntScheduler.Variable strongAttackTimer = getMob().attacksTillStrongAttack.get();
                        if (strongAttackTimer.hasEnded()) {
                            tryAddRunningAction(ANIMATION_ACTIONS_MODULE, this.strongAttackAction);
                            strongAttackTimer.reset();
                        } else {
                            strongAttackTimer.advance();
                            tryAddRunningAction(ANIMATION_ACTIONS_MODULE, this.sideAttackAction);
                        }
                        getMob().attackDelay.get().reset();
                    } else {
                        getMob().attackDelay.get().advance();
                    }
                }

                tryAddRunningAction(MAIN_MODULE, this.followTargetAction);
            } else {
                tryAddRunningAction(MAIN_MODULE, this.randomStrollAction);
                tryAddRunningAction(MAIN_MODULE, this.lookRandomlyAction);
            }
        });
        tryAddRunningAction(CHECKS_MODULE, globalCheck);
        tryAddRunningAction(CHECKS_MODULE, new TargetBullyAction(getMob()));
        tryAddRunningAction(CHECKS_MODULE, new LookForAttackTargetAction<>(getMob(), LookForAttackTargetAction.PLAYER_CLASS_TARGET).setUnseeTimer(new Timer(4)));
        tryAddRunningAction(CHECKS_MODULE, new LookForAristocratAction<>(getMob()).setUnseeTimer(new Timer(0)));
        tryAddRunningAction(IDLE_ACTIONS_MODULE, new FloatAction(getMob(), 1.0F));
    }

    public static class SideAttackAction extends AnimationAction<PetrifiedGhoul, Animation> {

        public SideAttackAction(PetrifiedGhoul mob) {
            super(mob, mob.getSideAttackAnimation(), mob.mainAnimationHandler);
        }

        @Override
        public void update() {
            super.update();
            if (this.animationHandler.getTick() == 2) {
                this.mob.level.playSound(null, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ(), DESounds.ES_PETRIFIED_GHOUL_ATTACK.get(), SoundSource.HOSTILE, 1.0F + 0.07F * this.mob.getRandom().nextFloat(), 1.0F + 0.2F * this.mob.getRandom().nextFloat());
            } else if (this.animationHandler.getTick() == 9) {
                LivingEntity livingEntity = this.mob.getTarget();
                if (livingEntity != null && livingEntity.isAlive() && ReachHelper.reachSqr(this.mob, livingEntity) < PetrifiedGhoul.ATTACK_RANGE * 1.2F) {
                    this.mob.attackWithMultiplier(livingEntity, 1.0F);
                }
            }
        }
    }

    public static class StrongAttackAction extends AnimationAction<PetrifiedGhoul, Animation> {

        public StrongAttackAction(PetrifiedGhoul mob) {
            super(mob, mob.getStrongAttackAnimation(), mob.mainAnimationHandler);
        }

        @Override
        public void update() {
            super.update();
            if (this.animationHandler.getTick() == 8) {
                this.mob.level.playSound(null, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ(), DESounds.ES_PETRIFIED_GHOUL_ATTACK.get(), SoundSource.HOSTILE, 1.5F + 0.07F * this.mob.getRandom().nextFloat(), 1.2F + 0.2F * this.mob.getRandom().nextFloat());
            } else if (this.animationHandler.getTick() == 14) {
                LivingEntity livingEntity = this.mob.getTarget();
                if (livingEntity != null && livingEntity.isAlive() && ReachHelper.reachSqr(this.mob, livingEntity) < PetrifiedGhoul.ATTACK_RANGE * 1.1F) {
                    this.mob.attackWithMultiplier(livingEntity, 1.33F);
                }
            }
        }
    }

    public static class LocalFollowTargetAction extends FollowTargetAction<PetrifiedGhoul> {

        public LocalFollowTargetAction(PetrifiedGhoul mob, ToDoubleFunction<LivingEntity> moveSpeed) {
            super(mob, moveSpeed);
        }

        @Override
        protected void start() {
            super.start();
            this.mob.attacksTillStrongAttack.get().reset();
            this.mob.attackDelay.get().end();
        }
    }
}
