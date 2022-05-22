package bottomtextdanny.de_json_generator.jsonBakers;

public abstract class JsonBaker<T extends JsonBaker<T>> extends JsonDecoder {
    public abstract T bake();
}
