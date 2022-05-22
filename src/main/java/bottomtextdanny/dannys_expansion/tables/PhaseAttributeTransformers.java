package bottomtextdanny.dannys_expansion.tables;

import bottomtextdanny.dannys_expansion._base.capabilities.world.LevelPhaseModule;
import bottomtextdanny.dannys_expansion.content.entities.mob.hollow_armor.HollowArmor;
import bottomtextdanny.dannys_expansion.content.entities._modules.phase_affected_provider.AttributeTransformer;
import com.google.common.collect.ImmutableMap;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;

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

        current[POST_ENDER_DRAGON] = new AttributeTransformer<HollowArmor>(Attributes.MAX_HEALTH, (phase, entity, value) -> value * 1.25);
        builder.put(DEEntities.HOLLOW_ARMOR.get(), current);
    }

    public AttributeTransformer<?>[] getTransformersForType(EntityType<?> type) {
        return this.lookupTable.getOrDefault(type, EMPTY_TRANSFORMER_ARRAY);
    }
}
