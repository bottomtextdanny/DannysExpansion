package net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation.recipe;

public record SolvedLazyRecipe(LazyRecipe recipe, LazyCraftState state) {

    public static SolvedLazyRecipe of(LazyRecipe recipe, LazyCraftState state) {
        return new SolvedLazyRecipe(recipe, state);
    }
}
