package net.bottomtextdanny.danny_expannny.capabilities;

import net.bottomtextdanny.braincell.mod.capability_helper.CapabilityWrap;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class CapabilityHelper {
    
    public static <T , D extends CapabilityWrap<?,T>, C extends Capability<D>> D get(T object, C capabilityKey) {
        return ((ICapabilityProvider)object).getCapability(capabilityKey).orElse(null);
    }
}
