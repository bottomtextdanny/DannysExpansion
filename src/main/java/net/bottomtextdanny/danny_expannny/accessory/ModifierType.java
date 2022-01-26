package net.bottomtextdanny.danny_expannny.accessory;

import net.bottomtextdanny.danny_expannny.objects.accessories.CoreAccessory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

public enum ModifierType {
	MOVEMENT_SPEED_MLT(UUID.fromString("975a4e79-8828-4fe5-8441-6196a2d17c6a"), () -> Attributes.MOVEMENT_SPEED, AttributeModifier.Operation.MULTIPLY_TOTAL, CoreAccessory::getFinalSpeedMultiplier),
	ATTACK_SPEED_ADD(UUID.fromString("1cc7f086-5151-11ec-bf63-0242ac130002"), () -> Attributes.ATTACK_SPEED, AttributeModifier.Operation.ADDITION, CoreAccessory::getFinalAttackSpeedAddition),
	ATTACK_DAMAGE_ADD(UUID.fromString("c9f149a2-5150-11ec-bf63-0242ac130002"), () -> Attributes.ATTACK_DAMAGE, AttributeModifier.Operation.ADDITION, CoreAccessory::getFinalAttackDamageAddition),
	ATTACK_KNOCKBACK_ADD(UUID.fromString("db9dd8e6-5150-11ec-bf63-0242ac130002"), () -> Attributes.ATTACK_KNOCKBACK, AttributeModifier.Operation.ADDITION, CoreAccessory::getFinalAttackKnockbackAddition),
	KNOCKBACK_RESISTANCE_ADD(UUID.fromString("e23297e6-5150-11ec-bf63-0242ac130002"), () -> Attributes.KNOCKBACK_RESISTANCE, AttributeModifier.Operation.ADDITION, CoreAccessory::getFinalKnockbackResistanceAddition),
	KNOCKBACK_RESISTANCE_MLT(UUID.fromString("e7417658-5150-11ec-bf63-0242ac130002"), () -> Attributes.KNOCKBACK_RESISTANCE, AttributeModifier.Operation.MULTIPLY_TOTAL, CoreAccessory::getFinalKnockbackResistanceMultiplier),
	ARMOR_ADD(UUID.fromString("ee1ca36c-5150-11ec-bf63-0242ac130002"), () -> Attributes.ARMOR, AttributeModifier.Operation.ADDITION, CoreAccessory::getFinalArmorAddition),
	LUCK_ADD(UUID.fromString("f3afe3fc-5150-11ec-bf63-0242ac130002"), () -> Attributes.LUCK, AttributeModifier.Operation.ADDITION, CoreAccessory::getFinalLuckAddition);
	
	public UUID uuid;
	public Supplier<Attribute> attribute;
	public Function<CoreAccessory, Double> valueGetter;
	public AttributeModifier.Operation operation;
	public double baseValue;
	
	ModifierType(UUID uuid, Supplier<Attribute> attribute, AttributeModifier.Operation operation, Function<CoreAccessory, Double> valueGetter) {
		this.uuid = uuid;
		this.attribute = attribute;
		this.operation = operation;
		this.valueGetter = valueGetter;
		if (operation == AttributeModifier.Operation.ADDITION) this.baseValue = 0.0;
		else this.baseValue = 1.0;
	}
}
