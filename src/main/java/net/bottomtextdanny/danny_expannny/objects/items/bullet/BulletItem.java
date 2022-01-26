package net.bottomtextdanny.danny_expannny.objects.items.bullet;

import net.bottomtextdanny.danny_expannny.object_tables.DEEntities;
import net.bottomtextdanny.danny_expannny.objects.entities.projectile.bullet.AbstractBulletEntity;
import net.bottomtextdanny.danny_expannny.objects.entities.projectile.bullet.BulletEntity;
import net.bottomtextdanny.danny_expannny.objects.items.gun.GunItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class BulletItem extends Item implements Bullet {

    public BulletItem(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractBulletEntity setupBullet(Level world, Player player, GunItem item, int lifetime) {
        BulletEntity bullet = new BulletEntity(DEEntities.BULLET.get(), world);
        bullet.setLifetime(lifetime);
        bullet.addBulletSpeed(5.0F);
        return bullet;
    }
}
