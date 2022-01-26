package net.bottomtextdanny.braincell.mod.entity.serialization;

import net.bottomtextdanny.braincell.mod.serialization.serializers.SerializerMark;
import net.minecraft.world.entity.Entity;

import java.util.Objects;
import java.util.function.Supplier;

public final class RawEntityDataReference<T> {
    private final SerializerMark<T> serializer;
    private final Supplier<T> defaultProvider;
    private final String storageKey;

    private RawEntityDataReference(SerializerMark<T> serializer, Supplier<T> defaultProvider, String storageKey) {
        this.serializer = serializer;
        this.defaultProvider = defaultProvider;
        this.storageKey = storageKey;
    }

    public static <T> RawEntityDataReference<T> of(SerializerMark<T> serializer, Supplier<T> defaultProvider, String storageKey) {
        return new RawEntityDataReference<>(serializer, defaultProvider, storageKey);
    }

    public EntityDataReference<T> mark(Class<? extends Entity> accessor, int lookupMark) {
        return new EntityDataReference<>(this.serializer, this.defaultProvider, this.storageKey, accessor, lookupMark);
    }

    public SerializerMark<T> serializer() {
        return this.serializer;
    }

    public Supplier<T> defaultProvider() {
        return this.defaultProvider;
    }

    public String storageKey() {
        return this.storageKey;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (RawEntityDataReference) obj;
        return Objects.equals(this.serializer, that.serializer) &&
                Objects.equals(this.defaultProvider, that.defaultProvider) &&
                Objects.equals(this.storageKey, that.storageKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.serializer, this.defaultProvider, this.storageKey);
    }

    @Override
    public String toString() {
        return "RawDataReference[" +
                "reference=" + this.serializer + ", " +
                "defaultProvider=" + this.defaultProvider + ", " +
                "storageKey=" + this.storageKey + ']';
    }

}
