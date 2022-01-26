package net.bottomtextdanny.danny_expannny.objects.items.armor;

import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.core.Packets.DENetwork;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class AntiqueArmorDirection {
    public final Direction type;
    public final AntiqueArmorItem armorItem;
    public int timer = 28;
    int timesPressed;

    public AntiqueArmorDirection(AntiqueArmorItem armorItem, Direction type) {
        this.type = type;
        this.armorItem = armorItem;
    }

    public void tick() {
        if (this.timesPressed > 0) {
            if (--this.timer <= 0) {
                this.timer = 28;
                this.timesPressed = 0;
            } else {
                if (this.timesPressed >= 3) {
                    doDash();
                }
            }
        }
    }

    void doDash() {
        if (this.armorItem.dashCooldown <=0 && this.armorItem.player.getVehicle() == null) {
            Player player = this.armorItem.player;
            Vec3 vec0 = DEMath.fromPitchYaw(0, player.yHeadRot);
            Vec3 vec1 = DEMath.fromPitchYaw(0, Mth.wrapDegrees(player.yHeadRot) + 90);
            float f = 4;

            if (this.armorItem.player.isSwimming()) {
                vec0 = DEMath.fromPitchYaw(player.getXRot(), player.yHeadRot);
                vec1 = DEMath.fromPitchYaw(player.getXRot(), Mth.wrapDegrees(player.yHeadRot) + 90);
            }

            if (!this.armorItem.player.isOnGround()) {
                f = 2;
            }
            switch (this.type) {
                case FORWARD: player.push(vec0.x * f, vec0.y * f, vec0.z * f);
                    break;
                case BACK: player.push(vec0.x * -f * 0.7, vec0.y * -f * 0.7, vec0.z * -f * 0.7);
                    break;
                case RIGHT: player.push(vec1.x * f * 0.6, vec1.y * f * 0.6, vec1.z * f * 0.6);
                    break;
                case LEFT: player.push(vec1.x * -f * 0.6, vec1.y * -f * 0.6, vec1.z * -f * 0.6);
                    break;
            }
            player.playNotifySound(DESounds.IS_ANTIQUE_ARMOR_SWOOSH.get(), SoundSource.NEUTRAL,1.0F, 0.5F);
            DENetwork.sendPlayerVelocityPacket(this.armorItem.player);

            this.armorItem.dashCooldown = 50;
            this.armorItem.dashDuration = 18;

        }
    }

    public void addTimesPressed(int timesPressed) {
        this.timesPressed = this.timesPressed + timesPressed;
    }
}
