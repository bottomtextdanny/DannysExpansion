package bottomtextdanny.dannys_expansion.content._client.ambiances;

import bottomtextdanny.dannys_expansion.tables.DESounds;
import bottomtextdanny.dannys_expansion.content._client.sound_instances.AmbianceSound;
import com.mojang.math.Vector3f;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;

public class EndAmbiance extends Ambiance {

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
		Holder<Biome> biome = player.level.getBiome(player.blockPosition());
		return biome.value().getAmbientLoop().isEmpty() && Biome.getBiomeCategory(biome) == Biome.BiomeCategory.THEEND;
	}
}
