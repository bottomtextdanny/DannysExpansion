package net.bottomtextdanny.braincell.mod.entity.modules.variable;

import net.bottomtextdanny.braincell.mod.entity.modules.EntityModule;
import net.bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManager;
import net.bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManagerProvider;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityData;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityDataReference;
import net.bottomtextdanny.braincell.mod.entity.serialization.RawEntityDataReference;
import net.bottomtextdanny.braincell.mod.serialization.serializers.builtin.BuiltinSerializers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class IndexedVariableModule extends VariableModule {
    public static int NOT_UPDATED = -1;
    public static final EntityDataReference<Integer> VARIANT_REF =
            BCDataManager.attribute(Entity.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.INTEGER,
                            () -> NOT_UPDATED,
                            VARIANT_TAG)
            );
    private final IndexedFormManager formManager;
    private final EntityData<Integer> formKey;

    public IndexedVariableModule(LivingEntity entity, IndexedFormManager variantList) {
        super(entity);
        this.formManager = variantList;
        if (entity instanceof BCDataManagerProvider manager) {
            this.formKey = manager.bcDataManager().addSyncedData(EntityData.of(VARIANT_REF));
        } else {
            throw new UnsupportedOperationException("IndexedVariableModule needs the holder entity to inherit DEDataManager");
        }
    }

    public Form<?> getForm() {
        return this.formManager.getForm(this.formKey.get());
    }

    public void setForm(Form<?> form) {
        this.formKey.set(this.formManager.getKey(form));
    }

    public boolean isUpdated() {
        return this.formKey.get() != NOT_UPDATED;
    }
}
