package net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.chest;

import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;

public class BCBaseLeftChestModel extends BCChestModel {

    public BCBaseLeftChestModel() {
        this.texWidth = 64;
        this.texHeight = 64;

        this.chest = new BCVoxel(this);
        this.chest.setPos(0.0F, 0.0F, 0.0F);
        this.chest.texOffs(0, 19).addBox(-8.0F, -10.0F, -7.0F, 15.0F, 10.0F, 14.0F, 0.0F, false);

        this.lid = new BCVoxel(this);
        this.lid.setPos(0.0F, -9.5F, 6.5F);
        this.chest.addChild(this.lid);
        this.lid.texOffs(0, 0).addBox(-8.0F, -4.5F, -13.5F, 15.0F, 5.0F, 14.0F, 0.0F, false);
        this.lid.texOffs(0, 0).addBox(-8.0F, -1.5F, -14.5F, 1.0F, 4.0F, 1.0F, 0.0F, false);
    }
}
