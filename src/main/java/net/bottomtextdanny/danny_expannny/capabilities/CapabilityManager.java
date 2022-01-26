package net.bottomtextdanny.danny_expannny.capabilities;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.capabilities.item.DEStackCapability;
import net.bottomtextdanny.danny_expannny.capabilities.item.ProvideCapability;
import net.bottomtextdanny.danny_expannny.capabilities.chunk.ChunkCapability;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerCapability;
import net.bottomtextdanny.danny_expannny.capabilities.world.LevelCapability;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DannysExpansion.ID)
public class CapabilityManager {

    public static void callForSomeReason() {
    }

    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event) {
        event.register(LevelCapability.class);
        event.register(PlayerCapability.class);
        event.register(ChunkCapability.class);
        event.register(DEStackCapability.class);
    }

    @SubscribeEvent
    public static void attachToWorld(AttachCapabilitiesEvent<Level> event) {
        addCap(event, "world_capability", new LevelCapability(event.getObject()));
    }

    @SubscribeEvent
    public static void attachToChunk(AttachCapabilitiesEvent<LevelChunk> event) {
        addCap(event, "chunk_capability", new ChunkCapability(event.getObject()));
    }

    @SubscribeEvent
    public static void attachToItemStack(AttachCapabilitiesEvent<ItemStack> event) {
        if (event.getObject().getItem() instanceof ProvideCapability) {
            addCap(event, "danny_stack", new DEStackCapability());
        }
    }

    @SubscribeEvent
    public static void attachToEntity(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player) {
            addCap(event, "player_capability", new PlayerCapability(player));
        }
    }


    static void addCap(AttachCapabilitiesEvent<?> event, String key, ICapabilityProvider cap) {
        event.addCapability(new ResourceLocation(DannysExpansion.ID, key), cap);
    }
}
