package bottomtextdanny.dannys_expansion.content.entities.mob.klifour;

import bottomtextdanny.dannys_expansion.content.entities.Shared;
import bottomtextdanny.dannys_expansion.content.entities.projectile.KlifourSpit;
import bottomtextdanny.dannys_expansion.tables.DEEntities;
import bottomtextdanny.dannys_expansion.tables.DESounds;
import bottomtextdanny.braincell.base.FloatRandomPicker;
import bottomtextdanny.braincell.base.scheduler.IntScheduler;
import bottomtextdanny.braincell.base.value_mapper.FloatMappers;
import bottomtextdanny.braincell.base.value_mapper.RandomIntegerMapper;
import bottomtextdanny.braincell.mod.entity.modules.animatable.SimpleAnimation;
import bottomtextdanny.braincell.mod.entity.psyche.MarkedTimer;
import bottomtextdanny.braincell.mod.entity.psyche.Psyche;
import bottomtextdanny.braincell.mod.entity.psyche.actions.AnimationAction;
import bottomtextdanny.braincell.mod.entity.psyche.actions.ConstantThoughtAction;
import bottomtextdanny.braincell.mod.entity.psyche.actions.LookRandomlyAction;
import bottomtextdanny.braincell.mod.entity.psyche.actions.target.TargetBullyAction;
import bottomtextdanny.braincell.mod.entity.psyche.extras.BCAABBs;
import bottomtextdanny.braincell.mod.entity.psyche.extras.TargetConsumers;
import bottomtextdanny.braincell.mod.entity.psyche.extras.TargetSideReaction;
import bottomtextdanny.braincell.mod.entity.psyche.input.ActionInputKey;
import bottomtextdanny.braincell.mod.entity.psyche.input.UnbuiltActionInputs;
import bottomtextdanny.braincell.mod.world.helpers.CombatHelper;
import bottomtextdanny.braincell.mod.world.helpers.ReachHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.shapes.Shapes;

import javax.annotation.Nonnull;
import java.util.Random;

public class KlifourPsyche extends Psyche<Klifour> {
    public static final int ATTACH_CHECK_INTERVAL = 8;
    public static final int
            MAIN_MODULE = 1,
            ATTACK_ACTIONS_MODULE = 2,
            IDLE_ACTIONS_MODULE = 3,
            HEAL_MODULE = 4;
    private static final TargetSideReaction<Klifour> AGGRO_PEER = (mob, targeter, target) -> {
        LivingEntity actualTarget = mob.getTarget();

        if (!CombatHelper.isValidAttackTarget(mob, actualTarget)) {
            mob.setAggressive(true);
            mob.setTarget(target);
        }
    };
    private SpitAction spitTargetAction;
    private ScrubAction scrubAction;
    private HideAction hideAction;
    private UnhideAction unhideAction;
    private NauseaAction nauseaAction;
    private LookRandomlyAction lookRandomlyAction;
    private int blockUpdate;

    public KlifourPsyche(Klifour mob) {
        super(mob);
        allocateModules(5);
    }

    @Override
    protected void populateInputs(UnbuiltActionInputs inputs) {
        MarkedTimer unseenTimer = new MarkedTimer(IntScheduler.simple(40));
        inputs.put(ActionInputKey.MARKED_UNSEEN, () -> unseenTimer);
    }

    @Override
    protected void initialize() {
        spitTargetAction = new SpitAction(getMob());
        scrubAction = new ScrubAction(getMob());
        nauseaAction = new NauseaAction(getMob());
        nauseaAction.addBlockedModule(ATTACK_ACTIONS_MODULE);
        hideAction = new HideAction(getMob());
        hideAction.addBlockedModule(ATTACK_ACTIONS_MODULE);
        unhideAction = new UnhideAction(getMob());
        lookRandomlyAction = new LookRandomlyAction(getMob(), RandomIntegerMapper.of(120, 160)).vertical(FloatMappers.of(-0.28F, 0.15F));
        lookRandomlyAction.addModule(IDLE_ACTIONS_MODULE);

        ConstantThoughtAction<Klifour> globalCheck = ConstantThoughtAction.withUpdateCallback(getMob(), mobo -> {
            LivingEntity target = mobo.getTarget();
            boolean hidden = mobo.isHidden();
            boolean validTarget = CombatHelper.isValidAttackTarget(mobo, target);
            boolean hiding = mobo.hideHandler.isPlaying(Klifour.HIDE);
            boolean showingUp = mobo.hideHandler.isPlaying(Klifour.SHOW_UP);

            if (hidden) hidden(mobo);
            else if (!hiding && !showingUp) {
                unhidden(mobo);

                if (!validTarget)
                    idle(mobo);
            }

            if (validTarget && !hiding && !showingUp) {
                targeting(mobo, target, hidden);
            }

            handlePosition(mobo);

            if (blockUpdate == 0) {
                blockUpdate = ATTACH_CHECK_INTERVAL;
                checkAttachingBlock(mobo);
            } else blockUpdate--;

            mobo.syncHiddenState();
        });
        tryAddRunningAction(CHECKS_MODULE, globalCheck);
        tryAddRunningAction(CHECKS_MODULE, new TargetBullyAction(getMob(), Shared.DEFAULT_TARGET_PARAMETERS)
                .findTargetCallOut(TargetConsumers.entityGroupAction(
                        Klifour.class,
                        targeter -> BCAABBs.livingEntityRange(targeter, 20, 10),
                        AGGRO_PEER)));
    }

