package bottomtextdanny.dannys_expansion._base.network.servertoclient;

import bottomtextdanny.dannys_expansion._base.gun_rendering.GunClientData;
import bottomtextdanny.dannys_expansion._base.network.DEPacketInitialization;
import bottomtextdanny.dannys_expansion._util._client.CMC;
import bottomtextdanny.dannys_expansion.DannysExpansion;
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
			if (CMC.player() != null) {
				GunClientData gunData = DannysExpansion.client().getGunData();
				PlayerGunModule gunModule = PlayerHelper.gunModule(CMC.player());
				ItemStack handStack = CMC.player().getItemInHand(InteractionHand.values()[this.handOrdinal]);


				gunData.recoil = 0.0F;
				gunData.recoilO = 0.0F;

				int cooldown = 0;

				if (handStack.getItem() instanceof GunItem<?>) {
					cooldown = ((GunItem<?>) handStack.getItem()).cooldown();
					gunData.retrieveFactor = (int) (cooldown * 0.75F + 5);
				}

				gunModule.setGunScoping(ItemStack.EMPTY);
				gunModule.setPreviousGun(handStack);
				gunModule.setGunCooldownTicks(cooldown);

				gunModule.setGunUseTicks(-1);
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
