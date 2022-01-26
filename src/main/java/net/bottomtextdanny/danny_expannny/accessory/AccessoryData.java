package net.bottomtextdanny.danny_expannny.accessory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.bottomtextdanny.braincell.underlying.misc.CompressedBooleanGroup;
import net.bottomtextdanny.dannys_expansion.core.Util.Pair;
import net.bottomtextdanny.danny_expannny.capabilities.player.MiniAttribute;

import java.util.LinkedList;
import java.util.List;

public final class AccessoryData {
    public final List<ModifierType> modifierIterable;
    private final CompressedBooleanGroup usedModifiedAttributes;
    private final CompressedBooleanGroup usedModifiedLesserAttributes;
    private final double[] modifierValues;
    private final float[] lesserModifierValues;

    private AccessoryData(List<ModifierType> modifierIterable, CompressedBooleanGroup modifiedAttributes, CompressedBooleanGroup modifiedLesserAttribute, double[] attributeModifierBaseValues, float[] lesserAttributeModifierBaseValues) {
        super();
        this.modifierIterable = modifierIterable;
        this.usedModifiedAttributes = modifiedAttributes;
        this.usedModifiedLesserAttributes = modifiedLesserAttribute;
        this.modifierValues = attributeModifierBaseValues;
        this.lesserModifierValues = lesserAttributeModifierBaseValues;
    }

    public static AccessoryData create(List<Pair<ModifierType, Double>> modifiers, List<Pair<MiniAttribute, Float>> lesserModifiers) {
        double[] newModifiedValues = new double[ModifierType.values().length];
        float[] newLesserModifiedValues = new float[MiniAttribute.values().length];
        CompressedBooleanGroup newModifiedAttributes = CompressedBooleanGroup.create(ModifierType.values().length);
        CompressedBooleanGroup newModifiedLesserAttributes = CompressedBooleanGroup.create(MiniAttribute.values().length);
        LinkedList<ModifierType> newModifierList = Lists.newLinkedList();

        for (Pair<ModifierType, Double> modifierData : modifiers) {
            ModifierType modifier = modifierData.getKey();
            double modifierValue = modifierData.getValue();
            int idx = modifier.ordinal();

            newModifierList.add(modifier);
            newModifiedValues[idx] = modifierValue;
            newModifiedAttributes.set(idx, true);
        }

        for (Pair<MiniAttribute, Float> miniAttributeData : lesserModifiers) {
            MiniAttribute attribute = miniAttributeData.getKey();
            float modifierValue = miniAttributeData.getValue();
            int idx = attribute.ordinal();

            newLesserModifiedValues[idx] = modifierValue;
            newModifiedLesserAttributes.set(idx, true);
        }

        return new AccessoryData(ImmutableList.copyOf(newModifierList), newModifiedAttributes, newModifiedLesserAttributes, newModifiedValues, newLesserModifiedValues);
    }

    public boolean containsModifier(ModifierType modifierType) {
        return this.usedModifiedAttributes.get(modifierType.ordinal());
    }

    public boolean containsLesserModifier(MiniAttribute attribute) {
        return this.usedModifiedLesserAttributes.get(attribute.ordinal());
    }

    public double getBaseValue(ModifierType modifierType) {
        return this.modifierValues[modifierType.ordinal()];
    }

    public float getBaseValue(MiniAttribute attribute) {
        return this.lesserModifierValues[attribute.ordinal()];
    }
}
