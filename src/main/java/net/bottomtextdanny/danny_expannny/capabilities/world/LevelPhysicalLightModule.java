package net.bottomtextdanny.danny_expannny.capabilities.world;

import net.bottomtextdanny.dannys_expansion.core.base.pl.PhysicalLight;
import net.bottomtextdanny.braincell.mod.capability_helper.CapabilityModule;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.LinkedList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class LevelPhysicalLightModule extends CapabilityModule<Level, LevelCapability> {
    private final List<PhysicalLight> lightList = new LinkedList<>();

    public LevelPhysicalLightModule(LevelCapability capability) {
        super("physical_lights", capability);
    }

    public void tick() {
        if (!this.lightList.isEmpty()) {
            this.lightList.removeIf(pl -> {
                pl.tick();
                return pl.isRemoved();
            });
        }
    }

    public void add(PhysicalLight pl) {
        this.lightList.add(pl);
    }

    public List<PhysicalLight> getLightList() {
        return this.lightList;
    }

    @Override
    public void serializeNBT(CompoundTag nbt) {

    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

    }
}
