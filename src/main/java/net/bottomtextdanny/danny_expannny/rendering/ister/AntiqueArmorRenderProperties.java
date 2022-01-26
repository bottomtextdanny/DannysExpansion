package net.bottomtextdanny.danny_expannny.rendering.ister;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemRenderProperties;

public class AntiqueArmorRenderProperties implements IItemRenderProperties {

    @Override
    public <A extends HumanoidModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, A _default) {
//        if (DannyItems.ANTIQUE_ARMOR_HELMET.equals(itemStack.getItem())) {
//            return (A) new AntiqueArmorHelmetModel<LivingEntity>();
//        } else if (DannyItems.ANTIQUE_ARMOR_CHESTPLATE.equals(itemStack.getItem())) {
//            return (A) new AntiqueArmorChestplateModel<LivingEntity>();
//        } else if (DannyItems.ANTIQUE_ARMOR_LEGGINGS.equals(itemStack.getItem())) {
//            return (A) new AntiqueArmorLeggingsModel<LivingEntity>();
//        } else {
//            return (A) new AntiqueArmorBootsModel<LivingEntity>();
//        }
        return null;
    }
}
