package bottomtextdanny.dannys_expansion.content.items.gun;

import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerGunModule;
import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerHelper;
import bottomtextdanny.dannys_expansion.tables.DESounds;
import bottomtextdanny.braincell.Braincell;
import bottomtextdanny.braincell.mod.graphics.screen_tonemapping.TransientTonemapAgent;
import bottomtextdanny.braincell.mod.network.Connection;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ScopingGun {
	
	float scopingMovementSpeed(ItemStack stack, Player playerEntity);
	
	default boolean scopingAction(ItemStack stack, Player playerEntity, PlayerGunModule capability) {
		if (capability.getGunScoping() == stack) {
			capability.setGunScoping(ItemStack.EMPTY);
			playerEntity.playSound(DESounds.IS_UNPEEK.get(), 0.6F, 0.5F);
			return true;
		} else if (playerEntity.getUseItem() == ItemStack.EMPTY) {
			capability.setGunScoping(stack);

			Connection.doClientSide(() -> {
					blink();
			});
			playerEntity.playSound(DESounds.IS_PEEK.get(), 0.6F, 1.0F);
			return true;
		}
		
		return false;
	}

	@OnlyIn(Dist.CLIENT)
	static void blink() {
		Braincell.client()
				.getShaderHandler()
				.getTonemapWorkflow()
				.addAgent(TransientTonemapAgent.createBlink(6));
	}
	
	float scopingSensMult();
	
	float scopingFov();
	
	default boolean isScoping(Player player) {
		return PlayerHelper.gunModule(player).getGunScoping().getItem() == this;
	}
}
