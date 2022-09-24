package net.dreamer.wtsis.criteria;


import net.minecraft.advancement.criterion.Criteria;

public class WtsisCriteriaRegistry {
    public static DeathCuzGlassCriterion DEATH_CUZ_GLASS;
    public static InventoryChangedWithEnchantmentsCriterion INVENTORY_CHANGED_WITH_ENCHANTMENTS;
    public static InventoryChangedWithoutEnchantmentsCriterion INVENTORY_CHANGED_WITHOUT_ENCHANTMENTS;
    public static ConsumeItemWithEnchantmentCriterion CONSUME_ITEM_WITH_ENCHANTMENT;
    public static ThrowItemCriterion THROW_ITEM;
    public static KillSlashJCriterion KILL_SLASH_J;
    public static ExplosionzillaCriterion EXPLOSIONZILLA;
    public static DatDoNotBeHotCriterion DAT_DO_NOT_BE_HOT;
    public static DatDoBeHotCriterion DAT_DO_BE_HOT;
    public static YouBounceMeRightRoundCriterion YOU_BOUNCE_ME_RIGHT_ROUND;

    public static void register() {
        DEATH_CUZ_GLASS = Criteria.register(new DeathCuzGlassCriterion());
        INVENTORY_CHANGED_WITH_ENCHANTMENTS = Criteria.register(new InventoryChangedWithEnchantmentsCriterion());
        INVENTORY_CHANGED_WITHOUT_ENCHANTMENTS = Criteria.register(new InventoryChangedWithoutEnchantmentsCriterion());
        CONSUME_ITEM_WITH_ENCHANTMENT = Criteria.register(new ConsumeItemWithEnchantmentCriterion());
        THROW_ITEM = Criteria.register(new ThrowItemCriterion());
        KILL_SLASH_J = Criteria.register(new KillSlashJCriterion());
        EXPLOSIONZILLA = Criteria.register(new ExplosionzillaCriterion());
        DAT_DO_NOT_BE_HOT = Criteria.register(new DatDoNotBeHotCriterion());
        DAT_DO_BE_HOT = Criteria.register(new DatDoBeHotCriterion());
        YOU_BOUNCE_ME_RIGHT_ROUND = Criteria.register(new YouBounceMeRightRoundCriterion());
    }
}
