package bottomtextdanny.dannys_expansion.content.entities.mob;

import bottomtextdanny.braincell.mod.entity.psyche.Psyche;
import bottomtextdanny.braincell.mod.world.builtin_entities.ModuledMob;
import bottomtextdanny.braincell.mod.world.entity_utilities.PsycheEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;

public abstract class SmartyMob extends ModuledMob implements PsycheEntity {
    private Psyche<?> psyche;

    public SmartyMob(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerExtraGoals() {
        super.registerExtraGoals();
        if (!this.level.isClientSide) {
            this.psyche = makePsyche();
        }
        this.psyche.coreInitialization();
    }

    public abstract Psyche<?> makePsyche();


    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level.isClientSide && this.isAlive() && this.isEffectiveAi()) {
            int i = this.level.getServer().getTickCount() + this.getId();
            if (this.psyche != null) {
                this.psyche.update();
            }
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level,
                                        DifficultyInstance difficulty,
                                        MobSpawnType spawnType,
                                        @Nullable SpawnGroupData group,
                                        @Nullable CompoundTag tag) {
        onAnyPossibleSpawn();
        return super.finalizeSpawn(level, difficulty, spawnType, group, tag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        onAnyPossibleSpawn();
    }

    protected void onAnyPossibleSpawn() {}


    @Override
    public Psyche<?> getPsyche() {
        return this.psyche;
    }
}
