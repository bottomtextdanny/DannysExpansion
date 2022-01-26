package net.bottomtextdanny.braincell.mod.minecraft_front_rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.bottomtextdanny.braincell.mod.world.builtin_entities.BCBoat;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.Boat;

public class BCBoatRenderer extends EntityRenderer<BCBoat> {
	protected final BoatModel modelBoat;

	public BCBoatRenderer(Object manager) {
		this((EntityRendererProvider.Context) manager);
	}

	public BCBoatRenderer(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn);
		this.shadowRadius = 0.8F;
        this.modelBoat = new BoatModel(renderManagerIn.bakeLayer(ModelLayers.createBoatModelName(Boat.Type.OAK)));
	}
	
	public void render(BCBoat entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
		matrixStackIn.pushPose();
		matrixStackIn.translate(0.0D, 0.375D, 0.0D);
		matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0F - entityYaw));
		float f = (float)entityIn.getHurtTime() - partialTicks;
		float f1 = entityIn.getDamage() - partialTicks;
		if (f1 < 0.0F) {
			f1 = 0.0F;
		}
		
		if (f > 0.0F) {
			matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(Mth.sin(f) * f * f1 / 10.0F * (float)entityIn.getHurtDir()));
		}
		
		float f2 = entityIn.getBubbleAngle(partialTicks);
		if (!Mth.equal(f2, 0.0F)) {
			matrixStackIn.mulPose(new Quaternion(new Vector3f(1.0F, 0.0F, 1.0F), entityIn.getBubbleAngle(partialTicks), true));
		}
		
		matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
		matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90.0F));
		this.modelBoat.setupAnim(entityIn, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
		VertexConsumer ivertexbuilder = bufferIn.getBuffer(this.modelBoat.renderType(this.getTextureLocation(entityIn)));
		this.modelBoat.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		if (!entityIn.isUnderWater()) {
			VertexConsumer ivertexbuilder1 = bufferIn.getBuffer(RenderType.waterMask());
			this.modelBoat.waterPatch().render(matrixStackIn, ivertexbuilder1, packedLightIn, OverlayTexture.NO_OVERLAY);
		}
		
		matrixStackIn.popPose();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	public ResourceLocation getTextureLocation(BCBoat entity) {
		return entity.getBCBoatType().texturePath;
	}
	
}
