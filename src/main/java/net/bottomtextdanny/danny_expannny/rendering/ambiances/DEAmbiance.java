package net.bottomtextdanny.danny_expannny.rendering.ambiances;

import com.mojang.math.Vector3f;
import net.bottomtextdanny.danny_expannny.objects.sound_instances.AmbianceSound;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public abstract class DEAmbiance {
	public static final LinkedHashSet<DEAmbiance> AMBIANCES = new LinkedHashSet<>(0);
	public final int id = DEUtil.getIdentifier();
	@Nullable private Supplier<AmbianceSound> soundProvider;
	@Nullable private AmbianceSound ambianceLoopSound;
	
	public DEAmbiance() {
		AMBIANCES.add(this);
	}
	
	public <T extends DEAmbiance> T setSound(Supplier<AmbianceSound> sound) {
        this.soundProvider = sound;
		return (T)this;
	}
	
	public void tick() {
		if (this.soundProvider != null) {
		
			handleSoundTick();
		}
	}
	
	public void handleSoundTick() {
		if (this.ambianceLoopSound == null) {
            this.ambianceLoopSound = this.soundProvider.get();
			Minecraft.getInstance().getSoundManager().play(this.ambianceLoopSound);
		} else {
			if (this.ambianceLoopSound.isStopped() ) {
                this.ambianceLoopSound = null;
			}
		}
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
		if (!(o instanceof DEAmbiance)) return false;
		DEAmbiance ambiance = (DEAmbiance) o;
		return this.id == ambiance.id;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.id);
	}
}
