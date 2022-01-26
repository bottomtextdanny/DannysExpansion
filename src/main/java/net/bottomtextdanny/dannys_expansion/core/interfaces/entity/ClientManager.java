package net.bottomtextdanny.dannys_expansion.core.interfaces.entity;


import net.bottomtextdanny.braincell.mod.packet_front.server_to_client.MSGEntityClientManager;
import net.bottomtextdanny.braincell.mod.serialization.WorldPacketData;
import net.bottomtextdanny.braincell.underlying.misc.ObjectFetcher;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nullable;

public interface ClientManager {

	@OnlyIn(Dist.CLIENT)
    default void clientCallOutHandler(int flag, ObjectFetcher fetcher) {}

	default void sendClientMsg(int flag, @Nullable WorldPacketData<?>... o) {
		this.sendClientMsg(flag, PacketDistributor.TRACKING_ENTITY.with(() -> (Entity) this), o);
	}

	default void sendClientMsg(int flag, PacketDistributor.PacketTarget distributor, @Nullable WorldPacketData<?>... o) {
		Entity asEntity = (Entity) this;
		if (asEntity.level instanceof ServerLevel level) {
			new MSGEntityClientManager(((Entity) this).getId(), flag, level, o).sendTo(distributor);
		}
	}

	@Deprecated
	default void sendClientMsg(int flag) {
		this.sendClientMsg(flag, (WorldPacketData<?>) null);
	}
}
