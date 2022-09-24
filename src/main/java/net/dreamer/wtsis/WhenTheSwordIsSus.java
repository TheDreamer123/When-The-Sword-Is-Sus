package net.dreamer.wtsis;

import net.dreamer.wtsis.criteria.WtsisCriteriaRegistry;
import net.dreamer.wtsis.effect.WtsisStatusEffectRegistry;
import net.dreamer.wtsis.entity.WtsisEntityRegistry;
import net.dreamer.wtsis.item.WtsisItemRegistry;
import net.dreamer.wtsis.misc.WtsisCauldronBehavior;
import net.dreamer.wtsis.misc.WtsisDispenserBehavior;
import net.dreamer.wtsis.recipe.WtsisRecipeSerializer;
import net.dreamer.wtsis.sound.WtsisSoundRegistry;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class WhenTheSwordIsSus implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("When The Sword Is Sus:");
	public static String MOD_ID = "wtsis";

	@Override
	public void onInitialize() {
		String[] swords = new String[] {
				"Wooden sword",
				"Stone sword",
				"Iron sword",
				"Golden sword",
				"Diamond sword",
				"Netherite sword"
		};
		int i;
		do i = new Random().nextInt(3); while(i < 1);
		LOGGER.info("\n\"When The Sword Is Sus:\" says:\n{} was{}an impostor\n{} impostors left\n", swords[new Random().nextInt(swords.length)], new Random().nextInt(10) == 0 ? " " : " not ", i);



		WtsisCauldronBehavior.register();
		WtsisCriteriaRegistry.register();
		WtsisDispenserBehavior.register();
		WtsisEntityRegistry.register();
		WtsisItemRegistry.register();
		WtsisRecipeSerializer.register();
		WtsisSoundRegistry.register();
		WtsisStatusEffectRegistry.register();
	}
}
