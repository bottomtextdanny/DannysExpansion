package net.bottomtextdanny.braincell.underlying.util;

import net.minecraft.core.Position;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec2;

public final class BCDistance2Getter {
    private final Start2D start = new Start2D();

    public Start2D start(double x, double y) {
        this.start.set(x, y);
        return this.start;
    }

    public Start2D start(Vec2 vector) {
        this.start.set(
                vector.x,
                vector.y);
        return this.start;
    }

    public Start2D start(double[] vector) {
        this.start.set(
                vector[0],
                vector[1]);
        return this.start;
    }

    public Start2D start(float[] vector) {
        this.start.set(
                vector[0],
                vector[1]);
        return this.start;
    }

    public static final class Start2D {
        private double x;
        private double y;
        
        private void set(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double get(double endX, double endY) {
            double xDif = this.x - endX;
            double yDif = this.y - endY;

            return Math.sqrt(xDif * xDif + yDif * yDif);
        }

        public double getManhattan(double endX, double endY) {
            double xDif = this.x - endX;
            double yDif = this.y - endY;

            return xDif + yDif;
        }

        public double get(Vec2 vector) {
            return get(vector.x, vector.y);
        }

        public double getManhattan(Vec2 vector) {
            return getManhattan(vector.x, vector.y);
        }

        public double get(double[] vector) {
            return get(
                    vector[0],
                    vector[1]);
        }

        public double getManhattan(double[] vector) {
            return getManhattan(
                    vector[0],
                    vector[1]);
        }

        public double get(float[] vector) {
            return get(
                    vector[0],
                    vector[1]);
        }

        public double getManhattan(float[] vector) {
            return getManhattan(
                    vector[0],
                    vector[1]);
        }
    }
}
