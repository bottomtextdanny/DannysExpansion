package net.bottomtextdanny.danny_expannny.objects.items.bullet;

import net.bottomtextdanny.danny_expannny.objects.entities.projectile.bullet.AbstractBulletEntity;
import net.bottomtextdanny.danny_expannny.objects.items.gun.GunItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public interface Ammo extends ItemLike {

	AbstractBulletEntity setupBullet(Level world, Player player, GunItem<?> item, int lifetime);
	
	default void onShot(Player player, Level world) {}
}
