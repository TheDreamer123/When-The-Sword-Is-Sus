package net.dreamer.wtsis.misc;

import net.dreamer.wtsis.item.WtsisItemRegistry;
import net.minecraft.block.cauldron.CauldronBehavior;

public interface WtsisCauldronBehavior extends CauldronBehavior {
    static void register() {
        WATER_CAULDRON_BEHAVIOR.put(WtsisItemRegistry.GLASS_SWORD, CLEAN_DYEABLE_ITEM);
    }
}
