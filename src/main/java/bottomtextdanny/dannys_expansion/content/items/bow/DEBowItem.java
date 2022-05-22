package bottomtextdanny.dannys_expansion.content.items.bow;

import bottomtextdanny.dannys_expansion._util.tooltip.*;
import bottomtextdanny.dannys_expansion.tables.DESounds;
import bottomtextdanny.dannys_expansion.tables._client.DERenderProperties;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.IItemRenderProperties;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class DEBowItem extends ProjectileWeaponItem {
    protected Random random;
    private final boolean big;
    private WeakReference<TooltipWriter> tooltipInfo;
    protected float speedFactor;
    protected float damageFactor;
    protected int nockTime;
    protected int nockZoom;
    protected float nockMovementSpeed;

    public DEBowItem(boolean big, Properties properties) {
        super(properties);
        damageFactor = 1.0F;
        speedFactor = 1.0F;
        nockTime = 15;
        nockZoom = 10;
        nockMovementSpeed = 0.2F;
        random = new Random();
        this.big = big;
        init();
        tooltipInfo = new WeakReference<>(null);
    }

    public void init() {}

    @Nonnull
    public TooltipWriter createTooltipInfo() {
        return TooltipTable.builder()
                .block(TooltipWriter.component(new TextComponent("")))
                .block(TooltipBlock.builder()
                        .header(TooltipWriter.trans("description.dannys_expansion.statistics",
                                StringSuppliers.translatable("description.dannys_expansion.bow"),
                                Style.EMPTY.applyFormats(ChatFormatting.GRAY),
                                Style.EMPTY.applyFormats(ChatFormatting.GRAY)))
                        .add(TooltipData.ARROW_DAMAGE_FACTOR.message(StringSuppliers.float_(damageFactor))
                                .withStyle(ChatFormatting.DARK_GREEN))
                        .add(TooltipData.ARROW_SPEED_FACTOR.message(StringSuppliers.float_(speedFactor))
                                .withStyle(ChatFormatting.DARK_GREEN))
                        .add(TooltipData.NOCK_TIME.message(StringSuppliers.ticksToSeconds(nockTime))
                                .withStyle(ChatFormatting.DARK_GREEN))
                        .add(TooltipBlock.builder()
                                .condition(TooltipCondition.HOLD_SHIFT)
                                .add(TooltipData.NOCK_MOVEMENT_SPEED.message(StringSuppliers.ticksToSeconds(nockMovementSpeed))
                                        .withStyle(ChatFormatting.DARK_GRAY))
                                .add(TooltipData.NOCK_ZOOM.message(StringSuppliers.int_(nockZoom))
                                        .withStyle(ChatFormatting.DARK_GRAY))
                                .build())
                        .build())
                .build();
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(this.big ? DERenderProperties.BIG_BOW : DERenderProperties.BOW);
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
    }

    @Override
    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
        super.releaseUsing(stack, worldIn, entityLiving, timeLeft);
        if (entityLiving instanceof Player) {
            Player player = (Player)entityLiving;
            int trueMaxCount = getTrueMaxCount(stack);
            shoot(worldIn, player, stack, (float) Math.min(getUseDuration(stack) - timeLeft, trueMaxCount) / trueMaxCount, timeLeft);
        }
    }

    public void shoot(Level world, Player player, ItemStack stack, float progress, int timeLeft) {
        boolean doesntNeedArrow = player.getAbilities().instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0;

        float f = getArrowVelocity(progress);
        ArrowItem arrow;

        if (f < 0.3F) return;

        ItemStack arrowStack = tryToGetArrow(player);

        if (doesntNeedArrow && arrowStack == null) {
            arrowStack = new ItemStack(Items.ARROW);
        }

        if (arrowStack == null) return;

        arrow = (ArrowItem) arrowStack.getItem();

        AbstractArrow abstractarrowentity = arrow.createArrow(world, arrowStack, player);
        abstractarrowentity.setPos(player.getX(), player.getEyeY(), player.getZ());
        abstractarrowentity = customArrow(abstractarrowentity);
        abstractarrowentity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, f * 3.0F * getSpeedFactor(world, player, stack), 1.0F);

        if (progress == 1.0F) {
            abstractarrowentity.setCritArrow(true);
        }

        int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
        if (j > 0) {
            abstractarrowentity.setBaseDamage(abstractarrowentity.getBaseDamage() + ((double)j * 0.5D + 0.5D) * getDamageMultiplier(world, player, stack));
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

        if (player.isEffectiveAi())
            player.playSound(getShootSound(world, player, stack), 1.0F, 1.0F + this.random.nextInt(3) * 0.1F);

        if (!player.level.isClientSide() && !doesntNeedArrow)
            arrowStack.shrink(1);

        stack.hurtAndBreak(1, player, p_220009_1_ -> {
            p_220009_1_.broadcastBreakEvent(player.getUsedItemHand());
        });
    }

    public float getSpeedFactor(Level world, Player player, ItemStack stack) {
        return speedFactor;
    }

    public float getDamageMultiplier(Level world, Player player, ItemStack stack) {
        return damageFactor;
    }

    public SoundEvent getShootSound(Level world, Player player, ItemStack stack) {
        return DESounds.IS_FROZEN_BOW_SHOT.get();
    }

    public int getNockZoom(Level world, Player player, ItemStack stack) {
        return nockZoom;
    }

    public int getNockTime(ItemStack stack) {
        return nockTime;
    }

    public float getNockMovementSpeed(Level world, Player player, ItemStack stack) {
        return nockMovementSpeed;
    }

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

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip,
                                TooltipFlag flag) {
        super.appendHoverText(stack, worldIn, tooltip, flag);
        TooltipWriter writerCache = tooltipInfo.get();

        if (writerCache != null)
            writerCache.write(0, stack, worldIn, tooltip, flag);
        else {
            TooltipWriter writer = createTooltipInfo();
            this.tooltipInfo = new WeakReference<>(writer);
            writer.write(0, stack, worldIn, tooltip, flag);
        }
    }

    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return ARROW_ONLY;
    }

    public int getTrueMaxCount(ItemStack stack) {
        return (int)getNockTime(stack);
    }

    public float getFloatProgression(LivingEntity entity, ItemStack stack) {
        return Math.min((float) entity.getTicksUsingItem() / getTrueMaxCount(stack), getTrueMaxCount(stack));
    }

    public float getClampCount(LivingEntity entity, ItemStack stack) {
        return Math.min((float) entity.getTicksUsingItem(), getTrueMaxCount(stack));
    }
}
