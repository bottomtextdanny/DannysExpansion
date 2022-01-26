package net.bottomtextdanny.danny_expannny.capabilities.world;

import net.bottomtextdanny.braincell.mod.capability_helper.CapabilityModule;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class LevelPhaseModule extends CapabilityModule<Level, LevelCapability> {
    private static final String PHASE_TAG = "phase";
    private Phase phase = Phase.NORMAL;

    public LevelPhaseModule(LevelCapability capability) {
        super("phase", capability);
    }

    public void assertPhase(Phase assertedPhase) {
        if (assertedPhase.ordinal() > this.phase.ordinal()) {
            this.phase = assertedPhase;
        }
    }

    public void forcePhase(Phase newPhase) {
        this.phase = newPhase;
    }

    public Phase getPhase() {
        return this.phase;
    }

    @Override
    public void serializeNBT(CompoundTag nbt) {
        nbt.putByte(PHASE_TAG, (byte)this.phase.ordinal());
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.phase = Phase.values()[nbt.getByte(PHASE_TAG)];
    }

    public enum Phase {
        NORMAL("normal"),
        DRAGON("post_dragon");

        public static final Map<String, Integer> STRING_TO_ORDINAL = Arrays.stream(Phase.values()).collect(Collectors.toMap(Phase::getName, Enum::ordinal));
        public static Iterable<String> names = Arrays.stream(Phase.values()).map(Phase::getName).collect(Collectors.toCollection(ArrayList::new));
        public static Iterable<String> ordinals = Arrays.stream(Phase.values()).map(phase -> String.valueOf(phase.ordinal())).collect(Collectors.toCollection(ArrayList::new));

        public final String name;

        Phase(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
}
