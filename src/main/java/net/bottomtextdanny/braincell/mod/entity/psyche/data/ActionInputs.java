package net.bottomtextdanny.braincell.mod.entity.psyche.data;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import javax.annotation.Nullable;
import java.util.Map;

public class ActionInputs {
    private final Map<ActionInputKey<?>, ActionInput> inputMap;

    public ActionInputs(UnbuiltActionInputs rawInputs) {
        super();
        this.inputMap = rawInputs.getInputMap();
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <T extends ActionInput> T get(ActionInputKey<T> inputKey) {
        return (T)this.inputMap.getOrDefault(inputKey, null);
    }
    
    public boolean containsInput(ActionInputKey<?> key) {
        return this.inputMap.containsKey(key);
    }
}
