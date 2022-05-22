package bottomtextdanny.dannys_expansion.content.entities.mob.slimes.mundane_slime;

import bottomtextdanny.dannys_expansion.content.items.material.GelItem;
import bottomtextdanny.braincell.mod.entity.modules.variable.Form;

public abstract class MundaneSlimeForm extends Form<MundaneSlime> {

    public abstract GelItem.Model gelModel(MundaneSlime entity);
}
