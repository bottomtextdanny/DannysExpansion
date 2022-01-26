package net.bottomtextdanny.danny_expannny.objects.items.bullet;

import net.bottomtextdanny.danny_expannny.object_tables.DEEntities;
import net.bottomtextdanny.danny_expannny.objects.entities.projectile.bullet.AbstractBulletEntity;
import net.bottomtextdanny.danny_expannny.objects.entities.projectile.bullet.AquaticBulletEntity;
import net.bottomtextdanny.danny_expannny.objects.items.gun.GunItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class AquaticBullet extends Item implements Bullet {

    public AquaticBullet(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractBulletEntity setupBullet(Level world, Player player, GunItem item, int lifetime) {
        AquaticBulletEntity bullet = new AquaticBulletEntity(DEEntities.AQUATIC_BULLET.get(), world);
        bullet.setLifetime(lifetime);
        bullet.addBulletSpeed(4.25F);
        return bullet;
    }
}
