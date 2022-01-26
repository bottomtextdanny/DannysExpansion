package net.bottomtextdanny.danny_expannny.objects.entities.modules.phase_affected_provider;

import net.bottomtextdanny.braincell.mod.entity.modules.EntityModule;
import net.bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManager;
import net.bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManagerProvider;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityData;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityDataReference;
import net.bottomtextdanny.braincell.mod.entity.serialization.RawEntityDataReference;
import net.bottomtextdanny.braincell.mod.serialization.serializers.builtin.BuiltinSerializers;
import net.bottomtextdanny.danny_expannny.capabilities.world.LevelPhaseModule;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;

public class PhaseAffectedModule extends EntityModule<Mob, PhaseAffectedProvider> {
    public static byte NOT_UPDATED = (byte) -1;
    public static final EntityDataReference<Byte> PHASE_SPAWN_REF =
            BCDataManager.attribute(Entity.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.BYTE,
                            () -> NOT_UPDATED,
                            "phase_spawn")
            );
    private final EntityData<Byte> phaseSpawn;
    public boolean processed;

    public PhaseAffectedModule(Mob entity) {
        super(entity);
        if (entity instanceof BCDataManagerProvider manager) {
            this.phaseSpawn = manager.bcDataManager().addSyncedData(EntityData.of(PHASE_SPAWN_REF));
        } else {
            throw new UnsupportedOperationException("PhaseAffectedModule needs the holder entity to inherit DEDataManager.");
        }
    }

    public LevelPhaseModule.Phase getPhaseSpawned() {
        if (this.phaseSpawn.get() == NOT_UPDATED) throw new ArrayIndexOutOfBoundsException("PhaseAffectedModule has not retrieved image.");
        return LevelPhaseModule.Phase.values()[(int)this.phaseSpawn.get()];
    }


    public boolean isUpdated() {
        return this.phaseSpawn.get() != NOT_UPDATED;
    }

    public void setPhaseSpawned(LevelPhaseModule.Phase phaseSpawned) {
        this.phaseSpawn.set((byte)phaseSpawned.ordinal());
    }
}
