package net.bottomtextdanny.danny_expannny.objects.sound_instances;

import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.GolemDroneEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundSource;

public class GolemDroneLoopSound extends AbstractTickableSoundInstance {
    private final GolemDroneEntity drone;

    public GolemDroneLoopSound(GolemDroneEntity playerIn) {
        super(DESounds.ES_GOLEM_DRONE_LOOP.get(), SoundSource.HOSTILE);
        this.drone = playerIn;
        this.looping = true;
        this.delay = 0;
        this.volume = 0.6F;
        this.relative = false;
    }



    public void tick() {

        if (!this.drone.isRemoved() && Minecraft.getInstance().player != null && Minecraft.getInstance().player.distanceTo(this.drone) < 20) {
            this.x = this.drone.position().x;
            this.y = this.drone.position().y;
            this.z = this.drone.position().z;
        } else {
            this.stop();
        }
    }
}
