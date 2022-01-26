package net.bottomtextdanny.braincell.mod.screen;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class BCScreenUtil {

    public static boolean cursorOn(double x, double y, double width, double height) {
        double mouzX = Minecraft.getInstance().mouseHandler.xpos();
        double mouzY = Minecraft.getInstance().mouseHandler.ypos();
        return mouzX > x && mouzX <= x + width && mouzY > y && mouzY <= y + height;
    }

    public static int cursorX() {
        return (int) Minecraft.getInstance().mouseHandler.xpos();
    }

    public static int cursorY() {
        return (int) Minecraft.getInstance().mouseHandler.xpos();
    }
}
