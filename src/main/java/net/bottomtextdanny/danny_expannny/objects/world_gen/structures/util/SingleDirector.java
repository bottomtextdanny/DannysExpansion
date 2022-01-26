package net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util;

import net.minecraft.core.Direction;

public class SingleDirector extends Director {
    private final int indexHolder;

    public SingleDirector(Direction director) {
        this.indexHolder = director.get3DDataValue();
        this.booleans.set(director.get3DDataValue(), true);
    }

    public Direction getDirection() {
        return Direction.from3DDataValue(this.indexHolder);
    }
}
