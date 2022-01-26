package net.bottomtextdanny.danny_expannny.objects.items.gun;

import net.bottomtextdanny.braincell.mod.base.util.Connection;
import net.bottomtextdanny.danny_expannny.DEOp;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.object_tables.items.DEItems;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.danny_expannny.structure.client_sided.gun_rendering.SimpleGunModelRenderer;
import net.bottomtextdanny.danny_expannny.vertex_models.guns.HandgunModel;
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

public class HandgunItem extends GunItem<Bullet> {

    public HandgunItem(Properties properties) {
        super(Bullet.class, properties);
        this.tooltipDamage = 2.0F;
    }

	@OnlyIn(Dist.CLIENT)
	@Override
	protected void renderRegistry() {
		Connection.doClientSide(() -> {
			DannysExpansion.client().getGunRenderData().putRenderer(this,
					SimpleGunModelRenderer.create(
							new ResourceLocation(DannysExpansion.ID, "textures/models/item/handgun.png"),
							HandgunModel.create()
					));
		});
	}

	@Override
	public void shotWithBullet(Player player, ItemStack stack, PlayerGunModule capability, ItemStack bulletStack, Bullet bulletItem) {
		float dispersion = baseDispersionFactor(player, capability.getRecoil(), (float) DEOp.LOGIC_DIST3D_UTIL.start(player.xOld, player.yOld, player.zOld).get(player));
		float mult = getMotionMultiplier(player);
		bulletItem.onShot(player, player.level);
		
		bulletStack.shrink(1);
		applyCooldown(player);
		
		if (!player.level.isClientSide) {
			AbstractBulletEntity bullet = bulletItem.setupBullet(player.level, player, this, 60);
			
			bullet.addBulletDamage(2.0F);
			bullet.setupForGun(player, dispersion);
			EntityUtil.playEyeSound(player, DESounds.IS_PISTOL_SHOT.get(), 1.0F, 1.0F + this.random.nextFloat() * 0.1F);
		} else {
			setCameraRecoil(player, 12.0F * mult, 0.6F * mult, 0.9F);
		}
		
		setRecoil(player, 45.0F * mult, 70.0F, 4.0F, 0.9F);
	}
	
    @Override
    public int cooldown() {
        return 4;
    }

    @Override
    public Bullet getDefaultBullet() {
        return DEItems.BULLET.get();
    }
	
	@Override
	public float dispersionFactor(Player player, float recoil, float movement) {
		float mult = getMotionMultiplier(player);
		
		return (Mth.clamp((recoil - 10.0F) * 0.25F, 0.0F, 25.0F) + Mth.clamp(movement * 7, 0, 60)) * mult;
	}
	
	public double movementFactor(Player player, double movement) {
		float mult = 1;
		if (player.isShiftKeyDown() && player.isOnGround()) mult = 0.1666F;
		if (player.isVisuallySwimming()) mult = 2.666F;
		return Mth.clamp(movement * 7, 0, 60) * mult;
	}
}
