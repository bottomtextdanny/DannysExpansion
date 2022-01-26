package net.bottomtextdanny.danny_expannny.vertex_models.kites;

import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.danny_expannny.objects.entities.kite.KiteEntity;

public class KiteModel <E extends KiteEntity> extends KiteBaseModel<E> {

    public KiteModel() {
        this.texWidth = 32;
        this.texHeight = 32;

        this.kite = new BCVoxel(this);
        this.kite.setPos(0.0F, 0.0F, -0.5F);
        this.kite.texOffs(0, 0).addBox(-0.5F, -8.5F, -0.5F, 1.0F, 17.0F, 1.0F, 0.0F, false);
        this.kite.texOffs(4, 0).addBox(-6.5F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, 0.0F, false);
        this.kite.texOffs(4, 2).addBox(-6.5F, -9.0F, 0.51F, 13.0F, 18.0F, 0.0F, 0.0F, false);

        setupDefaultState();
    }

    @Override
    public int kiteIndex() {
        return 0;
    }
}
