package net.bottomtextdanny.danny_expannny.object_tables;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.object_tables.items.DEBuildingItems;
import net.bottomtextdanny.braincell.mod.world.builtin_entities.BCBoatType;
import net.minecraft.resources.ResourceLocation;

public final class DEBoatTypes {
    public static final BCBoatType FOAMSHROOM = new BCBoatType(
            new ResourceLocation(DannysExpansion.ID, "textures/entity/boat/foamshroom_boat.png"),
            DEBuildingItems.FOAMSHROOM_BOAT::get,
            DEBuildingItems.FOAMSHROOM_PLANKS::get,
            true
    );
    public static final BCBoatType FANCY_FOAMSHROOM = new BCBoatType(
            new ResourceLocation(DannysExpansion.ID, "textures/entity/boat/foamshroom_fancy_boat.png"),
            DEBuildingItems.FOAMSHROOM_FANCY_BOAT::get,
            DEBuildingItems.FOAMSHROOM_FANCY_PLANKS::get,
            true
    );
}
