package net.bottomtextdanny.dannys_expansion.mixin.client;

import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.dannys_expansion.core.Util.MixinMethods;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.resources.language.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	

    @Inject(at = @At(value = "TAIL"), method = "resizeDisplay", remap = false)
    public void de_updateWindowSize(CallbackInfo ci) {
		Braincell.client().getRenderingHandler().captureWindowModification();
    }
	
	@Inject(at = @At(value = "HEAD"), method = "createTitle", remap = false, cancellable = true)
	public void de_getWindowTitle(CallbackInfoReturnable<String> cir) {
    	if (true) {
		    StringBuilder de_stringbuilder = new StringBuilder("Minecraft");
		    if (Minecraft.checkModStatus().shouldReportAsModified()) {
			    de_stringbuilder.append("*");
		    }

		    Minecraft mc = Minecraft.getInstance();
		    de_stringbuilder.append(" ");
		    de_stringbuilder.append(SharedConstants.getCurrentVersion().getName());
			ClientPacketListener clientpacketlistener = mc.getConnection();
			if (clientpacketlistener != null && clientpacketlistener.getConnection().isConnected()) {
				de_stringbuilder.append(" - ");
				if (mc.getSingleplayerServer() != null && !mc.getSingleplayerServer().isPublished()) {
					de_stringbuilder.append(I18n.get("title.singleplayer"));
				} else if (mc.isConnectedToRealms()) {
					de_stringbuilder.append(I18n.get("title.multiplayer.realms"));
				} else if (mc.getSingleplayerServer() == null && (mc.getCurrentServer() == null || !mc.getCurrentServer().isLan())) {
					de_stringbuilder.append(I18n.get("title.multiplayer.other"));
				} else {
					de_stringbuilder.append(I18n.get("title.multiplayer.lan"));
				}
			}

		    de_stringbuilder.append(": ");
		    de_stringbuilder.append(MixinMethods.DE_FUNI_TEXT.get(MixinMethods.DE_TEXT_ID).get());
		    cir.setReturnValue(de_stringbuilder.toString());
	    }
	}
}
