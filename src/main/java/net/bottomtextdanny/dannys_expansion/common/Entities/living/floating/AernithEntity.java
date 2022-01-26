package net.bottomtextdanny.dannys_expansion.common.Entities.living.floating;

import net.bottomtextdanny.braincell.mod.world.builtin_entities.ModuledMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class AernithEntity extends ModuledMob {

    public AernithEntity(EntityType<? extends AernithEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        setNoGravity(true);
    }
}
