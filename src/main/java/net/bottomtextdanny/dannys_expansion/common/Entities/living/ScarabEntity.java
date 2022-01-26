package net.bottomtextdanny.dannys_expansion.common.Entities.living;

import net.bottomtextdanny.braincell.mod.world.builtin_entities.ModuledMob;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.UUID;

public class ScarabEntity extends ModuledMob {

    private MummyEntity caster;
    private UUID casterUuid;

    public ScarabEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);

    }

    public static AttributeSupplier.Builder Attributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 5.0D)
                .add(Attributes.FOLLOW_RANGE, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.35D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.caster != null && this.caster.getTarget() != null) {
            this.setTarget(this.caster.getTarget());
        }
    }

    public void setCaster(@Nullable MummyEntity p_190549_1_) {
        this.caster = p_190549_1_;
        this.casterUuid = p_190549_1_ == null ? null : p_190549_1_.getUUID();
    }

    @Nullable
    public LivingEntity getCaster() {
        if (this.caster == null && this.casterUuid != null && this.level instanceof ServerLevel) {
            Entity entity = ((ServerLevel)this.level).getEntity(this.casterUuid);
            if (entity instanceof MummyEntity) {
                this.caster = (MummyEntity) entity;
            }
        }

        return this.caster;
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        if (compound.hasUUID("Owner")) {
            this.casterUuid = compound.getUUID("Owner");
        }
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        if (this.casterUuid != null) {
            compound.putUUID("Owner", this.casterUuid);
        }
    }
}
