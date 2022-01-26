package net.bottomtextdanny.danny_expannny.objects.items;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;

import java.util.function.Supplier;

public class BCSpawnEggItemBuilder {
    private final int firstTint;
    private final int secondTint;
    private boolean unstable;
    private BCSpawnEggItem.SpawnLogic logic = BCSpawnEggItem.DEFAULT_LOGIC;
    private Supplier<? extends EntityType<? extends Mob>> typeSupplier;

    private BCSpawnEggItemBuilder(int firstTint, int secondTint) {
        super();
        this.firstTint = firstTint;
        this.secondTint = secondTint;
    }

    public static BCSpawnEggItemBuilder create(int firstTint, int secondTint) {
        return new BCSpawnEggItemBuilder(firstTint, secondTint);
    }

    public BCSpawnEggItemBuilder setUnstable(boolean unstable) {
        this.unstable = unstable;
        return this;
    }

    public BCSpawnEggItemBuilder setLogic(BCSpawnEggItem.SpawnLogic logic) {
        this.logic = logic;
        return this;
    }

    public BCSpawnEggItemBuilder setTypeSupplier(Supplier<? extends EntityType<? extends Mob>> typeSupplier) {
        this.typeSupplier = typeSupplier;
        return this;
    }

    public BCSpawnEggItem createDESpawnEggItem() {
        return new BCSpawnEggItem(this.firstTint, this.secondTint, this.unstable, this.logic, this.typeSupplier);
    }
}