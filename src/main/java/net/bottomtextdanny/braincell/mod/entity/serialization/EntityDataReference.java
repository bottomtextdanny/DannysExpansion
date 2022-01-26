package net.bottomtextdanny.braincell.mod.entity.serialization;

import net.bottomtextdanny.braincell.mod.serialization.serializers.SerializerMark;
import net.minecraft.world.entity.Entity;

import java.util.function.Supplier;

public record EntityDataReference<T>(SerializerMark<T> serializer, Supplier<T> defaultProvider, String storageKey, Class<? extends Entity> accessor, int lookupMark) {}
