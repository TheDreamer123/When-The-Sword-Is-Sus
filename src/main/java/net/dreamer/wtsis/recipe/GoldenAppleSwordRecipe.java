package net.dreamer.wtsis.recipe;

import net.dreamer.wtsis.item.WtsisItemRegistry;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class GoldenAppleSwordRecipe extends SpecialCraftingRecipe {
    public GoldenAppleSwordRecipe(Identifier id) {
        super(id);
    }

    public boolean matches(CraftingInventory craftingInventory,World world) {
        if(craftingInventory.getWidth() == 3 && craftingInventory.getHeight() == 3) {
            for(int i = 0; i < craftingInventory.getWidth(); ++i)
                for(int j = 0; j < craftingInventory.getHeight(); ++j) {
                    ItemStack itemStack = craftingInventory.getStack(i + j * craftingInventory.getWidth());
                    if(itemStack.isEmpty()) return false;

                    if(i == 1 && j == 1) {
                        if(!itemStack.isOf(WtsisItemRegistry.APPLE_SWORD)) return false;
                    } else {
                        if(!itemStack.isOf(Items.GOLD_INGOT)) return false;
                        if(itemStack.getCount() < 2) return false;
                    }
                }

            return true;
        } else return false;
    }

    public ItemStack craft(CraftingInventory craftingInventory) {
        ItemStack itemStack = ItemStack.EMPTY;

        for(int i = 0; i < craftingInventory.size(); ++i) {
            ItemStack itemStack2 = craftingInventory.getStack(i);
            if(!itemStack2.isEmpty() && itemStack2.getItem() == WtsisItemRegistry.APPLE_SWORD) {
                itemStack = itemStack2;
                break;
            }
        }

        ItemStack itemStack3 = new ItemStack(WtsisItemRegistry.GOLDEN_APPLE_SWORD, 1);
        itemStack3.getOrCreateNbt().copyFrom(itemStack.getNbt());

        return itemStack3;
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return false;
    }

    @Override
    public DefaultedList<ItemStack> getRemainder(CraftingInventory inventory) {
        DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(inventory.size(), ItemStack.EMPTY);

        for(int i = 0; i < defaultedList.size(); ++i) {
            ItemStack stack = inventory.getStack(i);
            if(stack.isOf(Items.GOLD_INGOT))
                stack.decrement(1);
        }

        return defaultedList;
    }

    public boolean fits(int width, int height) {
        return width >= 2 && height >= 2;
    }

    public RecipeSerializer<?> getSerializer() {
        return WtsisRecipeSerializer.GOLDEN_APPLE_SWORD;
    }
}
