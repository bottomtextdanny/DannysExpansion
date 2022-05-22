package bottomtextdanny.dannys_expansion._mixin.client;

import com.google.common.collect.ImmutableList;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.resources.language.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;
import java.util.function.Supplier;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    private static final String LOADINGDOTDOTDOT = "Loading...";
    private static final int DE_TEXT_ARR_SIZE;
    private static final ImmutableList<Supplier<String>> DE_FUNI_TEXT;
    private static final int DE_TEXT_ID;
    static {
        ImmutableList.Builder<Supplier<String>> builder = new ImmutableList.Builder<>();
//        builder.add(() -> "glumbis is awesome");
//        builder.add(() -> "honestly quite incredible");
//        builder.add(() -> "f(x) = (1 / (1 + e^-x)) my beloved");
//        builder.add(() -> "yosh is awesome");
        builder.add(() -> ":pleading_face:");
//        builder.add(() -> "we do an interesting amount of buffoonery");
//        builder.add(() -> "just a substantial deficit in skill");
//        builder.add(() -> "im uninstalling your trash mod");
//        builder.add(() -> "do your homework first!");
        DE_FUNI_TEXT = builder.build();
        DE_TEXT_ARR_SIZE = DE_FUNI_TEXT.size();
        DE_TEXT_ID = new Random().nextInt(DE_TEXT_ARR_SIZE);
    }

    @Inject(at = @At(value = "HEAD"), method = "createTitle", remap = true, cancellable = true)
    public void de_getWindowTitle(CallbackInfoReturnable<String> cir) {
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
        de_stringbuilder.append(DE_FUNI_TEXT.get(DE_TEXT_ID).get());
        cir.setReturnValue(de_stringbuilder.toString());
    }
}
