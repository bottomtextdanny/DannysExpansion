package bottomtextdanny.de_json_generator.inner;

public class CommonTextureProviders {

    public static TextureProvider tp_all(String path) {
        return new TextureProvider(path, path, path, path, path, path);
    }

    public static TextureProvider tp_side_vertical(String side, String vertical) {
        return new TextureProvider(vertical, vertical, side, side, side, side);
    }

    public static TextureProvider tp_side_up_down(String side, String up, String down) {
        return new TextureProvider(up, down, side, side, side, side);
    }

    public static TextureProvider tp_side_front_up_down(String side, String front, String up, String down) {
        return new TextureProvider(up, down, front, side, side, side);
    }

    public static TextureProvider tp_basic(String up, String down, String north, String south, String west, String east) {
        return new TextureProvider(up, down, north, south, west, east);
    }
}
