package bottomtextdanny.dannys_expansion.content._client.model.entities;

import bottomtextdanny.braincell.mod._base.rendering.core_modeling.JointMirroring;
import bottomtextdanny.braincell.mod.rendering.modeling.BCEntityModel;
import bottomtextdanny.braincell.mod._base.rendering.core_modeling.BCJoint;
import net.minecraft.world.entity.LivingEntity;

public abstract class DEBipedModel<T extends LivingEntity> extends BCEntityModel<T> {
    protected BCJoint root;
    protected BCJoint leftLeg;
    protected BCJoint rightLeg;
    protected BCJoint body;
    protected BCJoint head;
    protected BCJoint rightArm;
    protected BCJoint leftArm;

    protected DEBipedModel(int width, int height) {
        super();
        texWidth = width;
        texHeight = height;

        root = new BCJoint(this, "root")
                .mirror(JointMirroring.YZ);
        body = new BCJoint(this, "body")
                .mirror(JointMirroring.YZ);
        head = new BCJoint(this, "head")
                .mirror(JointMirroring.YZ);
        leftArm = new BCJoint(this, "leftArm")
                .mirror(JointMirroring.YZ);
        rightArm = new BCJoint(this, "rightArm")
                .mirror(JointMirroring.YZ);
        leftLeg = new BCJoint(this, "leftLeg")
                .mirror(JointMirroring.YZ);
        rightLeg = new BCJoint(this, "rightLeg")
                .mirror(JointMirroring.YZ);

        leftLeg.mirrorJoint(rightLeg);
        rightLeg.mirrorJoint(leftLeg);

        leftArm.mirrorJoint(rightArm);
        rightArm.mirrorJoint(leftArm);
    }

    public BCJoint getRoot() {
        return root;
    }

    public BCJoint getBody() {
        return body;
    }

    public BCJoint getHead() {
        return head;
    }

    public BCJoint getRightArm() {
        return rightArm;
    }

    public BCJoint getLeftArm() {
        return leftArm;
    }

    public BCJoint getRightLeg() {
        return rightLeg;
    }

    public BCJoint getLeftLeg() {
        return leftLeg;
    }
}
