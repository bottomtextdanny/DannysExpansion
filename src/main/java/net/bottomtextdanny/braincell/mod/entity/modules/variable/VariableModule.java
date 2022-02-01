package net.bottomtextdanny.braincell.mod.entity.modules.variable;

import net.bottomtextdanny.braincell.mod.entity.modules.EntityModule;
import net.minecraft.world.entity.LivingEntity;

public abstract class VariableModule extends EntityModule<LivingEntity, VariantProvider> {
    protected static final String VARIANT_TAG = "variant";

    public VariableModule(LivingEntity entity) {
        super(entity);
    }

    public abstract Form<?> getForm();

    public abstract void setForm(Form<?> form);

    public abstract boolean isUpdated();
}
