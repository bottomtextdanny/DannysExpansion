package net.bottomtextdanny.braincell.mod.structure.common_sided.events;

import com.google.common.collect.Multimap;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.eventbus.api.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class BlockEntityPlacementBuildEvent extends Event {
    private final Multimap<Class<? extends BlockEntity>, ? super Block> lookup;

    public BlockEntityPlacementBuildEvent(Multimap<Class<? extends BlockEntity>, ? super Block> lookup) {
        super();
        this.lookup = lookup;
    }

    public void put(Class<? extends BlockEntity> classRef, Iterable<Block> placements) {
        this.lookup.putAll(classRef, placements);
    }

    public void put(Class<? extends BlockEntity> classRef, Block... placements) {
        this.lookup.putAll(classRef, Arrays.stream(placements).toList());
    }

    public <E extends Block> Iterable<Block> getAllBlocksFromClass(Class<E> classRef) {
        return Registry.BLOCK
                .stream()
                .filter(block -> classRef.isAssignableFrom(block.getClass()))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
