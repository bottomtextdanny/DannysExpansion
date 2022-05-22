package bottomtextdanny.dannys_expansion._base.rendering_hooks;

import bottomtextdanny.dannys_expansion._util._client.CMC;
import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerGunModule;
import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerHelper;
import bottomtextdanny.dannys_expansion.content.items.bow.DEBowItem;
import bottomtextdanny.dannys_expansion.content.items.gun.ScopingGun;
import bottomtextdanny.braincell.base.Easing;
import bottomtextdanny.braincell.mod._base.BCStaticData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;

@OnlyIn(Dist.CLIENT)
public final class FovTweaking {
    private static float fovMultiplier;

    public static void changeFovIfShould(EntityViewRenderEvent.FieldOfView event) {
        LocalPlayer player = CMC.player();

        if (player != null && player.isAlive()) {

            float newFov = getScopingFov(player);

            if (newFov != 0.0F) {
                event.setFOV(newFov);
                return;
            }

            setManualBowFov(event, player);
        }
    }

    private static float getScopingFov(LocalPlayer player) {
        PlayerGunModule gunModule = PlayerHelper.gunModule(player);

        if (gunModule.getGunScoping().getItem() instanceof ScopingGun scopingGun) {
            return (float) (scopingGun.scopingFov() * Minecraft.getInstance().options.fov);
        }

        return 0.0F;
    }

    private static void setManualBowFov(EntityViewRenderEvent.FieldOfView event, LocalPlayer player) {
        ItemStack useItem = player.getUseItem();
        float tickOffset = BCStaticData.partialTick();
        int usageTicks = player.getTicksUsingItem();

        if (useItem.getItem() instanceof DEBowItem bow && usageTicks > 0) {
            float bowModifier = Easing.EASE_OUT_CUBIC.progression(Math.min((float) Mth.lerp(tickOffset, usageTicks - 1, usageTicks), bow.getTrueMaxCount(useItem)) / bow.getTrueMaxCount(useItem)) * bow.getTrueMaxCount(useItem);
            fovMultiplier = bow.getNockZoom(player.level, player, useItem) * bowModifier / bow.getTrueMaxCount(useItem);
        } else {
            fovMultiplier *= Mth.clamp(fovMultiplier, 0, 0.9);
        }

        event.setFOV(event.getFOV() - fovMultiplier);
    }

    private FovTweaking() {}
}
