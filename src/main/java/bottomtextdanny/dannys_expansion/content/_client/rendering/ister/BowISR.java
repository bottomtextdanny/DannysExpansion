package bottomtextdanny.dannys_expansion.content._client.rendering.ister;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content.items.bow.DEBowItem;
import bottomtextdanny.dannys_expansion._util._client.RenderUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import bottomtextdanny.braincell.base.BCMath;
import bottomtextdanny.braincell.mixin_support.ItemStackClientExtensor;
import bottomtextdanny.braincell.mod._base.BCStaticData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class BowISR extends BlockEntityWithoutLevelRenderer {

	public BowISR() {
		super(null, null);
	}
	@Override
    public void renderByItem(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        BakedModel ibakedmodel = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager().getModel(new ModelResourceLocation(DannysExpansion.ID, stack.getItem().getRegistryName().getPath() + "_handheld", "inventory"));
        boolean flag = transformType == ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND || transformType == ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND;
        boolean flag1 = transformType == ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND || transformType == ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND;

        matrixStack.translate(0.5F, 0.5F, 0.5F);

		LivingEntity living = ((ItemStackClientExtensor)(Object)stack).getCachedHolder();

		onLiving:
		{
			if (living == null) break onLiving;

			if (living.getUseItem() == stack) {
				DEBowItem bow = (DEBowItem) stack.getItem();
				float prog = bow.getFloatProgression(living, stack);
				int state = 0;

				if (prog > 0) {
					if (prog > 0.65F) {
						state++;
					}
					if (prog > 0.9F) {
						state++;
					}

					ibakedmodel = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager().getModel(new ModelResourceLocation(DannysExpansion.ID, stack.getItem().getRegistryName().getPath() + "_handheld_pulling_" + state, "inventory"));

					if (flag1) {
						float k = flag ? -1.0F : 1.0F;

						matrixStack.translate((double) ((float) k * -0.2785682F), (double) 0.18344387F, (double) 0.15731531F);
						matrixStack.mulPose(Vector3f.XP.rotationDegrees(-13.935F));
						matrixStack.mulPose(Vector3f.YP.rotationDegrees((float) k * 35.3F));
						matrixStack.mulPose(Vector3f.ZP.rotationDegrees((float) k * -9.785F));
						float f8 = bow.getClampCount(living, stack) - (bow.getTrueMaxCount(stack) + 1.0F);
						float f12 = f8 / 20.0F;
						f12 = (f12 * f12 + f12 * 2.0F) / 3.0F;
						if (f12 > 1.0F) {
							f12 = 1.0F;
						}

						if (f12 < 0.9F) {
							float f15 = BCMath.sin((living.tickCount + BCStaticData.partialTick()) * 1.3F);
							float f18 = 1 - f12;
							float f20 = f15 * f18;
							matrixStack.translate(0.0, (double) f20 * 0.004, 0.0);
						}

						matrixStack.translate((double) (f12 * 0.0F), (double) (f12 * 0.0F), (double) (f12 * 0.04F));
						matrixStack.scale(1.0F, 1.0F, 1.0F + f12 * 0.2F);
						matrixStack.mulPose(Vector3f.YN.rotationDegrees((float) k * 45.0F));

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
