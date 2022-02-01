package net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.ai;

import net.bottomtextdanny.braincell.mod.entity.psyche.actions.Action;
import net.bottomtextdanny.braincell.underlying.util.BCMath;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.AbstractSlime;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.data.SlimeHopAnimationInput;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.data.SlimeHopMovementInput;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.data.SlimeHopSoundInput;

public class SlimeHopAction extends Action<AbstractSlime> {
    private int tillHopCounter;
    private boolean wasJumping;

    public SlimeHopAction(AbstractSlime mob) {
        super(mob);
    }

    @Override
    protected void update() {
        super.update();
        final boolean canJump = this.mob.onGround() || this.mob.InFluid();
        final SlimeHopMovementInput movementData = this.mob.hopInput();
        final SlimeHopSoundInput soundData = this.mob.hopSoundsInput();
        final SlimeHopAnimationInput animationData = this.mob.hopAnimationInput();

        if (canJump && --this.tillHopCounter < 0) {
            this.tillHopCounter = movementData.hopDelay().map(UNSAFE_RANDOM);
            this.mob.mainHandler.play(animationData.animation());
        }

        if (this.mob.mainHandler.getTick() >= animationData.hopLiftTick() && this.mob.mainHandler.getTick() < animationData.hopLiftTick() + animationData.hopTicks()) {
            float sine = BCMath.sin(this.mob.getYRot() * BCMath.FRAD);
            float cosine = BCMath.cos(this.mob.getYRot() * BCMath.FRAD);

            this.mob.push(movementData.speed() * -sine, 0, movementData.speed() * cosine);

            if (this.mob.mainHandler.getTick() == animationData.hopLiftTick()) {

                this.mob.push(0, movementData.height(), 0);
                this.mob.playSound(soundData.hop(), 0.3F + UNSAFE_RANDOM.nextFloat() * 0.4F, soundData.pitch().map(UNSAFE_RANDOM));
            }

            if (this.mob.mainHandler.getTick() == 5) {
                this.wasJumping = true;
            }
        }

        if (this.wasJumping) {
            if (canJump) {
                this.mob.playSound(soundData.fall(), 0.3F + UNSAFE_RANDOM.nextFloat() * 0.4F, soundData.pitch().map(UNSAFE_RANDOM));
                this.wasJumping = false;
            }
        }
    }

    @Override
    public boolean shouldKeepGoing() {
        return true;
    }
}
