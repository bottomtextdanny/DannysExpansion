package net.bottomtextdanny.braincell.mod.opengl_front.screen_tonemapping;

import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

public class TonemapAgent {
	private final Vector3f channelModifier = new Vector3f();
	private float saturationModifier;
	private final LocalPlayer player = Minecraft.getInstance().player;
	public final int lifetime;
	private int lifeTicks;
	
	public TonemapAgent(int lifetime) {
		super();
		this.lifetime = lifetime;
	}
	
	public void render(float partialTick) {
	}
	
	public final void baseTick() {
        this.lifeTicks++;
		tick();
	}
	
	public void tick() {
	}
	
	public boolean removeIf() {
		return this.lifeTicks >= this.lifetime;
	}
	
	public Vector3f getChannelModifier() {
		return this.channelModifier;
	}
	
	public float getSaturationModifier() {
		return this.saturationModifier;
	}
	
	public int getLifeTicks() {
		return this.lifeTicks;
	}
	
	public static TonemapAgent createBlink(int lifetime) {
		return new TonemapAgent(lifetime) {
			
			@Override
			public void render(float partialTick) {
				super.render(partialTick);
				float blackness = -((this.lifetime - (getLifeTicks() + partialTick)) / this.lifetime) * 0.8F;
				getChannelModifier().set(blackness, blackness, blackness);
			}
		};
	}
}
