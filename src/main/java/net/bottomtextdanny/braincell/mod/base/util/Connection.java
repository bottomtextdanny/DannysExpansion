package net.bottomtextdanny.braincell.mod.base.util;

import net.minecraftforge.fml.loading.FMLLoader;

import java.util.function.Supplier;

public final class Connection {

    public static <T> T makeServerSide(Supplier<T> objectSupplier) {
        if (FMLLoader.getDist().isDedicatedServer()) {
            return objectSupplier.get();
        } else {
            return null;
        }
    }

    public static Object makeServerSideUnknown(Supplier<?> objectSupplier) {
        if (FMLLoader.getDist().isDedicatedServer()) {
            return objectSupplier.get();
        } else {
            return null;
        }
    }

    public static <T> T makeServerSideOrElse(Supplier<? extends T> objectSupplier, Supplier<? extends T> fallbackSupplier) {
        if (FMLLoader.getDist().isDedicatedServer()) {
            return objectSupplier.get();
        } else {
            return fallbackSupplier.get();
        }
    }

    public static void doServerSide(Runnable action) {
        if (FMLLoader.getDist().isDedicatedServer()) {
            action.run();
        }
    }

    public static <T> T makeClientSide(Supplier<T> objectSupplier) {
        if (FMLLoader.getDist().isClient()) {
            return objectSupplier.get();
        } else {
            return null;
        }
    }

    public static Object makeClientSideUnknown(Supplier<?> objectSupplier) {
        if (FMLLoader.getDist().isClient()) {
            return objectSupplier.get();
        } else {
            return null;
        }
    }

    public static <T> T makeClientSideOrElse(Supplier<? extends T> objectSupplier, Supplier<? extends T> fallbackSupplier) {
        if (FMLLoader.getDist().isClient()) {
            return objectSupplier.get();
        } else {
            return fallbackSupplier.get();
        }
    }

    public static void doClientSide(Runnable action) {
        if (FMLLoader.getDist().isClient()) {
            action.run();
        }
    }
}
