package net.bottomtextdanny.danny_expannny.network.servertoclient.player;

import com.google.common.collect.Lists;
import net.bottomtextdanny.braincell.mod.base.util.Connection;
import net.bottomtextdanny.braincell.mod.packet_helper.BCEntityPacket;
import net.bottomtextdanny.danny_expannny.ClientInstance;
import net.bottomtextdanny.danny_expannny.network.DEPacketInitialization;
import net.bottomtextdanny.danny_expannny.accessory.AccessoryKey;
import net.bottomtextdanny.danny_expannny.objects.accessories.CoreAccessory;
import net.bottomtextdanny.danny_expannny.accessory.IAccessory;
import net.bottomtextdanny.danny_expannny.capabilities.CapabilityHelper;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerAccessoryModule;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerCapability;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import javax.annotation.Nullable;
import java.util.List;

public class MSGSendPlayerAccessories extends BCEntityPacket<MSGSendPlayerAccessories, Player> {
    private List<ItemStack> items;
    List<CoreAccessory> serverAccessories;
    @Nullable
    private List<AccessoryKey<?>> accessoryKeys;
    @Nullable
    private Player cachedPlayer;

    public MSGSendPlayerAccessories(FriendlyByteBuf packetBuffer) {
        super(packetBuffer.readInt());
        Connection.doClientSide(() -> {
            if (ClientInstance.player() != null && ClientInstance.player().level.getEntity(getEntityId()) instanceof Player trackedPlayer) {
                this.cachedPlayer = trackedPlayer;
                List<ItemStack> newItemlist = Lists.newArrayListWithCapacity(PlayerAccessoryModule.CORE_ACCESSORIES_SIZE);
                List<AccessoryKey<?>> newKeylist = Lists.newArrayListWithCapacity(PlayerAccessoryModule.CORE_ACCESSORIES_SIZE);

                for (int i = 0; i < PlayerAccessoryModule.CORE_ACCESSORIES_SIZE; i++) {
                    newItemlist.add(packetBuffer.readItem());
                    newKeylist.add(AccessoryKey.getAccessoriesById().get(packetBuffer.readInt()));
                }

                this.items = newItemlist;
                this.accessoryKeys = newKeylist;
            }
        });
    }

    public MSGSendPlayerAccessories(int entityId, List<ItemStack> coreItems, List<CoreAccessory> accessories) {
        super(entityId);
        this.items = coreItems;
        this.serverAccessories = accessories;
    }

    @Override
    public MSGSendPlayerAccessories deserialize(FriendlyByteBuf stream) {
        return new MSGSendPlayerAccessories(stream);
    }

    @Override
    public void serialize(FriendlyByteBuf stream) {
        super.serialize(stream);
        for (int i = 0; i < PlayerAccessoryModule.CORE_ACCESSORIES_SIZE; i++) {
            stream.writeItem(this.items.get(i));
            stream.writeInt(this.serverAccessories.get(i).getKey().getId());
        }
    }

    @Override
    public void postDeserialization(NetworkEvent.Context ctx, Level world) {
        if (this.accessoryKeys != null) {
            PlayerAccessoryModule accessoryModule = CapabilityHelper.get(this.cachedPlayer, PlayerCapability.TOKEN).accessoryModule();

            for (int i = 0; i < PlayerAccessoryModule.CORE_ACCESSORIES_SIZE; i++) {
                IAccessory accessory = this.accessoryKeys.get(i).create(this.cachedPlayer);
                if (accessory instanceof CoreAccessory coreAccessory) {
                    accessoryModule.setAccessoryStack(i, this.items.get(i));
                    accessoryModule.getCoreAccessoryList().set(i, coreAccessory);
                    coreAccessory.prepare(i);
                }
            }
        }
    }

    @Override
    public LogicalSide side() {
        return LogicalSide.CLIENT;
    }

    @Override
    public SimpleChannel mainChannel() {
        return DEPacketInitialization.CHANNEL;
    }
}
