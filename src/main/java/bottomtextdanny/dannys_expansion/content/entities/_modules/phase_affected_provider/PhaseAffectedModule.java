package bottomtextdanny.dannys_expansion.content.entities._modules.phase_affected_provider;

import bottomtextdanny.dannys_expansion._base.capabilities.world.LevelPhaseModule;
import bottomtextdanny.braincell.mod.entity.modules.EntityModule;
import bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManager;
import bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManagerProvider;
import bottomtextdanny.braincell.mod.entity.serialization.EntityData;
import bottomtextdanny.braincell.mod.entity.serialization.EntityDataReference;
import bottomtextdanny.braincell.mod.entity.serialization.RawEntityDataReference;
import bottomtextdanny.braincell.mod._base.serialization.builtin.BuiltinSerializers;
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
