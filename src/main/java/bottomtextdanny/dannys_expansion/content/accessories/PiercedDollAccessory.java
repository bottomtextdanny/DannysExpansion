package bottomtextdanny.dannys_expansion.content.accessories;

import bottomtextdanny.braincell.mod.capability.player.accessory.AccessoryKey;
import bottomtextdanny.braincell.mod.capability.player.accessory.extensions.FinnHit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.mutable.MutableObject;

public class PiercedDollAccessory extends CoreAccessory implements FinnHit {

    public PiercedDollAccessory(AccessoryKey<?> key, Player player) {
        super(key, player);
    }

    @Override
    public void onMeleeCritical(LivingEntity entity, MutableObject<Float> amount, float baseValue) {
        amount.setValue(amount.getValue() + 2);
    }

    @Override
    public int criticalModificationPriority() {
        return C_ADD_OP;
    }

    @Override
    public int hitModificationPriority() {
        return H_LAST_MULT_OP;
    }

    @Override
    public String getGeneratedDescription() {
        return "Increases melee critical damage by 2 points.";
    }
}
