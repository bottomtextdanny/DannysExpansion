package net.bottomtextdanny.danny_expannny.objects.items.arrow;

import net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.DispenserBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;

public class DannyArrowItem extends ArrowItem {
    public static SplittableRandom random = DEUtil.S_RANDOM;
    protected static final List<DannyArrowItem> DANNY_ARROWS = new ArrayList<>();

    public DannyArrowItem(Properties builder) {
        super(builder);
    }

    public static void dispenserBehavior() {
        DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior() {
            public ItemStack execute(BlockSource source, ItemStack stack) {
                Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
                EntityType<?> entitytype = ((SpawnEggItem) stack.getItem()).getType(stack.getTag());
                entitytype.spawn(source.getLevel(), stack, null, source.getPos().relative(direction), MobSpawnType.DISPENSER, direction != Direction.UP, false);
                stack.shrink(1);
                return stack;
            }
        };
        for (final DannyArrowItem arrow : DANNY_ARROWS) {
            DispenserBlock.registerBehavior(arrow, defaultDispenseItemBehavior);
        }
        DANNY_ARROWS.clear();
    }

    public boolean isInfinite(ItemStack stack, ItemStack bow, Player player) {
        int enchant = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, bow);
        return enchant > 0 && random.nextInt(2) == 1;
    }
}
