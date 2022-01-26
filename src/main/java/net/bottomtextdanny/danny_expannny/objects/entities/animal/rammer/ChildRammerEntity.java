package net.bottomtextdanny.danny_expannny.objects.entities.animal.rammer;

import net.bottomtextdanny.danny_expannny.object_tables.DEEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;

public class ChildRammerEntity extends Animal {
       int growTicks = (int) (7200 + 4800 * this.random.nextFloat());

    public ChildRammerEntity(EntityType<? extends ChildRammerEntity> type, Level worldIn) {
        super(type, worldIn);
        this.setSize(1 + this.random.nextFloat() / 3F);
        refreshDimensions();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.4d));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 0.6D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 0.4D, Ingredient.of(Items.CHORUS_FRUIT), false));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_146746_, DifficultyInstance p_146747_, MobSpawnType p_146748_, @Nullable SpawnGroupData p_146749_, @Nullable CompoundTag p_146750_) {
        this.setSize(1 + this.random.nextFloat() / 3F);
        refreshDimensions();
        return super.finalizeSpawn(p_146746_, p_146747_, p_146748_, p_146749_, p_146750_);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    public static AttributeSupplier.Builder Attributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 25.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D);
    }


    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putFloat("RammerSize", this.getSize());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setSize(compound.getFloat("RammerSize"));
    }

    public void setSize(float sizeIn) {
    }

    public EntityDimensions getDimensions(Pose poseIn) {
        return super.getDimensions(poseIn).scale(getSize());
    }

    public float getSize() {
        return 1;
    }



    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public void aiStep() {
        super.aiStep();


        if (this.growTicks > 0) {
            --this.growTicks;
        }
        else {
            RammerEntity rammerEntity = new RammerEntity(DEEntities.RAMMER.get(), this.level);
            rammerEntity.size.set(getSize());
            if (this.hasCustomName()) {
                rammerEntity.setCustomName(this.getCustomName());
                rammerEntity.setCustomNameVisible(this.isCustomNameVisible());
            }
            rammerEntity.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
            this.level.addFreshEntity(rammerEntity);
            this.remove(RemovalReason.DISCARDED);
        }
    }
}
