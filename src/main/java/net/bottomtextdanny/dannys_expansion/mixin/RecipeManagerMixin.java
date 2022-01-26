package net.bottomtextdanny.dannys_expansion.mixin;

import com.google.gson.JsonElement;
import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.world.ChestOverriderRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {
	@Shadow
	private Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> recipes;

	@Inject(at = @At(value = "TAIL"), method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", remap = false)
	public <C extends Container, T extends Recipe<C>> void getRecipeFor(Map<ResourceLocation, JsonElement> jsonparseexception, ResourceManager resourcelocation, ProfilerFiller entry, CallbackInfo ci) {
		Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> temp = new LinkedHashMap<>(this.recipes);
		Map<ResourceLocation, Recipe<?>> child = new LinkedHashMap<>(this.recipes.size());
		ResourceLocation loc = new ResourceLocation(Braincell.ID, "chest_overrider");
		child.put(loc, new ChestOverriderRecipe(loc));
		child.putAll(temp.get(RecipeType.CRAFTING));
		temp.replace(RecipeType.CRAFTING, child);
		this.recipes = temp;
	}
}
