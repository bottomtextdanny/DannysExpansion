package net.bottomtextdanny.danny_expannny.objects.items;

import net.bottomtextdanny.danny_expannny.object_tables.DEEntities;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.SporeBombEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.ItemUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class SporeBombItem extends Item {
    public SporeBombItem(Properties properties) {
        super(properties);
        properties.stacksTo(16);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);

            SporeBombEntity sporeBombEntity = new SporeBombEntity(DEEntities.SPORE_BOMB.get(), worldIn);
            sporeBombEntity.setCaster(playerIn);
            sporeBombEntity.absMoveTo(playerIn.getX(), playerIn.getEyeY(), playerIn.getZ(), playerIn.yHeadRot, Mth.clamp(playerIn.getXRot(), -85, 90));
            Vec3 vec = DEMath.fromPitchYaw(Mth.clamp(playerIn.getXRot(), -85, 90), playerIn.yHeadRot);
            sporeBombEntity.push(vec.x * 2, vec.y * 2, vec.z * 2);
            worldIn.addFreshEntity(sporeBombEntity);


        playerIn.playSound(SoundEvents.EXPERIENCE_BOTTLE_THROW, 1.0F, 0.4F + new Random().nextFloat() * 0.1F);

        playerIn.awardStat(Stats.ITEM_USED.get(this));

        if (!playerIn.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        playerIn.getCooldowns().addCooldown(this, 20);
        return super.use(worldIn, playerIn, handIn);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        ItemUtil.createDescription(this, tooltip);
    }
}
