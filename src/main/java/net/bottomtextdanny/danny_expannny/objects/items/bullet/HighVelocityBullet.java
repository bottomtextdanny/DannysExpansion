package net.bottomtextdanny.danny_expannny.objects.items.bullet;

import net.bottomtextdanny.danny_expannny.object_tables.DEEntities;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.danny_expannny.objects.entities.projectile.bullet.AbstractBulletEntity;
import net.bottomtextdanny.danny_expannny.objects.entities.projectile.bullet.BulletEntity;
import net.bottomtextdanny.danny_expannny.objects.items.gun.GunItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class HighVelocityBullet extends Item implements Bullet {

    public HighVelocityBullet(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractBulletEntity setupBullet(Level world, Player player, GunItem item, int lifetime) {
        BulletEntity bullet = new BulletEntity(DEEntities.BULLET.get(), world);
        bullet.setLifetime(lifetime);
        bullet.addBulletSpeed(8.0F);
        return bullet;
    }

    @Override
    public void onShot(Player player, Level world) {
        player.playSound(DESounds.ES_HIGH_VELOCITY_BULLET.get(), 1.7F, 1.0F);
    }
}
