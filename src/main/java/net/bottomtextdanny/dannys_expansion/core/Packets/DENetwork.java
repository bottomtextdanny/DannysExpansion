package net.bottomtextdanny.dannys_expansion.core.Packets;

import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

@Deprecated
public class DENetwork {
    /** Valid parameter for {@code mountActions}. */
    public static int
            MA_SET_PROGRESS_INCREASING,
            MA_EXECUTE_ACTION          = 1,
            MA_EXECUTE_ABILITY         = 2;

    /** Valid parameter for {@code notifyAntiqueArmor}. */
    public static int
            AA_FORWARD_DIR,
            AA_BACK_DIR    = 1,
            AA_RIGHT_DIR   = 2,
            AA_LEFT_DIR    = 3;

    /**
     * tells the server that the player should be moved
     */
    public static void sendPlayerVelocityPacket(Entity player) {
        if (player instanceof ServerPlayer) {
            ((ServerPlayer)player).connection.send(new ClientboundSetEntityMotionPacket(player));
        }
    }
}
