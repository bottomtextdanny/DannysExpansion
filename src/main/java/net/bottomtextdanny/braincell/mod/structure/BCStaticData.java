package net.bottomtextdanny.braincell.mod.structure;

import net.bottomtextdanny.braincell.mod.structure.client_sided.BCClientToken;

public final class BCStaticData {
    public static final IllegalArgumentException INVALID_TOKEN_EX =
            new IllegalArgumentException("Tried to modify image with invalid token");
    private static float partialTick;

    public static float partialTick() {
        return partialTick;
    }

    public static void setPartialTick(float value, BCClientToken token) {
        if (token == null) throw INVALID_TOKEN_EX;
        partialTick = value;
    }
}
