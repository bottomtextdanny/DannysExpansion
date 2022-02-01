package net.bottomtextdanny.de_json_generator;

import net.bottomtextdanny.braincell.underlying.misc.CompressedBooleanGroup;

import java.util.ArrayList;
import java.util.LinkedList;

public class DeleteTrash {
	private static int count;
	public static final int CHUNK_X_SHIFT = 21;
	public static final int CHUNK_SERIAL_SHIFT = 4;
	public static final int CHUNK_DISTRIBUTION = 24;
	public static final long CHUNK_Z_OFFSET = 2097152L;

	public static void main(String[] args) throws InterruptedException {
//		Vec3 view = new Vec3(0, 1, 12);
//		Vec3 start = new Vec3(3, 0, 6);
//		Vec3 end = new Vec3(6, 9, 14);
//		Vec3 inter = DEMath.getVecLerp(0.5, start, end);
//		double dist1 = (view.distanceToSqr(end) + view.distanceToSqr(start)) / 2.0F;
//		double dist2 = view.distanceToSqr(inter);
		CompressedBooleanGroup byteBooleanGroup = CompressedBooleanGroup.create(40000);

		Object obj = new Object();
		ArrayList<Object> array = new ArrayList<>();
		LinkedList<Object> linked = new LinkedList<>();
		long arraylist = 0L;
		long linkedlist = 0L;

		for (int current = 0; current < 20; current++) {
			long t0 = System.nanoTime();

			for (int i = 0; i < 40000; i++) {
				array.add(obj);
				array.add(obj);
				array.remove(0);
			}

			long t1 = System.nanoTime();

			for (int i = 0; i < 5000; i++) {
				linked.add(obj);
				linked.add(obj);
				linked.remove(0);
			}

			long t2 = System.nanoTime();

			System.out.printf("serial: %.2fs, parallel %.2fs%n", (t1 - t0) * 1e-9, (t2 - t1) * 1e-9);

			if (current > 10) {
				arraylist += (t1 - t0);
				linkedlist += (t2 - t1);
			}
		}
		System.out.printf("total>>serial: %.2fs, parallel %.2fs", arraylist * 1e-9, linkedlist * 1e-9);
		System.out.println();

		//print(computeChunkSectionSerial(1300000L, 1300000L, 12));
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
