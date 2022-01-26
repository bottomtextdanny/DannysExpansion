package net.bottomtextdanny.danny_expannny.rendering.entity;

import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.SpellRenderer;
import net.bottomtextdanny.braincell.mod.opengl_front.point_lighting.SimplePointLight;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.misc.EnderDragonRewardModel;
import net.bottomtextdanny.danny_expannny.objects.entities.EnderDragonRewardEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.EntityUtil;
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
			float radius = 10.0F * Mth.lerp(DEUtil.PARTIAL_TICK, livingEntityIn.getPrevFxIntensity(), livingEntityIn.getFxIntensity());
			Braincell.client().getPostprocessingHandler().getLightingWorkflow().addLight(new SimplePointLight(EntityUtil.easedPos(livingEntityIn, DEUtil.PARTIAL_TICK).add(0, 0.5, 0), new Vec3(0.8F, 0.1F, 0.6F), radius, 0.8F, 3.8F));
		}
		return super.shouldRender(livingEntityIn, camera, camX, camY, camZ);
		
	}
	
	@Override
	public ResourceLocation getTextureLocation(EnderDragonRewardEntity entity) {
		return TEXTURES;
	}
}
