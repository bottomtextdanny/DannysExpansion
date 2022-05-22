package bottomtextdanny.dannys_expansion.content.accessories;

import bottomtextdanny.braincell.mod.capability.player.accessory.AccessoryKey;
import bottomtextdanny.braincell.mod.capability.player.accessory.extensions.CriticalStateMutable;
import bottomtextdanny.braincell.mod.capability.player.accessory.extensions.FinnMakeMeleeCritical;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.mutable.MutableFloat;

public class BrieyeAccessory extends CoreAccessory implements FinnMakeMeleeCritical {

    public BrieyeAccessory(AccessoryKey<?> key, Player player) {
        super(key, player);
    }

    @Override
    public void makeMeleeCritical(LivingEntity entity, MutableFloat chance, CriticalStateMutable directState) {
        chance.setValue(chance.floatValue() + 0.2F);
    }

    @Override
    public int critMakingModulePriority() {
        return MAKE_CRITICAL_PRIORITY_HIGH;
    }

    @Override
    public String getGeneratedDescription() {
        return "Increases melee critical chance 10%.";
    }
}
