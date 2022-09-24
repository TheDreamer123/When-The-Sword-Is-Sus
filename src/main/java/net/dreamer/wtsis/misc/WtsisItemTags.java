package net.dreamer.wtsis.misc;

import net.dreamer.wtsis.WhenTheSwordIsSus;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WtsisItemTags {
    public static final TagKey<Item> SCULK = TagKey.of(Registry.ITEM_KEY, new Identifier(WhenTheSwordIsSus.MOD_ID, "sculk"));
}
