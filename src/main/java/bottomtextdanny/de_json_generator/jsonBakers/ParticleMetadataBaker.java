package bottomtextdanny.de_json_generator.jsonBakers;

import java.util.List;

public class ParticleMetadataBaker extends JsonBaker<ParticleMetadataBaker> {
    private final List<String> entries;

    public ParticleMetadataBaker(List<String> entries) {
        this.entries = entries;
    }

    @Override
    public ParticleMetadataBaker bake() {
        this.jsonObj.add("textures", cStringCollection(this.entries));
        return this;
    }
}
