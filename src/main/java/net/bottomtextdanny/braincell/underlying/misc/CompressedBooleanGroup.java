package net.bottomtextdanny.braincell.underlying.misc;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//n/8+37
public final class CompressedBooleanGroup {
	int size;
	private final byte[] bytes;
	
	private CompressedBooleanGroup(int size) {
		this.size = size;
		this.bytes = new byte[size / 8 + (size % 8 == 0 ? 1 : 2)];
	}
	
	public static CompressedBooleanGroup create(int size) {
		return new CompressedBooleanGroup(size);
	}
	
	public boolean get(int index) {
		int byteIdx = index / 8;
		int bitShift = index % 8;
		int shifted = 1 << bitShift;
		return ((int) this.bytes[byteIdx] & shifted) == shifted;
	}
	
	public void set(int index, boolean value) {
		int byteIdx = index / 8;
		int bitShift = index % 8;
		byte currentByte = this.bytes[byteIdx];
		
		currentByte |= 1 << bitShift;
		if(!value) currentByte ^= 1 << bitShift;
		this.bytes[byteIdx] = currentByte;
	}
	
	public void clear() {
		int length = this.bytes.length;
		for (int i = 0; i < length; i++) {
			this.bytes[i] = 0;
		}
	}

	public List<Boolean> asList() {
		return IntStream.range(0, this.size).mapToObj(this::get).collect(Collectors.toList());
	}
}
