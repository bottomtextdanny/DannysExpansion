package net.bottomtextdanny.danny_expannny;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class ClientInstance {
	private static int chatDelayHandler;

    public static LocalPlayer player() {
        return Minecraft.getInstance().player;
    }

    public static void chatMsg(Object str) {
        if (player() != null) {
            player().displayClientMessage(new TextComponent(str.toString()), false);
        }
        
    }
	
	public static void chatMsg(Object str, int skips) {
		if (player() != null) {
    	    if (chatDelayHandler <= 0) {
                player().displayClientMessage(new TextComponent(str.toString()), false);
		        chatDelayHandler = skips;
	        } else {
    	    	if (chatDelayHandler > skips) chatDelayHandler = skips;
    	    	chatDelayHandler--;
	        }
		}
	}

    public static Options gs() {
        return Minecraft.getInstance().options;
    }
}
