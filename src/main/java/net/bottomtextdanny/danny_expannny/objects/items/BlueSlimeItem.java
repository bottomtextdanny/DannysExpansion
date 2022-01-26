package net.bottomtextdanny.danny_expannny.objects.items;

import net.bottomtextdanny.danny_expannny.object_tables.DEEntities;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slime.BlueSlimeEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.ItemUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class BlueSlimeItem extends Item {

    public BlueSlimeItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        CompoundTag compoundnbt = stack.getTagElement("BlueSlimeTags");
        if (compoundnbt != null) {
            if (compoundnbt.contains("name")) {
                tooltip.add(ItemUtil.descComp("pet_name").withStyle(ChatFormatting.GREEN).append(Component.nullToEmpty(": " + compoundnbt.getString("name"))));
            }

            if (compoundnbt.contains("health")) {
                tooltip.add(ItemUtil.descComp("pet_health").withStyle(ChatFormatting.GREEN).append(Component.nullToEmpty(": " + compoundnbt.getFloat("health"))));
            }

            if (compoundnbt.contains("owner")) {
                tooltip.add(ItemUtil.descComp("pet_owner").withStyle(ChatFormatting.GREEN).append(": " + worldIn.getPlayerByUUID(compoundnbt.getUUID("owner")).getName().getString()));
            }
        }

        ItemUtil.createDescription(this, tooltip);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos pos = context.getClickedPos();
        ItemStack stack = context.getItemInHand();
        Level world = context.getLevel();
        BlockState blockstate = world.getBlockState(pos);
        CompoundTag compoundnbt = stack.getTagElement("BlueSlimeTags");
        BlueSlimeEntity blueSlimeEntity = new BlueSlimeEntity(DEEntities.BLUE_SLIME.get(), context.getLevel());
        if (!(world instanceof ServerLevel)) {
            return InteractionResult.SUCCESS;
        }
        if (compoundnbt != null) {
            blueSlimeEntity.setCustomName(new TextComponent(compoundnbt.getString("name")));
            blueSlimeEntity.setHealth(compoundnbt.getFloat("health"));
            blueSlimeEntity.setTamedBy(world.getPlayerByUUID(compoundnbt.getUUID("owner")));
        } else {
            blueSlimeEntity.setTamedBy(context.getPlayer());
        }

        if (!blockstate.getCollisionShape(world, pos).isEmpty()) {
           pos = pos.relative(context.getClickedFace());
        }

        stack.setCount(stack.getCount() -1);

        blueSlimeEntity.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        context.getLevel().addFreshEntity(blueSlimeEntity);
        blueSlimeEntity.mainAnimationHandler.play(blueSlimeEntity.fromItem);
        return super.useOn(context);
    }
}
