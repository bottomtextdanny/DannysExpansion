package net.bottomtextdanny.danny_expannny.structure.client_sided.gun_rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.bottomtextdanny.braincell.mod.structure.BCStaticData;
import net.bottomtextdanny.braincell.underlying.util.BCMath;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.objects.items.gun.GunItem;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerGunModule;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerHelper;
import net.minecraft.client.model.Model;
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
    private final Model model;

    private SimpleGunModelRenderer(ResourceLocation texturePath, Model model) {
        super();
        this.texturePath = texturePath;
        this.model = model;
    }

    public static SimpleGunModelRenderer create(ResourceLocation texturePath, Model model) {
        return new SimpleGunModelRenderer(texturePath, model);
    }

    @Override
    public void render(AbstractClientPlayer player, GunItem<?> gun, ItemStack stack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        float f = 0.0F;
        PlayerGunModule gunModule = PlayerHelper.gunModule(player);

        if (gunModule.getPreviousGun().sameItem(stack) && (transformType == ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND || transformType == ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND)) {
            f = Mth.lerp(BCStaticData.partialTick(), DannysExpansion.clientManager().cPrevRecoil - DannysExpansion.clientManager().cPitchPrevRecoil, DannysExpansion.clientManager().cRecoil - DannysExpansion.clientManager().cPitchRecoil);

            if (DannysExpansion.clientManager().cGunRetrieveFactor > 5) {
                f += Easing.EASE_OUT_BACK.progression(((DannysExpansion.clientManager().cGunRetrieveFactor - 5) - BCStaticData.partialTick()) / (int)(gun.cooldown() * 0.75)) * 135;
            }
        }

        poseStack.scale(0.5F, 0.5F, 0.5F);
        poseStack.translate(0.95, 1.05, 1.05);
        poseStack.mulPose(Vector3f.ZP.rotation(BCMath.FPI));
        poseStack.mulPose(Vector3f.XP.rotation(BCMath.FRAD * -f));
        poseStack.scale(1.2F, 1.2F, 1.2F);
        poseStack.translate(0.0, 0.075, 0.0);
        VertexConsumer ivertexbuilder = ItemRenderer.getFoilBufferDirect(buffer, this.model.renderType(this.texturePath), false, stack.hasFoil());
        this.model.renderToBuffer(poseStack, ivertexbuilder, combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
