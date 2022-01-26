package net.bottomtextdanny.dannys_expansion.core.Util.dannybaseentity;

import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.base.util.Connection;
import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.ModDeferringManager;
import net.bottomtextdanny.braincell.mod.structure.client_sided.EntityRendererDeferring;
import net.bottomtextdanny.braincell.mod.structure.client_sided.EntityRendererMaker;
import net.bottomtextdanny.braincell.mod.structure.common_sided.EntityAttributeDeferror;
import net.bottomtextdanny.braincell.mod.structure.common_sided.RawEntitySpawnDeferring;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.object_tables.DEEntities;
import net.bottomtextdanny.danny_expannny.objects.items.BCSpawnEggItem;
import net.bottomtextdanny.danny_expannny.objects.items.SpecialKiteItem;
import net.bottomtextdanny.dannys_expansion.core.Util.setup.EntityWrap;
import net.bottomtextdanny.dannys_expansion.core.interfaces.IEntityBuilder;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Function;
import java.util.function.Supplier;

public class DEMobBuilder<T extends Mob> implements IEntityBuilder<T> {
    private final ModDeferringManager solving;
	private RawEntitySpawnDeferring<T> spawn;
    protected String entityId;
    protected EntityType.EntityFactory<T> factory;
    protected MobCategory classification;
    protected Supplier<AttributeSupplier.Builder> attributeMap;
    protected float width, height;
    protected BCSpawnEggItem.Builder eggBuilder;
    protected Supplier<SpecialKiteItem> kiteInst;
    @OnlyIn(Dist.CLIENT)
    protected Supplier<?> renderFactory;

    public DEMobBuilder(ModDeferringManager solving) {
        super();
        this.solving = solving;
    }

    @Override
    public DEMobBuilder<T> declare(String entityId, EntityType.EntityFactory<T> factory) {
        this.entityId = entityId;
        this.factory = factory;

        return this;
    }

    @Override
    public DEMobBuilder<T> classification(MobCategory classification) {
        this.classification = classification;

        return this;
    }

    @Override
    public DEMobBuilder<T> dimensions(float width, float height) {
        this.width = width;
        this.height = height;

        return this;
    }

    @Override
    public DEMobBuilder<T> renderer(Supplier<? extends Function<?, ?>> renderer) {
        Connection.doClientSide(() -> {
            this.renderFactory = () -> EntityRendererMaker.makeOf(renderer.get());
        });

        return this;
    }

    @Override
    public DEMobBuilder<T> attributes(Supplier<AttributeSupplier.Builder> attributeMap) {
        this.attributeMap = attributeMap;
        return this;
    }

    @Override
    public DEMobBuilder<T> spawn(SpawnPlacements.Type placement, Heightmap.Types heightMap, SpawnPlacements.SpawnPredicate<T> pred) {
        this.spawn = new RawEntitySpawnDeferring<>(placement, heightMap, pred);

        return this;
    }

    @Override
    public DEMobBuilder<T> egg(BCSpawnEggItem.Builder egg) {
        this.eggBuilder = egg;
        return this;
    }

    @Override
    public DEMobBuilder<T> kite(Supplier<SpecialKiteItem> kite) {
        this.kiteInst = kite;
        return this;
    }
	
	@Override
	public EntityWrap<EntityType<T>> build() {
		EntityWrap<EntityType<T>> type = new EntityWrap<>(new ResourceLocation(DannysExpansion.ID, this.entityId), () -> EntityType.Builder.of(this.factory, this.classification).sized(this.width, this.height).build(DannysExpansion.ID + ":" + this.entityId));
		type.setupForDeferring(this.solving);
		DEEntities.ENTRIES.addDeferredSolving(type);

		if (this.eggBuilder != null) {
            Braincell.common().getEntityCoreDataDeferror().saveEggBuilder(type.getKey(), this.eggBuilder);
		}
		if (this.kiteInst != null) {
			type.inferKite(this.kiteInst);
		}
		if (this.attributeMap != null) {
            Braincell.common().getEntityCoreDataDeferror()
                    .deferAttributeAttachment(new EntityAttributeDeferror(type, this.attributeMap));
        }
		if (this.spawn != null) {
            Braincell.common().getEntityCoreDataDeferror().deferSpawnPlacement(this.spawn.makeDeferring(type));
		}
        Connection.doClientSide(() -> {
            if (this.renderFactory != null) {
                Braincell.client().getEntityRendererDeferror().add(
                        new EntityRendererDeferring<>(type, (Supplier<EntityRendererProvider<T>>)this.renderFactory));
            }
        });
		return type;
	}
}
