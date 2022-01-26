package net.bottomtextdanny.dannys_expansion.core.interfaces;

import net.minecraft.world.entity.Entity;

public interface IAttachEntities {

    Entity getAttachedEntity(int index);

    void setAttachedEntity(Entity entity, int index);
}
