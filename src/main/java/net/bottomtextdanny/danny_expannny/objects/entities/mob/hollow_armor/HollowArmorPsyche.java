package net.bottomtextdanny.danny_expannny.objects.entities.mob.hollow_armor;

import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.Animation;
import net.bottomtextdanny.braincell.mod.entity.psyche.Psyche;
import net.bottomtextdanny.braincell.mod.entity.psyche.actions.*;
import net.bottomtextdanny.braincell.mod.entity.psyche.data.ActionInputKey;
import net.bottomtextdanny.braincell.mod.entity.psyche.actions.target.LookForAristocratAction;
import net.bottomtextdanny.braincell.mod.entity.psyche.actions.target.LookForAttackTargetAction;
import net.bottomtextdanny.braincell.mod.entity.psyche.actions.target.TargetBullyAction;
import net.bottomtextdanny.braincell.mod.entity.psyche.data.UnbuiltActionInputs;
import net.bottomtextdanny.braincell.mod.world.helpers.CombatHelper;
import net.bottomtextdanny.braincell.mod.world.helpers.ReachHelper;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.Timer;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.RandomFloatMapper;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.RandomIntegerMapper;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;

import java.util.Random;
import java.util.function.ToDoubleFunction;

public class HollowArmorPsyche extends Psyche<HollowArmor> {
    public static final int
            MAIN_MODULE = 1,
            ATTACK_ACTIONS_MODULE = 2,
            IDLE_ACTIONS_MODULE = 3,
            HEAL_MODULE = 4;
    private BluntAttackAction bluntAttackAction;
    private SlashAttackAction slashAttackAction;
    private SwingAttackAction swingAttackAction;
    private DoubleSwingAttackAction doubleSwingAttackAction;
    private ImpaleAttackAction impaleAttackAction;
    private DashAction dashAction;
    private HealAction healAction;
    private LookRandomlyAction lookRandomlyAction;
    private RandomStrollAction randomStrollAction;
    private FollowTargetAction<HollowArmor> followTargetAction;

    public HollowArmorPsyche(HollowArmor mob) {
        super(mob);
        initializeActionMap(MAIN_MODULE, IDLE_ACTIONS_MODULE, ATTACK_ACTIONS_MODULE, HEAL_MODULE);
    }

    @Override
    protected void populateInputs(UnbuiltActionInputs inputs) {
        inputs.put(ActionInputKey.SET_TARGET_CALL, () -> {
            getMob().attack_timer.get().end();
            getMob().dash_timer.get().reset();
            getMob().lethal_attack_timer.get().reset();
        });
    }

