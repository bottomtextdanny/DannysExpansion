package net.bottomtextdanny.braincell.mod.entity.psyche.data;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.Map;

public class UnbuiltActionInputs {
    private final Map<ActionInputKey<?>, ActionInput> inputMap = Maps.newIdentityHashMap();

    public <T extends ActionInput> void put(ActionInputKey<T> inputKey, T input) {
        this.inputMap.put(inputKey, input);
    }

    public Map<ActionInputKey<?>, ActionInput> getInputMap() {
        return ImmutableMap.copyOf(this.inputMap);
    }
}
