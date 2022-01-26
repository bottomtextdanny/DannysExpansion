package net.bottomtextdanny.dannys_expansion.common.crumbs;

import net.minecraft.world.level.Level;

@FunctionalInterface
public interface CrumbFactory<E extends Crumb> {

    E create(CrumbRoot<E> root, Level level);
}
