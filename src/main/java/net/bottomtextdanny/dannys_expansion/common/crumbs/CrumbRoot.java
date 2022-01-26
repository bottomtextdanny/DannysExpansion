package net.bottomtextdanny.dannys_expansion.common.crumbs;

import com.google.common.collect.*;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.dannys_expansion.common.crumbs.content.TestTickableCrumb;
import net.bottomtextdanny.dannys_expansion.common.crumbs.crumb_extensions.CrumbSearchable;
import net.bottomtextdanny.dannys_expansion.common.crumbs.crumb_extensions.CrumbSerializer;
import net.bottomtextdanny.dannys_expansion.common.crumbs.crumb_extensions.CrumbSynchronizer;
import net.bottomtextdanny.dannys_expansion.common.crumbs.crumb_extensions.CrumbTicker;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;

public class CrumbRoot<E extends Crumb> {
    private static final Map<ResourceLocation, CrumbRoot<?>> ROOTS_BY_KEY = Maps.newHashMap();
    private static final List<CrumbRoot<?>> INDEXED_ROOTS = Lists.newArrayList();
    private static final List<CrumbRoot<?>> SPECIAL_ROOTS = Lists.newArrayList();
    private static final List<CrumbRoot<?>> SEARCHABLE_ROOTS = Lists.newArrayList();

    public static final CrumbRoot<Crumb> SERIALIZABLE = makeSpecialEntry("serializable", CrumbSerializer.class::isInstance);
    public static final CrumbRoot<Crumb> SYNCHRONIZABLE = makeSpecialEntry("synchronizable", CrumbSynchronizer.class::isInstance);
    public static final CrumbRoot<Crumb> TICKABLE = makeSpecialEntry("tickable", CrumbTicker.class::isInstance);
    public static final CrumbRoot<Crumb> SEARCHABLE = makeSpecialEntry("searchable", CrumbSearchable.class::isInstance);
    public static final CrumbRoot<TestTickableCrumb> TEST_TICKABLE = makeEntry("test_tickable", TestTickableCrumb::new, TestTickableCrumb.class::isInstance, TICKABLE, SERIALIZABLE);

    private final ResourceLocation key;
    private final int id;
    private final Set<CrumbRoot<? super E>> parentHashSet;
    private final Predicate<E> instanceTest;
    private @Nullable final CrumbFactory<E> factory;
    private final Iterable<CrumbRoot<? super E>> allParentsIterable;

    private CrumbRoot(ResourceLocation key, boolean special, @Nullable CrumbFactory<E> factory, Predicate<E> instanceTest, CrumbRoot<? super E>... extensions) {
        super();
        this.id = INDEXED_ROOTS.size();
        this.key = key;
        INDEXED_ROOTS.add(this);
        if (special) SPECIAL_ROOTS.add(this);
        ROOTS_BY_KEY.put(this.key, this);
        this.factory = factory;

        if (extensions.length != 0) {
            Collection<CrumbRoot<? super E>> parentCollection = Lists.newLinkedList();
            this.parentHashSet = ImmutableSet.copyOf(extensions);
            findParentThenAddToCollection(this, parentCollection);
            this.allParentsIterable = Iterables.unmodifiableIterable(parentCollection);
            this.instanceTest = crumb -> instanceTest.test(crumb) && Arrays.stream(extensions).allMatch(root -> root.instanceTest.test(crumb));
        } else {
            this.allParentsIterable = ImmutableList.of();
            this.parentHashSet = ImmutableSet.of();
            this.instanceTest = instanceTest;
        }

        if (this == SEARCHABLE || this.parentHashSet.contains(SEARCHABLE)) SEARCHABLE_ROOTS.add(this);
    }

    public E create(Level level) {
        if (this.factory == null) throw new UnsupportedOperationException("Danny's Expansion: crumb root doesn't provide a factory.");
        return this.factory.create(this, level);
    }

    public Iterable<CrumbRoot<? super E>> getAllParentsIterable() {
        return this.allParentsIterable;
    }

    public Set<CrumbRoot<? super E>> getParentHashSet() {
        return this.parentHashSet;
    }

    public Predicate<E> getInstanceTest() {
        return this.instanceTest;
    }

    public boolean is(CrumbRoot<?> anotherRoot) {
        return anotherRoot == this || this.parentHashSet.contains(anotherRoot);
    }

    public int getId() {
        return this.id;
    }

    public ResourceLocation getKey() {
        return this.key;
    }

    public static <E extends Crumb> CrumbRoot<E> makeEntry(String name, Predicate<E> instanceTest, CrumbRoot<? super E>... extensions) {
        return makeEntry(name, null, instanceTest, extensions);
    }

    public static <E extends Crumb> CrumbRoot<E> makeEntry(String name, @Nullable CrumbFactory<E>factory, Predicate<E> instanceTest, CrumbRoot<? super E>... extensions) {
        if (!DannysExpansion.solvingState.isOpen()) throw new UnsupportedOperationException("Danny's Expansion: Can't make more crumb root entries after mod initialization");
        return new CrumbRoot<>(new ResourceLocation(DannysExpansion.ID, name), false, factory, instanceTest, extensions);
    }

    public static <E extends Crumb> CrumbRoot<E> makeSpecialEntry(String name, Predicate<E> instanceTest, CrumbRoot<? super E>... extensions) {
        return makeSpecialEntry(name, null, instanceTest, extensions);
    }

    public static <E extends Crumb> CrumbRoot<E> makeSpecialEntry(String name, @Nullable CrumbFactory<E>factory, Predicate<E> instanceTest, CrumbRoot<? super E>... extensions) {
        if (!DannysExpansion.solvingState.isOpen()) throw new UnsupportedOperationException("Danny's Expansion: Can't make more crumb root entries after mod initialization");
        return new CrumbRoot<>(new ResourceLocation(DannysExpansion.ID, name), true, factory, instanceTest, extensions);
    }

    public static CrumbRoot<?> getByKey(ResourceLocation location) {
        return ROOTS_BY_KEY.get(location);
    }

    public static CrumbRoot<?> getById(int id) {
        return INDEXED_ROOTS.get(id);
    }

    private static <E extends Crumb> void findParentThenAddToCollection(CrumbRoot<? super E> root, Collection<CrumbRoot<? super E>> collection) {
        if (!root.parentHashSet.isEmpty()) {
            root.parentHashSet.forEach(parent -> {
                collection.add(parent);
                findParentThenAddToCollection(parent, collection);
            });
        }
    }

    public static Iterable<CrumbRoot<?>> rootIterator() {
        return Iterables.unmodifiableIterable(INDEXED_ROOTS);
    }

    public static Iterable<CrumbRoot<?>> specialRootIterator() {
        return Iterables.unmodifiableIterable(SPECIAL_ROOTS);
    }

    public static Iterable<CrumbRoot<?>> searchableIterator() {
        return Iterables.unmodifiableIterable(SEARCHABLE_ROOTS);
    }

    public static void loadClass() {}
}
