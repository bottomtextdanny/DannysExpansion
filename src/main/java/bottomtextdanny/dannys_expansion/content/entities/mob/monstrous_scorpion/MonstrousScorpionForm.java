package bottomtextdanny.dannys_expansion.content.entities.mob.monstrous_scorpion;

import bottomtextdanny.dannys_expansion.content.items.material.ScorpionGlandItem;
import bottomtextdanny.braincell.mod.entity.modules.variable.Form;

public abstract class MonstrousScorpionForm extends Form<MonstrousScorpion> {

    public MonstrousScorpionForm() {
        super();
    }

    public abstract ScorpionGlandItem.Model gland();
}
