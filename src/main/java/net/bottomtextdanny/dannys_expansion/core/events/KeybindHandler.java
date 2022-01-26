package net.bottomtextdanny.dannys_expansion.core.events;

import com.google.common.collect.Lists;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.network.clienttoserver.MSGSShoot;
import net.bottomtextdanny.danny_expannny.object_tables.items.DEItems;
import net.bottomtextdanny.danny_expannny.ClientInstance;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.MountEntity;
import net.bottomtextdanny.danny_expannny.objects.items.gun.GunItem;
import net.bottomtextdanny.dannys_expansion.core.Packets.DENetwork;
import net.bottomtextdanny.danny_expannny.network.clienttoserver.MSGCheckJump;
import net.bottomtextdanny.danny_expannny.network.clienttoserver.MSGMountActions;
import net.bottomtextdanny.dannys_expansion.core.Registries.DannyKeybinds;
import net.bottomtextdanny.dannys_expansion.core.Util.KeybindWrapper;
import net.bottomtextdanny.danny_expannny.capabilities.CapabilityHelper;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerAccessoryModule;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = DannysExpansion.ID, value = Dist.CLIENT)
public class KeybindHandler {
    public static boolean holdableUsed;

    private static final List<KeybindWrapper> modKeybinds = Lists.newArrayList();
    public static KeybindWrapper
            keyBindForward = registerModKeybind(new KeybindWrapper(Minecraft.getInstance().options.keyUp)),
            keyBindBack = registerModKeybind(new KeybindWrapper(Minecraft.getInstance().options.keyDown)),
            keyBindRight = registerModKeybind(new KeybindWrapper(Minecraft.getInstance().options.keyRight)),
            keyBindLeft = registerModKeybind(new KeybindWrapper(Minecraft.getInstance().options.keyLeft)),
            keyBindJump = registerModKeybind(new KeybindWrapper(Minecraft.getInstance().options.keyJump)),
	        keyBindSwitch = registerModKeybind(new KeybindWrapper(Minecraft.getInstance().options.keySwapOffhand)),
            keyBindAbility = registerModKeybind(new KeybindWrapper(DannyKeybinds.MOUNT_ABILITY)),
            keyBindAttack = registerModKeybind(new KeybindWrapper(Minecraft.getInstance().options.keyAttack)),
            keyBindPlace = registerModKeybind(new KeybindWrapper(Minecraft.getInstance().options.keyUse));


    private static KeybindWrapper registerModKeybind(KeybindWrapper keybind) {
        modKeybinds.add(keybind);
        return keybind;
    }
     static boolean shouldJump;
    @SubscribeEvent
    public static void tickHandler(TickEvent.ClientTickEvent event) {
        Options settings = Minecraft.getInstance().options;
        LocalPlayer player = Minecraft.getInstance().player;

        if (player != null && player.isAlive() && Minecraft.getInstance().isWindowActive()) {
            PlayerCapability capability = CapabilityHelper.get(player, PlayerCapability.CAPABILITY);
            PlayerAccessoryModule accessoryModule = capability.accessoryModule();

            if (event.phase == TickEvent.Phase.START) {

                modKeybinds.forEach(KeybindWrapper::tick);

                if (!player.isOnGround() && !player.isPassenger() && keyBindJump.onPressAction() ) {
                    shouldJump = true;
                }



            } else if (event.phase.equals(TickEvent.Phase.END)) {
	
	            if (keyBindSwitch.onPressAction()) {
	            //	DLShaderManager.showBoxes = !DLShaderManager.showBoxes;
	            }

                if (shouldJump && ClientInstance.player().input.jumping) {
                    new MSGCheckJump(player.getId()).sendToServer();

                } shouldJump = false;


                accessoryModule.handleClientKeybinds(settings);

                if (player.isPassenger() && player.getVehicle() instanceof MountEntity) {
                    handleMountingKeys(player, (MountEntity) player.getVehicle(), settings);
                }

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

    public static void handleMountingKeys(LocalPlayer player, MountEntity mount, Options settings) {
        if (mount.progressIncreasingParams()) {
            if(settings.keyJump.isDown()) {
                mount.setProgressIsIncreasing(true);
                new MSGMountActions(mount.getId(), DENetwork.MA_SET_PROGRESS_INCREASING).sendToServer();
            } else if (keyBindJump.onUnpressAction()) {
                mount.doAct();
                new MSGMountActions(mount.getId(), DENetwork.MA_EXECUTE_ACTION).sendToServer();
            }
        }

        if (keyBindAbility.onPressAction() && mount.getAbilityTimer() == 10000) {
            mount.doAbility();
            new MSGMountActions(mount.getId(), DENetwork.MA_EXECUTE_ABILITY).sendToServer();
        }
    }



    public static boolean isWearingAntiqueArmor(Player player) {
        return  player.getItemBySlot(EquipmentSlot.HEAD).getItem() == DEItems.ANTIQUE_ARMOR_HELMET.get() &&
                player.getItemBySlot(EquipmentSlot.CHEST).getItem() == DEItems.ANTIQUE_ARMOR_CHESTPLATE.get() &&
                player.getItemBySlot(EquipmentSlot.LEGS).getItem() == DEItems.ANTIQUE_ARMOR_LEGGINGS.get() &&
                player.getItemBySlot(EquipmentSlot.FEET).getItem() == DEItems.ANTIQUE_ARMOR_BOOTS.get();
    }
}
