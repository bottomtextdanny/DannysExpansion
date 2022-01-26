package net.bottomtextdanny.danny_expannny.objects.items.gun;

import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.opengl_front.screen_tonemapping.TonemapAgent;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerGunModule;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IScopingGun {
	
	float scopingMovementSpeed(ItemStack stack, Player playerEntity);
	
	default boolean scopingAction(ItemStack stack, Player playerEntity, PlayerGunModule capability) {
		
		if (capability.getGunScoping() == stack) {
			capability.setGunScoping(ItemStack.EMPTY);
			playerEntity.playSound(DESounds.IS_UNPEEK.get(), 0.6F, 0.5F);
			return true;
		} else if (playerEntity.getUseItem() == ItemStack.EMPTY) {
			capability.setGunScoping(stack);
			DEUtil.executeClientTask(playerEntity.level, () -> {
				Braincell.client().getPostprocessingHandler().getTonemapWorkflow().addAgent(TonemapAgent.createBlink(10));
			});
			playerEntity.playSound(DESounds.IS_PEEK.get(), 0.6F, 1.0F);
			return true;
		}
		
		return false;
	}
	
	float scopingSensMult();
	
	float scopingFov();
	
	default boolean isScoping(Player player) {
		return PlayerHelper.gunModule(player).getGunScoping().getItem() == this;
	}
}
