package bottomtextdanny.dannys_expansion._mixin;

import bottomtextdanny.dannys_expansion.DEEvaluations;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinAi.class)
public class PiglinAiMixin {
    public PiglinAiMixin() {
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"isWearingGold"},
            remap = true,
            cancellable = true
    )
    private static void wearingGold(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof Player) {
            Player player = (Player)entity;
            if ((Boolean) DEEvaluations.COOL_WITH_PIGLINS.test(player)) {
                cir.setReturnValue(true);
            }
        }

    }

    @Inject(
            at = {@At("HEAD")},
            method = {"angerNearbyPiglins"},
            remap = true,
            cancellable = true
    )
    private static void angerNearbyPiglins(Player player, boolean idk, CallbackInfo ci) {
        if (DEEvaluations.COOL_WITH_PIGLINS.test(player)) {
            ci.cancel();
        }
    }
}
