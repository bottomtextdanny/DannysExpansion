package net.bottomtextdanny.dannys_expansion.common.crumbs.crumb_extensions;

import net.bottomtextdanny.dannys_expansion.common.NBTHelper;
import net.bottomtextdanny.dannys_expansion.common.crumbs.Crumb;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.chunk.LevelChunk;

public interface CrumbSerializer extends CrumbExtension {

    String POSITION_TAG = "pos";

    default CompoundTag write(CompoundTag tag) {
        Crumb crumb = asCrumb();
        ListTag positionTag = NBTHelper.newDoubleList(crumb.getPosition().x, crumb.getPosition().y, crumb.getPosition().z);
        tag.put(POSITION_TAG, positionTag);
        writeAdditional(tag);
        return tag;
    }

    default void read(CompoundTag tag) {
        ListTag positionTag = tag.getList(POSITION_TAG, 6);
        asCrumb().setPosition(positionTag.getDouble(0), positionTag.getDouble(1), positionTag.getDouble(2));
        readAdditional(tag);
    }

    void readAdditional(CompoundTag tag);

    void writeAdditional(CompoundTag tag);

    default void markForSerialization() {
        LevelChunk chunk = asCrumb().getChunk();
        if (chunk != null) {
            chunk.setUnsaved(true);
        }
    }
}
