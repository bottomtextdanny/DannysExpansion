package net.bottomtextdanny.braincell.underlying.util;

import net.minecraft.core.Position;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Entity;

public final class BCDistance3Getter {
    private final Start3D start = new Start3D();

    public Start3D start(double x, double y, double z) {
        this.start.set(x, y, z);
        return this.start;
    }

    public Start3D start(Position vector) {
        this.start.set(
                (float) vector.x(),
                (float) vector.y(),
                (float) vector.z());
        return this.start;
    }

    public Start3D start(Entity vector) {
        this.start.set(
                (float) vector.position().x(),
                (float) vector.position().y(),
                (float) vector.position().z());
        return this.start;
    }

    public Start3D start(Vec3i integerPosition) {
        this.start.set(
                (float)integerPosition.getX() + 0.5F,
                (float)integerPosition.getY() + 0.5F,
                (float)integerPosition.getZ() + 0.5F);
        return this.start;
    }

    public Start3D start(double[] vector) {
        this.start.set(
                vector[0],
                vector[1],
                vector[2]);
        return this.start;
    }

    public Start3D start(float[] vector) {
        this.start.set(
                vector[0],
                vector[1],
                vector[2]);
        return this.start;
    }
    
    public static final class Start3D {
        private double x;
        private double y;
        private double z;

        private Start3D() {
            super();
        }

        private void set(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public double get(double endX, double endY, double endZ) {
            double xDif = this.x - endX;
            double yDif = this.y - endY;
            double zDif = this.z - endZ;

            return Math.sqrt(xDif * xDif + yDif * yDif + zDif * zDif);
        }

        public double getManhattan(double endX, double endY, double endZ) {
            double xDif = this.x - endX;
            double yDif = this.y - endY;
            double zDif = this.z - endZ;

            return xDif + yDif + zDif;
        }

        public double get(Position vector) {
            return get(vector.x(), vector.y(), vector.z());
        }

        public double getManhattan(Position vector) {
            return getManhattan(vector.x(), vector.y(), vector.z());
        }

        public double get(Entity entity) {
            return get(
                    entity.position().x,
                    entity.position().y,
                    entity.position().z);
        }

        public double getManhattan(Entity entity) {
            return getManhattan(
                    entity.position().x,
                    entity.position().y,
                    entity.position().z);
        }

        public double get(Vec3i integerPosition) {
            return get(
                    integerPosition.getX() + 0.5F,
                    integerPosition.getY() + 0.5F,
                    integerPosition.getZ() + 0.5F);
        }

        public double getManhattan(Vec3i integerPosition) {
            return getManhattan(
                    integerPosition.getX() + 0.5F,
                    integerPosition.getY() + 0.5F,
                    integerPosition.getZ() + 0.5F);
        }

        public double get(double[] vector) {
            return get(
                    vector[0],
                    vector[1],
                    vector[2]);
        }

        public double getManhattan(double[] vector) {
            return getManhattan(
                    vector[0],
                    vector[1],
                    vector[2]);
        }

        public double get(float[] vector) {
            return get(
                    vector[0],
                    vector[1],
                    vector[2]);
        }

        public double getManhattan(float[] vector) {
            return getManhattan(
                    vector[0],
                    vector[1],
                    vector[2]);
        }
    }
}
