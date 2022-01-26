package net.bottomtextdanny.danny_expannny.objects.items.gun;

import net.bottomtextdanny.braincell.mod.base.util.Connection;
import net.bottomtextdanny.danny_expannny.DEOp;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.object_tables.items.DEItems;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.danny_expannny.structure.client_sided.gun_rendering.SimpleGunModelRenderer;
import net.bottomtextdanny.danny_expannny.vertex_models.guns.ShotgunModel;
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

public class ShotgunItem extends GunItem<Bullet> {

    public ShotgunItem(Properties properties) {
        super(Bullet.class, properties);
        this.tooltipDamage = 18.0F;
    }

	@OnlyIn(Dist.CLIENT)
	@Override
	protected void renderRegistry() {
		Connection.doClientSide(() -> {
			DannysExpansion.client().getGunRenderData().putRenderer(this,
					SimpleGunModelRenderer.create(
							new ResourceLocation(DannysExpansion.ID, "textures/models/item/shotgun.png"),
							new ShotgunModel()
					));
		});
	}

	@Override
	public void shotWithBullet(Player player, ItemStack stack, PlayerGunModule capability, ItemStack bulletStack, Bullet bulletItem) {
		bulletItem.onShot(player, player.level);
		applyCooldown(player);
		bulletStack.shrink(1);
		
		if (!player.level.isClientSide) {
			EntityUtil.playEyeSound(player, DESounds.IS_SHOTGUN_SHOT.get(), 2.0F, 1.0F + this.random.nextFloat() * 0.1F);
			
			float dispersion = baseDispersionFactor(player, capability.getRecoil(), (float) DEOp.LOGIC_DIST3D_UTIL.start(player.xOld, player.yOld, player.zOld).get(player));
			float bias = 8.0F;
			float yDispersion = dispersion * (float) this.random.nextGaussian();
			float xDispersion = dispersion * (float) this.random.nextGaussian();
			
			for (int i = 0; i < 6; i++) {
				AbstractBulletEntity bullet = bulletItem.setupBullet(player.level, player, this, 40);
				bullet.addBulletDamage(3.0F);
				bullet.setupForGunPellet(player, bias, xDispersion, yDispersion);
			}
		} else {
			setCameraRecoil(player, 24.0F, 1.8F, 0.91F);
		}
		setRecoil(player, 50.0F, 70.0F, 4.0F, 1.0F);
	}

    @Override
    public Bullet getDefaultBullet() {
        return DEItems.BULLET.get();
    }
	
	@Override
	public float dispersionFactor(Player player, float recoil, float movement) {
		float mult = getMotionMultiplier(player);
		return (Mth.clamp(recoil * 0.55F, 0, 10) + Mth.clamp(movement * 20, 0, 40)) * mult;
	}
	
	@Override
    public int getExtraCrosshairDegrees(Player player) {
        return 8;
    }

    @Override
    public int cooldown() {
        return 20;
    }
}
