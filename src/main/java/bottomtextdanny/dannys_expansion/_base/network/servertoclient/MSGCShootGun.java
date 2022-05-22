package bottomtextdanny.dannys_expansion._base.network.servertoclient;

import bottomtextdanny.dannys_expansion._base.network.DEPacketInitialization;
import bottomtextdanny.dannys_expansion._util._client.CMC;
import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerGunModule;
import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerHelper;
import bottomtextdanny.dannys_expansion.content.items.gun.GunItem;
import bottomtextdanny.braincell.mod.network.Connection;
import bottomtextdanny.braincell.mod.network.BCEntityPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

public class MSGCShootGun extends BCEntityPacket<MSGCShootGun, Player> {
	final int hand;
	
	public MSGCShootGun(int entityId, int hand) {
		super(entityId);
		this.hand = hand;
	}
	
	@Override
	public void serialize(FriendlyByteBuf stream) {
		super.serialize(stream);
		stream.writeInt(this.hand);
	}
	
	@Override
	public MSGCShootGun deserialize(FriendlyByteBuf stream) {
		return new MSGCShootGun(stream.readInt(), stream.readInt());
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void postDeserialization(NetworkEvent.Context ctx, Level world) {
		Connection.doClientSide(() -> {
			if (CMC.player() != null) {
				Player player = CMC.player();
				PlayerGunModule capability = PlayerHelper.gunModule(player);

				if (this.hand >= 0x100) {
					InteractionHand handE = InteractionHand.values()[this.hand - 0x100];
					ItemStack handStack = CMC.player().getItemInHand(handE);

					if (handStack.getItem() instanceof GunItem<?> gunItem) {
						gunItem.shootFirstCallout(player, handStack, capability);
						capability.setGunUseTicks(0);
					}
				} else {
					InteractionHand handE = InteractionHand.values()[this.hand];
					ItemStack handStack = CMC.player().getItemInHand(handE);

					if (handStack.getItem() instanceof GunItem<?> gunItem) {
						gunItem.shootFirstCallout(player, handStack, capability);
					}
				}
				
			}
		});
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
