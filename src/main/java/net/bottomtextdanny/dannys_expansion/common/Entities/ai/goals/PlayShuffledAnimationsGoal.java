package net.bottomtextdanny.dannys_expansion.common.Entities.ai.goals;

import net.bottomtextdanny.braincell.mod.entity.modules.animatable.IAnimation;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.NullAnimation;
import net.bottomtextdanny.braincell.mod.world.builtin_entities.ModuledMob;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class PlayShuffledAnimationsGoal extends PlayAnimationGoal {
    private final List<IAnimation> animations;

    public PlayShuffledAnimationsGoal(ModuledMob entity, List<IAnimation> animations, Predicate<ModuledMob> shoulExec, Consumer<ModuledMob> executionConsumer) {
        super(entity, NullAnimation.UNI, shoulExec, executionConsumer);
        this.animations = animations;
    }

    public PlayShuffledAnimationsGoal(ModuledMob entity, List<IAnimation> animations, Predicate<ModuledMob> shoulExec) {
        super(entity, NullAnimation.UNI, shoulExec);
        this.animations = animations;
    }

    @Override
    public void start() {
        this.animation = this.animations.get(DEUtil.GOAL_RANDOM.nextInt(this.animations.size()));
        super.start();
    }
}
