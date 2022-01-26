package net.bottomtextdanny.dannys_expansion.common.Entities.living;

import net.bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManager;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityData;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityDataReference;
import net.bottomtextdanny.braincell.mod.entity.serialization.RawEntityDataReference;
import net.bottomtextdanny.braincell.mod.serialization.serializers.builtin.BuiltinSerializers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class OwnableMountEntity extends MountEntity {
    public static final EntityDataReference<Entity> OWNER_REF =
            BCDataManager.attribute(OwnableMountEntity.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.ENTITY_REFERENCE,
                            () -> null,
                            "owner")
            );
    private final EntityData<Entity> owner;
    private boolean cachedTamedState;

    public OwnableMountEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        this.owner = bcDataManager().addSyncedData(EntityData.of(OWNER_REF));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        updateTamedState();
    }

    public boolean isTamed() {
        return this.cachedTamedState;
    }

    public void updateTamedState() {
        this.cachedTamedState = this.owner.get() != null;
    }

    public void setTamedBy(Player player) {
        this.owner.set(player);
        updateTamedState();
    }

    @Nullable
    public LivingEntity getOwner() {
        if (this.owner.get() instanceof LivingEntity) {
            return (LivingEntity) this.owner.get();
        } else {
            return null;
        }
    }

    public boolean isOwner(LivingEntity entityIn) {
        return entityIn == this.getOwner();
    }
}
