package net.bottomtextdanny.braincell.mod.structure.client_sided;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.InputConstants;
import io.netty.util.collection.IntObjectHashMap;
import net.bottomtextdanny.braincell.mod.structure.client_sided.BCClientToken;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWDropCallback;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@OnlyIn(Dist.CLIENT)
public final class BCInputHelper {
    private static final BCClientToken TOKEN = new BCClientToken();

    public static boolean isShiftDown() {
        return InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), InputConstants.KEY_LSHIFT) ||
                InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), InputConstants.KEY_RSHIFT);
    }

    public static boolean isCTRLDown() {
        return InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), InputConstants.KEY_LCONTROL) ||
                InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), InputConstants.KEY_RCONTROL);
    }

    public static boolean isAltDown() {
        return InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), InputConstants.KEY_LALT) ||
                InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), InputConstants.KEY_RALT);
    }
}
