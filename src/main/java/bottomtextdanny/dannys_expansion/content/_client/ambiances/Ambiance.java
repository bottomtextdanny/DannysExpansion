package bottomtextdanny.dannys_expansion.content._client.ambiances;

import bottomtextdanny.dannys_expansion.content._client.sound_instances.AmbianceSound;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public abstract class Ambiance {
	public static final List<Ambiance> AMBIANCES = new LinkedList<>();
	public final int id;
	@Nullable private Supplier<AmbianceSound> soundProvider;
	@Nullable private AmbianceSound ambianceLoopSound;
	
	public Ambiance() {
		id = AMBIANCES.size();
		AMBIANCES.add(this);
	}
	
	public <T extends Ambiance> T setSound(Supplier<AmbianceSound> sound) {
        this.soundProvider = sound;
		return (T)this;
	}
	
	public void tick() {
		if (this.soundProvider != null)
			handleSoundTick();
	}
	
	public void handleSoundTick() {
		if (this.ambianceLoopSound == null) {
            this.ambianceLoopSound = this.soundProvider.get();
			if (this.ambianceLoopSound != null)
				Minecraft.getInstance().getSoundManager().play(this.ambianceLoopSound);
		}
		else if (this.ambianceLoopSound.isStopped())
			this.ambianceLoopSound = null;
	}
	
	@Nullable
	public AmbianceSound getAmbianceSound() {
		return this.ambianceLoopSound;
	}
	
	public void setAmbianceLoopSound(@Nullable AmbianceSound ambianceLoopSound) {
		this.ambianceLoopSound = ambianceLoopSound;
	}
	
	public abstract Vector3f tonemapping();
	
	public abstract boolean meetsConditions(LocalPlayer player, int weight);
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Ambiance ambiance)) return false;
		return this.id == ambiance.id;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.id);
	}
}
