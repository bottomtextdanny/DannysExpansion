package bottomtextdanny.dannys_expansion.content._client.model.guns;

import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerGunModule;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import bottomtextdanny.braincell.mod._base.BCStaticData;
import bottomtextdanny.braincell.mod._base.animation.ModelAnimator;
import bottomtextdanny.braincell.mod._base.rendering.core_modeling.BCJoint;
import net.minecraft.world.entity.player.Player;

public class GolemHandgunModel extends GunModel {
    private final BCJoint root;
    private final BCJoint cog;
    private final ModelAnimator animator = new ModelAnimator(this, 0.0F);

    public GolemHandgunModel() {
        texWidth = 32;
        texHeight = 32;

        root = new BCJoint(this);
        root.setPosCore(0.0F, 0.0F, 0.0F);
        root.uvOffset(0, 0).addBox(-2.0F, -7.0F, -8.0F, 4.0F, 5.0F, 9.0F, 0.0F, false);
        root.uvOffset(0, 14).addBox(-1.5F, -4.0F, -2.0F, 3.0F, 5.0F, 4.0F, 0.0F, false);
        root.uvOffset(0, 0).addBox(0.0F, -2.0F, -3.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
        root.uvOffset(0, 0).addBox(0.0F, -8.0F, -7.0F, 0.0F, 1.0F, 2.0F, 0.0F, false);

        cog = new BCJoint(this);
        cog.setPosCore(0.0F, -7.0F, 1.25F);
        root.addChild(cog);
        setRotationAngle(cog, -0.7854F, 0.0F, 0.0F);
        cog.uvOffset(14, 9).addBox(0.0F, -2.5F, -2.5F, 0.0F, 5.0F, 5.0F, 0.0F, false);

        fire = new BCJoint(this);
        fire.setPosCore(0.0F, -4.5F, -9.0F);
        root.addChild(fire);
    }

    @Override
    public void animate(Player player, PlayerGunModule module, int useTicks) {
        if (player != null && useTicks >= 0) {
            animator.setTimer(useTicks + BCStaticData.partialTick());

            animator.setupKeyframe(3.0F);
            animator.rotate(cog, 180.0F, 0.0F, 0.0F);
            animator.apply();

            animator.emptyKeyframe(3.0F);

            animator.reset();
        }
    }

    @Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.root.render(poseStack, buffer, packedLight, packedOverlay);
	}
}
