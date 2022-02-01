package net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.mundane_slime;

import net.bottomtextdanny.braincell.mod.entity.modules.variable.Form;
import net.bottomtextdanny.danny_expannny.objects.items.GelItem;

public abstract class MundaneSlimeForm extends Form<MundaneSlime> {

    public abstract GelItem.Model gelModel(MundaneSlime entity);
}
