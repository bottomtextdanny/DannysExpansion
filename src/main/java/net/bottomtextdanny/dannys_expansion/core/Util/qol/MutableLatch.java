package net.bottomtextdanny.dannys_expansion.core.Util.qol;

import java.util.function.Supplier;

public class MutableLatch<T> implements Supplier<T> {
	private boolean locked;
	private T object;
	
	private MutableLatch() {}
	
	public static <OBJ> MutableLatch<OBJ> empty() {
		return new MutableLatch<OBJ>();
	}
	
	public void setLocked(T object) {
		if (!this.locked) {
            this.locked = true;
			this.object = object;
		}
	}
	
	@Override
	public T get() {
		return this.object;
	}
}
