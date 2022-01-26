package net.bottomtextdanny.braincell.mod.structure.common_sided;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.function.Supplier;

public record RawEntitySpawnDeferring<T extends Mob>(
        SpawnPlacements.Type placement,
        Heightmap.Types heightMap,
        SpawnPlacements.SpawnPredicate<T> predicate) {

    public EntitySpawnDeferring<T> makeDeferring(Supplier<? extends EntityType<T>> wrappedType) {
        return new EntitySpawnDeferring<>(wrappedType, this.placement, this.heightMap, this.predicate);
    }
}

