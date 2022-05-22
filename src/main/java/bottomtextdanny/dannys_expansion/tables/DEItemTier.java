package bottomtextdanny.dannys_expansion.tables;

import bottomtextdanny.dannys_expansion.tables.items.DEItems;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum DEItemTier implements Tier {
	ECLIPSE(4, 3781, 9.0F, 5.5F, 12, () -> {
		return Ingredient.of(DEItems.BULLET.get());
	}),
    SCORPION_SWORD(4, 1781, 9.0F, 4.5F, 15, () -> {
        return Ingredient.of(DEItems.SCORPION_GLAND.get());
    }),

    KLIFOUR(4, 1781, 9.0F, 3.5F, 15, () -> {
        return Ingredient.of(DEItems.SCORPION_GLAND.get());
    });

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final LazyLoadedValue<Ingredient> repairMaterial;

    DEItemTier(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
        this.harvestLevel = harvestLevelIn;
        this.maxUses = maxUsesIn;
        this.efficiency = efficiencyIn;
        this.attackDamage = attackDamageIn;
        this.enchantability = enchantabilityIn;
        this.repairMaterial = new LazyLoadedValue<>(repairMaterialIn);
    }

    public int getUses() {
        return this.maxUses;
    }

    public float getSpeed() {
        return this.efficiency;
    }

    public float getAttackDamageBonus() {
        return this.attackDamage;
    }

    public int getLevel() {
        return this.harvestLevel;
    }

    public int getEnchantmentValue() {
        return this.enchantability;
    }

    public Ingredient getRepairIngredient() {
        return this.repairMaterial.get();
    }
}
