package net.bottomtextdanny.braincell.mod.structure.client_sided.events;

import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraftforge.eventbus.api.Event;

public class PlayerPoseEvent extends Event {
    private final PlayerModel<?> model;
    private final AbstractClientPlayer player;
    private final float
            limbSwing,
            limbSwingAmount,
            ageInTicks,
            netHeadYaw,
            headPitch;

    public PlayerPoseEvent(
            PlayerModel<?> model,
            AbstractClientPlayer player,
            float limbSwing,
            float limbSwingAmount,
            float ageInTicks,
            float netHeadYaw,
            float headPitch) {
        super();
        this.model = model;
        this.player = player;
        this.limbSwing = limbSwing;
        this.limbSwingAmount = limbSwingAmount;
        this.ageInTicks = ageInTicks;
        this.netHeadYaw = netHeadYaw;
        this.headPitch = headPitch;
    }

    public PlayerModel<?> getModel() {
        return this.model;
    }

    public AbstractClientPlayer getPlayer() {
        return this.player;
    }

    public float getLimbSwing() {
        return this.limbSwing;
    }

    public float getLimbSwingAmount() {
        return this.limbSwingAmount;
    }

    public float getEasedLifetime() {
        return this.ageInTicks;
    }

    public float getHeadYaw() {
        return this.netHeadYaw;
    }

    public float getHeadPitch() {
        return this.headPitch;
    }
}
