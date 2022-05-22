package bottomtextdanny.dannys_expansion.content.entities;

import bottomtextdanny.dannys_expansion._base.capabilities.world.LevelCapability;
import bottomtextdanny.dannys_expansion._base.capabilities.world.LevelPhaseModule;
import bottomtextdanny.dannys_expansion.tables.DEEntities;
import bottomtextdanny.braincell.base.BCMath;
import bottomtextdanny.braincell.mod.capability.CapabilityHelper;
import bottomtextdanny.braincell.mod.entity.psyche.pos_finder.MobPosComparators;
import bottomtextdanny.braincell.mod.entity.psyche.pos_finder.MobPosPredicates;
import bottomtextdanny.braincell.mod.entity.psyche.pos_finder.MobPosProcessor;
import bottomtextdanny.braincell.mod.entity.psyche.targeting.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.phys.Vec2;

import java.util.Comparator;
import java.util.function.Function;

import static bottomtextdanny.braincell.mod.entity.psyche.pos_finder.MobPosProcessors.*;

public class Shared {
    public static final TargetPredicate DEFAULT_TARGET_PARAMETERS = Targeter.Builder
            .start(TargetRange.followRange())
            .isForCombat()
            .targetRangeForInvisible(TargetRange.fixedRange(2.0D))
            .build();
    public static final TargetPredicate GOODIE = (mob, le) -> {
        if (CapabilityHelper.get(mob.level, LevelCapability.TOKEN).getPhaseModule().getPhase().ordinal()
                >= LevelPhaseModule.Phase.DRAGON.ordinal()) {
            return le.getType().builtInRegistryHolder().is(DEEntities.POST_DRAGON_GOODIES);
        } else {
            return le.getType().builtInRegistryHolder().is(DEEntities.GOODIES);
        }
    };
    public static final MobPosProcessor<Object> LAND_STROLL = compose(
            (pos, mob, r, extra) -> stack()
                    .push(sample(10, stack()
                                    .push(randomOffset(7, 4, 7))
                                    .push(moveIf(Direction.DOWN, 16, MobPosPredicates.isSolid().negate()))
                                    .push(offset(0, 1, 0)),
                    MobPosComparators.compareWalkValue(mob)
    )));

    public static SearchPredicate<LivingEntity> searchTargetSpecifically() {
        return (mob, serverLevel, rangeTest, lazy, targetPredicate) -> {
            LivingEntity target = mob.getTarget();
            if (target == null || !target.isAlive()
                    || !targetPredicate.test(mob, target)) return null;
            return target;
        };
    }

    public static Function<LivingEntity, Comparator<BlockPos>> evaluateAvoidingPos(Mob mob, float walkValueMult) {
        return (target) -> mob instanceof PathfinderMob pf ? Comparator.comparingDouble(pos -> {
            double value = Math.min(walkValueMult, pf.getWalkTargetValue(pos) * walkValueMult);
            value -= mob.position().distanceTo(target.position()) / 8;

            return value;
        }) : null;
    }

    public static Function<LivingEntity, Comparator<BlockPos>> evaluateApproachingPos(Mob mob, float walkValueMult) {
        return (target) -> mob instanceof PathfinderMob pf ? Comparator.comparingDouble(pos -> {
            double value = Math.min(walkValueMult, pf.getWalkTargetValue(pos) * walkValueMult);
            value += mob.position().distanceTo(target.position()) / 8;

            return value;
        }) : null;
    }

    public static Vec2 rotationsToTarget(double x1, double y1, double z1,
                                         double x2, double y2, double z2) {
        double diffX = x2 - x1;
        double diffY = y2 - y1;
        double diffZ = z2 - z1;
        double horizontalAbsoluteDiff = Mth.sqrt((float) (diffX * diffX + diffZ * diffZ));
        return new Vec2((float)(-Mth.atan2(diffY, horizontalAbsoluteDiff) * BCMath.FRAD2DEG), (float)Mth.atan2(diffZ, diffX) * BCMath.FRAD2DEG);
    }

    public static Vec2 rotationsToTarget(Position vec1, Position vec2) {
        return rotationsToTarget(vec1.x(), vec1.y(), vec1.z(), vec2.x(), vec2.y(), vec2.z());
    }
}
