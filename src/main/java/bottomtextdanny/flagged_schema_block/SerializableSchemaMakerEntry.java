package bottomtextdanny.flagged_schema_block;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;

import java.util.List;

public record SerializableSchemaMakerEntry(String name, List<String> properties, IntList flags) {
    static String T_NAME = "n";
    static String T_PROPERTIES = "p";
    static String T_FLAGS = "f";

    public CompoundTag getTag() {
        CompoundTag tagMap = new CompoundTag();
        ListTag propertiesSerial = new ListTag();

        this.properties.forEach(p -> {
            propertiesSerial.add(StringTag.valueOf(p));
        });

        tagMap.putString(T_NAME, name);
        tagMap.put(T_PROPERTIES, propertiesSerial);
        tagMap.putIntArray(T_FLAGS, flags.toIntArray());

        return tagMap;
    }

    public static SerializableSchemaMakerEntry fromCompoundTag(CompoundTag tag) {
        ListTag propertiesSerial = (ListTag) tag.get(T_PROPERTIES);
        List<String> properties = Lists.newArrayListWithExpectedSize(propertiesSerial.size());
        propertiesSerial.forEach(serial -> {
            properties.add(serial.getAsString());
        });
        return new SerializableSchemaMakerEntry(tag.getString(T_NAME), properties, IntList.of(tag.getIntArray(T_FLAGS)));
    }
}
