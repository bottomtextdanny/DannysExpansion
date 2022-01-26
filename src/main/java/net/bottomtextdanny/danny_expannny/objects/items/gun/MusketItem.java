package net.bottomtextdanny.danny_expannny.objects.items.gun;

import net.bottomtextdanny.braincell.mod.base.util.Connection;
import net.bottomtextdanny.danny_expannny.DEOp;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.object_tables.items.DEItems;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.danny_expannny.structure.client_sided.gun_rendering.SimpleGunModelRenderer;
import net.bottomtextdanny.danny_expannny.vertex_models.guns.MusketModel;
import net.bottomtextdanny.danny_expannny.objects.entities.projectile.bullet.AbstractBulletEntity;
import net.bottomtextdanny.danny_expannny.objects.items.bullet.Bullet;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.EntityUtil;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerGunModule;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MusketItem extends GunItem<Bullet> {

    public MusketItem(Properties properties) {
        super(Bullet.class, properties);
        this.tooltipDamage = 5.0F;
    }

	@OnlyIn(Dist.CLIENT)
	@Override
	protected void renderRegistry() {
		Connection.doClientSide(() -> {
			DannysExpansion.client().getGunRenderData().putRenderer(this,
					SimpleGunModelRenderer.create(
							new ResourceLocation(DannysExpansion.ID, "textures/models/item/musket.png"),
							new MusketModel()
					));
		});
	}

	@Override
	public void shotWithBullet(Player player, ItemStack stack, PlayerGunModule capability, ItemStack bulletStack, Bullet bulletItem) {
		float dispersion = baseDispersionFactor(player, capability.getRecoil(), (float) DEOp.LOGIC_DIST3D_UTIL.start(player.xOld, player.yOld, player.zOld).get(player));
		float mult = getMotionMultiplier(player);
		bulletStack.shrink(1);
		bulletItem.onShot(player, player.level);
		applyCooldown(player);
		
		if (!player.level.isClientSide) {
			AbstractBulletEntity bullet = bulletItem.setupBullet(player.level, player, this, 60);
			bullet.addBulletDamage(6.0F);
			bullet.addBulletSpeed(3.0F);
			bullet.setupForGun(player, dispersion);
			EntityUtil.playEyeSound(player, DESounds.IS_MUSKET_SHOT.get(), 1.0F, 1.0F + this.random.nextFloat() * 0.1F);
		} else {
			setCameraRecoil(player, 25.0F, 0.3F * mult, 0.89F);
		}
		
		player.setXRot(player.getXRot() - 3.0F);
		player.setYRot((float) (player.getYRot() + this.random.nextGaussian() * 3.0));
		
		setRecoil(player, 48 * mult, 120, 0.5F, 0.92F);
	}

    @Override
    public int cooldown() {
        return 28;
    }

    @Override
    public Bullet getDefaultBullet() {
        return DEItems.BULLET.get();
    }
	
	@Override
	public float dispersionFactor(Player player, float recoil, float movement) {
		float mult = getMotionMultiplier(player);
		return (Mth.clamp((recoil - 10.0F) * 0.25F, 0.0F, 25.0F) + Mth.clamp(movement * 7.0F, 0.0F, 60.0F)) * mult;
	}
}
