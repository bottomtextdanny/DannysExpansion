package net.bottomtextdanny.dannys_expansion.core.Util;

import com.mojang.math.Vector3f;
import net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util.PieceUtil;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class DEMath {
    public static final double SQRT_2 = 1.4142135623730951;
    public static final double RAD = Math.PI / 180.0;
    public static final float FRAD = (float)Math.PI / 180.0F;
    public static final float FPI = (float)Math.PI;
    public static final double PI_BY_TWO = Math.PI * 2.0;
    public static final float FPI_BY_TWO = (float)Math.PI * 2.0F;
    public static final double PI_HALF = Math.PI / 2.0;
    public static final float FPI_HALF = (float)Math.PI / 2.0F;
	
	public static double pow(final double val, final double expo) {
		return Math.pow(val, expo);
	}
	
	public static double fastpow(final double val, final double expo) {
		final long tmp = Double.doubleToLongBits(val);
		final long tmp2 = (long)(expo * (double) (tmp - 4606921280493453312L)) + 4606921280493453312L;
		return Double.longBitsToDouble(tmp2);
	}

    public static int intRandomOffset(int base, float offset) {
        return base + (int)(base * new Random().nextGaussian() * offset);
    }

    public static int intRandomOffset(int base, float offset, Random random) {
        return base + (int)(base * random.nextGaussian() * offset);
    }

    public static Vector3f approach(float appr, Vector3f start, Vector3f end) {
        return new Vector3f(
                Mth.lerp(appr, start.x(), end.x()),
                Mth.lerp(appr, start.y(), end.y()),
                Mth.lerp(appr, start.z(), end.z()));
    }

    public static Vec3 advance(Vec3 point, double distance, Vec3 lookTarget) {
        Vec3 look = fromPitchYaw(getTargetPitch(point, lookTarget), getTargetYaw(point.x, point.z, lookTarget.x, lookTarget.z));
        return point.add(look.scale(distance));
    }

    public static boolean intersectsVec(Vec3 point, AABB bb) {
        return point.x > bb.minX && point.x < bb.maxX && point.y > bb.minY && point.y < bb.maxY && point.z > bb.minZ && point.z < bb.maxZ;
    }

    //////////FOR LONGEVITY
    public static float sin(float rad) {
        return Mth.sin(rad);
    }

    public static float cos(float rad) {
        return Mth.cos(rad);
    }

    public static float sin(double rad) {
        return Mth.sin((float)rad);
    }

    public static float cos(double rad) {
        return Mth.cos((float)rad);
    }
    //////////FOR LONGEVITY

    public static float minA0(float a, float b) {
        final int aSign = Mth.sign(a);
        final int bSign = Mth.sign(b);
        a = Math.abs(a);
        b = Math.abs(b);

        return a <= b ? a * aSign : b * bSign;
    }

    public static double minA0(double a, double b) {
        final int aSign = Mth.sign(a);
        final int bSign = Mth.sign(b);
        a = Math.abs(a);
        b = Math.abs(b);

        return a <= b ? a * aSign : b * bSign;
    }

    public static float maxA0(float a, float b) {
        final int aSign = Mth.sign(a);
        final int bSign = Mth.sign(b);
        a = Math.abs(a);
        b = Math.abs(b);

        return a <= b ? b * bSign : a * aSign;
    }

    public static double maxA0(double a, double b) {
        final int aSign = Mth.sign(a);
        final int bSign = Mth.sign(b);
        a = Math.abs(a);
        b = Math.abs(b);

        return a <= b ? b * bSign : a * aSign;
    }

    public static float minA0Bias(float a, float b, boolean aSided) {
        final int sign = aSided ? Mth.sign(a) : Mth.sign(b);
        a = Math.abs(a);
        b = Math.abs(b);

        return a <= b ? a * sign : b * sign;
    }

    public static double minA0Bias(double a, double b, boolean aSided) {
        final int sign = aSided ? Mth.sign(a) : Mth.sign(b);
        a = Math.abs(a);
        b = Math.abs(b);

        return a <= b ? a * sign : b * sign;
    }

    public static float maxA0Bias(float a, float b, boolean aSided) {
        final int sign = aSided ? Mth.sign(a) : Mth.sign(b);
        a = Math.abs(a);
        b = Math.abs(b);

        return a <= b ? b * sign : a * sign;
    }

    public static double maxA0Bias(double a, double b, boolean aSided) {
        final int sign = aSided ? Mth.sign(a) : Mth.sign(b);
        a = Math.abs(a);
        b = Math.abs(b);

        return a <= b ? b * sign : a * sign;
    }

    public static float maxMinSignA(float a, float b) {

        return a > 0 ? a <= b ? b : a : a <= b ? a : b;
    }

    public static double maxMinSignA(double a, double b) {

        return a > 0 ? a <= b ? b : a : a <= b ? a : b;
    }

    public static boolean cylindersCollide(Vec3 cylinder1Pos, float cylinder1Height, float cylinder1Radius, Vec3 cylinder2Pos, float cylinder2Height, float cylinder2Radius) {
        if (cylinder1Pos.y + cylinder1Height / 2 > cylinder2Pos.y  && cylinder1Pos.y - cylinder1Height / 2 < cylinder2Pos.y + cylinder2Height || cylinder2Pos.y + cylinder2Height / 2 > cylinder1Pos.y && cylinder2Pos.y + cylinder2Height / 2 < cylinder1Pos.y + cylinder1Height) {
            return getHorizontalDistance(cylinder1Pos.x, cylinder1Pos.z, cylinder2Pos.x, cylinder2Pos.z) < cylinder1Radius + cylinder2Radius;
        }

        return false;
    }

    public static float loopLerp(float pct, float loopEnd, float start, float end) {
        float difference;

        if (start > end) {
            difference = loopEnd - start;
            difference += end;
        } else {
            difference = end - start;
        }

        return loop(start, loopEnd, pct * difference);
    }

    public static float freeAnimator(float amount, float weight, float prevTick, float tick, Easing easing, float timer) {
        if (timer >= prevTick && timer < tick) {
            float uneasedProg = (timer - prevTick) / (tick - prevTick);
            return easing.progression(uneasedProg) * amount + weight;
        }
        return 0;
    }

    public static float motionHelper(float amount, float weight, int prevTick, int tick, int timer) {
        if (timer >= prevTick && timer < tick) {
            return amount + weight;
        }
        return 0;
    }

    public static float getTargetPitch(Entity entity1, Entity entity2) {
        double d0 = entity2.getX() - entity1.getX();
        double d1 = entity2.getY() + entity2.getBoundingBox().getYsize() / 2 - entity1.getY() + entity1.getBoundingBox().getYsize() / 2;
        double d2 = entity2.getZ() - entity1.getZ();
        double d3 = Mth.sqrt((float) (d0 * d0 + d2 * d2));
        return (float) -(Mth.atan2(d1, d3) * (double)(180F / (float)Math.PI));
    }

    public static float getTargetPitch(Vec3 entity1, Vec3 entity2) {
        double radian = 180D / Math.PI;
        double differenceX = entity2.x - entity1.x;
        double differenceY = entity2.y - entity1.y;
        double differenceZ = entity2.z - entity1.z;
        double distance = Mth.sqrt((float) (differenceX * differenceX + differenceZ * differenceZ));

        return (float)(-Mth.atan2(differenceY, distance) * radian);
    }

    public static float getTargetPitch(Entity entity1, double x, double y, double z) {
        double d0 = x - entity1.getX();
        double d1 = y - entity1.getY();
        double d2 = z - entity1.getZ();
        double d3 = Mth.sqrt((float) (d0 * d0 + d2 * d2));
        return (float) -(Mth.atan2(d1, d3) * (double)(180F / (float)Math.PI));
    }

    public static float getTargetPitch(double x1, double y1, double z1, double x2, double y2, double z2) {
        double d0 = x2 - x1;
        double d1 = y2 - y1;
        double d2 = z2 - z1;
        double d3 = Mth.sqrt((float) (d0 * d0 + d2 * d2));
        return (float) -(Mth.atan2(d1, d3) * (double)(180F / (float)Math.PI));
    }

    public static float getTargetYaw(Entity entity1, Entity entity2) {
        double d0 = entity2.getX() - entity1.getX();
        double d1 = entity2.getZ() - entity1.getZ();
        return (float)(Mth.atan2(d1, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
    }

    public static float getTargetYaw(Entity entity1, double x, double y) {
        double d0 = x - entity1.getX();
        double d1 = y - entity1.getZ();
        return (float)(Mth.atan2(d1, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
    }

    public static float getTargetYaw(double x, double y, double x1, double y1) {
        double d0 = x1 - x;
        double d1 = y1 - y;
        return (float)(Mth.atan2(d1, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
    }

    public static Vec3 fromPitchYaw(float pitch, float yaw) {
        float f = cos(-yaw * ((float)Math.PI / 180F) - (float)Math.PI);
        float f1 = sin(-yaw * ((float)Math.PI / 180F) - (float)Math.PI);
        float f2 = -cos(-pitch * ((float)Math.PI / 180F));
        float f3 = sin(-pitch * ((float)Math.PI / 180F));

        return new Vec3(f1 * f2, f3, f * f2);
    }

    public static float loop(float holder, float end, float offset) {
        float result = holder + offset;
        while (result > end) result -= Mth.abs(end);
        return result;
    }
	
	public static float approachDegrees(float advance, float start, float goal) {
		start = moduloFromBaseRaw(-180.0F, start, 360.0F);
		goal = moduloFromBaseRaw(-180.0F, goal, 360.0F);
		
		float rawRes;
		boolean reached = start > goal;
		boolean goesForward = cicularDistanceIsForward(start, goal, 360.0F);
		
		if (goesForward) {
			if (reached) {
				rawRes = loopExt(start, 360.0F, advance) - 180.0F;
			} else {
				rawRes = loopExt(start, 360.0F, -advance) - 180.0F;
			}
		} else {
			if (reached) {
				rawRes = loopExt(start, 360.0F, -advance) - 180.0F;
			} else {
				rawRes = loopExt(start, 360.0F, advance) - 180.0F;
			}
		}
		
		float transform = rawRes + 180.0F;
		if (goesForward) {
			if (reached) {
				if (!cicularDistanceIsForwardTrue(transform, goal, 360.0F)) {
					rawRes = goal - 180.0F;
				}
			} else {
				if (cicularDistanceIsForwardTrue(transform, goal, 360.0F)) {
					rawRes = goal - 180.0F;
				}
			}
		} else {
			
			if (reached) {
				if (cicularDistanceIsForwardTrue(transform, goal, 360.0F)) {
					rawRes = goal - 180.0F;
				}
			} else {
				if (!cicularDistanceIsForwardTrue(transform, goal, 360.0F)) {
					rawRes = goal - 180.0F;
				}
			}
		}
		
		
		
		
		return rawRes;
	}
	
	public static float approachDegreesFrom(float advance, float start, float goal, float from) {
		start = moduloFromBaseRaw(-180.0F, start, 360.0F);
		goal = moduloFromBaseRaw(-180.0F, goal, 360.0F);
		
		float rawRes;
		boolean reached = start > goal;
		boolean goesForward = cicularDistanceIsForward(from, goal, 360.0F);
		
		if (goesForward) {
			if (reached) {
				rawRes = loopExt(start, 360.0F, advance) - 180.0F;
			} else {
				rawRes = loopExt(start, 360.0F, -advance) - 180.0F;
			}
		} else {
			if (reached) {
				rawRes = loopExt(start, 360.0F, -advance) - 180.0F;
			} else {
				rawRes = loopExt(start, 360.0F, advance) - 180.0F;
			}
		}
		
		float transform = rawRes + 180.0F;
		if (goesForward) {
			
			if (reached) {
				if (!cicularDistanceIsForwardTrue(transform, goal, 360.0F)) {
					rawRes = goal - 180.0F;
				}
			} else {
				if (cicularDistanceIsForwardTrue(transform, goal, 360.0F)) {
					rawRes = goal - 180.0F;
				}
			}
		} else {
			
			if (reached) {
				if (cicularDistanceIsForwardTrue(transform, goal, 360.0F)) {
					rawRes = goal - 180.0F;
				}
			} else {
				if (!cicularDistanceIsForwardTrue(transform, goal, 360.0F)) {
					rawRes = goal - 180.0F;
				}
			}
		}
		
		
		
		
		return rawRes;
	}
	
	public static float clampDegrees(float value, float clampRoot, float clamp) {
		float point0 = value;
		float point1 = clampRoot;
		float forwardDistance = point1 - point0;
		
		while (forwardDistance < 0.0F) forwardDistance += 360.0F;
		
		forwardDistance %= 360.0F;
		
		float backwardDistance = 360.0F - forwardDistance;
		
		float minDistanceToRoot = Math.min(forwardDistance, backwardDistance);
		
		if (minDistanceToRoot > clamp) {
			if (forwardDistance < backwardDistance) {
				return wrapDegrees(clampRoot - clamp);
			} else {
				return wrapDegrees(clampRoot + clamp);
			}
		}
		
		return value;
	}
	
	public static float wrapDegrees(float value) {
		float f = value % 360.0F;
		if (f >= 180.0F) {
			f -= 360.0F;
		}
		
		if (f < -180.0F) {
			f += 360.0F;
		}
		
		return f;
	}
	
	public static float loopExt(float holder, float end, float offset) {
		float result = holder + offset;
		
		if (result < 0) {
			while (result < 0) result += Math.abs(end);
		} else {
			while (result > end) result -= Math.abs(end);
		}
		
		return result;
	}
	
	public static boolean cicularDistanceIsForward(float point0, float point1, float end) {
		float forwardDistance = end - point1 + point0;
		
		if (forwardDistance > 360.0F) {
			forwardDistance = 720.0F - forwardDistance;
		}
		float backwardDistance = Math.abs(point1 - point0);
		//print("backwards: " + backwardDistance +);
		return forwardDistance < backwardDistance;
	}
	
	public static boolean cicularDistanceIsForwardTrue(float point0, float point1, float end) {
		float forwardDistance = point1 - point0;
		
		while (forwardDistance < 0.0F) forwardDistance += end;
		
		forwardDistance %= end;
		
		float backwardDistance = end - forwardDistance;
		
		return forwardDistance < backwardDistance;
	}
	
	public static float moduloFromBase(float start, float actual, float cut) {
		return (actual - start) % cut + start;
	}
	
	public static float moduloFromBaseRaw(float start, float actual, float cut) {
		return (actual - start) % cut;
	}

    public static int verticalNonSolid(LevelAccessor worldIn, BlockPos pos, int sum, int max) {
        int i;
        int j = 0;
        for(i = 0; i < Math.abs(max) && !PieceUtil.fullCollision(worldIn, pos.offset(0F, j + sum, 0F)); ++i) {
            j += sum;
        }
        return Math.abs(j);
    }

    public static float getLerp(double start, double end, double actual) {
        actual = Mth.clamp(actual, Math.min(start, end), Math.max(start, end));
        actual -= start;
        end -= start;

        if (start <= 0) return (float) 0;

        return (float) (actual / end);
    }

    public static Vec3 getVecLerp(double prog, Vec3 start, Vec3 end) {
        double easedX = (end.x - start.x) * prog + start.x;
        double easedY = (end.y - start.y) * prog + start.y;
        double easedZ = (end.z - start.z) * prog + start.z;

        return new Vec3(easedX, easedY, easedZ);
    }

    public static float getDistance(Entity entityIn, double x, double y, double z) {
        float f = (float)(entityIn.getX() - x);
        float f1 = (float)(entityIn.getY() - y);
        float f2 = (float)(entityIn.getZ() - z);
        return Mth.sqrt(f * f + f1 * f1 + f2 * f2);
    }

    public static float getDistance(double x1, double y1, double z1, double x2, double y2, double z2) {
        float f = (float) (x1 - x2);
        float f1 = (float) (y1 - y2);
        float f2 = (float) (z1 - z2);
        return Mth.sqrt(f * f + f1 * f1 + f2 * f2);
    }

    public static float getDistance(Vec3 vec0, Vec3 vec1) {
        float f = (float) (vec0.x() - vec1.x());
        float f1 = (float) (vec0.y() - vec1.y());
        float f2 = (float) (vec0.z() - vec1.z());
        return Mth.sqrt(f * f + f1 * f1 + f2 * f2);
    }

    public static float getHorizontalDistance(Entity entity1, Entity entity2) {
        double d0 = entity1.getX() - entity2.getX();
        double d1 = entity1.getZ() - entity2.getZ();
        return (float)Math.sqrt(d0 * d0 + d1 * d1);
    }

    public static float getHorizontalDistance(Entity entity, double x2, double z2) {
        double d0 = entity.getX() - x2;
        double d1 = entity.getZ() - z2;
        return (float)Math.sqrt(d0 * d0 + d1 * d1);
    }

    public static float getHorizontalDistance(double x1, double z1, double x2, double z2) {
        double d0 = x1 - x2;
        double d1 = z1 - z2;
        return (float)Math.sqrt(d0 * d0 + d1 * d1);
    }

    public static double manhattan(double x0, double y0, double z0, double x1, double y1, double z1) {
        double d0 = x0 - x1;
        double d1 = y0 - y1;
        double d2 = z0 - z1;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public static double manhattan(Vec3 vec0, Vec3 vec1) {
        double d0 = vec0.x - vec1.x;
        double d1 = vec0.y - vec1.y;
        double d2 = vec0.z - vec1.z;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }
}
