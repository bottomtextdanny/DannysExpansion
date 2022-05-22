package bottomtextdanny.dannys_expansion._base.gun_rendering;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerGunModule;
import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerHelper;
import bottomtextdanny.dannys_expansion.content._client.model.guns.GunFireModel;
import bottomtextdanny.dannys_expansion.content._client.model.guns.GunModel;
import bottomtextdanny.dannys_expansion.content.items.gun.GunItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import bottomtextdanny.braincell.base.BCMath;
import bottomtextdanny.braincell.base.Easing;
import bottomtextdanny.braincell.mod._base.BCStaticData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SimpleGunModelRenderer implements GunModelRenderer {
    private final ResourceLocation texturePath;
    private final GunModel model;

    private SimpleGunModelRenderer(ResourceLocation texturePath, GunModel model) {
        super();
        this.texturePath = texturePath;
        this.model = model;
    }

    public static SimpleGunModelRenderer create(ResourceLocation texturePath, GunModel model) {
        return new SimpleGunModelRenderer(texturePath, model);
    }

    @Override
    public void render(AbstractClientPlayer player, GunItem<?> gun, ItemStack stack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        float f = 0.0F;

        boolean playerExists = player != null;
        PlayerGunModule gunModule = playerExists ? PlayerHelper.gunModule(player) : null;

        boolean reduce = transformType == ItemTransforms.TransformType.FIXED
                || transformType == ItemTransforms.TransformType.GROUND
                || transformType == ItemTransforms.TransformType.HEAD;
        float scale = reduce ? 0.45F : 0.625F;

        if (playerExists) {
            boolean firstPerson = transformType == ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND
                    || transformType == ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND;

            if (gunModule.getPreviousGun().sameItem(stack) && firstPerson) {
                GunClientData data = DannysExpansion.client().getGunData();

                f = Mth.lerp(BCStaticData.partialTick(), data.recoilO - data.pitchRecoilO, data.recoil - data.pitchRecoil);

                if (data.retrieveFactor > 5) {
                    f += Easing.EASE_OUT_BACK.progression(((data.retrieveFactor - 5) - BCStaticData.partialTick()) / (int) (gun.cooldown() * 0.75)) * 135;
                }
            }
        }

        poseStack.translate(0.5, 0.47, 0.6);
       //
        poseStack.mulPose(Vector3f.ZP.rotation(BCMath.FPI));
        poseStack.mulPose(Vector3f.XP.rotation(BCMath.FRAD * -f));

        poseStack.scale(scale, scale, scale);

        if (playerExists) {
            PlayerGunModule module = PlayerHelper.gunModule(player);

            if (!Minecraft.getInstance().isPaused()) {
                if (module.fireTimer <= 0) module.fireAnimation = null;
                else module.fireTimer--;
            }

            ResourceLocation fire = module.fireAnimation;

            if (fire != null && model.getFire() != null) {
                GunFireModel fireModel = new GunFireModel();
                VertexConsumer fireBuffer = buffer.getBuffer(fireModel.renderType(fire));

                poseStack.pushPose();
                model.getFire().translateRotateWithParentsInverted(poseStack);
                poseStack.translate(0.0, 0.0, 0.0);

                fireModel.renderToBuffer(poseStack, fireBuffer, combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
                poseStack.popPose();
            }
        }

        VertexConsumer gunBuffer = ItemRenderer.getFoilBufferDirect(buffer, this.model.renderType(this.texturePath), false, stack.hasFoil());

        model._animate(player, gunModule, gunModule == null ? 0 : gunModule.getGunUseTicks());
        this.model.renderToBuffer(poseStack, gunBuffer, combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
