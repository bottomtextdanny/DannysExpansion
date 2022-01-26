package net.bottomtextdanny.danny_expannny.capabilities.player;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class MiniAttributeInstance {
	private final MiniAttribute attribute;
	private float value;
	
	public MiniAttributeInstance(MiniAttribute attribute) {
		this.attribute = attribute;
		this.value = attribute.baseValue;
	}
	
	public void modify(AttributeModifier.Operation operation, float factor) {
		if (operation == AttributeModifier.Operation.ADDITION) {
            this.value += factor;
		} else if (operation == AttributeModifier.Operation.MULTIPLY_BASE) {
            this.value = this.attribute.baseValue * factor;
		} else {
            this.value *= factor;
		}
        this.value = Mth.clamp(this.value, this.attribute.clampMin, this.attribute.clampMax);
	}
	
	public double getValue() {
		return this.value;
	}
	
	public void reset() {
        this.value = this.attribute.baseValue;
	}
}
