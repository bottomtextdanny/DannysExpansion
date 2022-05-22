package bottomtextdanny.dannys_expansion._base.network.servertoclient.world;

import bottomtextdanny.dannys_expansion._base.network.DEPacketInitialization;
import bottomtextdanny.dannys_expansion._base.capabilities.world.LevelCapability;
import bottomtextdanny.dannys_expansion._base.capabilities.world.LevelWindModule;
import bottomtextdanny.braincell.mod.capability.CapabilityHelper;
import bottomtextdanny.braincell.mod.network.BCPacket;
import bottomtextdanny.braincell.mod.network.Connection;
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
                LevelCapability capability = CapabilityHelper.get(Minecraft.getInstance().level, LevelCapability.TOKEN);
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
