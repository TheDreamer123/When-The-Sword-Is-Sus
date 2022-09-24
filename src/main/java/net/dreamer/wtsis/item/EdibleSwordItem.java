package net.dreamer.wtsis.item;

import net.minecraft.item.*;
import net.minecraft.sound.SoundEvent;

public class EdibleSwordItem extends SwordItem {
    public SoundEvent eatSound;

    public EdibleSwordItem(ToolMaterial toolMaterial,int attackDamage,float attackSpeed,FoodComponent component,SoundEvent eatSound) {
        super(toolMaterial,attackDamage,attackSpeed,new Item.Settings().group(ItemGroup.COMBAT).food(component));
        this.eatSound = eatSound;
    }

    @Override
    public SoundEvent getDrinkSound() {
        return eatSound;
    }

    @Override
    public SoundEvent getEatSound() {
        return eatSound;
    }
}
