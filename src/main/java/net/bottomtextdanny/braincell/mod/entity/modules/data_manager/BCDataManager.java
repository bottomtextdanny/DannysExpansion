package net.bottomtextdanny.braincell.mod.entity.modules.data_manager;

import com.google.common.collect.Lists;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityData;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityDataReference;
import net.bottomtextdanny.braincell.mod.entity.serialization.RawEntityDataReference;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BCDataManager {
    public static final UnsupportedOperationException CANNOT_EXECUTE_NBT_OPERATIONS_ON_CLIENT_EX =
            new UnsupportedOperationException(
                    "World image does not support the execution of nbt operations on client."
            );
    public static final int REFERENCE_SPLIT = 8;
    public static final Map<Class<? extends Entity>, List<EntityDataReference<?>>> REFERENCES =
            new ConcurrentHashMap<>(REFERENCE_SPLIT);
    private final List<EntityData<?>> syncedDataList;
    private final List<EntityData<?>> nonSyncedDataList;
    private final Entity provider;

    public BCDataManager(Entity provider) {
        super();
        this.provider = provider;
        this.syncedDataList = Lists.newArrayList();
        this.nonSyncedDataList = Lists.newArrayList();
    }

    public static <T> EntityDataReference<T> attribute(
            Class<? extends Entity> accessor,
            RawEntityDataReference<T> rawRef) {
        List<EntityDataReference<?>> list;
        if (REFERENCES.containsKey(accessor)) {
            list = REFERENCES.get(accessor);
        } else {
            list = Lists.newArrayList();
            REFERENCES.put(accessor, list);
        }
        int currSize = list.size();
        EntityDataReference<T> ref = rawRef.mark(accessor, currSize);
        list.add(ref);
        return ref;
    }

    public <T> EntityData<T> addNonSyncedData(EntityData<T> data) {
        this.nonSyncedDataList.add(data);
        return data;
    }

    public <T> EntityData<T> addSyncedData(EntityData<T> data) {
        this.syncedDataList.add(data);
        return data;
    }

    public void writeTag(CompoundTag tag) {
        if (this.provider.level instanceof ServerLevel level) {
            this.nonSyncedDataList.forEach(data -> {
                data.writeToNBT(tag, level);
            });
            this.syncedDataList.forEach(data -> {
                data.writeToNBT(tag, level);
            });
        } else throw CANNOT_EXECUTE_NBT_OPERATIONS_ON_CLIENT_EX;

    }

    public void readTag(CompoundTag tag) {
        if (this.provider.level instanceof ServerLevel level) {
            this.nonSyncedDataList.forEach(data -> {
                data.readFromNBT(tag, level);
            });
            this.syncedDataList.forEach(data -> {
                data.readFromNBT(tag, level);
            });
        } else throw CANNOT_EXECUTE_NBT_OPERATIONS_ON_CLIENT_EX;
    }

    public void writePacket(FriendlyByteBuf pct) {
        this.syncedDataList.forEach(data -> {
            data.writeToPacketStream(pct, this.provider.level);
        });
    }

    @OnlyIn(Dist.CLIENT)
    public void readPacket(FriendlyByteBuf pct) {
        this.syncedDataList.forEach(data -> {
            data.readFromPacketStream(pct, this.provider.level);
        });
    }
}
