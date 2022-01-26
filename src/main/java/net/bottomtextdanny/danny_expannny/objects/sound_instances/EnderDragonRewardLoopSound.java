package net.bottomtextdanny.danny_expannny.objects.sound_instances;

import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.danny_expannny.objects.entities.EnderDragonRewardEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundSource;

public class EnderDragonRewardLoopSound extends AbstractTickableSoundInstance {
    private final EnderDragonRewardEntity kite;

    public EnderDragonRewardLoopSound(EnderDragonRewardEntity playerIn) {
        super(DESounds.ES_ENDER_DRAGON_REWARD_LOOP.get(), SoundSource.NEUTRAL);
        this.kite = playerIn;
        this.looping = true;
        this.delay = 0;
        this.volume = 0.35F;
        this.relative = false;
    }

    public void tick() {
    	double dist = Minecraft.getInstance().player.distanceTo(this.kite);
        if (!this.kite.isRemoved() && Minecraft.getInstance().player != null && dist < 20) {
            this.x = this.kite.position().x;
            this.y = this.kite.position().y;
            this.z = this.kite.position().z;
            this.volume = this.kite.getFxIntensity() * 0.35F;

            this.volume *= 1.0 - dist / 20.0;
        } else {
            this.stop();
        }
    }
}
