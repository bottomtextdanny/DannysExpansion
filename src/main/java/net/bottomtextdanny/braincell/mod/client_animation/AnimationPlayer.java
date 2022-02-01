package net.bottomtextdanny.braincell.mod.client_animation;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AnimationPlayer<E extends Entity> {

    public AnimationPlayer() {
        super();
    }

    public abstract void play(E entity, float progress);
}
