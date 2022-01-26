package net.bottomtextdanny.danny_expannny.vertex_models.kites;

import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.danny_expannny.objects.entities.kite.SpecialKiteEntity;

public class LargeKiteModel <E extends SpecialKiteEntity> extends KiteBaseModel<E> {

    public LargeKiteModel() {
        this.texWidth = 64;
        this.texHeight = 32;

        this.kite = new BCVoxel(this);
        this.kite.setPos(0.0F, 0.0F, 0.0F);
        this.kite.texOffs(0, 0).addBox(-0.5F, -7.5F, -0.5F, 1.0F, 15.0F, 1.0F, 0.0F, false);
        this.kite.texOffs(4, 0).addBox(-10.5F, -0.5F, -0.5F, 21.0F, 1.0F, 1.0F, 0.0F, false);
        this.kite.texOffs(4, 2).addBox(-11.0F, -8.0F, 0.51F, 22.0F, 16.0F, 0.0F, 0.0F, false);

        setupDefaultState();
    }

    @Override
    public int kiteIndex() {
        return 1;
    }
}
