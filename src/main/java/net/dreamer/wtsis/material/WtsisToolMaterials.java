package net.dreamer.wtsis.material;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Lazy;

import java.util.function.Supplier;

public enum WtsisToolMaterials implements ToolMaterial {
    BREAD_SWORD(0, 37, 2.0F, 0.0F, 17, () -> Ingredient.ofItems(Items.BREAD)),
    WET_BREAD_SWORD(0, 17, 2.0F, 0.0F, 13, () -> Ingredient.ofItems(Items.BREAD)),
    APPLE_SWORD(1, 55, 3.0F, 0.0F, 14, () -> Ingredient.ofItems(Items.APPLE)),
    GOLDEN_APPLE_SWORD(1, 109, 4.0F, 0.0F, 19, () -> Ingredient.EMPTY),
    GLASS_SWORD(0, 1, 0.0F, 0.0F, 14, () -> Ingredient.EMPTY),
    ANVIL_SWORD(2, 747, 3.0F, 0.0F, 10, () -> Ingredient.ofItems(new Item[] {Items.ANVIL,Items.CHIPPED_ANVIL,Items.DAMAGED_ANVIL})),
    TOTEM_SWORD(2,148,4.5F,0.0F,12, () -> Ingredient.ofItems(Items.TOTEM_OF_UNDYING)),
    GUNPOWDER_SWORD(0, 28, 2.0F, 0.0F, 6, () -> Ingredient.ofItems(Items.GUNPOWDER)),
    SLIME_SWORD(0, 84, 2.0F, 0.0F, 6, () -> Ingredient.ofItems(Items.SLIME_BALL)),
    FLINT_AND_STEEL_SWORD(1, 135, 4.5F, 0.0F, 9, () -> Ingredient.ofItems(Items.FLINT_AND_STEEL)),
    SUSPICIOUS_STEW_SWORD(0, 69, 2.5F, 0.0F, 12, () -> Ingredient.ofItems(Items.SUSPICIOUS_STEW)),
    SCULK_SWORD(0, 45, 3.0F, 0.0F, 21, () -> Ingredient.ofItems(Items.SCULK));


    private final int miningLevel;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Lazy<Ingredient> repairIngredient;

    WtsisToolMaterials(int miningLevel,int itemDurability,float miningSpeed,float attackDamage,int enchantability,Supplier<Ingredient> repairIngredient) {
        this.miningLevel = miningLevel;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = new Lazy<>(repairIngredient);
    }

    public int getDurability() {
        return this.itemDurability;
    }

    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    public int getMiningLevel() {
        return this.miningLevel;
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
