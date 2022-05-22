package bottomtextdanny.dannys_expansion.content._client.rendering.entity.misc;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content._client.model.entities.misc_entities.EnderDragonRewardModel;
import bottomtextdanny.dannys_expansion.content.entities.misc.EnderDragonRewardEntity;
import bottomtextdanny.dannys_expansion._util.EntityUtil;
import bottomtextdanny.braincell.Braincell;
import bottomtextdanny.braincell.mod._base.BCStaticData;
import bottomtextdanny.braincell.mod.graphics.point_lighting.SimplePointLight;
import bottomtextdanny.braincell.mod.rendering.SpellRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class EnderDragonRewardRenderer extends SpellRenderer<EnderDragonRewardEntity, EnderDragonRewardModel> {
	public static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/ender_dragon_reward.png");

	public EnderDragonRewardRenderer(Object manager) {
		this((EntityRendererProvider.Context) manager);
	}

	public EnderDragonRewardRenderer(EntityRendererProvider.Context manager) {
		super(manager, new EnderDragonRewardModel());
	}
	
	@Override
	public boolean shouldRender(EnderDragonRewardEntity livingEntityIn,
								Frustum camera,
								double camX,
								double camY,
								double camZ) {
		if (camera.isVisible(new AABB(livingEntityIn.position().add(-10, -9.5, -10), livingEntityIn.position().add(10, 10.5, 10)))) {
			float radius = 10.0F * Mth.lerp(BCStaticData.partialTick(), livingEntityIn.getPrevFxIntensity(), livingEntityIn.getFxIntensity());
			Braincell.client().getShaderHandler().getLightingWorkflow().addLight(new SimplePointLight(EntityUtil.easedPos(livingEntityIn, BCStaticData.partialTick()).add(0, 0.5, 0), new Vec3(0.8F, 0.1F, 0.6F), radius, 0.8F, 3.8F));
		}
		return super.shouldRender(livingEntityIn, camera, camX, camY, camZ);
		
	}
	
	@Override
	public ResourceLocation getTextureLocation(EnderDragonRewardEntity entity) {
		return TEXTURES;
	}
}
