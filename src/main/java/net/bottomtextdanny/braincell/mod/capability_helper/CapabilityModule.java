package net.bottomtextdanny.braincell.mod.capability_helper;

import net.bottomtextdanny.braincell.mod.capability_helper.CapabilityWrap;
import net.minecraft.nbt.CompoundTag;

public abstract class CapabilityModule<T, C extends CapabilityWrap<C,T>> {
    private final String name;
    private final C capability;
    private final T holder;

    public CapabilityModule(String name, C capability) {
        super();
        this.name = name;
        this.capability = capability;
        this.holder = capability.getHolder();
    }

    public T getHolder() {
        return this.holder;
    }

    public C getCapability() {
        return this.capability;
    }

    public String getName() {
        return this.name;
    }

    public abstract void serializeNBT(CompoundTag nbt);

    public abstract void deserializeNBT(CompoundTag nbt);
}
