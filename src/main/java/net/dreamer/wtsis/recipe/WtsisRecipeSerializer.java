package net.dreamer.wtsis.recipe;

import net.dreamer.wtsis.WhenTheSwordIsSus;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WtsisRecipeSerializer {
    public static SpecialRecipeSerializer<GoldenAppleSwordRecipe> GOLDEN_APPLE_SWORD = new SpecialRecipeSerializer<>(GoldenAppleSwordRecipe::new);
    public static SpecialRecipeSerializer<MoltenSlimeSwordRecipe> MOLTEN_SLIME_SWORD = new SpecialRecipeSerializer<>(MoltenSlimeSwordRecipe::new);
    public static SpecialRecipeSerializer<SlimeSwordRecipe> SLIME_SWORD = new SpecialRecipeSerializer<>(SlimeSwordRecipe::new);
    public static RecipeSerializer<SuspiciousStewSwordRecipe> SUSPICIOUS_STEW_SWORD = new SuspiciousStewSwordRecipe.Serializer();

    public static void register() {
        Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(WhenTheSwordIsSus.MOD_ID, "crafting_special_golden_apple_sword"),GOLDEN_APPLE_SWORD);
        Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(WhenTheSwordIsSus.MOD_ID, "crafting_special_molten_slime_sword"), MOLTEN_SLIME_SWORD);
        Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(WhenTheSwordIsSus.MOD_ID, "crafting_special_slime_sword"), SLIME_SWORD);
        Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(WhenTheSwordIsSus.MOD_ID, "crafting_special_suspicious_stew_sword"), SUSPICIOUS_STEW_SWORD);
    }
}
