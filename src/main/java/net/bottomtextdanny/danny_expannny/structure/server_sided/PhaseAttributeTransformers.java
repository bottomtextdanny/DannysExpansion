package net.bottomtextdanny.danny_expannny.structure.server_sided;

import com.google.common.collect.ImmutableMap;
import net.bottomtextdanny.danny_expannny.object_tables.DEEntities;
import net.bottomtextdanny.danny_expannny.objects.entities.modules.phase_affected_provider.AttributeTransformer;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.hollow_armor.HollowArmor;
import net.bottomtextdanny.danny_expannny.capabilities.world.LevelPhaseModule;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.DEDICATED_SERVER)
public class PhaseAttributeTransformers {
    private static final AttributeTransformer<?>[] EMPTY_TRANSFORMER_ARRAY = {};
    private static final int POST_ENDER_DRAGON = 0;
    private final ImmutableMap<EntityType<?>, AttributeTransformer<?>[]> lookupTable;

    public PhaseAttributeTransformers() {
        super();
        ImmutableMap.Builder<EntityType<?>, AttributeTransformer<?>[]> builder = ImmutableMap.builder();
        populateLookup(builder);
        this.lookupTable = builder.build();
    }

    private void populateLookup(ImmutableMap.Builder<EntityType<?>, AttributeTransformer<?>[]> builder) {
        AttributeTransformer<?>[] current =
                new AttributeTransformer<?>[LevelPhaseModule.Phase.values().length - 1];

        current[POST_ENDER_DRAGON] = new AttributeTransformer<HollowArmor>(
                Attributes.MAX_HEALTH, (phase, entity, value) -> value * 1.25);
        builder.put(DEEntities.HOLLOW_ARMOR.get(), current);
    }

    public AttributeTransformer<?>[] getTransformersForType(EntityType<?> type) {
        return this.lookupTable.getOrDefault(type, EMPTY_TRANSFORMER_ARRAY);
    }
}
