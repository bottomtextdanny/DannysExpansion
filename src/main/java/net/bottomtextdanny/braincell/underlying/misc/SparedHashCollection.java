package net.bottomtextdanny.braincell.underlying.misc;

import com.google.common.collect.Lists;

import javax.annotation.Nullable;
import java.util.*;

public final class SparedHashCollection<K, V> extends AbstractCollection<K> {
	private final Map<K, V> map;
	private final Queue<K> queue;
	private final int threshold;
	
	public SparedHashCollection(int threshold) {
		super();
		this.threshold = threshold;
		this.map = new HashMap<>(threshold + 1);
		this.queue = Lists.newLinkedList();
	}
	
	public void insert(K key, V value) {
		this.map.remove(key);
		if (this.queue.size() >= this.threshold) {
			this.map.remove(this.queue.element());
			this.queue.remove();
		}
		this.map.put(key, value);
		this.queue.add(key);
	}
	
	@Nullable
	public V look(K key) {
		return this.map.getOrDefault(key, null);
	}

	@Override
	public Iterator<K> iterator() {
		return this.queue.iterator();
	}

	@Override
	public int size() {
		return this.queue.size();
	}
}
