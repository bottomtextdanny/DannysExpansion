package bottomtextdanny.dannys_expansion._base.network.servertoclient.player;

import bottomtextdanny.dannys_expansion._util._client.CMC;
import bottomtextdanny.dannys_expansion._base.network.DEPacketInitialization;
import bottomtextdanny.dannys_expansion._base.capabilities.player.DEAccessoryModule;
import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerCapability;
import bottomtextdanny.dannys_expansion.tables.DEAccessoryKeys;
import bottomtextdanny.dannys_expansion.content.accessories.CoreAccessory;
import com.google.common.collect.Lists;
import bottomtextdanny.braincell.mod.network.Connection;
import bottomtextdanny.braincell.mod.capability.player.accessory.AccessoryKey;
import bottomtextdanny.braincell.mod.capability.player.accessory.IAccessory;
import bottomtextdanny.braincell.mod.capability.CapabilityHelper;
import bottomtextdanny.braincell.mod.network.BCEntityPacket;
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

    public MSGSendPlayerAccessories(FriendlyByteBuf stream) {
        super(stream.readInt());
        Connection.doClientSide(() -> {
            if (CMC.player() != null && CMC.player().level.getEntity(getEntityId()) instanceof Player trackedPlayer) {
                this.cachedPlayer = trackedPlayer;
                List<ItemStack> newItemlist = Lists.newArrayListWithCapacity(DEAccessoryModule.CORE_ACCESSORIES_SIZE);
                List<AccessoryKey<?>> newKeylist = Lists.newArrayListWithCapacity(DEAccessoryModule.CORE_ACCESSORIES_SIZE);

                for (int i = 0; i < DEAccessoryModule.CORE_ACCESSORIES_SIZE; i++) {
                    if (stream.readBoolean()) {
                        newItemlist.add(stream.readItem());
                        newKeylist.add(AccessoryKey.getAccessoriesById().get(stream.readInt()));
                    } else {
                        newItemlist.add(ItemStack.EMPTY);
                        newKeylist.add(DEAccessoryKeys.CORE_EMPTY);
                    }
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
        for (int i = 0; i < DEAccessoryModule.CORE_ACCESSORIES_SIZE; i++) {
            final boolean writeFlag = this.items.get(i) != ItemStack.EMPTY;
            stream.writeBoolean(writeFlag);
            if (writeFlag) {
                stream.writeItem(this.items.get(i));
                stream.writeInt(this.serverAccessories.get(i).getKey().getId());
            }
        }
    }

    @Override
    public void postDeserialization(NetworkEvent.Context ctx, Level world) {
        if (this.accessoryKeys != null) {
            DEAccessoryModule accessoryModule = CapabilityHelper.get(this.cachedPlayer, PlayerCapability.TOKEN).accessoryModule();

            for (int i = 0; i < DEAccessoryModule.CORE_ACCESSORIES_SIZE; i++) {
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
