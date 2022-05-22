package bottomtextdanny.dannys_expansion._mixin;

import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerHelper;
import bottomtextdanny.braincell.mod.capability.player.BCAccessoryModule;
import bottomtextdanny.braincell.mod.capability.player.accessory.MiniAttribute;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(value = Projectile.class, priority = 2000)
public abstract class ProjectileMixin {
    @Shadow @Nullable public abstract Entity getOwner();
    private boolean de_appliedModifiers;

    public ProjectileMixin() {
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"tick"},
            remap = true
    )
    public void tryPutModifiers(CallbackInfo ci) {
        if (!this.de_appliedModifiers && (Object)this instanceof AbstractArrow arrow) {
            Entity entity = this.getOwner();
            if (entity instanceof Player player) {
                BCAccessoryModule cap = PlayerHelper.braincellAccessoryModule(player);
                arrow.setDeltaMovement(arrow.getDeltaMovement().scale((double)(cap.getLesserModifier(MiniAttribute.ARROW_SPEED_MLT) / 100.0F)));
                arrow.setBaseDamage((arrow.getBaseDamage() + (double)cap.getLesserModifier(MiniAttribute.ARCHERY_DAMAGE_ADD)) * (double)(cap.getLesserModifier(MiniAttribute.ARCHERY_DAMAGE_MLT) / 100.0F));
                this.de_appliedModifiers = true;
            }
        }

    }
}
