package net.bottomtextdanny.dannys_expansion.core.Registries;

import com.google.common.collect.Lists;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;

import java.util.List;


public class DannyKeybinds {
    private static final List<KeyMapping> keyBinds = Lists.newArrayList();
    public static KeyMapping MOUNT_ABILITY = registerKeybind(new KeyMapping("key.dannys_expansion.mount_ability", 71, "key.categories.dannys_expansion"));

    public static void registerKeys() {
        for(KeyMapping keys : keyBinds) {
            ClientRegistry.registerKeyBinding(keys);
        }
    }

    private static KeyMapping registerKeybind(KeyMapping keybind) {
        keyBinds.add(keybind);
        return keybind;
    }
}
