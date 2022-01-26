package net.bottomtextdanny.braincell.mod.opengl_helpers.enums;

import java.util.function.IntSupplier;

public enum ShaderType implements IntSupplier {
    FRAGMENT(0x8B30), VERTEX(0x8B31), GEOMETRY(0x8DD9), COMPUTATION(0x91B9);

    int glInt;

    ShaderType(int glInt) {
        this.glInt = glInt;
    }

    @Override
    public int getAsInt() {
        return this.glInt;
    }

    public boolean compare(int other) {
        return this.glInt == other;
    }
}
