package bottomtextdanny.dannys_expansion.content.items.bullet;

import bottomtextdanny.dannys_expansion.tables.DEEntities;
import bottomtextdanny.dannys_expansion.content.entities.projectile.bullet.AbstractBulletEntity;
import bottomtextdanny.dannys_expansion.content.entities.projectile.bullet.AquaticBulletEntity;
import bottomtextdanny.dannys_expansion.content.items.gun.GunItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class AquaticBulletItem extends Item implements Bullet {

    public AquaticBulletItem(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractBulletEntity setupBullet(Level world, Player player, GunItem item, int lifetime) {
        AquaticBulletEntity bullet = new AquaticBulletEntity(DEEntities.AQUATIC_BULLET.get(), world);
        bullet.setLifetime(lifetime);
        bullet.addBulletSpeed(4.95F);
        return bullet;
    }
}
