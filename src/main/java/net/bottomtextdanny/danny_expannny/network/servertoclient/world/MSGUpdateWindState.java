package net.bottomtextdanny.danny_expannny.network.servertoclient.world;

import net.bottomtextdanny.braincell.mod.base.util.Connection;
import net.bottomtextdanny.braincell.mod.packet_helper.BCPacket;
import net.bottomtextdanny.danny_expannny.network.DEPacketInitialization;
import net.bottomtextdanny.danny_expannny.capabilities.CapabilityHelper;
import net.bottomtextdanny.danny_expannny.capabilities.world.LevelCapability;
import net.bottomtextdanny.danny_expannny.capabilities.world.LevelWindModule;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

public class MSGUpdateWindState implements BCPacket<MSGUpdateWindState> {
    private final float windDirection;
    private final float windStrength;
    private final boolean windStopped;

    public MSGUpdateWindState(float windDirection, float windStrength, boolean windStopped) {
        super();
        this.windDirection = windDirection;
        this.windStrength = windStrength;
        this.windStopped = windStopped;
    }

    @Override
    public void serialize(FriendlyByteBuf stream) {
        stream.writeFloat(this.windDirection);
        stream.writeFloat(this.windStrength);
        stream.writeBoolean(this.windStopped);
    }

    @Override
    public MSGUpdateWindState deserialize(FriendlyByteBuf stream) {
        return new MSGUpdateWindState(stream.readFloat(), stream.readFloat(), stream.readBoolean());
    }

    @Override
    public void postDeserialization(NetworkEvent.Context ctx, Level world) {
        BCPacket.super.postDeserialization(ctx, world);
        Connection.doClientSide(() -> {
            if (Minecraft.getInstance().level != null) {
                LevelCapability capability = CapabilityHelper.get(Minecraft.getInstance().level, LevelCapability.CAPABILITY);
                LevelWindModule windModule = capability.getWindModule();
                windModule.updateFromPacket(this);
            }
        });
    }

    public float getWindDirection() {
        return this.windDirection;
    }

    public float getWindStrength() {
        return this.windStrength;
    }

    public boolean isWindStopped() {
        return this.windStopped;
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
