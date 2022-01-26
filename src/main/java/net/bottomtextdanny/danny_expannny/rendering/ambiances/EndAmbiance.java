package net.bottomtextdanny.danny_expannny.rendering.ambiances;

import com.mojang.math.Vector3f;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.danny_expannny.objects.sound_instances.AmbianceSound;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.level.biome.Biome;

public  class EndAmbiance extends DEAmbiance {
	
	
	public EndAmbiance() {
		super();
		setSound(() -> new AmbianceSound(DESounds.A_END.get(), 0.9F, this));
	}
	
	@Override
	public Vector3f tonemapping() {
		return new Vector3f(-0.2F, -0.1F, 0.0F);
	}
	
	@Override
	public boolean meetsConditions(LocalPlayer player, int weight) {
		Biome biome = player.level.getBiome(player.blockPosition());
		return biome.getAmbientLoop().isEmpty() && biome.getBiomeCategory() == Biome.BiomeCategory.THEEND;
	}
}
