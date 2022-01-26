package net.bottomtextdanny.danny_expannny.objects.entities.mob.squig;

import net.bottomtextdanny.braincell.mod.entity.modules.variable.Form;

public abstract class SquigForm extends Form<SquigEntity> {

    public SquigForm() {
        super();
    }

    public abstract int getCrossSprite();

    public abstract int getRingSprite();
}
