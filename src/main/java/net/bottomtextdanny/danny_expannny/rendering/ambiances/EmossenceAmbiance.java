package net.bottomtextdanny.danny_expannny.rendering.ambiances;

import com.mojang.math.Vector3f;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.danny_expannny.objects.sound_instances.AmbianceSound;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EmossenceAmbiance extends DEAmbiance {
	
	public EmossenceAmbiance() {
		super();
		setSound(() -> new AmbianceSound(DESounds.A_EMOSSENCE.get(), 0.02F, this));
	}
	
	@Override
	public Vector3f tonemapping() {
		return new Vector3f(-0.2F, -0.4F, 0.0F);
	}
	
	@Override
	public boolean meetsConditions(LocalPlayer player, int weight) {
		return weight > 2250;
	}
}
