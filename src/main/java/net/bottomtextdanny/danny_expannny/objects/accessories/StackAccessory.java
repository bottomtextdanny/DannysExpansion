package net.bottomtextdanny.danny_expannny.objects.accessories;

import com.mojang.blaze3d.vertex.PoseStack;
import net.bottomtextdanny.braincell.mod.serialization.WorldPacketData;
import net.bottomtextdanny.braincell.underlying.misc.ObjectFetcher;
import net.bottomtextdanny.danny_expannny.accessory.AccessoryKey;
import net.bottomtextdanny.danny_expannny.accessory.IAccessory;
import net.bottomtextdanny.danny_expannny.object_tables.DEAccessoryKeys;
import net.bottomtextdanny.danny_expannny.network.clienttoserver.MSGAccessoryServerManager;
import net.bottomtextdanny.danny_expannny.network.servertoclient.MSGAccessoryClientManager;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerAccessoryModule;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerHelper;
import net.minecraft.client.Options;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;

import java.util.Random;

public class StackAccessory implements IAccessory {
	public static final StackAccessory EMPTY = new StackAccessory(DEAccessoryKeys.STACK_EMPTY, null);
	protected final AccessoryKey<?> key;
	protected final Player player;
	protected ItemStack globalStack;
	protected Item item;
	protected InteractionHand hand;
	protected Random random = new Random();
	
	public StackAccessory(AccessoryKey<?> key, Player player) {
		this.key = key;
		this.player = player;
	}

	public void prepare(ItemStack itemStack, InteractionHand side) {
		this.item = itemStack.getItem();
		this.globalStack = itemStack;
		this.hand = side;
	}

	public ItemStack getStack() {
		return this.globalStack;
	}
	
	public Item getItem() {
		return this.item;
	}
	
	public InteractionHand getHand() {
		return this.hand;
	}

	@Override
	public void tick() {}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void accessoryClientManager(int flag, ObjectFetcher fetcher) {}
	
	@Override
	public void accessoryServerManager(int flag, ObjectFetcher fetcher) {}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void frameTick(LocalPlayer cPlayer, PoseStack poseStack, float partialTicks) {}

	@Override
	public void triggerClientAction(int flag, PacketDistributor.PacketTarget target, WorldPacketData<?>... data) {
		if (this.player.level instanceof ServerLevel serverPlayer) {
			new MSGAccessoryClientManager(this.player.getId(), PlayerAccessoryModule.CORE_ACCESSORIES_SIZE + this.hand.ordinal(), flag, data, serverPlayer).sendTo(target);
		}
	}

	@Override
	public void triggerClientActionToTracking(int flag, WorldPacketData<?>... data) {
		triggerClientAction(flag, PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this.player), data);
	}

	public void triggerClientActionSpecific(int flag, ServerPlayer player, WorldPacketData<?>... data) {
		triggerClientAction(flag, PacketDistributor.PLAYER.with(() -> player), data);
	}

	@Override
	public void triggerClientActionToTracking(int flag) {
		triggerClientAction(flag, PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this.player), (WorldPacketData<?>) null);
	}
	
	@Override
	public void triggerServerAction(int flag, WorldPacketData<?>... data) {
		if (this.player.level.isClientSide()) {
			new MSGAccessoryServerManager(this.player.getId(), -this.hand.ordinal() - 1, flag, data).sendToServer();
		}
	}
	
	@Override
	public void triggerServerAction(int flag) {
		triggerServerAction(flag, (WorldPacketData<?>) null);
	}

	@Override
	public AccessoryKey<?> getKey() {
		return null;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void keyHandler(Options settings) {
	}
	
	@Override
	public void read(CompoundTag nbt) {}
	
	@Override
	public CompoundTag write() {
		return new CompoundTag();
	}
	
	protected PlayerAccessoryModule cap() {
		return PlayerHelper.accessoryModule(this.player);
	}
}
