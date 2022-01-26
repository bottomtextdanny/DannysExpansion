package net.bottomtextdanny.braincell.underlying.util;

import net.minecraft.core.Position;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public final class BCRotVector {
    //*\\*//*\\*//*\\POSITION LOOK HELPER*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//
    public static YP_DoubleLookStart start(double x, double y, double z) {
        return new YP_DoubleLookStart(x, y, z);
    }

    public static YP_DoubleLookStart start(Position vector) {
        return new YP_DoubleLookStart(
                (float) vector.x(),
                (float) vector.y(),
                (float) vector.z());
    }

    public static YP_DoubleLookStart start(Entity vector) {
        return new YP_DoubleLookStart(
                (float) vector.position().x(),
                (float) vector.position().y(),
                (float) vector.position().z());
    }

    public static YP_DoubleLookStart start(Vec3i integerPosition) {
        return new YP_DoubleLookStart(
                (float)integerPosition.getX() + 0.5F,
                (float)integerPosition.getY() + 0.5F,
                (float)integerPosition.getZ() + 0.5F);
    }

    public record YP_DoubleLookStart(double x, double y, double z) {

        public Vec3 get(double endX, double endY, double endZ) {
            double xDif = this.x - endX;
            double yDif = this.y - endY;
            double zDif = this.z - endZ;
            double squareDif = Mth.sqrt((float) (xDif * xDif + zDif * zDif));
            double yaw = Mth.atan2(yDif, xDif) * BCMath.RAD - 90.0D;
            double pitch = -(Mth.atan2(yDif, squareDif) * BCMath.RAD);
            float yCos = BCMath.cos(-yaw * BCMath.RAD - Math.PI);
            float ySin = BCMath.sin(-yaw * BCMath.RAD - Math.PI);
            float pCos = -BCMath.cos(-pitch * BCMath.RAD);
            float pSin = BCMath.sin(-pitch * BCMath.RAD);

            return new Vec3(ySin * pCos, pSin, yCos * pCos);
        }

        public Vec3 get(Position vector) {
            return get(vector.x(), vector.y(), vector.z());
        }

        public Vec3 get(Entity entity) {
            return get(
                    entity.position().x,
                    entity.position().y,
                    entity.position().z);
        }

        public Vec3 get(Vec3i integerPosition) {
            return get(
                    integerPosition.getX() + 0.5F,
                    integerPosition.getY() + 0.5F,
                    integerPosition.getZ() + 0.5F);
        }
    }
    //*\\*//*\\*//*\\POSITION LOOK HELPER*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//
}
