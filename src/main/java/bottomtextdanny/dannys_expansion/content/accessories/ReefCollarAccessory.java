package bottomtextdanny.dannys_expansion.content.accessories;

import bottomtextdanny.dannys_expansion.tables.DEMiniAttributes;
import bottomtextdanny.braincell.mod.capability.player.accessory.AccessoryKey;
import bottomtextdanny.braincell.mod.capability.player.accessory.MiniAttribute;
import bottomtextdanny.braincell.mod.capability.player.accessory.ModifierType;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class ReefCollarAccessory extends CoreAccessory {

    public ReefCollarAccessory(AccessoryKey<?> key, Player player) {
        super(key, player);
    }

    @Override
    public double getFinalArmorAddition() {
        return super.getFinalArmorAddition() + (this.player.isUnderWater() ? 2.0F : 0);
    }

    @Override
    public double getFinalAttackDamageAddition() {
        return super.getFinalAttackDamageAddition() + (this.player.isUnderWater() ? 1.5F : 0.0F);
    }

    @Override
    public float getLesserModifierBase(MiniAttribute modifierType) {
        if (this.player.isUnderWater() && (modifierType == MiniAttribute.ARCHERY_DAMAGE_ADD || modifierType == DEMiniAttributes.GUN_DAMAGE_ADD)) {
            return 2.2F;
        }
        return super.getLesserModifierBase(modifierType);
    }

    @Override
    public List<ModifierType> modifiedAttributes() {
        return List.of(ModifierType.ARMOR_ADD, ModifierType.ATTACK_DAMAGE_ADD);
    }

    @Override
    public String getGeneratedDescription() {
        return "Increases defense and attack damage when in water.";
    }
}
