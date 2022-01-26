package net.bottomtextdanny.dannys_expansion.core.Util.dannybaseentity;

import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.base.util.Connection;
import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.ModDeferringManager;
import net.bottomtextdanny.braincell.mod.structure.client_sided.EntityRendererDeferring;
import net.bottomtextdanny.braincell.mod.structure.client_sided.EntityRendererMaker;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.object_tables.DEEntities;
import net.bottomtextdanny.danny_expannny.objects.items.BCSpawnEggItem;
import net.bottomtextdanny.danny_expannny.objects.items.SpecialKiteItem;
import net.bottomtextdanny.dannys_expansion.core.Util.setup.EntityWrap;
import net.bottomtextdanny.dannys_expansion.core.interfaces.IEntityBuilder;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.function.Function;
import java.util.function.Supplier;

public class DEEntityBuilder<T extends Entity> implements IEntityBuilder<T> {
    private final ModDeferringManager solving;
    protected String entityId;
    protected EntityType.EntityFactory<T> factory;
    protected MobCategory classification;
    protected float width, height;
    protected Supplier<?> renderFactory;

    public DEEntityBuilder(ModDeferringManager solving) {
        super();
        this.solving = solving;
    }

    @Override
    public DEEntityBuilder<T> declare(String entityId, EntityType.EntityFactory<T> factory) {
        this.entityId = entityId;
        this.factory = factory;

        return this;
    }

    @Override
    public DEEntityBuilder<T> classification(MobCategory classification) {
        this.classification = classification;

        return this;
    }

    @Override
    public DEEntityBuilder<T> dimensions(float width, float height) {
        this.width = width;
        this.height = height;

        return this;
    }

    @Override
    public DEEntityBuilder<T> renderer(Supplier<? extends Function<?,?>> renderer) {
        Connection.doClientSide(() -> {
            this.renderFactory = () -> EntityRendererMaker.makeOf(renderer.get());
        });
        return this;
    }

    @Override
    public DEEntityBuilder<T> attributes(Supplier<AttributeSupplier.Builder> attributeMap) {
        throw new UnsupportedOperationException();
    }

    @Override
    public DEEntityBuilder<T> spawn(SpawnPlacements.Type placement, Heightmap.Types heightMap, SpawnPlacements.SpawnPredicate<T> pred) {
        throw new UnsupportedOperationException();
    }

    @Override
    public DEEntityBuilder<T> egg(BCSpawnEggItem.Builder egg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public DEEntityBuilder<T> kite(Supplier<SpecialKiteItem> kite) {
        throw new UnsupportedOperationException();
    }

    @Override
    public EntityWrap<EntityType<T>> build() {
        EntityWrap<EntityType<T>> type = new EntityWrap<EntityType<T>>(new ResourceLocation(DannysExpansion.ID, this.entityId), () -> EntityType.Builder.of(this.factory, this.classification).sized(this.width, this.height).build(DannysExpansion.ID + ":" + this.entityId));
        type.setupForDeferring(this.solving);
        DEEntities.ENTRIES.addDeferredSolving(type);

        Connection.doClientSide(() -> {
            if (this.renderFactory != null) {
                Braincell.client().getEntityRendererDeferror().add(new EntityRendererDeferring<>(type, (Supplier<EntityRendererProvider<T>>)this.renderFactory));
            }
        });
        return type;
    }
}
