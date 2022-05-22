package bottomtextdanny.dannys_expansion._base.keybinding;

import bottomtextdanny.dannys_expansion._util._client.CMC;
import bottomtextdanny.dannys_expansion._base.network.clienttoserver.MSGCheckJump;
import bottomtextdanny.dannys_expansion._base.network.clienttoserver.MSGSShoot;
import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerHelper;
import bottomtextdanny.dannys_expansion.content.items.gun.GunItem;
import com.google.common.collect.Lists;
import bottomtextdanny.braincell.mod.capability.player.BCAccessoryModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;

import java.util.List;

public final class KeybindHandler {
    private List<KeybindWrapper> trackedKeybinds = Lists.newLinkedList();
    public final KeybindWrapper
            keyBindForward = registerModKeybind(new KeybindWrapper(Minecraft.getInstance().options.keyUp)),
            keyBindBack = registerModKeybind(new KeybindWrapper(Minecraft.getInstance().options.keyDown)),
            keyBindRight = registerModKeybind(new KeybindWrapper(Minecraft.getInstance().options.keyRight)),
            keyBindLeft = registerModKeybind(new KeybindWrapper(Minecraft.getInstance().options.keyLeft)),
            keyBindJump = registerModKeybind(new KeybindWrapper(Minecraft.getInstance().options.keyJump)),
	        keyBindSwitch = registerModKeybind(new KeybindWrapper(Minecraft.getInstance().options.keySwapOffhand)),
            keyBindAttack = registerModKeybind(new KeybindWrapper(Minecraft.getInstance().options.keyAttack)),
            keyBindPlace = registerModKeybind(new KeybindWrapper(Minecraft.getInstance().options.keyUse));
    private boolean shouldJump;

    public KeybindHandler() {
        super();
        MinecraftForge.EVENT_BUS.addListener(this::tick);
    }

    private KeybindWrapper registerModKeybind(KeybindWrapper keybind) {
        this.trackedKeybinds.add(keybind);
        return keybind;
    }

    public void tick(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getInstance();
        Options settings = mc.options;
        LocalPlayer player = mc.player;

        if (player != null && player.isAlive() && mc.isWindowActive()) {
            BCAccessoryModule accessoryModule = PlayerHelper.braincellAccessoryModule(player);

            if (event.phase == TickEvent.Phase.START) {
                trackedKeybinds.forEach(KeybindWrapper::tick);

                if (!player.isOnGround() && !player.isPassenger() && keyBindJump.onPressAction() ) {
                    shouldJump = true;
                }
            } else if (event.phase.equals(TickEvent.Phase.END)) {
                if (shouldJump && CMC.player().input.jumping) {
                    new MSGCheckJump(player.getId()).sendToServer();
                }
                shouldJump = false;

                accessoryModule.handleClientKeybinds(settings);

                for (InteractionHand handSide : InteractionHand.values()) {
                    if (player.getItemInHand(handSide).getItem() instanceof GunItem<?>) {
                        if (keyBindAttack.onPressAction()) {
                            new MSGSShoot(handSide.ordinal()).sendToServer();
                            break;
                        }
                    }
                }
            }
        }
    }
}
