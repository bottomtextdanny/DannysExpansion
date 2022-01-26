package net.bottomtextdanny.danny_expannny.vertex_models.living_entities.ghouls;

import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;

public class GhoulModel extends BaseGhoulModel {

    public GhoulModel() {
        this.texWidth = 64;
        this.texHeight = 64;

        this.body = new BCVoxel(this);
        this.body.setPos(0.0F, 12.0F, 1.0F);


        this.hip = new BCVoxel(this);
        this.hip.setPos(0.0F, 0.0F, -1.0F);
        this.body.addChild(this.hip);
        setRotationAngle(this.hip, -0.1309F, 0.0F, 0.0F);
        this.hip.texOffs(0, 0).addBox(-4.0F, -7.0F, -1.5F, 8.0F, 7.0F, 3.0F, 0.0F, false);

        this.chest = new BCVoxel(this);
        this.chest.setPos(0.0F, -7.0F, 0.0F);
        this.hip.addChild(this.chest);
        setRotationAngle(this.chest, 0.0873F, 0.0F, 0.0F);
        this.chest.texOffs(0, 10).addBox(-6.0F, -7.0F, -2.5F, 12.0F, 7.0F, 6.0F, 0.0F, false);

        this.rightArm = new BCVoxel(this);
        this.rightArm.setPos(-6.0F, -4.0F, 0.0F);
        this.chest.addChild(this.rightArm);
        setRotationAngle(this.rightArm, 0.0436F, 0.0F, 0.0F);
        this.rightArm.texOffs(22, 0).addBox(-3.0F, -1.0F, -1.5F, 3.0F, 7.0F, 3.0F, 0.0F, false);

        this.rightForearm = new BCVoxel(this);
        this.rightForearm.setPos(-1.0F, 6.0F, 0.0F);
        this.rightArm.addChild(this.rightForearm);
        this.rightForearm.texOffs(34, 0).addBox(-2.0F, 0.0F, -1.5F, 3.0F, 7.0F, 3.0F, 0.02F, false);

        this.leftArm = new BCVoxel(this);
        this.leftArm.setPos(6.0F, -4.0F, 0.0F);
        this.chest.addChild(this.leftArm);
        setRotationAngle(this.leftArm, 0.0436F, 0.0F, 0.0F);
        this.leftArm.texOffs(22, 0).addBox(0.0F, -1.0F, -1.5F, 3.0F, 7.0F, 3.0F, 0.0F, true);

        this.leftForearm = new BCVoxel(this);
        this.leftForearm.setPos(2.0F, 6.0F, 0.0F);
        this.leftArm.addChild(this.leftForearm);
        this.leftForearm.texOffs(34, 0).addBox(-2.0F, 0.0F, -1.5F, 3.0F, 7.0F, 3.0F, 0.02F, true);

        this.head = new BCVoxel(this);
        this.head.setPos(0.0F, -7.0F, 1.0F);
        this.chest.addChild(this.head);
        setRotationAngle(this.head, 0.1309F, 0.0F, 0.0F);
        this.head.texOffs(0, 32).addBox(-4.0F, -8.0F, -6.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

        this.rightLeg = new BCVoxel(this);
        this.rightLeg.setPos(-2.5F, 0.0F, -1.0F);
        this.body.addChild(this.rightLeg);
        this.rightLeg.texOffs(0, 23).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, 0.0F, false);

        this.rightCalf = new BCVoxel(this);
        this.rightCalf.setPos(0.5F, 6.0F, 0.0F);
        this.rightLeg.addChild(this.rightCalf);
        this.rightCalf.texOffs(12, 23).addBox(-2.0F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, 0.02F, false);

        this.leftLeg = new BCVoxel(this);
        this.leftLeg.setPos(2.5F, 0.0F, -1.0F);
        this.body.addChild(this.leftLeg);
        this.leftLeg.texOffs(0, 23).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, 0.0F, true);

        this.leftCalf = new BCVoxel(this);
        this.leftCalf.setPos(0.5F, 6.0F, 0.0F);
        this.leftLeg.addChild(this.leftCalf);
        this.leftCalf.texOffs(12, 23).addBox(-2.0F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, 0.02F, true);
        
        setupDefaultState();
    }
}
