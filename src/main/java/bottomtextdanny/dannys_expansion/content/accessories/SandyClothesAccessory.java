package bottomtextdanny.dannys_expansion.content.accessories;

import bottomtextdanny.braincell.base.pair.Pair;
import bottomtextdanny.braincell.mod.capability.player.accessory.AccessoryKey;
import bottomtextdanny.braincell.mod.capability.player.accessory.MiniAttribute;
import bottomtextdanny.braincell.mod.capability.player.accessory.ModifierType;
import bottomtextdanny.braincell.mod.capability.player.accessory.extensions.FinnHurt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.List;

public class SandyClothesAccessory extends CoreAccessory implements FinnHurt {

    public SandyClothesAccessory(AccessoryKey<?> key, Player player) {
        super(key, player);
    }

    @Override
    public void populateModifierData(List<Pair<ModifierType, Double>> modifierList, List<Pair<MiniAttribute, Float>> lesserModifierList) {
        modifierList.add(Pair.of(ModifierType.ARMOR_ADD, 1.0D));
    }

    @Override
    public void onHurt(DamageSource source, MutableObject<Float> amount) {
        if (source.getEntity() instanceof Mob mob && mob.getMobType() == MobType.UNDEAD) {
            amount.setValue(Math.max(amount.getValue() - Math.min(amount.getValue() * 0.2F, 2.0F), 0));
        }
    }

    @Override
    public String getGeneratedDescription() {
        return "Reduces damage from undead mobs.";
    }
}
