package net.bottomtextdanny.danny_expannny.network.servertoclient;

import net.bottomtextdanny.braincell.mod.base.util.Connection;
import net.bottomtextdanny.braincell.mod.packet_helper.BCEntityPacket;
import net.bottomtextdanny.danny_expannny.ClientInstance;
import net.bottomtextdanny.danny_expannny.objects.items.gun.GunItem;
import net.bottomtextdanny.danny_expannny.network.DEPacketInitialization;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerGunModule;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerHelper;
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
			if (ClientInstance.player() != null) {
				Player player = ClientInstance.player();
				PlayerGunModule capability = PlayerHelper.gunModule(player);

				if (this.hand >= 0x100) {
					InteractionHand handE = InteractionHand.values()[this.hand - 0x100];
					ItemStack handStack = ClientInstance.player().getItemInHand(handE);

					if (handStack.getItem() instanceof GunItem<?> gunItem) {
						gunItem.shootFirstCallout(player, handStack, capability);
						capability.setGunUseTicks(0);
					}
				} else {
					InteractionHand handE = InteractionHand.values()[this.hand];
					ItemStack handStack = ClientInstance.player().getItemInHand(handE);

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
