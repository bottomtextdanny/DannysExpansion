package net.bottomtextdanny.dannys_expansion.common.crumbs.crumb_extensions;

import net.bottomtextdanny.dannys_expansion.common.crumbs.Crumb;

public interface CrumbExtension {

    default Crumb asCrumb() {
        return (Crumb)this;
    }
}
