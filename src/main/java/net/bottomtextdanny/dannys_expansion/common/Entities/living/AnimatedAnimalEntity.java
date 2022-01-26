package net.bottomtextdanny.dannys_expansion.common.Entities.living;

import net.bottomtextdanny.braincell.mod.world.builtin_entities.ModuledMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class AnimatedAnimalEntity extends ModuledMob {

	
    public AnimatedAnimalEntity(EntityType<? extends ModuledMob> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    public void tick() {
        super.tick();
    }
}
