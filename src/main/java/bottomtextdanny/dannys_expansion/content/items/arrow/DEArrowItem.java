package bottomtextdanny.dannys_expansion.content.items.arrow;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion._util.tooltip.TooltipWriter;
import bottomtextdanny.dannys_expansion.content.entities.projectile.arrow.DEArrow;
import net.minecraft.client.renderer.entity.TippableArrowRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class DEArrowItem extends ArrowItem {
    public static final List<DEArrowItem> DANNY_ARROWS = new ArrayList<>();
    private WeakReference<TooltipWriter> tooltipInfo;
    private ResourceLocation texture;

    public DEArrowItem(Properties builder) {
        super(builder);
        tooltipInfo = new WeakReference<>(null);
    }

    @Nonnull
    public TooltipWriter createTooltipInfo() {
        return ((spacing, stack, level, tooltip, flag) -> {});
    }

    public boolean isInfinite(ItemStack stack, ItemStack bow, Player player) {
        int enchant = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, bow);
        return enchant > 0 && player.getRandom().nextBoolean();
    }

    public void onEntityHurt(DEArrow arrow, Entity hitEntity, float damage) {}

    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getTexture() {

        if (getRegistryName() != null && this.texture == null) {
            texture = new ResourceLocation(DannysExpansion.ID, "textures/entity/arrow/" + getRegistryName().getPath() + ".png");
        }

        if (texture != null) return texture;
        return TippableArrowRenderer.NORMAL_ARROW_LOCATION;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level,
                                List<Component> toooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, toooltip, flag);
        TooltipWriter writer = tooltipInfo.get();

        if (writer == null) {
            writer = createTooltipInfo();
            tooltipInfo = new WeakReference<>(writer);
        }

        writer.write(0, stack, level, toooltip, flag);
    }
}
