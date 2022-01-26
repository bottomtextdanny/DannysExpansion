package net.bottomtextdanny.danny_expannny.objects.items;

import net.bottomtextdanny.danny_expannny.object_tables.DEEntities;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.danny_expannny.objects.entities.kite.KiteEntity;
import net.bottomtextdanny.danny_expannny.objects.entities.kite.KiteKnotEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class KiteItem extends BaseKiteItem {
    private final int color;

    public KiteItem(int color, Properties properties) {
        super(properties);
        this.color = color;
    }



    public InteractionResult useOn(UseOnContext context) {

        BlockPos blockpos = context.getClickedPos();
        Direction direction = context.getClickedFace();
        BlockPos blockpos1 = blockpos.relative(direction);
        Player playerentity = context.getPlayer();
        ItemStack itemstack = context.getItemInHand();
        if (playerentity != null && !this.canPlace(playerentity, direction, itemstack, blockpos1)) {
            return InteractionResult.FAIL;
        } else {

            if (KiteKnotEntity.onValidSurface(context.getLevel(), blockpos)) {
                if (!context.getLevel().isClientSide) {
                    KiteKnotEntity knot = new KiteKnotEntity(DEEntities.KITE_KNOT.get(), context.getLevel());
                    knot.setupPositionAndItemstack(blockpos, itemstack);
                    context.getLevel().addFreshEntity(knot);
                    itemstack.shrink(1);
                    context.getLevel().playSound(null, blockpos.getX() + 0.5, blockpos.getY() + 0.5, blockpos.getZ() + 0.5, DESounds.ES_KITE_ATTACH.get(), SoundSource.NEUTRAL, 1.0F, 1.0F + this.random.nextFloat() * 0.1F);
                }


                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.CONSUME;
            }
        }
    }

    protected boolean canPlace(Player playerIn, Direction directionIn, ItemStack itemStackIn, BlockPos posIn) {
        return !directionIn.getAxis().isVertical() && playerIn.mayUseItemAt(posIn, directionIn, itemStackIn);
    }

    public int getColor() {
        return this.color;
    }

    @Override
    public KiteEntity createKite(Level world, ItemStack stack) {
        return KiteEntity.provide(DEEntities.KITE.get(), world, stack.copy().split(1));
    }

    public static boolean isDesigned(ItemStack stack) {
        return stack.getTagElement("Design") != null;
    }

    public static int getTagColor(ItemStack stack, int color) {
        if (color == 0) {
            return -1;
        }

        if (stack.getTagElement("Design") != null) {
            CompoundTag compoundNBT = stack.getTagElement("Design");
            int id = compoundNBT.getInt("Color");
            return DyeColor.byId(id).getTextColor();
        }
        return 0;
    }
}
