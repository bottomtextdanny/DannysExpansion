package net.bottomtextdanny.braincell.mod.world.builtin_entities;

import net.bottomtextdanny.braincell.Braincell;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.function.Supplier;

public class BCBoatType {
    public static final BCBoatType INVALID = new BCBoatType(
            new ResourceLocation(Braincell.ID, "invalid"),
            () -> Items.AIR,
            () -> Items.AIR,
            false
    );
    public final ResourceLocation texturePath;
    public final Supplier<Item> boatItem;
    public final Supplier<Item> materialItem;
    public final boolean throwSticks;

    public BCBoatType(ResourceLocation texturePath,
                      Supplier<Item> boatItem,
                      Supplier<Item> materialItem,
                      boolean throwSticks) {
        super();
        this.texturePath = texturePath;
        this.boatItem = boatItem;
        this.materialItem = materialItem;
        this.throwSticks = throwSticks;
    }
}
