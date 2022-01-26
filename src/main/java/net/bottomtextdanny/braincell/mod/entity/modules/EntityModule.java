package net.bottomtextdanny.braincell.mod.entity.modules;

import net.minecraft.world.entity.Entity;

public class EntityModule<E extends Entity, P extends ModuleProvider> {
    protected final E entity;
    protected final P provider;

    public EntityModule(E entity) {
        super();
        this.entity = entity;
        this.provider = (P)entity;
    }
}
