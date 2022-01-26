package net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util;

import net.minecraft.core.Direction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Director {
    public List<Boolean> booleans = Arrays.asList(false, false, false, false, false, false);

    public Director(Direction... dirs) {

        for (int i = 0; i < Direction.values().length; i++) {

            for (Direction direction : dirs) {
                if (direction == Direction.from3DDataValue(i)) {
                    this.booleans.set(i, true);
                    break;
                }
            }
        }
    }

    public void forEachActive(Predicate<Direction> direction) {
        Direction[] directions = getDirectionsArray();

        for (Direction direction1 : directions) {
            boolean pre = direction.test(direction1);

            if (pre) break;
        }
    }

    public Direction getFirst() {

        for (Direction dir : Direction.values()) {
            if (this.booleans.get(dir.get3DDataValue())) {
                return dir;
            }
        }

        return null;
    }

    public Direction[] getDirectionsArray() {
        Direction[] activeDirections = new Direction[6];

        int count = 0;
        for (Direction dir : Direction.values()) {
            if (this.booleans.get(dir.get3DDataValue())) {
                activeDirections[count] = dir;
                count++;
            }
        }

        return activeDirections;
    }

    public List<Direction> getDirections() {
        List<Direction> activeDirections = new ArrayList<>(6);

        for (Direction dir : Direction.values()) {
            if (this.booleans.get(dir.get3DDataValue())) {
                activeDirections.add(dir);
            }
        }

        return activeDirections;
    }

    public void setDirection(Direction direction, boolean v) {

        this.booleans.set(direction.get3DDataValue(), v);
    }

    public boolean hasDirection(Direction direction) {
        return this.booleans.get(direction.get3DDataValue());
    }
}
