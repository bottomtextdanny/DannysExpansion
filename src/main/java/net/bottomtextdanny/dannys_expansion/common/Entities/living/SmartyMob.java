package net.bottomtextdanny.dannys_expansion.common.Entities.living;

import net.bottomtextdanny.braincell.mod.entity.psyche.Psyche;
import net.bottomtextdanny.braincell.mod.world.builtin_entities.ModuledMob;
import net.bottomtextdanny.braincell.mod.world.entity_utilities.PsycheEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

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

    public abstract Psyche<?> makePsyche();

    @Override
    public Psyche<?> getPsyche() {
        return this.psyche;
    }
}
