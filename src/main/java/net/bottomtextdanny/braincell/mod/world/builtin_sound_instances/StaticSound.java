package net.bottomtextdanny.braincell.mod.world.builtin_sound_instances;

import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;

public class StaticSound extends AbstractTickableSoundInstance {
    private final float distance;
    private final float baseVolume;

	private StaticSound(SoundEvent sound, SoundSource category, float volume, float pitch, Vec3 position, float distance) {
		super(sound, category);
		this.looping = false;
		this.delay = 0;
		this.volume = volume;
		this.relative = false;
		this.pitch = pitch;
		this.distance = distance;
		this.baseVolume = volume;
		this.x = position.x();
		this.y = position.y();
		this.z = position.z();
	}

	public static Builder builder(Vec3 position, SoundEvent sound, SoundSource category) {
		return new Builder(position, sound, category);
	}

    public void tick() {
		LocalPlayer player = Minecraft.getInstance().player;
    	if (player != null) {
    		float dist = DEMath.getDistance(player, this.x, this.y, this.z);
	        boolean distanceFlag = dist < this.distance;
	
	        if (distanceFlag) {
				this.volume = (1.0F - dist / this.distance) * this.baseVolume;
	        } else {
		        this.stop();
	        }
        } else {
            this.stop();
        }
    }

	public static class Builder {
		private final SoundEvent sound;
		private final SoundSource category;
		private final Vec3 position;
		private float volume;
		private float pitch;
		private float distance;

		private Builder(Vec3 position, SoundEvent sound, SoundSource category) {
			super();
			this.position = position;
			this.sound = sound;
			this.category = category;
		}

		public Builder volume(float volume) {
			this.volume = volume;
			return this;
		}

		public Builder pitch(float pitch) {
			this.pitch = pitch;
			return this;
		}

		public Builder distance(float distance) {
			this.distance = distance;
			return this;
		}

		public StaticSound build() {
			return new StaticSound(this.sound, this.category, this.volume, this.pitch, this.position, this.distance);
		}
	}
}
