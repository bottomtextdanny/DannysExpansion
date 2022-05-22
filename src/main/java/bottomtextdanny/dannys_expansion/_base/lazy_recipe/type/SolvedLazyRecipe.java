package bottomtextdanny.dannys_expansion._base.lazy_recipe.type;

public record SolvedLazyRecipe(LazyRecipe recipe, LazyCraftState state) {

    public static SolvedLazyRecipe of(LazyRecipe recipe, LazyCraftState state) {
        return new SolvedLazyRecipe(recipe, state);
    }
}
