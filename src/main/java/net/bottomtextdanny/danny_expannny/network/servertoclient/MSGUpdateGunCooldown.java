package net.bottomtextdanny.danny_expannny.network.servertoclient;

import net.bottomtextdanny.braincell.mod.base.util.Connection;
import net.bottomtextdanny.braincell.mod.packet_helper.BCEntityPacket;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
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

public class MSGUpdateGunCooldown extends BCEntityPacket<MSGUpdateGunCooldown, Player> {
	private final int handOrdinal;
	
	public MSGUpdateGunCooldown(int entityId, int hand) {
		super(entityId);
		this.handOrdinal = hand;
	}
	
	@Override
	public void serialize(FriendlyByteBuf stream) {
		super.serialize(stream);
		stream.writeInt(this.handOrdinal);
	}
	
	@Override
	public MSGUpdateGunCooldown deserialize(FriendlyByteBuf stream) {
		return new MSGUpdateGunCooldown(stream.readInt(), stream.readInt());
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void postDeserialization(NetworkEvent.Context ctx, Level world) {
		Connection.doClientSide(() -> {
			if (ClientInstance.player() != null) {
				PlayerGunModule gunModule = PlayerHelper.gunModule(ClientInstance.player());
				DannysExpansion.clientManager().cRecoil = 0.0F;
				DannysExpansion.clientManager().cPrevRecoil = 0.0F;
				ItemStack handStack = ClientInstance.player().getItemInHand(InteractionHand.values()[this.handOrdinal]);
				int cooldown = 0;
				if (handStack.getItem() instanceof GunItem<?>) {
					cooldown = ((GunItem<?>) handStack.getItem()).cooldown();
					DannysExpansion.clientManager().cGunRetrieveFactor = (int) (cooldown * 0.75F + 5);
				}
				gunModule.setGunScoping(ItemStack.EMPTY);
				gunModule.setPreviousGun(handStack);
				gunModule.setGunCooldownTicks(cooldown);

				gunModule.setGunUseTicks(99999);
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
