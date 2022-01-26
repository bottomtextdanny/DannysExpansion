package net.bottomtextdanny.dannys_expansion.common.Entities.ai.goals;

import net.bottomtextdanny.braincell.mod.entity.modules.animatable.IAnimation;
import net.bottomtextdanny.braincell.mod.world.builtin_entities.ModuledMob;
import net.minecraft.world.entity.ai.goal.Goal;

import javax.annotation.Nullable;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class PlayAnimationGoal extends Goal {
    protected final ModuledMob entity;
    protected IAnimation animation;
    @Nullable
    protected final Consumer<ModuledMob> executionConsumer;
    protected final Predicate<ModuledMob> shoulExec;

    public PlayAnimationGoal(ModuledMob entity, IAnimation animation, Predicate<ModuledMob> shoulExec, @Nullable Consumer<ModuledMob> executionConsumer) {
        this.entity = entity;
        this.animation = animation;
        this.shoulExec = shoulExec;
        this.executionConsumer = executionConsumer;
    }

    public PlayAnimationGoal(ModuledMob entity, IAnimation animation, Predicate<ModuledMob> shoulExec) {
	    this.entity = entity;
	    this.animation = animation;
	    this.shoulExec = shoulExec;
	    this.executionConsumer = null;
    }

    @Override
    public void start() {

    	if (this.animation != null) {
            this.entity.mainAnimationHandler.play(this.animation);
		    if (this.executionConsumer != null) {
                this.executionConsumer.accept(this.entity);
            }
	    }
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public boolean canUse() {
        return this.shoulExec.test(this.entity);
    }
}
