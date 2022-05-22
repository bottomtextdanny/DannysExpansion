package bottomtextdanny.dannys_expansion._base.rendering_hooks;

import bottomtextdanny.dannys_expansion.content.items.bow.DEBowItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderPlayerEvent;

@OnlyIn(Dist.CLIENT)
public final class PlayerPoseTweaking {

    public static void changePoseIfShould(RenderPlayerEvent.Pre event) {
        Player player = event.getPlayer();
        ItemStack useItem = player.getUseItem();

        if (useItem.getItem() instanceof DEBowItem) {
            if (player.getUseItemRemainingTicks() > 0) {
                PlayerModel<?> playerModel = event.getRenderer().getModel();

                if (player.getItemInHand(InteractionHand.MAIN_HAND) == useItem)
                    playerModel.rightArmPose = HumanoidModel.ArmPose.BOW_AND_ARROW;
                else playerModel.leftArmPose = HumanoidModel.ArmPose.BOW_AND_ARROW;
            }
        }
    }

    private PlayerPoseTweaking() {}
}
