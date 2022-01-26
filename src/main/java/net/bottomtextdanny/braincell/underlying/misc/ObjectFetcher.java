package net.bottomtextdanny.braincell.underlying.misc;

public class ObjectFetcher {
	private final Object[] objects;
	
	private ObjectFetcher(Object[] objs) {
		super();
		this.objects = objs;
	}
	
	public static ObjectFetcher of(Object[] objs) {
		return new ObjectFetcher(objs);
	}
	
	@SuppressWarnings("unchecked")
	public <A> A get(int index) {
		return (A) this.objects[index];
	}

	public <A> A get(int index, Class<A> clazz) {
		return (A) this.objects[index];
	}
}
