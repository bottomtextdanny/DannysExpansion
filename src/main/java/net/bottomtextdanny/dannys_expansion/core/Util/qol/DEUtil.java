package net.bottomtextdanny.dannys_expansion.core.Util.qol;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nullable;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import java.util.SplittableRandom;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Supplier;

public class DEUtil {
    public static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();
    public static final SplittableRandom S_RANDOM = new SplittableRandom();
	public static final SplittableRandom C_RANDOM = new SplittableRandom();
	public static final SplittableRandom C_ENTITY_LOGIC_RANDOM = C_RANDOM.split();
    public static final SplittableRandom ENTITY_LOGIC_RANDOM = S_RANDOM.split();
	public static final SplittableRandom PSYCHE_RANDOM = S_RANDOM.split();
	public static final SplittableRandom PARTICLE_RANDOM = C_RANDOM.split();
	public static final SplittableRandom GOAL_RANDOM = S_RANDOM.split();
	public static int identifierProvider;
    public static float PARTIAL_TICK;
	public static boolean curioLoaded;
	
	public static int getIdentifier() {
		if (identifierProvider == Integer.MAX_VALUE) {
			identifierProvider = 0;
		}
		return identifierProvider++;
	}
	
	public static <T> T getByName(Registry<T> reg, String name) {
		return reg.get(new ResourceLocation(DannysExpansion.ID, name));
	}

    public static <T> T getRandomObject(List<T> selections) {
        return selections.get(ENTITY_LOGIC_RANDOM.nextInt(selections.size()));
    }

    public static <T> T getRandomObject(T[] selections) {
        return selections[ENTITY_LOGIC_RANDOM.nextInt(selections.length)];
    }
	
    @SuppressWarnings("unchecked")
	public static <T> T[] merge(Class<T> clazz, T[] array, T... extra) {
    	T[] targetArray = (T[]) Array.newInstance(clazz, array.length + extra.length);
    	
    	System.arraycopy(array, 0, targetArray, 0, array.length);
	    System.arraycopy(extra, 0, targetArray, array.length, extra.length);
		return targetArray;
	}
	
	@SuppressWarnings("unchecked")
	public static <T, F> F[] arrayPopulateArray(Class<F> clazz, T[] referenceArray, Function<T, F> t) {
    	if (referenceArray != null && referenceArray.length > 0) {
    		F[] targetArray = (F[]) Array.newInstance(clazz, referenceArray.length);
		  
		    for (int i = 0; i < referenceArray.length; i++)
			    targetArray[i] = t.apply(referenceArray[i]);
		    
		    return targetArray;
	    }
		
		return null;
	}
	
	public static <T, D, F extends List<D>> F arrayPopulateList(F collection, T[] referenceArray, Function<T, D> t) {
		if (referenceArray != null && referenceArray.length > 0) {
			
			for (int i = 0; i < referenceArray.length; i++) {
				collection.set(i, t.apply(referenceArray[i]));
			}
			
			return collection;
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <F> F[] makeArray(Class<F> clazz, int length, Function<Integer, F> t) {
		F[] targetArray = (F[]) Array.newInstance(clazz, length);
		
		for (int i = 0; i < length; i++) targetArray[i] = t.apply(i);
		
		return targetArray;
		
	}
	
	@SuppressWarnings("unchecked")
	public static <RES, INPUT> RES[] collectionPopulateArray(Class<RES> clazz, Collection<INPUT> referenceCollection, Function<INPUT, RES> t) {
		if (referenceCollection != null && referenceCollection.size() > 0) {
			RES[] targetArray = (RES[]) Array.newInstance(clazz, referenceCollection.size());
			
			int[] counter = {0};
			referenceCollection.forEach(ref -> {
				targetArray[counter[0]] = t.apply(ref);
				counter[0]++;
			});
			
			return targetArray;
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <RES> RES[] collectionPopulateArray(Class<RES> clazz, Collection<RES> referenceCollection) {
		if (referenceCollection != null && referenceCollection.size() > 0) {
			RES[] targetArray = (RES[]) Array.newInstance(clazz, referenceCollection.size());
			
			int[] counter = {0};
			referenceCollection.forEach(ref -> {
				targetArray[counter[0]] = ref;
				counter[0]++;
			});
			
			return targetArray;
		}
		
		return null;
	}
	
	public static <OBJ> OBJ makeServerSide(OBJ object) {
		if (FMLLoader.getDist() == Dist.DEDICATED_SERVER) {
			return object;
		}
		
		return null;
	}
	
	public static <OBJ> OBJ makeClientSide(Supplier<OBJ> object) {
		if (FMLLoader.getDist() == Dist.CLIENT) {
			return object.get();
		}
		
		return null;
	}
	
	public static <OBJ> OBJ makeClientSide(OBJ object, Function<OBJ, OBJ> clientWrap) {
		if (FMLLoader.getDist() == Dist.CLIENT) {
			return clientWrap.apply(object);
		}
		
		return object;
	}
	
	public static void doClientSide(Runnable object) {
		if (FMLLoader.getDist() == Dist.CLIENT) {
			object.run();
		}
	}

	@Nullable
	public static String loadResource(ResourceLocation fileName) {
		try {
			StringWriter strBuf = new StringWriter();
			IOUtils.copy(Minecraft.getInstance().getResourceManager().getResource(fileName).getInputStream(), strBuf, "UTF-8");
			String str = strBuf.toString();

			return str;
		} catch(Exception e) {
			e.fillInStackTrace();
		}

		return null;
	}
	
	public static TextComponent translationWithReplacements(String translationKey, String... changes) {
		String translationString = new TranslatableComponent(translationKey).getString();
		int replacements = changes.length / 2;
		
		for (int i = 1; i <= replacements; i++) {
			translationString = translationString.replace(changes[i-1], changes[1]);
		}
		
		return new TextComponent(translationString);
	}
	
	public static String normalizeKey(String key) {
		StringBuilder stringBuilder = new StringBuilder(key);
		int length = stringBuilder.length();
		for (int i = 0; i < length; i++) {
			if (i == 0 || stringBuilder.charAt(i - 1) == '_') {
				if (i != 0) stringBuilder.setCharAt(i - 1, ' ');
				stringBuilder.setCharAt(i, Character.toUpperCase(stringBuilder.charAt(i)));
			}
		}
		return stringBuilder.toString();
	}
	
	public static void executeClientTask(Level world, Runnable action) {
		if (world.isClientSide()) action.run();
	}

    public static void loadData() {
		curioLoaded = ModList.get().isLoaded("curios");
	}
}
