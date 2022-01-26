package net.bottomtextdanny.danny_expannny.objects.items.bow;

import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.core.interfaces.item.IBowModelLoader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class FrozenBowItem extends DannyBowItem implements IBowModelLoader {

    public FrozenBowItem(Properties properties) {
        super(false, properties);
    }

    @Override
    public void shoot(Level world, Player player, ItemStack stack, float progress) {
        super.shoot(world, player, stack, progress);
        boolean flag = player.isCreative();
        float f = getArrowVelocity(progress);
        ArrowItem arrow;

        if (f < 0.3F) return;

        ItemStack arrowStack = tryToGetArrow(player);
		
        if (arrowStack == null) {
            arrowStack = new ItemStack(Items.ARROW);
        }

        arrow = (ArrowItem) arrowStack.getItem();

        AbstractArrow abstractarrowentity = arrow.createArrow(world, arrowStack, player);
        abstractarrowentity.setPos(player.getX(), player.getEyeY(), player.getZ());
        abstractarrowentity = customArrow(abstractarrowentity);
        abstractarrowentity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, f * 3.0F, 1.0F);

        if (progress == 0.35F) {
            abstractarrowentity.setCritArrow(true);
        }

        int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
        if (j > 0) {
            abstractarrowentity.setBaseDamage(abstractarrowentity.getBaseDamage() + (double)j * 0.15D + 0.25D);
        }

        int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack);
        if (k > 0) {
            abstractarrowentity.setKnockback(k);
        }
        

        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, stack) > 0) {
            abstractarrowentity.setSecondsOnFire(100);
        }

        if (player.getAbilities().instabuild) {
            abstractarrowentity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
        }

        world.addFreshEntity(abstractarrowentity);
        if (player.isEffectiveAi()) player.playSound(DESounds.IS_FROZEN_BOW_SHOT.get(), 1.0F, 1.0F + this.random.nextInt(3) * 0.1F);
        if (!player.level.isClientSide() && !flag) arrowStack.shrink(1);
        stack.hurtAndBreak(1, player, p_220009_1_ -> {
            p_220009_1_.broadcastBreakEvent(player.getUsedItemHand());
        });
    }
    

    @Override
    public float holdMovementSpeed() {
	    
        return 0.6F;
    }

    @Override
    public boolean automatic() {
        return false;
    }
}
