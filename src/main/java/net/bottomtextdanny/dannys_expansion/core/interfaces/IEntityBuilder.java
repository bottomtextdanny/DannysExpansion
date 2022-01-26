package net.bottomtextdanny.dannys_expansion.core.interfaces;

import net.bottomtextdanny.danny_expannny.objects.items.BCSpawnEggItem;
import net.bottomtextdanny.danny_expannny.objects.items.SpecialKiteItem;
import net.bottomtextdanny.dannys_expansion.core.Util.setup.EntityWrap;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.function.Function;
import java.util.function.Supplier;

public interface IEntityBuilder<T extends Entity> {

    IEntityBuilder<T> declare(String entityId, EntityType.EntityFactory<T> factory);

    IEntityBuilder<T> classification(MobCategory classification);

    IEntityBuilder<T> dimensions(float width, float height);

    IEntityBuilder<T> renderer(Supplier<? extends Function<?, ?>> renderer);

    IEntityBuilder<T> attributes(Supplier<AttributeSupplier.Builder> attributeMap);

    IEntityBuilder<T> spawn(SpawnPlacements.Type placement, Heightmap.Types heightMap, SpawnPlacements.SpawnPredicate<T> pred);

    IEntityBuilder<T> egg(BCSpawnEggItem.Builder egg);

    IEntityBuilder<T> kite(Supplier<SpecialKiteItem> kite);

    EntityWrap<EntityType<T>> build();
}
