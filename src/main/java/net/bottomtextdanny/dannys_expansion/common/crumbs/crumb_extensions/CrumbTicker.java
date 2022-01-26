package net.bottomtextdanny.dannys_expansion.common.crumbs.crumb_extensions;

import net.bottomtextdanny.dannys_expansion.common.crumbs.Crumb;

public interface CrumbTicker {

    static void tickCrumb(Crumb crumb) {
        if (crumb.getChunk() != null && crumb instanceof CrumbTicker ticker) {
            if (crumb instanceof CrumbSerializer serializer && ticker.recordDirty()) {
                serializer.markForSerialization();
            }
            ticker.tick();
        }
    }

    void tick();

    default boolean recordDirty() {
        return true;
    }
}
