package net.bottomtextdanny.dannys_expansion.core.Util.dannybaseentity;

import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.base.util.Connection;
import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.ModDeferringManager;
import net.bottomtextdanny.braincell.mod.structure.client_sided.EntityRendererDeferring;
import net.bottomtextdanny.braincell.mod.structure.client_sided.EntityRendererMaker;
import net.bottomtextdanny.braincell.mod.structure.common_sided.EntityAttributeDeferror;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.object_tables.DEEntities;
import net.bottomtextdanny.danny_expannny.objects.items.BCSpawnEggItem;
import net.bottomtextdanny.danny_expannny.objects.items.SpecialKiteItem;
import net.bottomtextdanny.dannys_expansion.core.Util.setup.EntityWrap;
import net.bottomtextdanny.dannys_expansion.core.interfaces.IEntityBuilder;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.function.Function;
import java.util.function.Supplier;

public class DELivingEntityBuilder<T extends LivingEntity> implements IEntityBuilder<T> {
    private final ModDeferringManager solving;
    protected String entityId;
    protected EntityType.EntityFactory<T> factory;
    protected MobCategory classification;
    protected Supplier<AttributeSupplier.Builder> attributeMap;
    protected float width, height;
    protected Supplier<SpecialKiteItem> kiteInst;
    protected Supplier<?> renderFactory;

    public DELivingEntityBuilder(ModDeferringManager solving) {
        super();
        this.solving = solving;
    }

    @Override
    public DELivingEntityBuilder<T> declare(String entityId, EntityType.EntityFactory<T> factory) {
        this.entityId = entityId;
        this.factory = factory;

        return this;
    }

    @Override
    public DELivingEntityBuilder<T> classification(MobCategory classification) {
        this.classification = classification;

        return this;
    }

    @Override
    public DELivingEntityBuilder<T> dimensions(float width, float height) {
        this.width = width;
        this.height = height;

        return this;
    }

    @Override
    public DELivingEntityBuilder<T> renderer(Supplier<? extends Function<?,?>> renderer) {
        Connection.doClientSide(() -> {
            this.renderFactory = () -> EntityRendererMaker.makeOf(renderer.get());
        });
        return this;
    }

    @Override
    public DELivingEntityBuilder<T> attributes(Supplier<AttributeSupplier.Builder> attributeMap) {
        this.attributeMap = attributeMap;
        return this;
    }

    @Override
    public DELivingEntityBuilder<T> spawn(SpawnPlacements.Type placement, Heightmap.Types heightMap, SpawnPlacements.SpawnPredicate<T> pred) {
        throw new UnsupportedOperationException("cannot configure spawn of a non-mob entity type");
    }

    @Override
    public DELivingEntityBuilder<T> egg(BCSpawnEggItem.Builder egg) {
		throw new UnsupportedOperationException("cannot infer egg on a non-mob entity type");
    }

    @Override
    public DELivingEntityBuilder<T> kite(Supplier<SpecialKiteItem> kite) {
        this.kiteInst = kite;
        return this;
    }

    @Override
    public EntityWrap<EntityType<T>> build() {
        EntityWrap<EntityType<T>> type = new EntityWrap(new ResourceLocation(DannysExpansion.ID, this.entityId), () -> EntityType.Builder.of(this.factory, this.classification).sized(this.width, this.height).build(DannysExpansion.ID + ":" + this.entityId));
        type.setupForDeferring(this.solving);
        DEEntities.ENTRIES.addDeferredSolving(type);

        if (this.kiteInst != null) {
			type.inferKite(this.kiteInst);
        }
        if (this.attributeMap != null) {
            Braincell.common().getEntityCoreDataDeferror()
                    .deferAttributeAttachment(new EntityAttributeDeferror(type, this.attributeMap));
        }
        Connection.doClientSide(() -> {
            if (this.renderFactory != null) {
                Braincell.client().getEntityRendererDeferror().add(new EntityRendererDeferring<>(type, (Supplier<EntityRendererProvider<T>>)this.renderFactory));
            }
        });
        return type;
    }
}
