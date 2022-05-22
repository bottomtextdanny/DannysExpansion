package bottomtextdanny.dannys_expansion.content._client.sound_instances;

import bottomtextdanny.dannys_expansion._base.ambiance.AmbianceManager;
import bottomtextdanny.dannys_expansion._util._client.CMC;
import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content._client.ambiances.Ambiance;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;

public class AmbianceSound extends AbstractTickableSoundInstance {
	private final Ambiance type;
	private final float volumeBound;
	private int ticksInside;
	
    public AmbianceSound(SoundEvent sound, float volume, Ambiance type) {
        super(sound, SoundSource.AMBIENT);
        this.type = type;
	    this.volumeBound = volume;
        this.looping = true;
        this.delay = 0;
        this.relative = true;
        this.volume = 0.01F;
    }

    public void tick() {
		AmbianceManager manager = DannysExpansion.client().getAmbianceManager();
		LocalPlayer player = CMC.player();

		if (player != null && player.isAlive() && this.ticksInside >= 0) {
		    if (manager.getCurrentAmbiance() == this.type)
			    ++this.ticksInside;
			else this.ticksInside -= 5;

		    this.ticksInside = Mth.clamp(this.ticksInside, -1, 90);
		    this.volume = this.ticksInside / 90.0F * this.volumeBound;
	    }
		else stop();
    }
}
