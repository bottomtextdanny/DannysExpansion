package net.bottomtextdanny.braincell.mod.structure;

import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.function.Supplier;

public enum SideConfig {
    BOTH(() -> true),
    CLIENT(FMLEnvironment.dist::isClient),
    DEDICATED_SERVER(FMLEnvironment.dist::isDedicatedServer);

    private final Supplier<Boolean> test;

    SideConfig(Supplier<Boolean> test) {
        this.test = test;
    }

    public boolean test() {
        return this.test.get();
    }
}
