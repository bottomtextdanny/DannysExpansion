package bottomtextdanny.dannys_expansion.content.items.arrow;

import bottomtextdanny.dannys_expansion._util.tooltip.*;
import bottomtextdanny.dannys_expansion.content.entities.projectile.arrow.DEArrow;
import bottomtextdanny.dannys_expansion.tables.DEEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class WoodenArrowItem extends DEArrowItem {

    public static final float DAMAGE = 0.75F;
    public static final float SPEED = 0.65F;

    public WoodenArrowItem(Properties builder) {
        super(builder);
    }

    @Nonnull
    public TooltipWriter createTooltipInfo() {
        return TooltipTable.builder()
                .block(TooltipWriter.component(new TextComponent("")))
                .block(TooltipBlock.builder()
                        .header(TooltipWriter.trans("description.dannys_expansion.statistics",
                                StringSuppliers.translatable("description.dannys_expansion.arrow"),
                                Style.EMPTY.applyFormats(ChatFormatting.GRAY),
                                Style.EMPTY.applyFormats(ChatFormatting.GRAY)))
                        .add(TooltipData.DAMAGE.message(StringSuppliers.float_(DAMAGE))
                                .withStyle(ChatFormatting.DARK_GREEN))
                        .add(TooltipData.ARROW_SPEED_FACTOR.message(StringSuppliers.float_(SPEED))
                                .withStyle(ChatFormatting.DARK_GREEN))
                        .add(TooltipBlock.builder()
                                .condition(TooltipCondition.HOLD_SHIFT)
                                .build())
                        .build())
                .build();
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity shooter) {
        DEArrow arrow = new DEArrow(DEEntities.ARROW.get(), level);
        arrow.setPickItem(new ItemStack(this));
        arrow.syncPickItem();
        arrow.setDamage(DAMAGE);
        arrow.setVelocityFactor(SPEED);
        arrow.setOwner(shooter);
        arrow.setPos(shooter.getX(), shooter.getEyeY() - (double)0.1F, shooter.getZ());

        return arrow;
    }
}
