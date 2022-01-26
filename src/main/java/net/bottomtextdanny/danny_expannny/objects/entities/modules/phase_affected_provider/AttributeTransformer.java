package net.bottomtextdanny.danny_expannny.objects.entities.modules.phase_affected_provider;

import net.bottomtextdanny.danny_expannny.objects.entities.modules.phase_affected_provider.AttributeFunction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;

public record AttributeTransformer<E extends LivingEntity>(Attribute attribute, AttributeFunction<E> transformSupplier) {}