    @Override
    protected void initialize() {
        this.healAction = new HealAction(getMob());
        this.healAction.addBlockedModule(MAIN_MODULE);
        this.healAction.addBlockedModule(ATTACK_ACTIONS_MODULE);
        this.dashAction = new DashAction(getMob());
        this.dashAction.addBlockedModule(MAIN_MODULE);
        this.bluntAttackAction = new BluntAttackAction(getMob());
        this.bluntAttackAction.addBlockedModule(MAIN_MODULE);
        this.slashAttackAction = new SlashAttackAction(getMob());
        this.slashAttackAction.addBlockedModule(MAIN_MODULE);
        this.swingAttackAction = new SwingAttackAction(getMob());
        this.swingAttackAction.addBlockedModule(MAIN_MODULE);
        this.doubleSwingAttackAction = new DoubleSwingAttackAction(getMob());
        this.doubleSwingAttackAction.addBlockedModule(MAIN_MODULE);
        this.impaleAttackAction = new ImpaleAttackAction(getMob());
        this.impaleAttackAction.addBlockedModule(MAIN_MODULE);
        this.followTargetAction = new FollowTargetAction<>(getMob(), target -> HollowArmor.COMBAT_MOVE_SPEED).setRefreshRate(10);
        this.followTargetAction.addBlockedModule(IDLE_ACTIONS_MODULE);
        this.randomStrollAction = new WaterAvoidingRandomStrollAction(getMob(), RandomIntegerMapper.of(220, 320));
        this.randomStrollAction.addModule(IDLE_ACTIONS_MODULE);
        this.lookRandomlyAction = new LookRandomlyAction(getMob(), RandomIntegerMapper.of(120, 160)).vertical(RandomFloatMapper.of(-0.28F, 0.15F));
        this.lookRandomlyAction.addModule(IDLE_ACTIONS_MODULE);
        ConstantThoughtAction<HollowArmor> globalCheck = ConstantThoughtAction.withUpdateCallback(getMob(), mobo -> {
            getMob().setMovementReduction(0.0F);

            if (getMob().mainAnimationHandler.isPlayingNull() && !getMob().healing_used.get() && CombatHelper.getHealthNormalized(getMob()) < 0.5F) {
                tryAddRunningAction(HEAL_MODULE, this.healAction);
            }

            if (mobo.getTarget() != null) {
                if (getMob().mainAnimationHandler.isPlayingNull()) {
                    float reach = ReachHelper.reachSqr(getMob(), getMob().getTarget());
                    if (reach < HollowArmor.ATTACK_RANGE) {
                        if (getMob().lethal_attack_timer.get().hasEnded()) {
                            tryAddRunningAction(ATTACK_ACTIONS_MODULE, this.impaleAttackAction);
                            getMob().lethal_attack_timer.get().reset();
                        } else {
                            if (getMob().attack_timer.get().hasEnded()) {
                                float rgn = getMob().getRandom().nextFloat();
                                if (rgn < 0.15F) {
                                    tryAddRunningAction(ATTACK_ACTIONS_MODULE, this.doubleSwingAttackAction);
                                } else if (rgn < 0.40F) {
                                    tryAddRunningAction(ATTACK_ACTIONS_MODULE, this.bluntAttackAction);
                                } else if (rgn < 0.65F) {
                                    tryAddRunningAction(ATTACK_ACTIONS_MODULE, this.slashAttackAction);
                                } else {
                                    tryAddRunningAction(ATTACK_ACTIONS_MODULE, this.swingAttackAction);
                                }
                                getMob().attack_timer.get().reset();
                            } else {
                                getMob().attack_timer.get().advance();
                            }

                            getMob().lethal_attack_timer.get().advance();
                        }
                    } else if (reach > HollowArmor.START_DASHING_RANGE && getMob().dash_timer.get().hasEnded()) {
                        tryAddRunningAction(ATTACK_ACTIONS_MODULE, this.dashAction);
                        getMob().dash_timer.get().reset();
                    }
                }

                getMob().dash_timer.get().advance();
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
    }

    public static class LocalFollowTargetAction extends FollowTargetAction<HollowArmor> {

        public LocalFollowTargetAction(HollowArmor mob, ToDoubleFunction<LivingEntity> moveSpeed) {
            super(mob, moveSpeed);
        }

        @Override
        protected void start() {
            super.start();
        }
    }

    public static class BluntAttackAction extends AnimationAction<HollowArmor, Animation> {

        public BluntAttackAction(HollowArmor mob) {
            super(mob, mob.getBluntAnimation(), mob.mainAnimationHandler);
        }

        @Override
        public void update() {
            Random random = this.mob.getRandom();
            this.mob.setMovementReduction(1.0F);
            addBlockedModule(MAIN_MODULE);

            if (CombatHelper.validAttackTarget(this.mob)) {
                LivingEntity target = this.mob.getTarget();

                if (this.animationHandler.getTick() == 9 && ReachHelper.reachSqr(this.mob, target) < HollowArmor.ATTACK_RANGE * 1.5F) {

                    CombatHelper.attackWithMultiplier(this.mob, target, 1.0F);
                    CombatHelper.mayDisableShield(this.mob, target, 20, 0.25F);
                }
                this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
            }

            this.mob.setYRot(this.mob.yHeadRot);

            if (this.animationHandler.getTick() == 1) this.mob.playSound(DESounds.ES_POSSESSED_ARMOR_LOW_CREAK.get(), 1.0F, 1.0F + random.nextFloat() * 0.2F);
            else if (this.animationHandler.getTick() == 6) this.mob.playSound(DESounds.ES_POSSESSED_ARMOR_THIN_CREAK.get(), 1.0F, 1.0F + random.nextFloat() * 0.2F);
            else if (this.animationHandler.getTick() == 7) this.mob.playSound(DESounds.ES_BLADE_SWING_MODERATED.get(), 1.5F, 1.0F + random.nextFloat() * 0.2F);
            else if (this.animationHandler.getTick() == 8) this.mob.playSound(DESounds.ES_POSSESSED_ARMOR_CREAK.get(), 1.0F, 1.0F + random.nextFloat() * 0.2F);
        }
    }

    public static class SlashAttackAction extends AnimationAction<HollowArmor, Animation> {

        public SlashAttackAction(HollowArmor mob) {
            super(mob, mob.getSlashAnimation(), mob.mainAnimationHandler);
        }

        @Override
        public void update() {
            Random random = this.mob.getRandom();
            this.mob.setMovementReduction(1.0F);

            if (CombatHelper.validAttackTarget(this.mob)) {
                LivingEntity target = this.mob.getTarget();
                if (this.animationHandler.getTick() == 10 && ReachHelper.reachSqr(this.mob, target) < HollowArmor.ATTACK_RANGE * 1.5F) {

                    CombatHelper.attackWithMultiplier(this.mob, target, 1.1F);
                    CombatHelper.mayDisableShield(this.mob, target, 20, 0.25F);
                }

                this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
            }

            this.mob.setYRot(this.mob.yHeadRot);

            if (this.animationHandler.getTick() == 1) this.mob.playSound(DESounds.ES_POSSESSED_ARMOR_LOW_CREAK.get(), 1.0F, 1.0F + random.nextFloat() * 0.2F);
            else if (this.animationHandler.getTick() == 7) this.mob.playSound(DESounds.ES_POSSESSED_ARMOR_CREAK.get(), 1.0F, 1.0F + random.nextFloat() * 0.2F);
            else if (this.animationHandler.getTick() == 8) {
                this.mob.playSound(DESounds.ES_POSSESSED_ARMOR_SWISH.get(), 1.5F, 1.0F + random.nextFloat() * 0.2F);
                this.mob.playSound(DESounds.ES_BLADE_SWING_LARGE.get(), 1.5F, 1.0F + random.nextFloat() * 0.2F);
            }
        }
    }

    public static class SwingAttackAction extends AnimationAction<HollowArmor, Animation> {

        public SwingAttackAction(HollowArmor mob) {
            super(mob, mob.getSwingAnimation(), mob.mainAnimationHandler);
        }

        @Override
        public void update() {
            Random random = this.mob.getRandom();
            this.mob.setMovementReduction(1.0F);

            if (CombatHelper.validAttackTarget(this.mob)) {
                LivingEntity target = this.mob.getTarget();
                if (this.animationHandler.getTick() == 8 && ReachHelper.reachSqr(this.mob, target) < HollowArmor.ATTACK_RANGE * 1.5F) {
                    CombatHelper.attackWithMultiplier(this.mob, target, 1.15F);
                    CombatHelper.mayDisableShield(this.mob, target, 20, 0.25F);
                }
                this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
            }

            this.mob.setYRot(this.mob.yHeadRot);

            if (this.animationHandler.getTick() == 1) this.mob.playSound(DESounds.ES_POSSESSED_ARMOR_LOW_CREAK.get(), 1.0F, 1.0F + random.nextFloat() * 0.2F);
            else if (this.animationHandler.getTick() == 4) this.mob.playSound(DESounds.ES_POSSESSED_ARMOR_CREAK.get(), 1.0F, 1.0F + random.nextFloat() * 0.2F);
            else if (this.animationHandler.getTick() == 7) {
                this.mob.playSound(DESounds.ES_POSSESSED_ARMOR_SWISH.get(), 1.5F, 1.0F + random.nextFloat() * 0.2F);
                this.mob.playSound(DESounds.ES_BLADE_SWING_MODERATED.get(), 1.5F, 1.0F + random.nextFloat() * 0.2F);
            }
        }
    }

    public static class DoubleSwingAttackAction extends AnimationAction<HollowArmor, Animation> {

        public DoubleSwingAttackAction(HollowArmor mob) {
            super(mob, mob.getDoubleSwingAnimation(), mob.mainAnimationHandler);
        }

        @Override
        public void update() {
            Random random = this.mob.getRandom();
            this.mob.setMovementReduction(1.0F);

            if (CombatHelper.validAttackTarget(this.mob)) {
                LivingEntity target = this.mob.getTarget();
                
                if (ReachHelper.reachSqr(this.mob, target) < HollowArmor.ATTACK_RANGE * 1.5F) {
                    if (this.animationHandler.getTick() == 10) {

                        CombatHelper.attackWithMultiplier(this.mob, target, 1.0F);
                        CombatHelper.mayDisableShield(this.mob, target, 20, 0.25F);
                        target.invulnerableTime = 0;
                    } else if (this.animationHandler.getTick() == 14) {

                        CombatHelper.attackWithMultiplier(this.mob, target, 1.0F);
                        CombatHelper.disableShield(this.mob, target, 20);
                    }
                }

                this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
            }

            this.mob.setYRot(this.mob.yHeadRot);

            if (this.animationHandler.getTick() == 1) this.mob.playSound(DESounds.ES_POSSESSED_ARMOR_CREAK.get(), 1.0F, 1.0F + random.nextFloat() * 0.2F);

            else if (this.animationHandler.getTick() == 6 || this.animationHandler.getTick() == 12) {
                this.mob.playSound(DESounds.ES_POSSESSED_ARMOR_SWISH.get(), 1.5F, 1.0F + random.nextFloat() * 0.2F);
                this.mob.playSound(DESounds.ES_BLADE_SWING_SMALL.get(), 1.5F, 1.0F + random.nextFloat() * 0.2F);
                this.mob.playSound(DESounds.ES_POSSESSED_ARMOR_THIN_CREAK.get(), 1.0F, 1.0F + random.nextFloat() * 0.2F);
            }
            else if (this.animationHandler.getTick() == 7) this.mob.playSound(DESounds.ES_POSSESSED_ARMOR_LOW_CREAK.get(), 1.0F, 1.0F + random.nextFloat() * 0.2F);
        }
    }

    public static class ImpaleAttackAction extends AnimationAction<HollowArmor, Animation> {

        public ImpaleAttackAction(HollowArmor mob) {
            super(mob, mob.getImpaleAnimation(), mob.mainAnimationHandler);
        }

        @Override
        public void update() {
            Random random = this.mob.getRandom();
            this.mob.setMovementReduction(1.0F);

            if (CombatHelper.validAttackTarget(this.mob)) {
                LivingEntity target = this.mob.getTarget();

                if (this.animationHandler.getTick() == 16 && ReachHelper.reachSqr(this.mob, target) < HollowArmor.ATTACK_RANGE * 1.8F) {
                    CombatHelper.attackWithMultiplier(this.mob, target, 1.4F);
                    CombatHelper.disableShield(this.mob, target, 40);
                }
                this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
            }

            this.mob.setYRot(this.mob.yHeadRot);

            if (this.animationHandler.getTick() == 4) this.mob.playSound(DESounds.ES_POSSESSED_ARMOR_HEAVY_CREAK.get(), 1.0F, 1.0F + random.nextFloat() * 0.2F);
            else if (this.animationHandler.getTick() == 7) this.mob.playSound(DESounds.ES_POSSESSED_ARMOR_HARD_STEP.get(), 1.0F, 1.0F + random.nextFloat() * 0.2F);
            else if (this.animationHandler.getTick() == 16) this.mob.playSound(DESounds.ES_POSSESSED_ARMOR_THIN_CREAK.get(), 1.0F, 1.0F + random.nextFloat() * 0.2F);
            else if (this.animationHandler.getTick() == 14) this.mob.playSound(DESounds.ES_BLADE_SWING_LARGE.get(), 1.5F, 1.0F + random.nextFloat() * 0.2F);
            else if (this.animationHandler.getTick() == 15) this.mob.playSound(DESounds.ES_POSSESSED_ARMOR_CREAK.get(), 1.0F, 1.0F + random.nextFloat() * 0.2F);
        }
    }

    public static class DashAction extends AnimationAction<HollowArmor, Animation> {
        private boolean didDamage;

        public DashAction(HollowArmor mob) {
            super(mob, mob.getDashAnimation(), mob.mainAnimationHandler);
        }

        @Override
        public void update() {
            Random random = this.mob.getRandom();
            Vec3 vecToTarget = Vec3.ZERO;
            float distance = 2.0F;
            this.mob.setMovementReduction(1.0F);

            if (this.mob.getTarget() != null) {

                LivingEntity target = this.mob.getTarget();

                distance = ReachHelper.horizontalReach(this.mob, target) + 0.5F;

                this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);

                if (this.animationHandler.getTick() == 19) vecToTarget = DEMath.fromPitchYaw(0.0F, DEMath.getTargetYaw(this.mob, target));

                if (this.animationHandler.getTick() >= 20 && this.animationHandler.getTick() <= 32 && !this.didDamage && ReachHelper.reachSqr(this.mob, target) < 5.0F) {
                    CombatHelper.attackWithMultiplier(this.mob, target, 1.2F);
                    CombatHelper.disableShield(this.mob, target, 30);
                    this.didDamage = true;
                }


            } else {
                if (this.animationHandler.getTick() == 19) {
                    vecToTarget = DEMath.fromPitchYaw(0.0F, this.mob.yHeadRot);
                }

            }

            if (this.animationHandler.getTick() < 27) {
                this.mob.setYRot(this.mob.yHeadRot);
                this.mob.yBodyRot = this.mob.yHeadRot;
            }

            if (this.animationHandler.getTick() == 4) {
                this.mob.playSound(DESounds.ES_POSSESSED_ARMOR_HEAVY_CREAK.get(), 1.0F, 1.0F + random.nextFloat() * 0.2F);
            } else if (this.animationHandler.getTick() == 19) {
                this.mob.playSound(DESounds.ES_POSSESSED_ARMOR_SWISH.get(), 1.5F, 1.0F + random.nextFloat() * 0.2F);
                this.mob.playSound(DESounds.ES_BLADE_SWING_LARGE.get(), 1.5F, 1.0F + random.nextFloat() * 0.2F);

                this.mob.sendClientMsg(HollowArmor.PLAY_DASH_SOUND_CLIENT_CALL, PacketDistributor.TRACKING_ENTITY.with(() -> this.mob));
            }

            if (this.animationHandler.getTick() == 19) {

                this.mob.getDashMotion().setMotion(vecToTarget.multiply(distance, 0.0, distance));
               // this.mob.move(MoverType.SELF, vecToTarget.multiply(distance, 0.0, distance));
            }
        }

        @Override
        public void onEnd() {
            super.onEnd();
            this.didDamage = false;
        }
    }

    public static class HealAction extends AnimationAction<HollowArmor, Animation> {

        public HealAction(HollowArmor mob) {
            super(mob, mob.getHealAnimation(), mob.mainAnimationHandler);
        }

        @Override
        public void update() {
            this.mob.setMovementReduction(1.0F);

            for (int drinkTicks = 0; drinkTicks < 3; drinkTicks++) {
                if (this.animationHandler.getTick() == 12 + (drinkTicks << 1)) this.mob.playSound(SoundEvents.GENERIC_DRINK, 1.0F, 1.0F);
            }

            if (this.animationHandler.getTick() == 18) {
                this.mob.healing_used.set(true);
                this.mob.setHealth(this.mob.getHealth() + 10.0F);
            }
        }
    }
}
