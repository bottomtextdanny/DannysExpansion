package net.bottomtextdanny.danny_expannny.object_tables;

import com.google.common.collect.Maps;
import com.mojang.math.Vector3f;
import net.bottomtextdanny.danny_expannny.rendering.ambiances.DEAmbiance;
import net.bottomtextdanny.danny_expannny.rendering.ambiances.EmossenceAmbiance;
import net.bottomtextdanny.danny_expannny.rendering.ambiances.EndAmbiance;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public final class DEAmbiences {
	public static final Map<Class<? extends DEAmbiance>, DEAmbiance> SINGLETON_BY_CLASS = Maps.newIdentityHashMap();
	public static final DEAmbiance NONE = new DEAmbiance() {
		@Override
		public Vector3f tonemapping() {
			return new Vector3f(0.0F, 0.0F, 0.0F);
		}
		
		@Override
		public void handleSoundTick() {
		}
		
		@Override
		public boolean meetsConditions(LocalPlayer player, int weight) {
			return false;
		}
	};
	
	public static final EndAmbiance END = put(new EndAmbiance());
	public static final EmossenceAmbiance EMOSSENCE = put(new EmossenceAmbiance());

	public static <A extends DEAmbiance> A put(A ambiance) {
		SINGLETON_BY_CLASS.put(ambiance.getClass(), ambiance);
		return ambiance;
	}

	public static void loadClass() {}
}
