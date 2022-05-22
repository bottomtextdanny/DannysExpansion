package bottomtextdanny.dannys_expansion.content.block_properties;

import net.minecraft.util.StringRepresentable;

public enum WorkbenchPart implements StringRepresentable {
    MAIN("main"),
    OFF("off");

    private final String name;

    WorkbenchPart(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getSerializedName() {
        return this.name;
    }
}
