package net.bottomtextdanny.dannys_expansion.common.crumbs;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.bottomtextdanny.danny_expannny.ClientInstance;

import java.util.*;

public class CrumbMultiMap extends AbstractCollection<Crumb> {
    private final Map<CrumbRoot<?>, List<Crumb>> childrenMap = Maps.newHashMap();
    private final List<Crumb> completeList = Lists.newArrayList();

    public CrumbMultiMap(Iterable<CrumbRoot<?>> rootIterable) {
        rootIterable.forEach(crumbRoot -> {
            this.childrenMap.put(crumbRoot, Lists.newLinkedList());
        });
    }

    @Override
    public boolean add(Crumb crumb) {
        if (this.childrenMap.containsKey(crumb.getRoot())) {
            this.childrenMap.get(crumb.getRoot()).add(crumb);
        }  ClientInstance.chatMsg(crumb.getRoot().getParentHashSet().size());
        crumb.getRoot().getAllParentsIterable().forEach(parent -> {

            if (this.childrenMap.containsKey(parent)) {
                this.childrenMap.get(parent).add(crumb);
            }
        });
        return this.completeList.add(crumb);
    }

    @Override
    public boolean remove(Object object) {
        if (object instanceof Crumb crumb) {
            if (this.childrenMap.containsKey(crumb.getRoot())) {
                this.childrenMap.get(crumb.getRoot()).remove(crumb);
            }
            crumb.getRoot().getAllParentsIterable().forEach(parent -> {
                if (this.childrenMap.containsKey(parent)) {
                    this.childrenMap.get(parent).remove(crumb);
                }
            });
            return this.completeList.remove(crumb);
        }
        return false;
    }

    public boolean contains(Object object) {
        return this.completeList.contains(object);
    }

    public List<Crumb> getRootCrumbs(CrumbRoot<?> root) {
        return this.childrenMap.get(root);
    }

    public Iterator<Crumb> iterator() {
        return this.completeList.isEmpty() ? Collections.emptyIterator() : Iterators.unmodifiableIterator(this.completeList.iterator());
    }

    public List<Crumb> getCompleteList() {
        return ImmutableList.copyOf(this.completeList);
    }

    public int size() {
        return this.completeList.size();
    }
}
