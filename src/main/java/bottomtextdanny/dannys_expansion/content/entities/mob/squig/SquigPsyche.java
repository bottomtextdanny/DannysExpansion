package bottomtextdanny.dannys_expansion.content.entities.mob.squig;

import bottomtextdanny.dannys_expansion._util.DERayUtil;
import bottomtextdanny.dannys_expansion.content.entities.Shared;
import bottomtextdanny.dannys_expansion.content.entities.ai.HoverProfile;
import bottomtextdanny.dannys_expansion.content.entities.ai.controllers.MGLookController;
import bottomtextdanny.dannys_expansion.content.entities.projectile.SquigBubbleEntity;
import bottomtextdanny.dannys_expansion.tables.DEEntities;
import bottomtextdanny.braincell.base.scheduler.IntScheduler;
import bottomtextdanny.braincell.mod.entity.modules.animatable.SimpleAnimation;
import bottomtextdanny.braincell.mod.entity.psyche.MarkedTimer;
import bottomtextdanny.braincell.mod.entity.psyche.Psyche;
import bottomtextdanny.braincell.mod.entity.psyche.actions.AnimationAction;
import bottomtextdanny.braincell.mod.entity.psyche.actions.ConstantThoughtAction;
import bottomtextdanny.braincell.mod.entity.psyche.actions.target.TargetBullyAction;
import bottomtextdanny.braincell.mod.entity.psyche.input.ActionInputKey;
import bottomtextdanny.braincell.mod.entity.psyche.input.UnbuiltActionInputs;
import bottomtextdanny.braincell.mod.entity.psyche.pos_finder.MobPosPredicate;
import bottomtextdanny.braincell.mod.entity.psyche.pos_finder.MobPosProcessor;
import bottomtextdanny.braincell.mod.world.helpers.CombatHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.function.Function;

import static bottomtextdanny.braincell.mod.entity.psyche.pos_finder.MobPosProcessors.*;
import static bottomtextdanny.braincell.mod.entity.psyche.pos_finder.MobPosPredicates.*;

public class SquigPsyche extends Psyche<Squig> {
    private static final int
            IMPULSE_MODULE = 1;
    private static MobPosPredicate<Object> STABILIZE = isSolid().negate()
            .and(noFluid());
    private static HoverProfile HOVER_PROFILE = new HoverProfile(STABILIZE, 6, 20);
    private static MobPosProcessor<Object> IDLE_POS = (pos, mob, r, extra) -> {

            HOVER_PROFILE.update(mob);

        float yOffset = 0.0F;

        if (HOVER_PROFILE.isGroundLowerThan(15))
            yOffset = -32.0F;
        else if (!HOVER_PROFILE.isGroundLowerThan(8))
            yOffset = 6.0F;
        else if (!HOVER_PROFILE.isCeilingHigher(5))
            yOffset = -6.0F;

        yOffset *= r.nextFloat(0.5F, 1.0F);

        return sample(10, stack()
                        .push(randomOffset(4, 4, 4))
                        .push(offset(0, (int)yOffset, 0)),
                evaluatePos((Squig) mob)
        ).compute(pos, mob, r, extra);
    };
    private static MobPosProcessor<LivingEntity> COMBAT_POS = (pos, mob, r, extra) -> {
        HOVER_PROFILE.update(mob);

        int yOffset = 0;

        if (!HOVER_PROFILE.isGroundLowerThan(5))
            yOffset = 3;
        else if (!HOVER_PROFILE.isCeilingHigher(5))
            yOffset = -3;

        yOffset *= r.nextFloat();

        return sample(10, stack()
                        .push(randomOffset(4, 4, 4))
                        .push(offset(0, yOffset, 0)),
                evaluateCombatPos(mob)
        ).compute(pos, mob, r, extra);
    };
    private final MarkedTimer unseenTimer;
    private ImpulseAction impulseAction;

    public SquigPsyche(Squig mob) {
        super(mob);
        this.unseenTimer = new MarkedTimer(IntScheduler.simple(100));
        allocateModules(4);
    }

    @Override
    protected void populateInputs(UnbuiltActionInputs unbuiltActionInputs) {
        unbuiltActionInputs.put(ActionInputKey.MARKED_UNSEEN, () -> unseenTimer);
    }

    @Override
    protected void initialize() {
        this.impulseAction = new ImpulseAction(getMob());

        ConstantThoughtAction<Squig> global = new ConstantThoughtAction<>(getMob(), mob -> {
            rotateToTarget(mob);
            mob.setNoGravity(true);
            mob.fallDistance = 0;

            tryChangeTarget(mob, IDLE_POS);

            tryDoImpulse(mob);
        });

        tryAddRunningAction(CHECKS_MODULE, global);
        tryAddRunningAction(CHECKS_MODULE, new TargetBullyAction(getMob(), Shared.DEFAULT_TARGET_PARAMETERS));
    }

