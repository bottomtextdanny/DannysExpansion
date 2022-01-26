package net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features;

import com.google.common.collect.Maps;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public final class DeferrorType<T extends IForgeRegistryEntry<T>> {
    private static final Map<ResourceLocation, DeferrorType<?>> BY_KEY = Maps.newHashMap();
    private static final MutableInt ID_PROVIDER = new MutableInt(0);
    //*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//
    public static final DeferrorType<SoundEvent> SOUND_EVENT =
            make(new ResourceLocation("builtin:sound_event"), SoundEvent.class);
    public static final DeferrorType<Attribute> ATTRIBUTE =
            make(new ResourceLocation("builtin:attribute"), Attribute.class);
    public static final DeferrorType<EntityType<?>> ENTITY_TYPE =
            make(new ResourceLocation("builtin:entity_type"), EntityType.class);
    public static final DeferrorType<Feature<?>> FEATURE =
            make(new ResourceLocation("builtin:feature"), Feature.class);
    public static final DeferrorType<Biome> BIOME =
            make(new ResourceLocation("builtin:biome"), Biome.class);
    public static final DeferrorType<StructureFeature<?>> STRUCTURE =
            make(new ResourceLocation("builtin:structure"), StructureFeature.class);
    public static final DeferrorType<Block> BLOCK =
            make(new ResourceLocation("builtin:block"), Block.class);
    public static final DeferrorType<Item> ITEM =
            make(new ResourceLocation("builtin:item"), Item.class);
    public static final DeferrorType<MenuType<?>> MANU_TYPE =
            make(new ResourceLocation("builtin:menu_type"), MenuType.class);
    public static final DeferrorType<MobEffect> MOB_EFFECT =
            make(new ResourceLocation("builtin:mob_effect"), MobEffect.class);
    public static final DeferrorType<BlockEntityType<?>> BLOCK_ENTITY_TYPE =
            make(new ResourceLocation("builtin:block_entity_type"), BlockEntityType.class);
    public static final DeferrorType<ParticleType<?>> PARTICLE_TYPE =
            make(new ResourceLocation("builtin:particle_type"), ParticleType.class);
    public static final DeferrorType<RecipeSerializer<?>> RECIPE_SERIALIZER =
            make(new ResourceLocation("builtin:recipe_serializer"), RecipeSerializer.class);
    //*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//
    private final ResourceLocation key;
    private final int id;
    private final Class<T> classRef;

    public DeferrorType(ResourceLocation key, Class<T> classRef) {
        super();
        this.key = key;
        this.id = ID_PROVIDER.getAndIncrement();
        this.classRef = classRef;
    }

    private static <U extends IForgeRegistryEntry<U>> DeferrorType<U> make(ResourceLocation key, Class<?> classRef) {
        DeferrorType<U> type = new DeferrorType<>(key, (Class<U>) classRef);
        BY_KEY.put(key, type);
        return type;
    }

    public Class<T> getClassRef() {
        return this.classRef;
    }

    public int getId() {
        return this.id;
    }

    public static DeferrorType<?> getByKey(ResourceLocation key) {
        return BY_KEY.get(key);
    }

    public static Collection<DeferrorType<?>> collection() {
        return Collections.unmodifiableCollection(BY_KEY.values());
    }

    public ResourceLocation getKey() {
        return this.key;
    }
}
