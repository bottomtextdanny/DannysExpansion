package net.bottomtextdanny.danny_expannny.vertex_models;

import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.minecraft.world.entity.LivingEntity;

public abstract class DEBasicBipedModel<T extends LivingEntity> extends BCEntityModel<T> {
    protected BCVoxel model;
    protected BCVoxel leftLeg;
    protected BCVoxel rightLeg;
    protected BCVoxel hip;
    protected BCVoxel head;
    protected BCVoxel rightArm;
    protected BCVoxel leftArm;
}
