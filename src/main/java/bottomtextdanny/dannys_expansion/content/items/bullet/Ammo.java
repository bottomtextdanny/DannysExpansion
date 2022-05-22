package bottomtextdanny.dannys_expansion.content.items.bullet;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerCapability;
import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerGunModule;
import bottomtextdanny.dannys_expansion.content.entities.projectile.bullet.AbstractBulletEntity;
import bottomtextdanny.dannys_expansion.content.items.gun.GunItem;
import bottomtextdanny.braincell.mod.capability.CapabilityHelper;
import bottomtextdanny.braincell.mod.network.Connection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public interface Ammo extends ItemLike {
	ResourceLocation DEFAULT_FIRE =
			new ResourceLocation(DannysExpansion.ID, "textures/entity/bullet/bullet_fire.png");

	AbstractBulletEntity setupBullet(Level world, Player player, GunItem<?> item, int lifetime);
	
	default void onShot(Player player, Level world) {
		if (world.isClientSide) {
			ResourceLocation fire = fireTexture();

			if (fire != null) {
				PlayerGunModule module = CapabilityHelper.get(player, PlayerCapability.TOKEN).gunModule();

				Connection.doClientSide(() -> {
					module.fireAnimation(fire);
				});
			}
		}
	}

	@Nullable
	default ResourceLocation fireTexture() {
		return DEFAULT_FIRE;
	}
}
