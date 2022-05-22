package bottomtextdanny.dannys_expansion.content.entities.mob.goblin;

import bottomtextdanny.dannys_expansion.content._client.rendering.entity.mob.goblin.GoblinWeaponRenderingLayer;
import bottomtextdanny.braincell.Braincell;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public enum GoblinWeapon {
    NONE(-1, () -> null, 1.0F),
    SPEAR(0, () -> GoblinWeaponRenderingLayer.TEXTURES_SPEAR, 1.2F),
    SWORD(1, () -> GoblinWeaponRenderingLayer.TEXTURES_SWORD, 1.4F);

    private final int index;
    private final Supplier<ResourceLocation> texture;
    private final float damageMultiplier;

    GoblinWeapon(int index, Supplier<ResourceLocation> texture, float damageMultiplier) {
        this.index = index;
        this.texture = texture;
        this.damageMultiplier = damageMultiplier;
    }

    public void arm(Goblin goblin, boolean leftHand) {
        if (leftHand) goblin.setLeftWeapon(this);
        else goblin.setRightWeapon(this);
    }

    public boolean hasOnHand(Goblin goblin, boolean leftHand) {
        return leftHand ? goblin.getLeftWeapon() == this
                : goblin.getRightWeapon() == this;
    }

    public int getIndex() {
        return index;
    }

    public float getDamageMultiplier() {
        return damageMultiplier;
    }

    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getTexture() {
        ResourceLocation provided = this.texture.get();
        return provided == null ? Braincell.client().sink() : provided;
    }
}
