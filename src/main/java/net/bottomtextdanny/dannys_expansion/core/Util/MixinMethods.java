package net.bottomtextdanny.dannys_expansion.core.Util;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.Blaze3D;
import net.bottomtextdanny.braincell.mod.structure.BraincellModules;
import net.bottomtextdanny.braincell.underlying.util.BCMath;
import net.bottomtextdanny.danny_expannny.objects.items.gun.GunItem;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil;
import net.bottomtextdanny.danny_expannny.objects.items.gun.IScopingGun;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerGunModule;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.util.SmoothDouble;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Random;
import java.util.function.Supplier;

public class MixinMethods {
	public static final String LOADINGDOTDOTDOT = "Loading...";
	public static final int DE_TEXT_ARR_SIZE;
	public static final ImmutableList<Supplier<String>> DE_FUNI_TEXT;
	public static final int DE_TEXT_ID;
	static {
        ImmutableList.Builder<Supplier<String>> builder = new ImmutableList.Builder<>();
        builder.add(() -> "glumbis is awesome");
        builder.add(() -> "honestly quite incredible");
        builder.add(() -> "f(x) = (1 / (1 + e^-x)) my beloved");
        builder.add(() -> "yosh is awesome");
        builder.add(() -> ":pleading_face:");
        builder.add(() -> "we do an interesting amount of buffoonery");
        builder.add(() -> "just a substantial deficit in skill");
        builder.add(() -> "im uninstalling your trash mod");
        builder.add(() -> "do your homework first!");
        DE_FUNI_TEXT = builder.build();
        DE_TEXT_ARR_SIZE = DE_FUNI_TEXT.size();
        DE_TEXT_ID = new Random().nextInt(DE_TEXT_ARR_SIZE);
	}
	
    public static void playerModelSetRotationAngles(PlayerModel model, Player entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entityIn.isAlive() && entityIn.getUseItem() == ItemStack.EMPTY) {
            boolean rightHanded = entityIn.getMainArm() == HumanoidArm.RIGHT;
            ModelPart mainHand = rightHanded ? model.rightArm : model.leftArm;
            ModelPart offHand = rightHanded ? model.leftArm : model.rightArm;
            PlayerGunModule gunModule = PlayerHelper.gunModule(entityIn);
            float f = rightHanded ? 1 : -1;
            float f1 = f * BCMath.FRAD;
            float f3 = Mth.lerp(DEUtil.PARTIAL_TICK, gunModule.getPrevRecoil(), gunModule.getRecoil());

            if (!entityIn.isVisuallySwimming()) {
                if (entityIn.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof GunItem) {

                    mainHand.xRot = Math.max(model.head.xRot - BCMath.FRAD * (90.0F + f3), BCMath.FRAD * -205.0F);
                    mainHand.yRot = model.head.yRot + BCMath.FRAD;

                    offHand.x += -f * 1.5;
                    offHand.xRot = Math.max(model.head.xRot - BCMath.FRAD * (77.0F + f3), BCMath.FRAD * -190.0F);
                    offHand.yRot = Mth.clamp(model.head.yRot + f1 * 50.0F, BCMath.FRAD * -70.0F, BCMath.FRAD * 70.0F);

                    model.head.xRot += BCMath.FRAD * (0.2F * -f3);
                } else if (entityIn.getItemBySlot(EquipmentSlot.OFFHAND).getItem() instanceof GunItem) {
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
    }

    public static void updatePlayerLook(double accumulatedDeltaX, double accumulatedDeltaY, SmoothDouble smoothTurnX, SmoothDouble smoothTurnY, boolean mouseGrabbed, double lastMouseEventTime) {
        Minecraft minecraft = Minecraft.getInstance();
        if (BraincellModules.MOUSE_HOOKS.isActive() && minecraft.player != null && minecraft.player.isAlive()) {
            PlayerGunModule gunModule = PlayerHelper.gunModule(minecraft.player);

            if (gunModule.getGunScoping().getItem() instanceof IScopingGun) {
                double time = Blaze3D.getTime();
                double timeDelta = time - lastMouseEventTime;
                lastMouseEventTime = time;

                if (mouseGrabbed && minecraft.isWindowActive()) {
                    int finalMultiplierFactor = 1;
                    double sensitivityFactor = minecraft.options.sensitivity * (1 - ((IScopingGun) gunModule.getGunScoping().getItem()).scopingSensMult()) * (double) 0.6F + (double)0.2F;
                    double easedSensitivityFactor = sensitivityFactor * sensitivityFactor * sensitivityFactor;
                    double movementFactor = easedSensitivityFactor * 8.0D;
                    double deltaXMovement;
                    double deltaYMovement;
                    if (minecraft.options.smoothCamera) {
                        double localDeltaXMovement = smoothTurnX.getNewDeltaValue(accumulatedDeltaX * movementFactor, timeDelta * movementFactor);
                        double localDeltaYMovement = smoothTurnY.getNewDeltaValue(accumulatedDeltaY * movementFactor, timeDelta * movementFactor);
                        deltaXMovement = localDeltaXMovement;
                        deltaYMovement = localDeltaYMovement;
                    } else if (minecraft.options.getCameraType().isFirstPerson() && minecraft.player.isScoping()) {
                        smoothTurnX.reset();
                        smoothTurnY.reset();
                        deltaXMovement = accumulatedDeltaX * easedSensitivityFactor;
                        deltaYMovement = accumulatedDeltaY * easedSensitivityFactor;
                    } else {
                        smoothTurnX.reset();
                        smoothTurnY.reset();
                        deltaXMovement = accumulatedDeltaX * movementFactor;
                        deltaYMovement = accumulatedDeltaY * movementFactor;
                    }

                    accumulatedDeltaX = 0.0D;
                    accumulatedDeltaY = 0.0D;

                    if (minecraft.options.invertYMouse) {
                        finalMultiplierFactor = -1;
                    }

                    minecraft.player.turn(-deltaXMovement, -deltaYMovement * (double)finalMultiplierFactor);
                }
            }
        }
    }
}