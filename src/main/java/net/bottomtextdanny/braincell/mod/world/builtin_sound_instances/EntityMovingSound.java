package net.bottomtextdanny.braincell.mod.world.builtin_sound_instances;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;

public class EntityMovingSound extends AbstractTickableSoundInstance {
    private final Entity entity;
    private float distance;
    private float baseVolume;
	
	private EntityMovingSound(Entity entity, SoundEvent sound, SoundSource category, float volume, float pitch, float distance) {
		super(sound, category);
		this.entity = entity;
		this.looping = false;
		this.delay = 0;
		this.volume = volume;
		this.relative = false;
		this.pitch = pitch;
		this.distance = distance;
		this.baseVolume = volume;
	}

    public static Builder builder(Entity entity, SoundEvent sound, SoundSource category) {
        return new Builder(entity, sound, category);
    }

    public void tick() {
    	if (!this.entity.isRemoved() && Minecraft.getInstance().player != null) {
    		float dist = Minecraft.getInstance().player.distanceTo(this.entity);
	        boolean distanceFlag = this.distance == -1.0F ? dist < 20.0F :  dist < this.distance;
	
	        if (distanceFlag) {
	        	
	        	if (this.distance != -1.0F) {
                    this.volume = (1.0F - dist / this.distance) * this.baseVolume;
		        }

                this.x = this.entity.position().x;
                this.y = this.entity.position().y;
                this.z = this.entity.position().z;
	        } else {
		        this.stop();
	        }
        } else {
            this.stop();
        }
    }

    public static class Builder {
        private final Entity entity;
        private final SoundEvent sound;
        private final SoundSource category;
        private float volume = 1.0F;
        private float pitch = 1.0F;
        private float distance = 20.0F;

        private Builder(Entity entity, SoundEvent sound, SoundSource category) {
            super();
            this.entity = entity;
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

        public EntityMovingSound build() {
            return new EntityMovingSound(this.entity, this.sound, this.category, this.volume, this.pitch, this.distance);
        }
    }
}
