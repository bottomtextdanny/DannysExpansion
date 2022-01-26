package net.bottomtextdanny.dannys_expansion.core.Util.setup;

import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.DeferrorType;
import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.Wrap;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.object_tables.items.DEItems;
import net.bottomtextdanny.danny_expannny.objects.items.BCSpawnEggItem;
import net.bottomtextdanny.danny_expannny.objects.items.SpecialKiteItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public class EntityWrap<T extends EntityType<?>> extends Wrap<T> {
	private Supplier<SpecialKiteItem> kiteSup;
	private BCSpawnEggItem egg;
	private SpecialKiteItem kite;
	
	public EntityWrap(ResourceLocation name, Supplier<T> sup) {
		super(name, sup);
	}
	
	public void inferKite(Supplier<SpecialKiteItem> kiteSup) {
		this.kiteSup = kiteSup;
	}
	
	@Override
	public void solve() {
		super.solve();
		Optional<BCSpawnEggItem.Builder> eggBuilderOp =
				Braincell.common().getEntityCoreDataDeferror().getEggBuilder(this.key);
		if (eggBuilderOp.isPresent()) {
			ResourceLocation eggKey = new ResourceLocation(getModSolvingState().getModID(), this.key.getPath() + "_spawn_egg");
			BCSpawnEggItem.Builder eggBuilder = eggBuilderOp.get();

			Objects.requireNonNull(eggBuilderOp.get(), String.join("Attempted to register a null egg for entity type ", this.key.toString()));
			this.egg = eggBuilder.setTypeSupplier(() -> (EntityType<Mob>)this.obj).build();
			getModSolvingState().getRegistryDeferror(DeferrorType.ITEM).get().addDeferredRegistry(() -> this.egg);
			this.getModSolvingState().doHooksForObject(DeferrorType.ITEM, this.egg);
			if (eggBuilder.getSort() != -1) {
				Braincell.common().getItemSortData().setSortValue(eggKey, eggBuilder.getSort());
			}
			this.egg.setRegistryName(eggKey);
		}
		
		if (this.kiteSup != null) {
			this.kite = this.kiteSup.get();
			this.kiteSup = null;
			this.kite.setTextureName(this.key.getPath());
			this.kite.setTypeAndLock(() -> this.obj);
			DEItems.ENTRIES.addDeferredRegistry(() -> this.kite);
			this.getModSolvingState().doHooksForObject(DeferrorType.ITEM, this.kite);
            this.kite.setRegistryName(new ResourceLocation(DannysExpansion.ID, this.key.getPath() + "_kite"));
		}
	}
}
