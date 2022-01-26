package net.bottomtextdanny.de_json_generator.jsonBakers;

import com.google.gson.JsonObject;

import java.util.List;

public class SoundMetadataBaker extends JsonBaker<SoundMetadataBaker> {
    private final List<SoundMetadata> entries;

    public SoundMetadataBaker(List<SoundMetadata> key) {
        this.entries = key;
    }

    @Override
    public SoundMetadataBaker bake() {
        for (SoundMetadata metadata : this.entries) {
            JsonObject entry = new JsonObject();
            String[] soundArray = new String[metadata.sounds()];

            entry.add("category", cString("neutral"));

            int sounds = metadata.sounds();

            for (int i = 0; i < sounds; i++) {
                soundArray[i] = "dannys_expansion:" + metadata.name().replace('.', '/') + '_' + i;
            }

            entry.add("sounds", cStringArray(soundArray));
            entry.add("subtitle", cString("dannys_expansion.subtitles." + metadata.name()));

            this.jsonObj.add(metadata.name(), entry);
        }
        return this;
    }
}
