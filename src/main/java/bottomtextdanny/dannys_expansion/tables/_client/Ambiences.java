package bottomtextdanny.dannys_expansion.tables._client;

import bottomtextdanny.dannys_expansion.content._client.ambiances.Ambiance;
import bottomtextdanny.dannys_expansion.content._client.ambiances.EndAmbiance;
import com.google.common.collect.Maps;
import com.mojang.math.Vector3f;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public final class Ambiences {
	public static final Map<Class<? extends Ambiance>, Ambiance> SINGLETON_BY_CLASS = Maps.newIdentityHashMap();
	public static final Ambiance NONE = new Ambiance() {
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

	public static <A extends Ambiance> A put(A ambiance) {
		SINGLETON_BY_CLASS.put(ambiance.getClass(), ambiance);
		return ambiance;
	}

	public static void loadClass() {}
}
