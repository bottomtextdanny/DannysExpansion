package bottomtextdanny.dannys_expansion._base.sensitive_hooks;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion._base.capabilities.item.DEStackCapability;
import bottomtextdanny.dannys_expansion._base.capabilities.item.HoldedItem;
import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerCapability;
import bottomtextdanny.dannys_expansion._base.capabilities.world.LevelCapability;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class CapabilityManager {

    public static void sendHooks() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        modBus.addListener(CapabilityManager::register);
        forgeBus.addGenericListener(Level.class, CapabilityManager::attachToLevel);
        forgeBus.addGenericListener(Entity.class, CapabilityManager::attachToEntity);
        forgeBus.addGenericListener(ItemStack.class, CapabilityManager::attachToItemStack);
    }

    private static void register(RegisterCapabilitiesEvent event) {
        event.register(LevelCapability.class);
        event.register(PlayerCapability.class);
        event.register(DEStackCapability.class);
    }

    private static void attachToLevel(AttachCapabilitiesEvent<Level> event) {
        addCapability(event, "world_capability", new LevelCapability(event.getObject()));
    }

    private static void attachToItemStack(AttachCapabilitiesEvent<ItemStack> event) {
        if (event.getObject().getItem() instanceof HoldedItem) {
            addCapability(event, "danny_stack", new DEStackCapability());
        }
    }

    private static void attachToEntity(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player) {
            addCapability(event, "player_capability", new PlayerCapability(player));
        }
    }

    private static void addCapability(AttachCapabilitiesEvent<?> event, String key, ICapabilityProvider cap) {
        event.addCapability(new ResourceLocation(DannysExpansion.ID, key), cap);
    }
}
