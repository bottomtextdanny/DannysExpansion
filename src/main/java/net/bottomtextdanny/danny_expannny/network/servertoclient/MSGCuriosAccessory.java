package net.bottomtextdanny.danny_expannny.network.servertoclient;

import net.bottomtextdanny.braincell.mod.packet_helper.BCEntityPacket;
import net.bottomtextdanny.danny_expannny.network.DEPacketInitialization;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.MutableLatch;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.BiConsumer;

public class MSGCuriosAccessory extends BCEntityPacket<MSGCuriosAccessory, Player> {
	public static final int UNEQUIP = 0, EQUIP = 1;
	private static final MutableLatch<BiConsumer<MSGCuriosAccessory, Level>> postDeserialization = MutableLatch.empty();
    private final int itemId;
    private final int flag;

    public MSGCuriosAccessory(int entityId, int itemId, int flag) {
        super(entityId);
        this.itemId = itemId;
        this.flag = flag;
    }

    @Override
    public void serialize(FriendlyByteBuf stream) {
        super.serialize(stream);
        stream.writeInt(this.itemId);
        stream.writeInt(this.flag);
    }

    @Override
    public MSGCuriosAccessory deserialize(FriendlyByteBuf stream) {
        return new MSGCuriosAccessory(stream.readInt(), stream.readInt(), stream.readInt());
    }

    @Override
    public void postDeserialization(NetworkEvent.Context ctx, Level world) {
    	
    	if (postDeserialization.get() != null)
    		postDeserialization.get().accept(this, world);
    }
    
    public static void setPostDeserializationAction(BiConsumer<MSGCuriosAccessory, Level> msg) {
    	postDeserialization.setLocked(msg);
    }
	
	public int getFlag() {
		return this.flag;
	}
	
	public int getItemId() {
		return this.itemId;
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
