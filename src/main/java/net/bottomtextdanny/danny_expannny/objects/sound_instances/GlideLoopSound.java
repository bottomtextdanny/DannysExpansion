package net.bottomtextdanny.danny_expannny.objects.sound_instances;

import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.floating.MagmaGulperEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundSource;

public class GlideLoopSound extends AbstractTickableSoundInstance {
    private final MagmaGulperEntity kite;

    public GlideLoopSound(MagmaGulperEntity playerIn) {
        super(DESounds.ES_KITE_LOOP.get(), SoundSource.NEUTRAL);
        this.kite = playerIn;
        this.looping = true;
        this.delay = 0;
        this.volume = 0.075F;
        this.relative = false;
    }

    public void tick() {
        if (!this.kite.isRemoved() && Minecraft.getInstance().player != null && Minecraft.getInstance().player.distanceTo(this.kite) < 20) {
            this.x = this.kite.position().x;
            this.y = this.kite.position().y;
            this.z = this.kite.position().z;
            this.volume = volumeBase() * (Math.max(1.0F, this.kite.glidingVelocityMult) - 1.0F);
        } else {
            this.stop();
        }
    }

    public float volumeBase() {
        return 0.075F;
    }
}
