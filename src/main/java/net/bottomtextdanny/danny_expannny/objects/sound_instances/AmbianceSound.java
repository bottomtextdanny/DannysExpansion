package net.bottomtextdanny.danny_expannny.objects.sound_instances;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.ClientInstance;
import net.bottomtextdanny.danny_expannny.rendering.ambiances.DEAmbiance;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;

public class AmbianceSound extends AbstractTickableSoundInstance {
	private final DEAmbiance type;
	private final float volumeBound;
	private int ticksInside;
	
    public AmbianceSound(SoundEvent sound, float volume, DEAmbiance type) {
        super(sound, SoundSource.AMBIENT);
        this.type = type;
	    this.volumeBound = volume;
        this.looping = true;
        this.delay = 0;
        this.relative = true;
        this.volume = 0.01F;
    }

    public void tick() {
	   
	    if (ClientInstance.player() != null && ClientInstance.player().isAlive() && this.ticksInside >= 0) {
		    if (DannysExpansion.clientManager().ambianceManager.getCurrentAmbiance() == this.type) {
			    ++this.ticksInside;
		    } else {
			    this.ticksInside -= 5;
		    }
		    
		    
		    this.ticksInside = Mth.clamp(this.ticksInside, -1, 90);
		    this.volume = this.ticksInside / 90.0F * this.volumeBound;
	    } else {
	    	stop();
	    }
    }
}
