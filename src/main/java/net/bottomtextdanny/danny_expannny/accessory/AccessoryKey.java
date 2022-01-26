package net.bottomtextdanny.danny_expannny.accessory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.object_tables.DEAccessoryKeys;
import net.bottomtextdanny.danny_expannny.objects.accessories.CoreAccessory;
import net.bottomtextdanny.dannys_expansion.core.Util.Pair;
import net.bottomtextdanny.danny_expannny.capabilities.player.MiniAttribute;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AccessoryKey<E extends IAccessory> {
    private static Map<AccessoryKey<?>, AccessoryData> ATTRIBUTE_DATA;
    private static final ObjectOpenHashSet<AccessoryKey<?>> EMPTY_LOOKUP = Util.make(() -> {
        ObjectOpenHashSet<AccessoryKey<?>> table = new ObjectOpenHashSet<>(16);
        table.add(DEAccessoryKeys.EMPTY);
        table.add(DEAccessoryKeys.CORE_EMPTY);
        table.add(DEAccessoryKeys.STACK_EMPTY);
        return table;
    });
    private static List<AccessoryKey<?>> ACCESSORIES_BY_ID = Lists.newLinkedList();
    private static Map<ResourceLocation, AccessoryKey<?>> ACCESSORIES_BY_LOCATION = Maps.newHashMap();
    private static boolean initialized;
    private final ResourceLocation key;
    private final AccessoryFactory<E> accessoryFactory;
    private int id;

    private AccessoryKey(AccessoryFactory<E> accessoryFactory, ResourceLocation key) {
        this.accessoryFactory = accessoryFactory;
        this.key = key;
    }

    public static <E extends IAccessory> AccessoryKey<E> createKey(ResourceLocation location, AccessoryFactory<E> supplier) {
        if (!DannysExpansion.solvingState.isOpen()) throw new UnsupportedOperationException("Can't create new accessory keys after mod initialization.");
        AccessoryKey<E> key = new AccessoryKey<>(supplier, location);
        key.id = ACCESSORIES_BY_ID.size();
        ACCESSORIES_BY_ID.add(key);
        ACCESSORIES_BY_LOCATION.put(location, key);
        return key;
    }

    public ResourceLocation getLocation() {
        return this.key;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessoryKey<?> that = (AccessoryKey<?>) o;
        return this.id == that.id;
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    public E create(Player player) {
        return this.accessoryFactory.make(this, player);
    }

    public static List<AccessoryKey<?>> getAccessoriesById() {
        return ACCESSORIES_BY_ID;
    }

    public static Map<ResourceLocation, AccessoryKey<?>> getAccessoriesByLocation() {
        return ACCESSORIES_BY_LOCATION;
    }

    public static Map<AccessoryKey<?>, AccessoryData> getAttributeData() {
        return ATTRIBUTE_DATA;
    }

    public static boolean isEmpty(AccessoryKey<?> key) {
        return EMPTY_LOOKUP.contains(key);
    }

    public static void build() {
        if (!initialized) {
            ImmutableMap.Builder<AccessoryKey<?>, AccessoryData> dataBuilder = ImmutableMap.builder();
            ACCESSORIES_BY_ID = ImmutableList.copyOf(ACCESSORIES_BY_ID);
            ACCESSORIES_BY_LOCATION = ImmutableMap.copyOf(ACCESSORIES_BY_LOCATION);
            initialized = true;

            ACCESSORIES_BY_ID.forEach(key -> {
                if (key.create(null) instanceof CoreAccessory coreAccessory) {
                    LinkedList<Pair<ModifierType, Double>> modifierList = Lists.newLinkedList();
                    LinkedList<Pair<MiniAttribute, Float>> lesserModifierList = Lists.newLinkedList();
                    coreAccessory.populateModifierData(modifierList, lesserModifierList);
                    AccessoryData data = AccessoryData.create(modifierList, lesserModifierList);
                    dataBuilder.put(key, data);
                }
            });

            ATTRIBUTE_DATA = dataBuilder.build();
        } else {
            throw new UnsupportedOperationException("Accessories are already initialized!");
        }
    }
}
