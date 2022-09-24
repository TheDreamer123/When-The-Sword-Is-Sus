package net.dreamer.wtsis.recipe;

import net.dreamer.wtsis.item.WtsisItemRegistry;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class SlimeSwordRecipe extends SpecialCraftingRecipe {
    public SlimeSwordRecipe(Identifier identifier) {
        super(identifier);
    }

    public boolean matches(CraftingInventory craftingInventory,World world) {
        boolean bl1 = false;
        boolean bl2 = false;

        for(int i = 0; i < craftingInventory.size(); ++i) {
            ItemStack stack = craftingInventory.getStack(i);
            if (!stack.isEmpty()) {
                if(stack.getItem() == WtsisItemRegistry.SLIME_SWORD && !bl1) {
                    NbtCompound nbt = stack.getOrCreateNbt();
                    if(nbt.contains("moltenTime")) bl1 = true;
                }
                else if(stack.getItem() == Items.WATER_BUCKET && !bl2) bl2 = true;
                else {
                    if(stack != ItemStack.EMPTY) return false;
                }
            }
        }

        return bl1 && bl2;
    }

    public ItemStack craft(CraftingInventory craftingInventory) {
        ItemStack stack = ItemStack.EMPTY;

        for(int i = 0; i < craftingInventory.size(); ++i) {
            ItemStack stack2 = craftingInventory.getStack(i);
            if(!stack2.isEmpty() && stack2.getItem() == WtsisItemRegistry.SLIME_SWORD) {
                stack = stack2;
                break;
            }
        }

        ItemStack stack3 = new ItemStack(WtsisItemRegistry.SLIME_SWORD, 1);
        stack3.getOrCreateNbt().copyFrom(stack.getNbt()).remove("moltenTime");

        return stack3;
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return false;
    }

    public boolean fits(int width,int height) {
        return width >= 2 && height >= 2;
    }

    public RecipeSerializer<?> getSerializer() {
        return WtsisRecipeSerializer.MOLTEN_SLIME_SWORD;
    }
}
