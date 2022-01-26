package net.bottomtextdanny.danny_expannny.structure.client_sided.gun_rendering;

import com.google.common.collect.Maps;
import net.bottomtextdanny.braincell.mod.structure.client_sided.events.PlayerPoseEvent;
import net.bottomtextdanny.braincell.underlying.util.BCMath;
import net.bottomtextdanny.danny_expannny.objects.items.gun.GunItem;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil;
import net.bottomtextdanny.danny_expannny.objects.items.gun.IScopingGun;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerGunModule;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerHelper;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public final class GunRenderData {
    private final Map<GunItem<?>, GunModelRenderer> rendererByGun = Maps.newIdentityHashMap();
    private final Map<IScopingGun, ScopeRenderingData> scopeTexturesByGun = Maps.newIdentityHashMap();

    public GunRenderData() {
        super();
        MinecraftForge.EVENT_BUS.addListener((PlayerPoseEvent event) -> {
            final AbstractClientPlayer player = event.getPlayer();
            final PlayerModel<?> model = event.getModel();
            if (player.isAlive() && player.getUseItem() == ItemStack.EMPTY) {
                boolean rightHanded = player.getMainArm() == HumanoidArm.RIGHT;
                ModelPart mainHand = rightHanded ? model.rightArm : model.leftArm;
                ModelPart offHand = rightHanded ? model.leftArm : model.rightArm;
                PlayerGunModule gunModule = PlayerHelper.gunModule(player);
                float f = rightHanded ? 1 : -1;
                float f1 = f * BCMath.FRAD;
                float f3 = Mth.lerp(DEUtil.PARTIAL_TICK, gunModule.getPrevRecoil(), gunModule.getRecoil());

                if (!player.isVisuallySwimming()) {
                    if (player.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof GunItem) {

                        mainHand.xRot = Math.max(model.head.xRot - BCMath.FRAD * (90.0F + f3), BCMath.FRAD * -205.0F);
                        mainHand.yRot = model.head.yRot + BCMath.FRAD;

                        offHand.x += -f * 1.5;
                        offHand.xRot = Math.max(model.head.xRot - BCMath.FRAD * (77.0F + f3), BCMath.FRAD * -190.0F);
                        offHand.yRot = Mth.clamp(model.head.yRot + f1 * 50.0F, BCMath.FRAD * -70.0F, BCMath.FRAD * 70.0F);

                        model.head.xRot += BCMath.FRAD * (0.2F * -f3);
                    } else if (player.getItemBySlot(EquipmentSlot.OFFHAND).getItem() instanceof GunItem) {
                        mainHand = rightHanded ? model.leftArm : model.rightArm;
                        offHand = rightHanded ? model.rightArm : model.leftArm;
                        f = rightHanded ? -1.0F : 1.0F;

                        mainHand.xRot = Math.max(model.head.xRot - BCMath.FRAD * (90.0F + f3), BCMath.FRAD * -205.0F);
                        mainHand.yRot = model.head.yRot + BCMath.FRAD;

                        offHand.x += -f * 1.5;
                        offHand.xRot = Math.max(model.head.xRot - BCMath.FRAD * (77.0F + f3), BCMath.FRAD * -190.0F);
                        offHand.yRot = Mth.clamp(model.head.yRot + f1 * -50.0F, BCMath.FRAD * -70.0F, BCMath.FRAD * 70.0F);
                    }

                    model.head.xRot = Math.max(model.head.xRot + BCMath.FRAD * (0.1F * -f3), BCMath.FRAD * -185.0F);
                }
            }
        });
    }

    public void putRenderer(GunItem<?> item, GunModelRenderer renderer) {
        this.rendererByGun.put(item, renderer);
    }

    public void putScopeResources(IScopingGun item, ScopeRenderingData resources) {
        this.scopeTexturesByGun.put(item, resources);
    }

    public GunModelRenderer getRenderer(GunItem<?> gunItem) {
        return this.rendererByGun.get(gunItem);
    }

    public ScopeRenderingData getScopeData(IScopingGun gunItem) {
        return this.scopeTexturesByGun.get(gunItem);
    }
}
