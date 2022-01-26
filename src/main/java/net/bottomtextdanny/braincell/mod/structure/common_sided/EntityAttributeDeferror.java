package net.bottomtextdanny.braincell.mod.structure.common_sided;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;

import java.util.function.Supplier;

public record EntityAttributeDeferror(
        Supplier<? extends EntityType<? extends LivingEntity>> wrappedType,
        Supplier<AttributeSupplier.Builder> rawAttributes) {

    public void register(EntityAttributeCreationEvent event) {
        event.put(this.wrappedType.get(), this.rawAttributes.get().build());
    }
}
