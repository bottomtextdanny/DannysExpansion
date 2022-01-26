package net.bottomtextdanny.danny_expannny.vertex_models.kites;

import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.danny_expannny.objects.entities.kite.SpecialKiteEntity;

public class HugeHalfKiteModel<E extends SpecialKiteEntity> extends KiteBaseModel<E> {

    public HugeHalfKiteModel() {
        this.texWidth = 128;
        this.texHeight = 64;

        this.kite = new BCVoxel(this);
        this.kite.setPos(0.0F, 0.0F, 0.0F);
        this.kite.texOffs(0, 0).addBox(-1.0F, -11.0F, -0.5F, 2.0F, 22.0F, 1.0F, 0.0F, false);
        this.kite.texOffs(6, 0).addBox(-18.0F, -1.0F, -0.5F, 36.0F, 2.0F, 1.0F, 0.0F, false);
        this.kite.texOffs(6, 3).addBox(-21.0F, -14.0F, 0.51F, 42.0F, 28.0F, 0.0F, 0.0F, false);

        setupDefaultState();
    }

    @Override
    public int kiteIndex() {
        return 1;
    }
}