    private void handlePosition(Klifour mob) {
        int xL = mob.getXLocation();
        int yL = mob.getYLocation();
        int zL = mob.getZLocation();
        Direction direction = mob.getAttachingDirection();

        if (mob.getAttachingLocation() == BlockPos.ZERO) mob.setLocation(mob.blockPosition());

        if (direction.get3DDataValue() == 0)
            mob.setPos(xL + 0.5F, yL + 0.0, zL + 0.5F);
        else if (direction.get3DDataValue() == 1)
            mob.setPos(xL + 0.5F, yL + 0.5, zL + 0.5F);
        else mob.setPos(xL + 0.5F, yL + 0.25, zL + 0.5F);
        mob.setNoGravity(true);
    }

    public void checkAttachingBlock(Klifour mob) {
        BlockPos pos = mob.blockPosition();
        BlockPos attachPos = mob.getAttachingBlock();

        if (mob.level.getBlockState(pos).is(BlockTags.LEAVES)) {
            mob.level.destroyBlock(pos, false);
        }

        if (!mob.level.getBlockState(attachPos).getCollisionShape(mob.level, attachPos).equals(Shapes.block()))
            mob.kill();
    }

    public void hidden(Klifour mob) {
        IntScheduler.Ranged timer = mob.getUnhideTimer();

        tryClearBadEffects(mob);

        if (unhideAction.isRunning()) return;

        if (timer.hasEnded()) {
            tryAddRunningAction(MAIN_MODULE, unhideAction);
            if (unhideAction.isRunning())
                timer.reset(FloatRandomPicker.normal());
        } else {
            timer.incrementFreely(1);
        }
    }

    private void tryClearBadEffects(Klifour mob) {
        if (getMob().isNauseous()) {
            mob.getActiveEffects().removeIf(effectInstance -> effectInstance.getEffect().getCategory() == MobEffectCategory.HARMFUL);
            mob.updateEffectVisibility();
            mob.setNauseous(false);
        }
    }

    public void idle(Klifour mob) {
        tryAddRunningAction(IDLE_ACTIONS_MODULE, lookRandomlyAction);
    }

    public void unhidden(Klifour mob) {
        IntScheduler.Ranged timer = mob.getHideTimer();

        if (mob.hasBadEffects()) {
            tryAddRunningAction(MAIN_MODULE, nauseaAction);
            if (nauseaAction.isRunning()) mob.setNauseous(true);
        }

        if (!hideAction.isRunning()) {
            if (timer.hasEnded() || (!nauseaAction.isRunning() && mob.isNauseous())) {
                tryAddRunningAction(MAIN_MODULE, hideAction);
                if (hideAction.isRunning())
                    timer.reset(FloatRandomPicker.normal());
            } else timer.incrementFreely(1);
        }
    }

    //we suppose 'target' is a valid target for this psyche
    public void targeting(Klifour mob, @Nonnull LivingEntity target, boolean hidden) {
        float reach = ReachHelper.reach3(mob, target, ReachHelper::euclideanEntityReach3);

        blockModule(IDLE_ACTIONS_MODULE);

        IntScheduler.Ranged spit = mob.getSpitTimer();

        spit.advance();

        if (!hidden) {

            if (reach < Klifour.SCRUB_REACH * 0.8F) {
                tryAddRunningAction(ATTACK_ACTIONS_MODULE, scrubAction);
            }

            if (spit.hasEnded()) {
                tryAddRunningAction(ATTACK_ACTIONS_MODULE, spitTargetAction);
                spit.reset(FloatRandomPicker.normal());
            }
        }

        mob.getLookControl().setLookAt(target, 5.0F, 90.0F);
    }

