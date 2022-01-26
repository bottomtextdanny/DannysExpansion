package net.bottomtextdanny.danny_expannny.objects.items.armor;

import net.bottomtextdanny.danny_expannny.object_tables.DEEntities;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.MummySoulEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.ItemUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class MummyChestplateItem extends ArmorItem {
    int mummySoulTimer;

    public MummyChestplateItem(ArmorMaterial materialIn, Properties p_i48534_3_) {
        super(materialIn, EquipmentSlot.CHEST, p_i48534_3_);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        ItemUtil.createDescription(this, tooltip, false);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {
        if (this.mummySoulTimer > 120) {
            List<Mob> list = world.getEntitiesOfClass(Mob.class, player.getBoundingBox().inflate(0.7));
            if (!list.isEmpty()) {
                Mob mobEntity = list.get(0);
                if (mobEntity.getTarget() == player) {
                    MummySoulEntity mummySoulEntity = new MummySoulEntity(DEEntities.MUMMY_SOUL.get(), world);
                    float yaw = DEMath.getTargetYaw(player, mobEntity);

                    float f0 = DEMath.sin(yaw * ((float) Math.PI / 180F));
                    float f1 = DEMath.cos(yaw * ((float) Math.PI / 180F));
                    float f2 = DEMath.sin((Mth.wrapDegrees(yaw) + -90) * ((float) Math.PI / 180F));
                    float f3 = DEMath.cos((Mth.wrapDegrees(yaw) + -90) * ((float) Math.PI / 180F));

                    mummySoulEntity.absMoveTo(player.getX() - 0.9 * -f0 - 0.5 * -f2, player.getY() + 1, player.getZ() - 0.9 * f1 - 0.5 * f3, yaw, 0);
                    mummySoulEntity.setCaster(player);
                    world.addFreshEntity(mummySoulEntity);
                    this.mummySoulTimer = 0;
                }
            }
        } else {
            this.mummySoulTimer++;
        }
    }
}
