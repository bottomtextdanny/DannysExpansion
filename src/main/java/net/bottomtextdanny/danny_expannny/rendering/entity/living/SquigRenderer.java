package net.bottomtextdanny.danny_expannny.rendering.entity.living;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.SquigModel;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.squig.SquigEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Pose;

public class SquigRenderer extends VariantRenderer<SquigEntity, SquigModel> {
	public static final ResourceLocation DEFAULT_TEXTURE_PATH = new ResourceLocation(DannysExpansion.ID, "textures/entity/squig/freedom_squig.png");
	public static SquigModel COMMON_MODEL = new SquigModel();

	public SquigRenderer(Object manager) {
		this((EntityRendererProvider.Context) manager);
	}

	public SquigRenderer(EntityRendererProvider.Context manager) {
		super(manager, COMMON_MODEL, 0.4F);
	}
	
	protected void setupRotations(SquigEntity entityLiving, PoseStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
		rotationYaw = 180.0F;
		
		Pose pose = entityLiving.getPose();
		if (pose != Pose.SLEEPING) {
			matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0F - rotationYaw));
		}
		
		if (entityLiving.deathTime > 0) {
			float f = ((float)entityLiving.deathTime + partialTicks - 1.0F) / 20.0F * 1.6F;
			f = Mth.sqrt(f);
			if (f > 1.0F) {
				f = 1.0F;
			}
			
			matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(f * this.getFlipDegrees(entityLiving)));
		} else if (entityLiving.isAutoSpinAttack()) {
			matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(-90.0F - entityLiving.getXRot()));
			matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(((float)entityLiving.tickCount + partialTicks) * -75.0F));
		} else if (pose == Pose.SLEEPING) {
			Direction direction = entityLiving.getBedOrientation();
			float f1 = direction != null ? getFacingAngle(direction) : 0.0F;
			matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(f1));
			matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(this.getFlipDegrees(entityLiving)));
			matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270.0F));
		} else if (entityLiving.hasCustomName()) {
			String s = ChatFormatting.stripFormatting(entityLiving.getName().getString());
			if ("Dinnerbone".equals(s) || "Grumm".equals(s)) {
				matrixStackIn.translate(0.0D, entityLiving.getBbHeight() + 0.1F, 0.0D);
				matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
			}
		}
		
	}
	
	private static float getFacingAngle(Direction facingIn) {
		switch(facingIn) {
			case SOUTH:
				return 90.0F;
			case WEST:
				return 0.0F;
			case NORTH:
				return 270.0F;
			case EAST:
				return 180.0F;
			default:
				return 0.0F;
		}
	}
	
	@Override
	public ResourceLocation getDefaultEntityTexture(SquigEntity entity) {
		return DEFAULT_TEXTURE_PATH;
	}
}
