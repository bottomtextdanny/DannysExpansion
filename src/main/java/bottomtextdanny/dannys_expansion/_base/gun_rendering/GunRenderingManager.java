package bottomtextdanny.dannys_expansion._base.gun_rendering;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion._util._client.CMC;
import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerGunModule;
import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerHelper;
import bottomtextdanny.dannys_expansion.content.items.bow.DEBowItem;
import bottomtextdanny.dannys_expansion.content.items.gun.GunItem;
import bottomtextdanny.dannys_expansion.content.items.gun.ScopingGun;
import com.google.common.collect.Maps;
import bottomtextdanny.braincell.base.BCMath;
import bottomtextdanny.braincell.base.vector.DistanceCalc3;
import bottomtextdanny.braincell.mod._base.BCStaticData;
import bottomtextdanny.braincell.mod._mod.client_sided.events.PlayerPoseEvent;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public final class GunRenderingManager {
    private final Map<GunItem<?>, GunModelRenderer> rendererByGun = Maps.newIdentityHashMap();
    private final Map<ScopingGun, ScopeRenderingData> scopeTexturesByGun = Maps.newIdentityHashMap();

    public GunRenderingManager() {
        super();
        IEventBus bus = MinecraftForge.EVENT_BUS;

        bus.addListener((PlayerPoseEvent event) -> {
            handlePlayerModel(event.getPlayer(), event.getModel());
        });

        bus.addListener(this::handleLevelCamera);
        bus.addListener(this::handleHandRendering);
        bus.addListener(this::handleClickInput);
        bus.addListener(this::handleMovementInput);
    }

    public void handlePlayerModel(AbstractClientPlayer player, PlayerModel<?> model) {
        if (player.isAlive() && player.getUseItem() == ItemStack.EMPTY) {
            boolean rightHanded = player.getMainArm() == HumanoidArm.RIGHT;
            ModelPart mainHand = rightHanded ? model.rightArm : model.leftArm;
            ModelPart offHand = rightHanded ? model.leftArm : model.rightArm;
            PlayerGunModule gunModule = PlayerHelper.gunModule(player);
            float handSignStep = rightHanded ? 1 : -1;
            float radianStep = handSignStep * BCMath.FRAD;
            float easedRecoil = Mth.lerp(BCStaticData.partialTick(), gunModule.getPrevRecoil(), gunModule.getRecoil());

            if (!player.isVisuallySwimming()) {
                if (player.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof GunItem) {
                    mainHand.xRot = Math.max(model.head.xRot - BCMath.FRAD * (90.0F + easedRecoil), BCMath.FRAD * -205.0F);
                    mainHand.yRot = model.head.yRot + BCMath.FRAD;

                    offHand.x += -handSignStep * 1.5;
                    offHand.xRot = Math.max(model.head.xRot - BCMath.FRAD * (77.0F + easedRecoil), BCMath.FRAD * -190.0F);
                    offHand.yRot = Mth.clamp(model.head.yRot + radianStep * 50.0F, BCMath.FRAD * -70.0F, BCMath.FRAD * 70.0F);

                    model.head.xRot += BCMath.FRAD * (0.2F * -easedRecoil);
                } else if (player.getItemBySlot(EquipmentSlot.OFFHAND).getItem() instanceof GunItem) {
                    mainHand = rightHanded ? model.leftArm : model.rightArm;
                    offHand = rightHanded ? model.rightArm : model.leftArm;
                    handSignStep = rightHanded ? -1.0F : 1.0F;

                    mainHand.xRot = Math.max(model.head.xRot - BCMath.FRAD * (90.0F + easedRecoil), BCMath.FRAD * -205.0F);
                    mainHand.yRot = model.head.yRot + BCMath.FRAD;

                    offHand.x += -handSignStep * 1.5;
                    offHand.xRot = Math.max(model.head.xRot - BCMath.FRAD * (77.0F + easedRecoil), BCMath.FRAD * -190.0F);
                    offHand.yRot = Mth.clamp(model.head.yRot + radianStep * -50.0F, BCMath.FRAD * -70.0F, BCMath.FRAD * 70.0F);
                }

                model.head.xRot = Math.max(model.head.xRot + BCMath.FRAD * (0.1F * -easedRecoil), BCMath.FRAD * -185.0F);
            }
        }
    }

    public void handleLevelTick() {
        LocalPlayer player = CMC.player();
        GunClientData gunData = DannysExpansion.client().getGunData();

        if (Minecraft.getInstance().isPaused()) return;
        if (player != null && gunData != null) {
            boolean hasGun = false;
            gunData.itemUseCountO = player.getTicksUsingItem();
            gunData.pitchRecoilO = gunData.pitchRecoil;
            gunData.recoilO = gunData.recoil;
            gunData.renderDispersionO = gunData.renderDispersion;

            for (InteractionHand hand : InteractionHand.values()) {
                Item itemInHand = player.getItemInHand(hand).getItem();
                if (!hasGun && itemInHand instanceof GunItem<?> gunInHand) {
                    float movement = (float) DistanceCalc3.EUCLIDEAN.distance(
                            player.xOld, player.yOld, player.zOld,
                            player.getX(), player.getY(), player.getZ());

                    gunInHand.updateClientDispersion(player, gunData.recoil, movement);
                    hasGun = true;
                }
            }

            if (gunData.recoil > 0.0F)
                gunData.recoil = Mth.clamp(gunData.recoil * gunData.recoilMult - gunData.recoilSubtract, 0.0F, Float.MAX_VALUE);

            gunData.pitchRecoil = Mth.clamp(gunData.pitchRecoil * gunData.pitchRecoilMult - gunData.pitchRecoilSubtract, 0.0F, Float.MAX_VALUE);
            gunData.renderDispersion += (gunData.dispersion - gunData.renderDispersion) * 0.6;
        }
    }

    private void handleLevelCamera(EntityViewRenderEvent.CameraSetup event) {
        LocalPlayer player = CMC.player();

        if (player != null && player.isAlive()) {
            GunClientData data = DannysExpansion.client().getGunData();
            PlayerGunModule gunModule = PlayerHelper.gunModule(player);
            float pitchRecoil = (float)Mth.lerp(event.getPartialTicks(), data.pitchRecoilO, data.pitchRecoil);
            float rollRecoil = pitchRecoil * 0.3F * data.rollDirection;
            float offsetFactor = 0.07F;

            event.setRoll(event.getRoll() - rollRecoil);

            if (Minecraft.getInstance().options.getCameraType() == CameraType.FIRST_PERSON) {
                event.setPitch(event.getPitch() - pitchRecoil);
            }

            if (gunModule.getGunScoping().getItem() instanceof ScopingGun) {
                offsetFactor *= 1.0F - ((ScopingGun) gunModule.getGunScoping().getItem()).scopingSensMult();
            }

            if (!Minecraft.getInstance().isPaused())
            data.pitchO = data.pitch;
            data.yawO = data.yaw;
            data.pitch = Mth.lerp(offsetFactor, data.pitch, event.getPitch());
            data.yaw = Mth.lerp(offsetFactor, data.yaw, event.getYaw());
        }
    }

    public void handleMovementInput(MovementInputUpdateEvent event) {
        LocalPlayer player = CMC.player();

        if (player == null || !player.isAlive()) return;

        ItemStack useItemStack = player.getUseItem();

        Item item = useItemStack.getItem();

        float mult = 1.0F;

        if (item instanceof DEBowItem bow) {
            mult = bow.getNockMovementSpeed(player.level, player, useItemStack) * 5;
        }

        PlayerGunModule gunModule = PlayerHelper.gunModule(player);
        if (gunModule.getGunScoping().getItem() instanceof ScopingGun scopingGun) {
            mult = scopingGun.scopingMovementSpeed(gunModule.getGunScoping(), player);
        }

        event.getInput().forwardImpulse *= mult;
        event.getInput().leftImpulse *= mult;
    }

    public void handleClickInput(InputEvent.ClickInputEvent event) {
        LocalPlayer player = CMC.player();

        if (player == null || !player.isAlive()) return;

        for (InteractionHand hand : InteractionHand.values()) {
            if (event.getKeyMapping() == Minecraft.getInstance().options.keyAttack
                    && player.getItemInHand(hand).getItem() instanceof GunItem) {
                event.setCanceled(true);
                event.setSwingHand(false);
                return;
            }
        }
    }

    private void handleHandRendering(RenderHandEvent event) {
        LocalPlayer player = CMC.player();

        if (player == null || !player.isAlive()) return;

        PlayerGunModule gunModule = PlayerHelper.gunModule(player);

        if (gunModule.getGunScoping().getItem() instanceof ScopingGun)
            event.setCanceled(true);
    }

    public void putRenderer(GunItem<?> item, GunModelRenderer renderer) {
        this.rendererByGun.put(item, renderer);
    }

    public void putScopeResources(ScopingGun item, ScopeRenderingData resources) {
        this.scopeTexturesByGun.put(item, resources);
    }

    public GunModelRenderer getRenderer(GunItem<?> gunItem) {
        return this.rendererByGun.get(gunItem);
    }

    public ScopeRenderingData getScopeData(ScopingGun gunItem) {
        return this.scopeTexturesByGun.get(gunItem);
    }
}
