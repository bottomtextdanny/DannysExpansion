package net.bottomtextdanny.dannys_expansion.mixin.client;

import net.bottomtextdanny.braincell.mod.structure.BraincellModules;
import net.bottomtextdanny.braincell.mod.structure.client_sided.events.PlayerPoseEvent;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerModel.class)
public class PlayerModelMixin<T extends LivingEntity> extends HumanoidModel<T> {


    public PlayerModelMixin(ModelPart p_170677_) {
        super(p_170677_);
    }

    @Inject(at = @At(value = "TAIL"), method = "setupAnim(Lnet/minecraft/world/entity/Entity;FFFFF)V", remap = false)
    private void setRotationAngles(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if(BraincellModules.PLAYER_MODEL_HOOKS.isActive() && entityIn instanceof AbstractClientPlayer player) {
            PlayerModel<?> model = (PlayerModel<?>)(Object)this;
            MinecraftForge.EVENT_BUS.post(
                    new PlayerPoseEvent(
                            model,
                            player,
                            limbSwing,
                            limbSwingAmount,
                            ageInTicks,
                            netHeadYaw,
                            headPitch));
            model.rightSleeve.copyFrom(model.rightArm);
            model.leftSleeve.copyFrom(model.leftArm);
            model.rightPants.copyFrom(model.rightLeg);
            model.leftPants.copyFrom(model.leftLeg);
            model.jacket.copyFrom(model.body);
            model.hat.copyFrom(model.head);
        }
    }
}
