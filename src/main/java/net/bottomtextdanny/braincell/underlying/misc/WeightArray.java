package net.bottomtextdanny.braincell.underlying.misc;

import net.bottomtextdanny.braincell.underlying.util.pair.IntPair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>Instances of this class hold an array of size {@link #weightArraySizeSize},
 * this array contains n entries of each element given in the constructor,
 * n being the weight of its corresponding element.</p>
 *
 * <p>Element positions are in insertion order.</p>
 *
 * <p>Create an instance with the {@link Builder} inner class.</p>
 *
 * @param <T> the type of elements to be fetched.
 */
public final class WeightArray<T> {
	public final int itemSize, weightArraySizeSize;
	private final T[] elementArray;

	@SuppressWarnings("unchecked")
	private WeightArray(Class<T> clazz, Collection<IntPair<T>> items, int totalWeight) {
		this.itemSize = items.size();
		this.weightArraySizeSize = totalWeight;
		this.elementArray = (T[])Array.newInstance(clazz, totalWeight);
		int[] majorPointer = {0};
		items.forEach(pair -> {
			int weightToGo = pair.left();

			while (weightToGo > 0) {
				this.elementArray[Math.max(weightToGo + majorPointer[0] - 1, 0)] = pair.right();
				weightToGo--;
			}

			majorPointer[0] += pair.left();
		});
	}

	/**
	 * @param position the value that will be clamped and then be the position where the element will be gotten from {@link #elementArray}.
	 * @return the element that is found at the given clamped position.
	 */
	public T mapClamped(int position) {
		return map(Math.max(Math.min(position, this.weightArraySizeSize), 0));
	}

	/**
	 * @param positionInbound the position where the element will be gotten from {@link #elementArray}.
	 * @return the element that is found at the given position.
	 *
	 * @throws IndexOutOfBoundsException if the given index is out of range
	 * ({@code index < 0 || index >= {@link #weightArraySizeSize}})
	 */
	public T map(int positionInbound) {
		return this.elementArray[positionInbound];
	}

	/**
	 * <p>A builder for the creation of {@code WeightArray} instances.</p>
	 * <p>Example:</p>
	 * <pre>{@code
	 * static final WeightArray<String> WEIGHTED_NAMES =
	 *     WeightArray.Builder.create(String.class)
	 *         .add(4, "Alicia")
	 *         .add(6, "Alfred")
	 *         .add(1, "Theresa")
	 *         .build();
	 * }</pre>
	 */
	public static class Builder<T> {
		private final  ArrayList<IntPair<T>> weightedCollection = new ArrayList<>(0);
		private int total;
		private final Class<T> itemClazz;
		
		private Builder(Class<T> clazz) {
			super();
			this.itemClazz = clazz;
		}

		/**
		 * Creates and returns a builder. This method is equivalent to an explicit {@link Builder} object creation.
		 */
		public static <T> Builder<T> create(Class<T> clazz) {
			return new Builder<T>(clazz);
		}

		/**
		 * Associates {@code weight} with {@code element} in the built array.
		 */
		public Builder<T> add(int weight, T element) {
			this.weightedCollection.add(IntPair.of(weight, element));
			this.total += weight;
			return this;
		}
		
		public WeightArray<T> build() {
			return new WeightArray<>(this.itemClazz, this.weightedCollection, this.total);
		}
	}
}
