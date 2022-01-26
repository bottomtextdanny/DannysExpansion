package net.bottomtextdanny.dannys_expansion.common.Entities.ai.pathnavigators;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.level.Level;

public class FreePathNavigator extends FlyingPathNavigation {

    public FreePathNavigator(Mob entityIn, Level worldIn) {
        super(entityIn, worldIn);
    }

    public void tick() {
    }


    public boolean moveTo(double x, double y, double z, double speedIn) {
        this.mob.getMoveControl().setWantedPosition(x, y, z, speedIn);
        return true;
    }

    public boolean moveTo(Entity entityIn, double speedIn) {
        this.mob.getMoveControl().setWantedPosition(entityIn.getX(), entityIn.getY(), entityIn.getZ(), speedIn);
        return true;
    }
}