    public static final class SpitAction extends AnimationAction<Klifour, SimpleAnimation> {

        public SpitAction(Klifour mob) {
            super(mob, Klifour.SPIT, mob.mainHandler);
        }

        @Override
        public boolean canStart() {
            return super.canStart() && animationHandler.isPlayingNull();
        }

        @Override
        public void update() {
            Random random = this.mob.getRandom();
            LivingEntity target = this.mob.getTarget();

            if (!CombatHelper.isValidAttackTarget(target)) return;

            mob.getLookControl().setLookAt(target, 30.0F, 30.0F);

            if (animationHandler.getTick() == 4)
                mob.playSound(DESounds.ES_KLIFOUR_SPIT.get(), 1.0F, 1.0F + random.nextFloat() * 0.2F);
            else if (animationHandler.getTick() == 6) {
                KlifourSpit spit = DEEntities.KLIFOUR_SPIT.get().create(mob.level);

                if (spit == null) return;

                spit.setPos(mob.getX(), mob.getEyeY(), mob.getZ());
                spit.setXRot(mob.getXRot());
                spit.setYRot(mob.getYHeadRot());
                spit.setCaster(mob);
                mob.level.addFreshEntity(spit);
            }
        }
    }

    public static final class ScrubAction extends AnimationAction<Klifour, SimpleAnimation> {

        public ScrubAction(Klifour mob) {
            super(mob, Klifour.SCRUB, mob.mainHandler);
        }

        @Override
        public boolean canStart() {
            return super.canStart() && animationHandler.isPlayingNull();
        }

        @Override
        public void update() {
            Random random = mob.getRandom();
            LivingEntity target = mob.getTarget();

            if (!CombatHelper.isValidAttackTarget(target)) return;

            mob.getLookControl().setLookAt(target, 30.0F, 30.0F);

            if (animationHandler.getTick() == 1)
                mob.playSound(DESounds.ES_KLIFOUR_SCRUB.get(), 1.0F, 1.0F + random.nextFloat() * 0.2F);
            else if (animationHandler.getTick() == 5) {
                float reach = ReachHelper.reach3(mob, target, ReachHelper::euclideanEntityReach3);

                if (reach < Klifour.SCRUB_REACH)
                    target.addEffect(new MobEffectInstance(MobEffects.POISON, 200, mob.level.getDifficulty().getId() - 1));
            }
        }
    }

    public static final class UnhideAction extends AnimationAction<Klifour, SimpleAnimation> {

        public UnhideAction(Klifour mob) {
            super(mob, Klifour.SHOW_UP, mob.hideHandler);
        }

        @Override
        public boolean canStart() {
            return super.canStart();
        }

        @Override
        protected void start() {
            super.start();
            mob.setHidden(false);
        }

        @Override
        public void update() {
            if (animationHandler.getTick() == 1) {
                Random random = mob.getRandom();

                mob.playSound(DESounds.ES_KLIFOUR_SHOW_UP.get(), 1.0F, 1.0F + random.nextFloat() * 0.2F);
            }
        }
    }

    public static final class HideAction extends AnimationAction<Klifour, SimpleAnimation> {

        public HideAction(Klifour mob) {
            super(mob, Klifour.HIDE, mob.hideHandler);
        }

        @Override
        public boolean canStart() {
            return super.canStart();
        }

        @Override
        public void update() {
            if (animationHandler.getTick() == 1) {
                Random random = mob.getRandom();
                mob.playSound(DESounds.ES_KLIFOUR_HIDE.get(), 1.0F, 1.0F + random.nextFloat() * 0.2F);
            } else if (animationHandler.getTick() == Klifour.HIDE.getDuration() - 5)
                mob.setHidden(true);
        }
    }

    public static final class NauseaAction extends AnimationAction<Klifour, SimpleAnimation> {

        public NauseaAction(Klifour mob) {
            super(mob, Klifour.NAUSEA, mob.hideHandler);
        }

        @Override
        public boolean canStart() {
            return super.canStart() && animationHandler.isPlayingNull() && !mob.isHidden();
        }

        @Override
        public void onEnd() {
            super.onEnd();
            if (!mob.isHidden()) mob.getHideTimer().end();
        }
    }
}
