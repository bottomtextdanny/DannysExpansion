package net.bottomtextdanny.braincell.mod.entity.modules.variable;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class StringedFormManager {
    private final BiMap<String, ? extends Form<?>> keyFormBimap;

    public StringedFormManager(BiMap<String, ? extends Form<?>> keyFormBimap) {
        super();
        this.keyFormBimap = keyFormBimap;
    }

    public static StringedFormManager.Builder builder() {
        return new StringedFormManager.Builder();
    }

    public Form<?> getForm(String key) {
        return this.keyFormBimap.get(key);
    }

    public String getKey(Form<?> form) {
        return this.keyFormBimap.inverse().get(form);
    }

    public static class Builder {
        private final BiMap<String, Form<?>> keyFormBimap = HashBiMap.create();

        private Builder() {
            super();
        }

        public Builder putForm(String key, Form<?> form) {
            this.keyFormBimap.put(key, form);
            return this;
        }

        public StringedFormManager create() {
            return new StringedFormManager(this.keyFormBimap);
        }
    }
}
