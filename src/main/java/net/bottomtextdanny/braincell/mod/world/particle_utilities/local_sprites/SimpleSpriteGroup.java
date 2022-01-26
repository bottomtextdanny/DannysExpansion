package net.bottomtextdanny.braincell.mod.world.particle_utilities.local_sprites;

import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class SimpleSpriteGroup implements SpriteGroup {
    private final SpriteSet set;
    private final int size;

    private SimpleSpriteGroup(SpriteSet spriteSet, int size) {
        this.set = spriteSet;
        this.size = size;
    }

    public static SimpleSpriteGroup of(SpriteSet spriteSet, int size) {
        return new SimpleSpriteGroup(spriteSet, size);
    }

    @Override
    public TextureAtlasSprite fetch(int index) {
        return this.set.get(index, this.size);
    }
}
