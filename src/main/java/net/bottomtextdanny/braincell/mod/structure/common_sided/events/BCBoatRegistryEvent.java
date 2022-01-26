package net.bottomtextdanny.braincell.mod.structure.common_sided.events;

import net.bottomtextdanny.braincell.mod.world.builtin_entities.BCBoatType;
import net.minecraftforge.eventbus.api.Event;

import java.util.function.Consumer;

public class BCBoatRegistryEvent extends Event {
    private Consumer<BCBoatType> registerer;

    public BCBoatRegistryEvent(Consumer<BCBoatType> registerer) {
        super();
        this.registerer = registerer;
    }

    public void register(BCBoatType boatType) {
        this.registerer.accept(boatType);
    }
}
