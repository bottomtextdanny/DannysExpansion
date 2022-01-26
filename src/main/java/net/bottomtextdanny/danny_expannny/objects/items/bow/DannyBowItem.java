package net.bottomtextdanny.danny_expannny.objects.items.bow;

import net.bottomtextdanny.danny_expannny.rendering.DERenderProperties;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.ItemUtil;
import net.bottomtextdanny.danny_expannny.capabilities.item.ProvideCapability;
import net.bottomtextdanny.dannys_expansion.core.interfaces.IDannyHoldable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.IItemRenderProperties;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class DannyBowItem extends ProjectileWeaponItem implements IDannyHoldable, ProvideCapability {
    public Random random = new Random();
    private final boolean big;

    public DannyBowItem(boolean big, Properties properties) {
        super(properties);
        this.big = big;
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(this.big ? DERenderProperties.BIG_BOW : DERenderProperties.BOW);
    }


    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
	    if (entityIn instanceof Player) {
		    ItemUtil.ifDannyCap(stack, capability -> {
			    capability.setHolder((Player) entityIn);
		    });
	    }
	    
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
    }

    public int getFovModifier() {
        return 10;
    }

    @Override
    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
        super.releaseUsing(stack, worldIn, entityLiving, timeLeft);
        if (entityLiving instanceof Player) {
            Player player = (Player)entityLiving;
            shoot(worldIn, player, stack, 1 - (float) Math.min(timeLeft - getUseDuration(stack) + getTrueMaxCount(stack), getTrueMaxCount(stack)) / getTrueMaxCount(stack));
        }

    }

    public void shoot(Level world, Player player, ItemStack stack, float progress) {
    }

    @Override
    public boolean automatic() {
        return false;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }

    public static ItemStack tryToGetArrow(Player player) {
        if (player.getOffhandItem().getItem() instanceof ArrowItem) return player.getOffhandItem();
        for (int i = 0; i < player.getInventory().getContainerSize(); ++i) {
            ItemStack itemstack = player.getInventory().getItem(i);

            if (itemstack.getItem() instanceof ArrowItem) {
                return itemstack;
                }
            }
        return null;
    }


    public Class<ArrowItem> getArrowType() {
        return ArrowItem.class;
    }

    public static float getArrowVelocity(float charge) {
        float f = charge;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    public AbstractArrow customArrow(AbstractArrow arrow) {
        return arrow;
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        if (entity instanceof  Player) return entity.getUseItemRemainingTicks() > 0;
        return false;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    /**
     * returns the action that specifies what entity to play when the items is being used
     */

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        boolean flag = !playerIn.getProjectile(itemstack).isEmpty();

        InteractionResultHolder<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, worldIn, playerIn, handIn, flag);
        if (ret != null) return ret;

        if (!playerIn.getAbilities().instabuild && !flag) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            playerIn.startUsingItem(handIn);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    /**
     * Get the predicate to match ammunition when searching the player's inventory, not their main/offhand
     */
    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return ARROW_ONLY;
    }

    @Override
    public int getTrueMaxCount(ItemStack stack) {
        return 15;
    }

    public float getFloatProgression(LivingEntity entity, ItemStack stack) {
        return Math.min((float) entity.getTicksUsingItem() / getTrueMaxCount(stack), getTrueMaxCount(stack));
    }

    public float getClampCount(LivingEntity entity, ItemStack stack) {
        return Math.min((float) entity.getTicksUsingItem(), getTrueMaxCount(stack));
    }

    @Override
    public float holdMovementSpeed() {
        return 0.2F;
    }
}
