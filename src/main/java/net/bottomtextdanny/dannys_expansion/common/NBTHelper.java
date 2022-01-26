package net.bottomtextdanny.dannys_expansion.common;

import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.ListTag;

public class NBTHelper {

    public static ListTag newDoubleList(double... doubleArray) {
        ListTag listtag = new ListTag();

        for(double val : doubleArray) {
            listtag.add(DoubleTag.valueOf(val));
        }

        return listtag;
    }

    public static ListTag newFloatList(float... floatArray) {
        ListTag listtag = new ListTag();

        for(float val : floatArray) {
            listtag.add(FloatTag.valueOf(val));
        }

        return listtag;
    }
}
