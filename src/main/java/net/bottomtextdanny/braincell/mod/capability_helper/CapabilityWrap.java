package net.bottomtextdanny.braincell.mod.capability_helper;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class CapabilityWrap<C extends CapabilityWrap<C,T>,T> implements INBTSerializable<CompoundTag>, ICapabilityProvider {
    private final ImmutableList<CapabilityModule<T,C>> moduleImmutableList;
    private final T holder;

    public CapabilityWrap(T holder) {
        super();
        this.holder = holder;
        ImmutableList.Builder<CapabilityModule<T,C>> moduleListBuilder = ImmutableList.builder();
        populateModuleList(moduleListBuilder);
        ImmutableList<CapabilityModule<T,C>> modules = moduleListBuilder.build();
        this.moduleImmutableList = modules.isEmpty() ? ImmutableList.of() : modules;
    }

    protected abstract void populateModuleList(ImmutableList.Builder<CapabilityModule<T,C>> moduleList);

    public T getHolder() {
        return this.holder;
    }

    public <CAP extends CapabilityModule<T, C>> CAP getModule(Class<CAP> clazz) {
        return (CAP)null;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == getToken() ? LazyOptional.of(() -> this).cast() : LazyOptional.empty();
    }

    protected abstract Capability<?> getToken();

    @Override
    public final CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        this.moduleImmutableList.forEach(module -> module.serializeNBT(tag));
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.moduleImmutableList.forEach(module -> module.deserializeNBT(nbt));
    }
}
