package bottomtextdanny.dannys_expansion.content.entities._modules.phase_affected_provider;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;

public record AttributeTransformer<E extends LivingEntity>(Attribute attribute, AttributeFunction<E> transformSupplier) {}
