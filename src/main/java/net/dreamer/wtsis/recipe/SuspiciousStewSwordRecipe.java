package net.dreamer.wtsis.recipe;

import com.google.gson.JsonObject;
import net.dreamer.wtsis.WhenTheSwordIsSus;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;

import java.util.Map;
import java.util.Objects;

public class SuspiciousStewSwordRecipe extends ShapedRecipe {
    final int width;
    final int height;
    final DefaultedList<Ingredient> input;
    final ItemStack output;
    final String group;

    public SuspiciousStewSwordRecipe(Identifier id,String group,int width,int height,DefaultedList<Ingredient> input,ItemStack output) {
        super(id,group,width,height,input,output);
        this.width = width;
        this.height = height;
        this.input = input;
        this.output = output;
        this.group = group;
    }

    @Override
    protected boolean matchesPattern(CraftingInventory inv,int offsetX,int offsetY,boolean flipped) {
        ItemStack stack = ItemStack.EMPTY;
        NbtElement element = null;
        for(int i = 0; i < inv.getWidth(); ++i) {
            for(int j = 0; j < inv.getHeight(); ++j) {
                int k = i - offsetX;
                int l = j - offsetY;
                Ingredient ingredient = Ingredient.EMPTY;
                if (k >= 0 && l >= 0 && k < this.width && l < this.height) {
                    if (flipped) ingredient = this.input.get(this.width - k - 1 + l * this.width);
                    else ingredient = this.input.get(k + l * this.width);
                }

                if (!ingredient.test(inv.getStack(i + j * inv.getWidth())))
                    return false;

                if(stack != ItemStack.EMPTY && inv.getStack(i + j * inv.getWidth()).getItem() == Items.SUSPICIOUS_STEW) {
                    ItemStack stack2 = inv.getStack(i + j * inv.getWidth());

                    if(stack2.getNbt() != null) {
                        boolean bl = stack2.getNbt().contains("Effects");
                        NbtElement element2 = stack2.getNbt().get("Effects");

                        if(element == null && bl)
                            return false;
                        if(element != null && !bl)
                            return false;
                        if(element != null && !Objects.equals(String.valueOf(element2),String.valueOf(element)))
                            return false;
                    }
                }

                if(inv.getStack(i + j * inv.getWidth()).getItem() == Items.SUSPICIOUS_STEW && stack == ItemStack.EMPTY && element == null) {
                    stack = inv.getStack(i + j * inv.getWidth());
                    if(stack.getNbt() != null && stack.getNbt().contains("Effects"))
                        element = stack.getNbt().get("Effects");
                }
            }
        }

        return true;
    }

    @Override
    public ItemStack craft(CraftingInventory craftingInventory) {
        for(int i = 0; i < craftingInventory.size(); i++) {
            ItemStack stack = craftingInventory.getStack(i);
            if(stack.getItem() == Items.SUSPICIOUS_STEW && stack.getOrCreateNbt().contains("Effects")) {
                NbtList list = stack.getOrCreateNbt().getList("Effects", 10);
                output.getOrCreateNbt().put("Effects", list);
            }
        }

        return output.copy();
    }

    @Override
    public ItemStack getOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return WtsisRecipeSerializer.SUSPICIOUS_STEW_SWORD;
    }

    public static class Serializer implements RecipeSerializer<SuspiciousStewSwordRecipe> {
        public Serializer() {
        }

        public SuspiciousStewSwordRecipe read(Identifier identifier, JsonObject jsonObject) {
            if(!Objects.equals(identifier,new Identifier(WhenTheSwordIsSus.MOD_ID, "suspicious_stew_sword")))
                throw new RuntimeException("wtsis:crafting_special_suspicious_stew_sword should only be used by wtsis:suspicious_stew_sword!");

            String string = JsonHelper.getString(jsonObject, "group", "");
            Map<String, Ingredient> map = SuspiciousStewSwordRecipe.readSymbols(JsonHelper.getObject(jsonObject, "key"));
            String[] strings = SuspiciousStewSwordRecipe.removePadding(SuspiciousStewSwordRecipe.getPattern(JsonHelper.getArray(jsonObject, "pattern")));
            int i = strings[0].length();
            int j = strings.length;
            DefaultedList<Ingredient> defaultedList = SuspiciousStewSwordRecipe.createPatternMatrix(strings, map, i, j);
            ItemStack itemStack = SuspiciousStewSwordRecipe.outputFromJson(JsonHelper.getObject(jsonObject, "result"));
            return new SuspiciousStewSwordRecipe(identifier, string, i, j, defaultedList, itemStack);
        }

        public SuspiciousStewSwordRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            int i = packetByteBuf.readVarInt();
            int j = packetByteBuf.readVarInt();
            String string = packetByteBuf.readString();
            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(i * j, Ingredient.EMPTY);

            for(int k = 0; k < defaultedList.size(); ++k) {
                defaultedList.set(k, Ingredient.fromPacket(packetByteBuf));
            }

            ItemStack itemStack = packetByteBuf.readItemStack();
            return new SuspiciousStewSwordRecipe(identifier, string, i, j, defaultedList, itemStack);
        }

        public void write(PacketByteBuf packetByteBuf, SuspiciousStewSwordRecipe suspiciousStewSword) {
            packetByteBuf.writeVarInt(suspiciousStewSword.width);
            packetByteBuf.writeVarInt(suspiciousStewSword.height);
            packetByteBuf.writeString(suspiciousStewSword.group);

            for (Ingredient ingredient : suspiciousStewSword.input)
                ingredient.write(packetByteBuf);

            packetByteBuf.writeItemStack(suspiciousStewSword.output);
        }
    }
}