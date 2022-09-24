package net.dreamer.wtsis.misc;

import net.minecraft.item.FoodComponent;

public class WtsisFoodComponents {
    public static final FoodComponent BREAD_SWORD = createComponent(3, 0.3F, false);
    public static final FoodComponent APPLE_SWORD = createComponent(2, 0.1F, false);
    public static final FoodComponent GOLDEN_APPLE_SWORD = createComponent(2, 0.48F, true);
    public static final FoodComponent ANVIL_SWORD = createComponent(10, 0.8F, false);
    public static final FoodComponent TOTEM_SWORD = createComponent(4, 0.5F, true);
    public static final FoodComponent GUNPOWDER_SWORD = createComponent(2, 0.5F, true);
    public static final FoodComponent SLIME_SWORD = createComponent(3, 0.5F, true);
    public static final FoodComponent FLINT_AND_STEEL_SWORD = createComponent(3, 0.3F, true);
    public static final FoodComponent SUSPICIOUS_STEW_SWORD = createComponent(2, 0.2F, true);
    public static final FoodComponent SCULK_SWORD = createComponent(3, 0.4F, true);
    public static final FoodComponent IMAGINE = new FoodComponent.Builder().alwaysEdible().build();


    public static FoodComponent createComponent(int hunger,float saturation,boolean alwaysEdible) {
        if(!alwaysEdible) return new FoodComponent.Builder().hunger(hunger).saturationModifier(saturation).build();
        else return new FoodComponent.Builder().hunger(hunger).saturationModifier(saturation).alwaysEdible().build();
    }
}
