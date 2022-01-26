package net.bottomtextdanny.braincell.mod.structure.common_sided;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.function.Supplier;

public record EntitySpawnDeferring<T extends Mob>(
        Supplier<? extends EntityType<T>> entityTypeWrapped,
        SpawnPlacements.Type placement,
        Heightmap.Types heightMap,
        SpawnPlacements.SpawnPredicate<T> predicate) {

    public void put() {
        SpawnPlacements.register(this.entityTypeWrapped.get(), this.placement, this.heightMap, this.predicate);
    }
}

