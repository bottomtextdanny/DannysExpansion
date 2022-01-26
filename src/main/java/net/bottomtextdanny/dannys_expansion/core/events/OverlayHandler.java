package net.bottomtextdanny.dannys_expansion.core.events;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.structure.client_sided.gun_rendering.ScopeRenderingData;
import net.bottomtextdanny.danny_expannny.ClientInstance;
import net.bottomtextdanny.danny_expannny.rendering.RenderUtils;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.MountEntity;
import net.bottomtextdanny.danny_expannny.objects.items.bow.DannyBowItem;
import net.bottomtextdanny.danny_expannny.objects.items.gun.GunItem;
import net.bottomtextdanny.danny_expannny.objects.items.gun.IScopingGun;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerGunModule;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = DannysExpansion.ID, value = Dist.CLIENT)
public class OverlayHandler {
    public static float fovMultiplier;
    public static float xInnerScopeOffeset;
	public static float yInnerScopeOffeset;
	
//    public static SimplexNoiseMapper NOISE_2D = new SimplexNoiseMapper();
//    public static Noise2DMapper NOISE_2D1 = new Noise2DMapper();
//    public static Noise2DMapper NOISE_2D2 = new Noise2DMapper();
//    public static Noise2DMapper NOISE_2D3 = new Noise2DMapper();
//    public static Noise2DMapper NOISE_2D4 = new Noise2DMapper();
//    public static Noise2DMapper NOISE_2D5 = new Noise2DMapper();

//minecraft simplex noise.
//only up to 3 dimensions :(.

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void bubbleExample(RenderGameOverlayEvent.Pre event) {
	//	if (Minecraft.getInstance().player != null) {
	//		//time in ticks, scaled to 0.02 for softer mutation over time.
	//		final double timeInTicks = Minecraft.getInstance().player.world.getGameTime() * 0.02;
	//
	//		//base size of the circle
	//		final float circleSize = 70;
	//
	//		//Size of the variable noise over the circle.
	//		final float noiseMutationSize = 60;
	//
	//		//Scales the circle of mapped noise, for sharper/softer looks.
	//		float scaleMutation = 0.25F;
	//
	//		//dots populating the circle
	//		final int numberOfDots = 100;
	//
	//		for (int i = 1; i <= numberOfDots; i++) {
	//
	//			//PI by two = 360 radians = full circle.
	//			float PIByTwo = (float) (Math.PI * 2);
	//
	//			//Fixes the difference between i and numberOfDots to 0.0 - 1.0
	//			//, then fixes that to 0 - 360 radians.
	//			float fixedRangeForTrigonometry = (PIByTwo * ((float)i / numberOfDots));
	//
	//			//3d noise from SimplexNoiseGenerator class
	//			double rawNoise = simplexNoiseGeneratorInstance.getValue(
	//				Mth.sin(fixedRangeForTrigonometry) * scaleMutation,
	//				Mth.cos(fixedRangeForTrigonometry) * scaleMutation,//First two dimensions are used to make a circle of mapped noise.
	//				timeInTicks); //Third dimension is time, so the circle of noise itself mutates creating cool look.
	//
	//			double noiseFactor = (rawNoise * noiseMutationSize) + circleSize;
	//
	//			///////////////////////////////
	//
	//			//Code for rendering each dot...
	//			int height = event.getWindow().getScaledHeight();
	//			int width = event.getWindow().getScaledWidth();
	//			final int blitOffset = 0;
	//
	//			int x1 = width / 2 - Mth.floor(noiseFactor * Math.cos(Math.PI * ((float)i / ((float)numberOfDots / 2))));
	//			int x2 = x1 + 1;
	//			int y1 = height / 2 - Mth.floor(noiseFactor * Math.sin(Math.PI * ((float)i / ((float)numberOfDots / 2))));
	//			int y2 = y1 + 1;
	//			RenderSystem.getModelViewStack().pushPose();
	//			RenderSystem.enableBlend();
	//			RenderSystem.enableAlphaTest();
	//			BufferBuilder bufferbuilder = Tesselator.getInstance().getBuffer();
	//			bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
	//			bufferbuilder.pos(event.getMatrixStack().getLast().getMatrix(), (float)x1, (float)y2, (float)blitOffset).color(255, 255, 255, 255).endVertex();
	//			bufferbuilder.pos(event.getMatrixStack().getLast().getMatrix(), (float)x2, (float)y2, (float)blitOffset).color(255, 255, 255, 255).endVertex();
	//			bufferbuilder.pos(event.getMatrixStack().getLast().getMatrix(), (float)x2, (float)y1, (float)blitOffset).color(255, 255, 255, 255).endVertex();
	//			bufferbuilder.pos(event.getMatrixStack().getLast().getMatrix(), (float)x1, (float)y1, (float)blitOffset).color(255, 255, 255, 255).endVertex();
	//			bufferbuilder.finishDrawing();
	//			RenderSystem.enableAlphaTest();
	//			WorldVertexBufferUploader.draw(bufferbuilder);
	//			RenderSystem.getModelViewStack().popPose();
	//		}
	//	}
	}


	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void overlayEvent(RenderGameOverlayEvent.PreLayer event) {
		LocalPlayer player = ClientInstance.player();
		Options gamesettings = Minecraft.getInstance().options;
		int height = event.getWindow().getGuiScaledHeight();
		int width = event.getWindow().getGuiScaledWidth();

		int widthByTwo = width / 2;
		int heightByTwo = height / 2;
		if (!ClientInstance.gs().hideGui) {
			if(ClientInstance.player().isPassenger() && ClientInstance.player().getVehicle() instanceof MountEntity) {
				if (event.getOverlay() == ForgeIngameGui.HOTBAR_ELEMENT && player.getVehicle() instanceof MountEntity) {
					MountEntity mount = (MountEntity) player.getVehicle();

					float progress = mount.getProgress01();
					float invProgress = 1.0F - mount.getProgress01();
					double abilityProg = (mount.getAbilityTimer() - 0.2) / 10000;
					double invAbilityProg = 1.0F - (mount.getAbilityTimer() - 0.2) / 10000;


					RenderSystem.getModelViewStack().pushPose();
					RenderSystem.setShaderTexture(0, new ResourceLocation(DannysExpansion.ID, "textures/gui/mount_bar.png"));

					if (mount.usesAbilityBar()) {
						GuiComponent.blit(event.getMatrixStack(), widthByTwo + 138, height - 80, 0, 12, 0, 5, 50, 80, 28);
						GuiComponent.blit(event.getMatrixStack(), widthByTwo + 138, (int) (height - 80 + 50 * invAbilityProg), 0, 17, (int) (50 * invAbilityProg), 5, (int) (50 * abilityProg + 1), 80, 28);
					}

					GuiComponent.blit(event.getMatrixStack(), widthByTwo + 132, height - 82, 0, 0, 0, 6, 52, 80, 28);
					GuiComponent.blit(event.getMatrixStack(), widthByTwo + 132, (int) (height - 82 + 52 * invProgress), 0, 6, (int) (52 * invProgress), 6, (int) (52 * progress + 1), 80, 28);
					GuiComponent.blit(event.getMatrixStack(), widthByTwo + 121, height - 30, 0, 0, 52, 28, 28, 80, 28);

					RenderSystem.getModelViewStack().popPose();
				}


			}

			if (event.getOverlay() == ForgeIngameGui.HOTBAR_ELEMENT) {
//				int lightsRendered = 0;
//				if (DE.clientManager.shaderManager.lightingWorkflow != null) {
//					lightsRendered = DE.clientManager.shaderManager.lightingWorkflow.lightsRendered();
//				}
//				GuiComponent.drawString(event.getMatrixStack(), Minecraft.getInstance().font, "lights rendered: " + lightsRendered, 1, heightByTwo + 50, 0x00ff00);

			}

			if (player != null && player.isAlive()) {
				if (event.getOverlay() == ForgeIngameGui.HOTBAR_ELEMENT) {
					PlayerGunModule gunModule = PlayerHelper.gunModule(player);

					if (gamesettings.getCameraType().isFirstPerson() && gunModule.getGunScoping().getItem() instanceof IScopingGun) {
						event.setCanceled(true);
					}
				} else if (event.getOverlay() == ForgeIngameGui.CROSSHAIR_ELEMENT) {
					if (gamesettings.getCameraType().isFirstPerson() && player.getUseItem() == ItemStack.EMPTY) {
						if (Minecraft.getInstance().gameMode.getPlayerMode() != GameType.SPECTATOR) {
							label:
							{
								PlayerGunModule gunModule = PlayerHelper.gunModule(player);
								if (gunModule.getPreviousGun().getItem() instanceof GunItem<?>) {
									if (gunModule.getGunScoping().getItem() instanceof IScopingGun gun) {
										ScopeRenderingData data = DannysExpansion.client().getGunRenderData().getScopeData(gun);
										event.setCanceled(true);
										int scopeSizeDivTwo = data.scopeSize() / 2;
										int scopeSizeByTwo = data.scopeSize() * 2;
										RenderSystem.enableBlend();
										RenderSystem.setShader(GameRenderer::getPositionTexShader);
										RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
										RenderSystem.setShaderTexture(0, data.scopeTexturePaths()[2]);

										RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_DST_COLOR, GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_DST_COLOR);

										RenderUtils.fBlit(event.getMatrixStack(), widthByTwo - scopeSizeDivTwo - DannysExpansion.clientManager().innerScopeXOffsetI(event.getPartialTicks()), heightByTwo - scopeSizeDivTwo - DannysExpansion.clientManager().innerScopeYOffsetI(event.getPartialTicks()), 0, 0, 0, data.scopeSize(), data.scopeSize(), data.scopeSize(), data.scopeSize());

										RenderSystem.defaultBlendFunc();

										RenderSystem.setShaderTexture(0, data.scopeTexturePaths()[1]);

										RenderUtils.fBlit(event.getMatrixStack(), widthByTwo - data.scopeSize() - DannysExpansion.clientManager().innerScopeXOffsetI(event.getPartialTicks()), heightByTwo - data.scopeSize() - DannysExpansion.clientManager().innerScopeYOffsetI(event.getPartialTicks()), 0, 0, 0, scopeSizeByTwo, scopeSizeByTwo, scopeSizeByTwo, scopeSizeByTwo);

										RenderSystem.setShaderTexture(0, data.scopeTexturePaths()[0]);

										GuiComponent.blit(event.getMatrixStack(), widthByTwo - scopeSizeDivTwo, heightByTwo - scopeSizeDivTwo, 0, 0, 0, data.scopeSize(), data.scopeSize(), data.scopeSize(), data.scopeSize());

										RenderSystem.getModelViewStack().pushPose();

										int x1 = 0, x2 = width, y1 = 0, y2 = heightByTwo - scopeSizeDivTwo;

										RenderSystem.setShader(GameRenderer::getPositionColorShader);
										Tesselator tess = Tesselator.getInstance();
										BufferBuilder bufferbuilder = tess.getBuilder();
										bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
										bufferbuilder.vertex(event.getMatrixStack().last().pose(), x1, y2, 0).color(10, 0, 20, 255).endVertex();
										bufferbuilder.vertex(event.getMatrixStack().last().pose(), x2, y2, 0).color(10, 0, 20, 255).endVertex();
										bufferbuilder.vertex(event.getMatrixStack().last().pose(), x2, y1, 0).color(10, 0, 20, 255).endVertex();
										bufferbuilder.vertex(event.getMatrixStack().last().pose(), x1, y1, 0).color(10, 0, 20, 255).endVertex();
										bufferbuilder.end();

										BufferUploader.end(bufferbuilder);

										y1 = heightByTwo + scopeSizeDivTwo;
										y2 = height;

										bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
										bufferbuilder.vertex(event.getMatrixStack().last().pose(), x1, y2, 0).color(10, 0, 20, 255).endVertex();
										bufferbuilder.vertex(event.getMatrixStack().last().pose(), x2, y2, 0).color(10, 0, 20, 255).endVertex();
										bufferbuilder.vertex(event.getMatrixStack().last().pose(), x2, y1, 0).color(10, 0, 20, 255).endVertex();
										bufferbuilder.vertex(event.getMatrixStack().last().pose(), x1, y1, 0).color(10, 0, 20, 255).endVertex();
										bufferbuilder.end();

										BufferUploader.end(bufferbuilder);

										x2 = widthByTwo - scopeSizeDivTwo;
										y1 = heightByTwo - scopeSizeDivTwo;
										y2 = heightByTwo + scopeSizeDivTwo;

										bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
										bufferbuilder.vertex(event.getMatrixStack().last().pose(), x1, y2, 0).color(10, 0, 20, 255).endVertex();
										bufferbuilder.vertex(event.getMatrixStack().last().pose(), x2, y2, 0).color(10, 0, 20, 255).endVertex();
										bufferbuilder.vertex(event.getMatrixStack().last().pose(), x2, y1, 0).color(10, 0, 20, 255).endVertex();
										bufferbuilder.vertex(event.getMatrixStack().last().pose(), x1, y1, 0).color(10, 0, 20, 255).endVertex();
										bufferbuilder.end();

										BufferUploader.end(bufferbuilder);

										x1 = widthByTwo + scopeSizeDivTwo;
										x2 = width;

										bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
										bufferbuilder.vertex(event.getMatrixStack().last().pose(), x1, y2, 0).color(10, 0, 20, 255).endVertex();
										bufferbuilder.vertex(event.getMatrixStack().last().pose(), x2, y2, 0).color(10, 0, 20, 255).endVertex();
										bufferbuilder.vertex(event.getMatrixStack().last().pose(), x2, y1, 0).color(10, 0, 20, 255).endVertex();
										bufferbuilder.vertex(event.getMatrixStack().last().pose(), x1, y1, 0).color(10, 0, 20, 255).endVertex();
										bufferbuilder.end();

										BufferUploader.end(bufferbuilder);

										RenderSystem.getModelViewStack().popPose();

										break label;
									}
									RenderSystem.getModelViewStack().pushPose();
									RenderSystem.enableBlend();

									RenderSystem.setShaderTexture(0, new ResourceLocation(DannysExpansion.ID, "textures/gui/gun_crosshair.png"));
									renderGunCrosshair(event.getMatrixStack(), width, height, event.getPartialTicks(), event);
									RenderSystem.getModelViewStack().popPose();
								}
							}
						}
					}
				}
			}
		}

	}



    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void playerRenderHandler(RenderPlayerEvent.Pre event) {
        if (event.getPlayer().getUseItem().getItem() instanceof DannyBowItem) {
            if (event.getPlayer().getUseItemRemainingTicks() > 0) {

                if (event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND) == event.getPlayer().getUseItem()) {
                    event.getRenderer().getModel().rightArmPose = HumanoidModel.ArmPose.BOW_AND_ARROW;
                }
                else event.getRenderer().getModel().leftArmPose = HumanoidModel.ArmPose.BOW_AND_ARROW;
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void fovModifierEvent(EntityViewRenderEvent.FieldOfView event) {
        LocalPlayer player = ClientInstance.player();

		if (player != null && player.isAlive()) {
			PlayerGunModule gunModule = PlayerHelper.gunModule(player);

			if (gunModule.getGunScoping().getItem() instanceof IScopingGun scopingGun) {
				event.setFOV(scopingGun.scopingFov() * Minecraft.getInstance().options.fov);
			}

			if (player.isAlive() && player.getUseItem().getItem() instanceof DannyBowItem bow && player.getTicksUsingItem() > 0) {
				float bowModifier = Math.min((float) Mth.lerp(event.getPartialTicks(), DannysExpansion.clientManager().cPrevItemUseCount, player.getTicksUsingItem()), bow.getTrueMaxCount(player.getUseItem()));

				fovMultiplier = bow.getFovModifier() * bowModifier / bow.getTrueMaxCount(player.getUseItem());

			} else {
				fovMultiplier *= Mth.clamp(fovMultiplier, 0, 0.9);
			}

			event.setFOV(event.getFOV() - fovMultiplier);
		}
    }

    @OnlyIn(Dist.CLIENT)
    protected static void renderGunCrosshair(PoseStack poseStack, int width, int height, float partialTicks, RenderGameOverlayEvent.PreLayer event) {
	    Options gamesettings = Minecraft.getInstance().options;
	    if (gamesettings.getCameraType().isFirstPerson()) {
		    if (Minecraft.getInstance().gameMode.getPlayerMode() != GameType.SPECTATOR || isTargetNamedMenuProvider(Minecraft.getInstance().hitResult)) {
			    if (gamesettings.renderDebug && !gamesettings.hideGui && !Minecraft.getInstance().player.isReducedDebugInfo() && !gamesettings.reducedDebugInfo) {
//				    PoseStack pose = RenderSystem.getModelViewStack();
//			    	pose.pushPose();
//				    pose.translate((float)(width / 2), (float)(height / 2), 0);
//				    Camera activerenderinfo = Minecraft.getInstance().gameRenderer.getMainCamera();
//				    pose.mulPose(new Quaternion(activerenderinfo.getXRot(), -1.0F, 0.0F, 0.0F));
//					pose.mulPose(new Quaternion(activerenderinfo.getYRot(), 0.0F, 1.0F, 0.0F));
//				    pose.scale(-1.0F, -1.0F, -1.0F);
//
//				    RenderSystem.renderCrosshair(10);
//				    RenderSystem.getModelViewStack().popPose();
			    } else {
					event.setCanceled(true);
				    double fov = Minecraft.getInstance().options.fov;
				    float visualDisp = (float)(width * Mth.lerp(partialTicks, DannysExpansion.clientManager().cPrevRenderDispersion, DannysExpansion.clientManager().cRenderDispersion) / fov) / 2;
				   
				    RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
				
				    RenderUtils.fBlit(poseStack, width / 2 - 5 - visualDisp, height / 2 - 3, 0, 0, 7, 6, 5, 19, 19);
				
				    RenderUtils.fBlit(poseStack, width / 2 + visualDisp, height / 2 - 3, 0, 13, 7, 6, 5, 19, 19);
				
				    RenderUtils.fBlit(poseStack, width / 2 - 2, height / 2 - 6 - visualDisp, 0, 7, 0, 5, 6, 19, 19);
				
				    RenderUtils.fBlit(poseStack, width / 2 - 2, height / 2 - 1 + visualDisp, 0, 7, 13, 5, 6, 19, 19);
				
				
			    }
		    }
	    }
    }

    @OnlyIn(Dist.CLIENT)
    private static boolean isTargetNamedMenuProvider(HitResult rayTraceIn) {
        if (rayTraceIn == null) {
            return false;
        } else if (rayTraceIn.getType() == HitResult.Type.ENTITY) {
            return ((EntityHitResult)rayTraceIn).getEntity() instanceof MenuProvider;
        } else if (rayTraceIn.getType() == HitResult.Type.BLOCK) {
            BlockPos blockpos = ((BlockHitResult)rayTraceIn).getBlockPos();
            Level world = Minecraft.getInstance().level;
            return world.getBlockState(blockpos).getMenuProvider(world, blockpos) != null;
        } else {
            return false;
        }
    }
}

