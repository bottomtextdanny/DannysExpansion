package net.bottomtextdanny.braincell.mod.structure.object_tables;

import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.BCBoatRenderer;
import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.BCRegistry;
import net.bottomtextdanny.braincell.mod.world.builtin_entities.BCBoat;
import net.bottomtextdanny.danny_expannny.object_tables.DEEntities;
import net.bottomtextdanny.dannys_expansion.core.Util.dannybaseentity.DEEntityBuilder;
import net.bottomtextdanny.dannys_expansion.core.Util.dannybaseentity.DELivingEntityBuilder;
import net.bottomtextdanny.dannys_expansion.core.Util.dannybaseentity.DEMobBuilder;
import net.bottomtextdanny.dannys_expansion.core.Util.setup.EntityWrap;
import net.minecraft.world.entity.*;

public class BraincellEntities {
    public static final BCRegistry<EntityType<?>> ENTRIES = new BCRegistry<>();

    //*\\*//*\\*//*\\MISC\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//
    public static final EntityWrap<EntityType<BCBoat>> BOAT =
            DEEntities.startE("boat", BCBoat::new)
                    .classification(MobCategory.MISC)
                    .dimensions(1.375F, 0.5625F)
                    .renderer(() -> BCBoatRenderer::new)
                    .build();
    //*\\*//*\\*//*\\MISC\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//

    private static <E extends Entity> DEEntityBuilder<E> startE(String entityId, EntityType.EntityFactory<E> factory) {
        DEEntityBuilder<E> builder = new DEEntityBuilder<E>(Braincell.DEFERRING_STATE);
        builder.declare(entityId, factory);
        return builder;
    }

    private static <E extends LivingEntity> DELivingEntityBuilder<E> startLE(String entityId, EntityType.EntityFactory<E> factory) {
        DELivingEntityBuilder<E> builder = new DELivingEntityBuilder<>(Braincell.DEFERRING_STATE);
        builder.declare(entityId, factory);
        return builder;
    }

    private static <E extends Mob> DEMobBuilder<E> start(String entityId, EntityType.EntityFactory<E> factory) {
        DEMobBuilder<E> builder = new DEMobBuilder<E>(Braincell.DEFERRING_STATE);
        builder.declare(entityId, factory);
        return builder;
    }
}
