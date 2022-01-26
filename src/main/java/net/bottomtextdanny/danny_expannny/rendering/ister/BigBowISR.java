package net.bottomtextdanny.danny_expannny.rendering.ister;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.bottomtextdanny.braincell.underlying.util.BCMath;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.objects.items.bow.DannyBowItem;
import net.bottomtextdanny.danny_expannny.rendering.RenderUtils;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil;
import net.bottomtextdanny.danny_expannny.capabilities.item.DEStackCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;

public class BigBowISR extends BlockEntityWithoutLevelRenderer {

	public BigBowISR() {
		super(null, null);
	}

	@Override
    public void renderByItem(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
	    String type;
	    if (transformType == ItemTransforms.TransformType.GUI) {
		    type = "_gui";
	    } else {
		    type = "_handheld";
	    }
	   
	    BakedModel ibakedmodel = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager().getModel(new ModelResourceLocation(DannysExpansion.ID, stack.getItem().getRegistryName().getPath() + type, "inventory"));
	    boolean flag = transformType == ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND || transformType == ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND;
	    boolean flag1 = transformType == ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND || transformType == ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND;
	    
	    matrixStack.translate(0.5F, 0.5F, 0.5F);
	
	    LazyOptional<DEStackCapability> lazyCap = stack.getCapability(DEStackCapability.CAP);
	
	    if (lazyCap.isPresent()) {
		    DEStackCapability capability = lazyCap.orElse(null);
		
		    if (capability.getHolder() != null) {
			    Player player = capability.getHolder();
			
			    if (player.getUseItem() == stack) {
				    DannyBowItem bow = (DannyBowItem) stack.getItem();
				    float prog = bow.getFloatProgression(player, stack);
				    int state = 0;
				
				    if (prog > 0) {
					    if (prog > 0.65F) {
						    state++;
					    }
					    if (prog > 0.9F) {
						    state++;
					    }
					    
					    ibakedmodel = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager().getModel(new ModelResourceLocation(DannysExpansion.ID, stack.getItem().getRegistryName().getPath() + type + "_pulling_" + state, "inventory"));
					
					    if (flag1) {
						    float k = flag ? -1.0F : 1.0F;
						
						    matrixStack.translate((double) ((float) k * -0.2785682F), (double) 0.18344387F, (double) 0.15731531F);
						    matrixStack.mulPose(Vector3f.XP.rotationDegrees(-13.935F));
						    matrixStack.mulPose(Vector3f.YP.rotationDegrees((float) k * 35.3F));
						    matrixStack.mulPose(Vector3f.ZP.rotationDegrees((float) k * -9.785F));
						    float f8 = bow.getClampCount(player, stack) - (bow.getTrueMaxCount(stack) - DEUtil.PARTIAL_TICK + 1.0F);
						    float f12 = f8 / 20.0F;
						    f12 = (f12 * f12 + f12 * 2.0F) / 3.0F;
						    if (f12 > 1.0F) {
							    f12 = 1.0F;
						    }
						
						    if (f12 > 0.1F) {
							    float f15 = BCMath.sin((f8 - 0.1F) * 1.3F);
							    float f18 = f12 - 0.1F;
							    float f20 = f15 * f18;
							    matrixStack.translate((double) (f20 * 0.0F), (double) (f20 * 0.04F), (double) (f20 * 0.0F));
						    }
						
						    matrixStack.translate((double) (f12 * 0.0F), (double) (f12 * 0.0F), (double) (f12 * 0.04F));
						    matrixStack.scale(1.0F, 1.0F, 1.0F + f12 * 0.2F);
						    matrixStack.mulPose(Vector3f.YN.rotationDegrees((float) k * 45.0F));
					    }
				    }
			    }
		    }
	    }
	
	
	
	    if (transformType == ItemTransforms.TransformType.GUI) {
		    RenderUtils.renderItemModelIntoGUI(stack, combinedLight, ibakedmodel);
	    } else {
		    Minecraft.getInstance().getItemRenderer().render(stack, transformType, flag, matrixStack, buffer, combinedLight, combinedOverlay, ibakedmodel);
	    }
    }
}
