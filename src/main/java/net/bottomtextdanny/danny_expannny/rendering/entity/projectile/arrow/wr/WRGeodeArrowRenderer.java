package net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow.wr;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow.DannyArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;

import java.lang.reflect.Field;
import java.util.Optional;

public class WRGeodeArrowRenderer extends DannyArrowRenderer<AbstractArrow> {
    public static Item RED_GEODE_ARROW;
    public static Item PURPLE_GEODE_ARROW;
    public static Item BLUE_GEODE_ARROW;

    public WRGeodeArrowRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
        Optional<Item> red = Registry.ITEM.getOptional(new ResourceLocation("wyrmroost:red_geode_tipped_arrow"));
        Optional<Item> purple = Registry.ITEM.getOptional(new ResourceLocation("wyrmroost:purple_geode_tipped_arrow"));
        Optional<Item> blue = Registry.ITEM.getOptional(new ResourceLocation("wyrmroost:blue_geode_tipped_arrow"));

        red.ifPresent(item -> RED_GEODE_ARROW = item);
        purple.ifPresent(item -> PURPLE_GEODE_ARROW = item);
        blue.ifPresent(item -> BLUE_GEODE_ARROW = item);
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractArrow entity) {

        try {
            Field geodearrowentity$item = entity.getClass().getDeclaredField("item");
            geodearrowentity$item.setAccessible(true);

            Item geodeArrow = (Item) geodearrowentity$item.get(entity);

            if (geodeArrow == RED_GEODE_ARROW) {
                return new ResourceLocation(DannysExpansion.ID, "textures/entity/arrow/wr/red_geode_arrow.png");
            } else if (geodeArrow == PURPLE_GEODE_ARROW) {
                return new ResourceLocation(DannysExpansion.ID, "textures/entity/arrow/wr/purple_geode_arrow.png");
            } else if (geodeArrow == BLUE_GEODE_ARROW) {
                return new ResourceLocation(DannysExpansion.ID, "textures/entity/arrow/wr/blue_geode_arrow.png");
            }
        } catch (Exception ignored) {
        }

        return new ResourceLocation(DannysExpansion.ID, "textures/entity/arrow/arrow.png");
    }
}
