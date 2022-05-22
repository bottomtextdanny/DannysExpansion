package bottomtextdanny.de_json_generator;

import com.mojang.math.Vector3f;
import bottomtextdanny.braincell.base.vector.Vector2f;

import java.util.Locale;

import static bottomtextdanny.braincell.base.BCMath.FRAD;
import static bottomtextdanny.braincell.base.BCMath.FRAD2DEG;

public class DeleteTrash {
	private static int count;
	public static final int CHUNK_X_SHIFT = 21;
	public static final int CHUNK_SERIAL_SHIFT = 4;
	public static final int CHUNK_DISTRIBUTION = 24;
	public static final long CHUNK_Z_OFFSET = 2097152L;

	public static void main(String[] args) throws InterruptedException {

		print(2 % 2);
	}

	private static double distanceFromRectangle(Vector3f point, Vector3f center, Vector3f size) {

		// First, transform the point into the rectangle's local coordinate space.


		// Take the absolute value of this offset, on x and y, and subtract the size.
		// This gives zero on the edge, increasing positively as we move away.
		// Clamp out any values less than zero.
		Vector3f outside = new Vector3f((float)Math.max(Math.abs(point.x() - center.x()) - size.x(), 0.0),
				(float)Math.max(Math.abs(point.y() - center.y()) - size.y(), 0.0),
				(float)Math.max(Math.abs(point.z() - center.z()) - size.z(), 0.0));

		// Return the length of this vector, which is our distance to the closest point
		// on the square's edges or corners, and zero inside the square.
		return Math.sqrt(outside.x() * outside.x() + outside.y() * outside.y() + outside.z() * outside.z());
	}

	public static double Q_rsqrt(double number){
		double x = number;
		double xhalf = 0.5d*x;
		long i = Double.doubleToLongBits(x);
		i = 0x5fe6ec85e7de30daL - (i>>1);
		x = Double.longBitsToDouble(i);
		for(int it = 0; it < 4; it++){
			x = x*(1.5d - xhalf*x*x);
		}
		x *= number;
		return x;
	}

	public static double even(double val, double evenTo) {
		double dif = evenTo - val;
		double nor = dif / evenTo;
		return (1 / (Math.pow(nor, nor) + 1)) * dif + val;
	}

	public static float radianAngleDiff(float a, float b) {
		float plainDif = FRAD2DEG * (a - b);
		float dif = (float)Math.abs(plainDif) % 360;
		if (dif > 180) dif = 360 - dif;
		return FRAD * dif * ((plainDif >= 0 && plainDif <= 180) || (plainDif <= -180 && plainDif >= -360) ? 1 : -1);
	}

	public static void smth() {
		count++;

	}

	public static long computeChunkSectionSerial(long x, long z, int section) {
		return ((x << CHUNK_X_SHIFT) + z + CHUNK_Z_OFFSET << CHUNK_SERIAL_SHIFT) + section;
	}


	public static float approachDegrees(float advance, float start, float goal) {
		start = moduloFromBaseRaw(-180.0F, start, 360.0F);
		goal = moduloFromBaseRaw(-180.0F, goal, 360.0F);
		
		float rawRes;
		boolean reachedFirst = start > goal;
		boolean goesForward = cicularDistanceIsForward(start, goal, 360);
		
		if (goesForward) {
			if (reachedFirst) {
				rawRes = loopExt(start, 360.0F, advance) - 180.0F;
			} else {
				rawRes = loopExt(start, 360.0F, -advance) - 180.0F;
			}
		} else {
			if (reachedFirst) {
				rawRes = loopExt(start, 360.0F, -advance) - 180.0F;
			} else {
				rawRes = loopExt(start, 360.0F, advance) - 180.0F;
			}
		}
		
		
		print((start - 180.0F) + " -> " + rawRes + " -> " + (goal - 180.0F));
		
		float transform = rawRes + 180.0F;
		if (goesForward) {
		
			print("output1");
			
			if (reachedFirst) {
				if (!cicularDistanceIsForwardTrue(transform, goal, 360.0F)) {
					rawRes = goal - 180.0F;
					print("CLAMPED");
				}
			} else {
				if (cicularDistanceIsForwardTrue(transform, goal, 360.0F)) {
					rawRes = goal - 180.0F;
					print("CLAMPED");
				}
			}
		} else {
			print("output2");
			
			if (reachedFirst) {
				if (cicularDistanceIsForwardTrue(transform, goal, 360.0F)) {
					rawRes = goal - 180.0F;
					print("CLAMPED");
				}
			} else {
				if (!cicularDistanceIsForwardTrue(transform, goal, 360.0F)) {
					rawRes = goal - 180.0F;
					print("CLAMPED");
				}
			}
		}
		
		
		
		
		return rawRes;
	}
	
	public static float clampDegrees(float value, float clampRoot, float clamp) {
		float point0 = value + 180.0F;
		float point1 = clampRoot + 180.0F;
		float forwardDistance = point1 - point0;
		
		while (forwardDistance < 0.0F) forwardDistance += 360.0F;
		
		forwardDistance %= 360.0F;
		
		float backwardDistance = 360.0F - forwardDistance;
		
		print("backward: " + backwardDistance + " | forward: " + forwardDistance);
		
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
		
		
		print("backward: " + backwardDistance + " | forward: " + forwardDistance);
		return forwardDistance < backwardDistance;
	}
	
	public static float maxCicularDistance(float point0, float point1) {
		point0 += 180.0F;
		point1 += 180.0F;
		float forwardDistance = point1 - point0;
		
		while (forwardDistance < 0.0F) forwardDistance += 360.0F;
		
		forwardDistance %= 360.0F;
		
		float backwardDistance = 360.0F - forwardDistance;
		
		
		print("backward: " + backwardDistance + " | forward: " + forwardDistance);
		return Math.max(forwardDistance, backwardDistance);
	}
	
	public static float minCicularDistance(float point0, float point1) {
		point0 += 180.0F;
		point1 += 180.0F;
		float forwardDistance = point1 - point0;
		
		while (forwardDistance < 0.0F) forwardDistance += 360.0F;
		
		forwardDistance %= 360.0F;
		
		float backwardDistance = 360.0F - forwardDistance;
		
		
		print("backward: " + backwardDistance + " | forward: " + forwardDistance);
		return Math.min(forwardDistance, backwardDistance);
	}
	
	public static float moduloFromBase(float start, float actual, float cut) {
		return (actual - start) % cut + start;
	}
	
	public static float moduloFromBaseRaw(float start, float actual, float cut) {
		return (actual - start) % cut;
	}
	
	public static void print(Object obj) {
		System.out.println(obj);
	}
	
}
