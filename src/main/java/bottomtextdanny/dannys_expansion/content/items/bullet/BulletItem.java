package bottomtextdanny.dannys_expansion.content.items.bullet;

import bottomtextdanny.dannys_expansion.content.items.gun.GunItem;
import bottomtextdanny.dannys_expansion.tables.DEEntities;
import bottomtextdanny.dannys_expansion.content.entities.projectile.bullet.AbstractBulletEntity;
import bottomtextdanny.dannys_expansion.content.entities.projectile.bullet.BulletEntity;
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
