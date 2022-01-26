package net.bottomtextdanny.dannys_expansion.core.Util;

import net.minecraft.core.BlockPos;

public class Pair<K, V> {
    private final K key;
    private final V value;

    public Pair(K key,V value) {
        this.key = key;
        this.value = value;
    }

    public static <K1, V1> Pair<K1, V1> of(K1 key, V1 val) {
        return new Pair<>(key, val);
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }

    public static Pair<BlockPos, Byte> bp(BlockPos bp, int val) {
        return Pair.of(bp, (byte)val);
    }

    public static Pair<BlockPos, Byte> bp(BlockPos bp) {
        return Pair.of(bp, (byte)0);
    }
}
