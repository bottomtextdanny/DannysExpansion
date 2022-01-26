package net.bottomtextdanny.danny_expannny.capabilities.world;

import net.bottomtextdanny.dannys_expansion.core.Util.Sch;
import net.bottomtextdanny.braincell.mod.capability_helper.CapabilityModule;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;

import java.util.HashSet;
import java.util.Set;

public class LevelScheduleModule extends CapabilityModule<Level, LevelCapability> {
    private final Set<Sch> scheduleMap = new HashSet<>();

    public LevelScheduleModule(LevelCapability capability) {
        super("schedules", capability);
    }

    public void tick() {
        this.scheduleMap.removeIf(schedule -> {
            schedule.decr();

            if (schedule.getOffset() <= 0) {
                schedule.action.accept(getHolder());
                return true;
            }
            return false;
        });
    }

    public void add(Sch schedule) {
        this.scheduleMap.add(schedule);
    }

    public Set<Sch> getScheduleMap() {
        return this.scheduleMap;
    }

    @Override
    public void serializeNBT(CompoundTag nbt) {

    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

    }
}
