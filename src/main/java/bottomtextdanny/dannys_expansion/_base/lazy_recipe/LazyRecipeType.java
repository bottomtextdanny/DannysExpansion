package bottomtextdanny.dannys_expansion._base.lazy_recipe;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import net.minecraft.resources.ResourceLocation;

public final class LazyRecipeType {
    public static final LazyRecipeType WORKBENCH = create("workbench");
    public static final LazyRecipeType ENGINEERING_STATION = create("engineering_station");
    private final ResourceLocation key;

    public LazyRecipeType(ResourceLocation key) {
        this.key = key;
        DannysExpansion.common().getLazyRecipeManager().tryAddType(this);
    }

    private static LazyRecipeType create(String name) {
        return new LazyRecipeType(new ResourceLocation(DannysExpansion.ID, name));
    }

    public ResourceLocation getKey() {
        return key;
    }

    public static void loadClass() {}
}
