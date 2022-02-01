package net.bottomtextdanny.danny_expannny.objects.entities.mob.varado;

import net.bottomtextdanny.braincell.mod.entity.psyche.Psyche;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.SmartyMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

public class Varado extends SmartyMob {
    public static final float COMBAT_SPEED_MULTIPLIER = 1.1F;
    public static final float ATTACK_RANGE = 2.0F;

    public Varado(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
    }

    public static AttributeSupplier.Builder Attributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.ARMOR, 5.0D)
                .add(Attributes.FOLLOW_RANGE, 40.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.22D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    @Override
    public Psyche<?> makePsyche() {
        return new VaradoPsyche(this);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return true;
    }
}
