package net.bottomtextdanny.braincell.mod.entity.modules.animatable;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class LivingAnimatableModule extends BaseAnimatableModule<LivingAnimatableProvider> {
    private final LivingEntity living;
    private final AnimationHandler<?> localHandler;
    private boolean deathHasBegun;

    public LivingAnimatableModule(LivingEntity entity) {
        super(entity);
        this.localHandler = new AnimationHandler<>(entity);
        this.localHandler.setIndex(0);
        animationHandlerList().add(this.localHandler);
        this.living = entity;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.living.isEffectiveAi() && this.living.getHealth() <= 0.0F) {
            if (!this.deathHasBegun) {
                if (this.provider.getDeathAnimation() != null) {
                    this.localHandler.play(this.provider.getDeathAnimation());

                }
                this.deathHasBegun = true;
                this.provider.onDeathAnimationStart();
            }
        }
    }

    public AnimationHandler<?> getLocalHandler() {
        return this.localHandler;
    }

    public void tickDeathHook() {
        ++this.living.deathTime;
        int duration = this.provider.getDeathAnimation() == null ? 20 : this.provider.getDeathAnimation().getDuration();

        if (this.living.deathTime >= duration) {
            this.provider.onDeathAnimationEnd();
            this.living.remove(Entity.RemovalReason.DISCARDED);
        }
    }

    public boolean deathHasBegun() {
        return this.deathHasBegun;
    }
}