    protected void tryDoImpulse(Squig mob) {
        IntScheduler delay = mob.getImpulseDelay();

        if (delay.hasEnded()) {
            delay.reset();

            Vec3 impulseVector = Vec3.directionFromRotation(mob.getXRot(), mob.getYHeadRot())
                    .scale(4.0);
            impulseVector = impulseVector.add(mob.position());

            if (mob.getRandom().nextFloat() < 0.1F || DERayUtil.noCollision(mob.level, mob.position(), impulseVector)) {
                tryAddRunningAction(IMPULSE_MODULE, impulseAction);
            }
        }

        delay.incrementFreely(1);
    }

    protected static void tryChangeTarget(Squig mob, MobPosProcessor<Object> finder) {
        IntScheduler wait = mob.getChangeTargetThreshold();

        wait.incrementFreely(1);

        if (canChangeTarget(mob) || wait.hasEnded()) {
            if (changeTarget(mob))
                wait.reset();
        }
    }

    protected static boolean changeTarget(Squig mob) {
        BlockPos pos;

        if (CombatHelper.isValidAttackTarget(mob.getTarget())) {
            pos = COMBAT_POS.compute(mob.blockPosition(), mob, mob.getRandom(), mob.getTarget());
        } else {
            pos = IDLE_POS.compute(mob.blockPosition(), mob, mob.getRandom(), null);
        }

        if (pos == null) return false;

        Vec2 newLook = Shared.rotationsToTarget(mob.position(), Vec3.atCenterOf(pos));

        mob.setTargetLook(newLook.x, newLook.y);

        return true;
    }

    protected static void rotateToTarget(Squig mob) {
        if (!(mob.getLookControl() instanceof MGLookController look)) return;
        look.setTargetPitch(mob.getXTarget(), 3.0F);
        look.setTargetYaw(mob.getYTarget(), 3.0F);
    }

    protected static boolean canChangeTarget(Squig mob) {
        float xDiff = Math.abs(mob.getXTarget() - mob.getXRot());
        float yDiff = Math.abs(mob.getYTarget() - mob.getYHeadRot());

        return xDiff < 5.0F && yDiff < 5.0F;
    }

    private static Function<Object, Comparator<BlockPos>> evaluatePos(Squig mob) {
        return ignored -> Comparator.comparingDouble(pos -> {
            if (pos == null) return Float.MAX_VALUE;

            Vec3 asVector = Vec3.atCenterOf(pos);
            double value = 0.0;

            if (DERayUtil.noCollision(mob.level, mob.getEyePosition(), asVector))
                value += 256.0;

            value -= Vec3.atCenterOf(mob.getHome()).distanceTo(asVector);

            return value;
        });
    }

    private static Function<LivingEntity, Comparator<BlockPos>> evaluateCombatPos(Entity mob) {
        return target -> Comparator.comparingDouble(pos -> {
            if (pos == null) return Float.MIN_VALUE;

            Vec3 asVector = Vec3.atCenterOf(pos);
            double value = 0.0;

            if (DERayUtil.noCollision(mob.level, mob.getEyePosition(), asVector))
                value += 256.0;

            value -= target.position().distanceToSqr(asVector) / 200;

            return value;
        });
    }

    protected static final class ImpulseAction extends AnimationAction<Squig, SimpleAnimation> {

        public ImpulseAction(Squig mob) {
            super(mob, Squig.IMPULSE, mob.mainHandler);
        }

        @Override
        protected void update() {
            super.update();
            int tick = mob.mainHandler.getTick();

            if (tick == 7) {
                Vec3 pos = mob.position();
                Vec3 impulseVector = Vec3.directionFromRotation(mob.getXRot(), mob.getYHeadRot())
                        .scale(0.45);

                mob.getImpulseMotion().addMotion(impulseVector);
                mob.tellClientToDoImpulse();

                if (CombatHelper.isValidAttackTarget(mob.getTarget())) {
                    Vec3 move = Vec3.directionFromRotation(mob.getXRot() + 20.0F * (float) RANDOM.nextGaussian(), mob.getYHeadRot() + 20.0F * (float) RANDOM.nextGaussian())
                            .scale(-1.0);
                    Vec3 mot = move.scale(0.45 + 0.25 * RANDOM.nextGaussian());

					SquigBubbleEntity bubble = new SquigBubbleEntity(DEEntities.SQUIG_BUBBLE.get(), mob.level);
					bubble.setCaster(mob);
					bubble.setBobbleDamage(1.5F);
					bubble.setTarget(mob.getTarget());
					bubble.absMoveTo(pos.x + move.x, pos.y + 0.31 + move.y, pos.z + move.z, 0.0F, 0.0F);
					bubble.hurtMotion.addMotion(mot.x, mot.y, mot.z);
                    mob.level.addFreshEntity(bubble);
                }
            }
        }
    }
}
