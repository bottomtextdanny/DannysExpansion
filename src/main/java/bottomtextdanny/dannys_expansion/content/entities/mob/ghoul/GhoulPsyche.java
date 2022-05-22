package bottomtextdanny.dannys_expansion.content.entities.mob.ghoul;

import bottomtextdanny.dannys_expansion.content.entities.Shared;
import bottomtextdanny.dannys_expansion.tables.DESounds;
import bottomtextdanny.braincell.base.Chooser;
import bottomtextdanny.braincell.base.scheduler.IntScheduler;
import bottomtextdanny.braincell.base.value_mapper.FloatMappers;
import bottomtextdanny.braincell.base.value_mapper.RandomIntegerMapper;
import bottomtextdanny.braincell.mod.entity.modules.animatable.SimpleAnimation;
import bottomtextdanny.braincell.mod.entity.psyche.Action;
import bottomtextdanny.braincell.mod.entity.psyche.MarkedTimer;
import bottomtextdanny.braincell.mod.entity.psyche.Psyche;
import bottomtextdanny.braincell.mod.entity.psyche.actions.*;
import bottomtextdanny.braincell.mod.entity.psyche.actions.target.LookForAttackTargetAction;
import bottomtextdanny.braincell.mod.entity.psyche.actions.target.TargetBullyAction;
import bottomtextdanny.braincell.mod.entity.psyche.input.ActionInputKey;
import bottomtextdanny.braincell.mod.entity.psyche.input.UnbuiltActionInputs;
import bottomtextdanny.braincell.mod.entity.psyche.targeting.MobMatchPredicate;
import bottomtextdanny.braincell.mod.entity.psyche.targeting.SearchNearestPredicates;
import bottomtextdanny.braincell.mod.world.helpers.CombatHelper;
import bottomtextdanny.braincell.mod.world.helpers.ReachHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

import javax.annotation.Nonnull;

public class GhoulPsyche extends Psyche<Ghoul> {
    public static final int
            ANIMATION_ACTIONS_MODULE = 1,
            MAIN_MODULE = 2,
            IDLE_ACTIONS_MODULE = 3;
    public static Chooser<SimpleAnimation> ATTACKS = Chooser.<SimpleAnimation>builder()
            .put(30.0F, Ghoul.PUNCH)
            .put(40.0F, Ghoul.UP_PUNCH)
            .put(10.0F, Ghoul.TRAP)
            .build();
    public static MobMatchPredicate<LivingEntity> ONLY_IF_HUNGRY = (Mob mob, LivingEntity target) -> {
        return /*hunger mechanic? nah*/Shared.DEFAULT_TARGET_PARAMETERS.test(mob, target);
    };
    private final MarkedTimer unseenTimer;
    private AttackAction attackAction;
    private LookRandomlyAction lookRandomlyAction;
    private RandomStrollAction randomStrollAction;
    private FollowTargetAction<Ghoul> followTargetAction;

    public GhoulPsyche(Ghoul mob) {
        super(mob);
        this.unseenTimer = new MarkedTimer(IntScheduler.simple(40));
        allocateModules(3);
    }

    @Override
    protected void populateInputs(UnbuiltActionInputs inputs) {
        inputs.put(ActionInputKey.MARKED_UNSEEN, () -> this.unseenTimer);
    }

    @Override
    protected void initialize() {
        this.attackAction = new AttackAction(getMob());

        this.followTargetAction = new FollowTargetAction<>(getMob(), target -> Ghoul.COMBAT_MOVE_SPEED).setRefreshRate(10);

        this.randomStrollAction = new WaterAvoidingRandomStrollAction(getMob(), Shared.LAND_STROLL, RandomIntegerMapper.of(220, 320));
        this.randomStrollAction.addModule(IDLE_ACTIONS_MODULE);
        this.lookRandomlyAction = new LookRandomlyAction(getMob(), RandomIntegerMapper.of(120, 160)).vertical(FloatMappers.of(-0.28F, 0.15F));
        this.lookRandomlyAction.addModule(IDLE_ACTIONS_MODULE);

        ConstantThoughtAction<Ghoul> globalCheck = ConstantThoughtAction.withUpdateCallback(getMob(), mobo -> {
            LivingEntity target = mobo.getTarget();

            if (CombatHelper.isValidAttackTarget(mobo, target))
                onTargeting(mobo, target);
            else idling(mobo);
        });

        tryAddRunningAction(CHECKS_MODULE, globalCheck);
        tryAddRunningAction(CHECKS_MODULE, new TargetBullyAction(getMob(), Shared.DEFAULT_TARGET_PARAMETERS));
        tryAddRunningAction(CHECKS_MODULE, new LookForAttackTargetAction<>(
                getMob(), IntScheduler.simple(12),
                Shared.DEFAULT_TARGET_PARAMETERS, SearchNearestPredicates.nearestPlayer()));
        tryAddRunningAction(CHECKS_MODULE, new LookForAttackTargetAction<>(
                getMob(), IntScheduler.simple(30),
                Shared.GOODIE.and(Shared.DEFAULT_TARGET_PARAMETERS), SearchNearestPredicates.nearestLiving()));
        tryAddRunningAction(IDLE_ACTIONS_MODULE, new FloatAction(getMob(), 0.5F));
    }

    protected void idling(Ghoul mob) {
        tryAddRunningAction(MAIN_MODULE, this.randomStrollAction);
        tryAddRunningAction(MAIN_MODULE, this.lookRandomlyAction);
    }

    protected void onTargeting(Ghoul mob, @Nonnull LivingEntity target) {
        blockModule(IDLE_ACTIONS_MODULE);
        tryAddRunningAction(MAIN_MODULE, this.followTargetAction);

        float reach = ReachHelper.reach3(mob, target, ReachHelper::euclideanEntityReach3);

        if (reach < Ghoul.MELEE_REACH) {
            tryAddRunningAction(ANIMATION_ACTIONS_MODULE, attackAction);
        }
    }

    public static class AttackAction extends Action<Ghoul> {
        private SimpleAnimation animation;

        public AttackAction(Ghoul mob) {
            super(mob);
        }

        @Override
        public boolean canStart() {
            return mob.mainHandler.isPlayingNull();
        }

        @Override
        protected void start() {
            SimpleAnimation animation = ATTACKS.pick(RANDOM);

            this.animation = animation;

            if (animation != Ghoul.TRAP) {
                mob.setLeftHandlingAndSync(RANDOM.nextBoolean());
            }

            mob.mainHandler.play(animation);
        }

        @Override
        public boolean shouldKeepGoing() {
            return this.active() && this.mob.mainHandler.isPlaying(animation);
        }

        @Override
        public void update() {
            getPsyche().blockModule(MAIN_MODULE);
            this.mob.setInputMovementMultiplier(0.0F);

            this.mob.setYRot(this.mob.yHeadRot);
            LivingEntity target = this.mob.getTarget();
            
            if (!CombatHelper.isValidAttackTarget(mob, target)) return;

            this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);

            if (mob.mainHandler.getTick() == 6)
                this.mob.playSound(DESounds.ES_GHOUL_PUNCH.get(), 1.0F, 1.0F + RANDOM.nextFloat() * 0.2F);

            if (mob.mainHandler.getTick() != 9) return;

            float reach = ReachHelper.reach3(mob, target, ReachHelper::euclideanEntityReach3);

            if (reach < Ghoul.MELEE_REACH * 1.25F) {
                CombatHelper.attackWithMultiplier(this.mob, target, 1.0F);
                CombatHelper.mayDisableShield(target, 20, 0.25F);
            }
        }
    }
}
