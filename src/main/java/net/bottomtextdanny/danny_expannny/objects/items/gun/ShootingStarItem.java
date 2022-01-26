package net.bottomtextdanny.danny_expannny.objects.items.gun;

import net.bottomtextdanny.braincell.mod.base.util.Connection;
import net.bottomtextdanny.danny_expannny.DEOp;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.object_tables.items.DEItems;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.danny_expannny.structure.client_sided.gun_rendering.ScopeRenderingData;
import net.bottomtextdanny.danny_expannny.structure.client_sided.gun_rendering.SimpleGunModelRenderer;
import net.bottomtextdanny.danny_expannny.vertex_models.guns.ShootingStarModel;
import net.bottomtextdanny.danny_expannny.objects.entities.projectile.bullet.AbstractBulletEntity;
import net.bottomtextdanny.danny_expannny.objects.items.bullet.Bullet;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.EntityUtil;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerGunModule;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Optional;

public class ShootingStarItem extends GunItem<Bullet> implements IScopingGun {
	
    public ShootingStarItem(Properties properties) {
        super(Bullet.class, properties);
        this.tooltipDamage = 12.0F;
    }

	@OnlyIn(Dist.CLIENT)
	@Override
	protected void renderRegistry() {
		Connection.doClientSide(() -> {
			DannysExpansion.client().getGunRenderData().putRenderer(this,
					SimpleGunModelRenderer.create(
							new ResourceLocation(DannysExpansion.ID, "textures/models/item/shooting_star.png"),
							new ShootingStarModel()
					));
			DannysExpansion.client().getGunRenderData().putScopeResources(this,
					new ScopeRenderingData(
							new ResourceLocation[] {
									new ResourceLocation(DannysExpansion.ID, "textures/gui/scope/shooting_star_outer.png"),
									new ResourceLocation(DannysExpansion.ID, "textures/gui/scope/shooting_star_inner.png"),
									new ResourceLocation(DannysExpansion.ID, "textures/gui/scope/shooting_star_crosshair.png")
							},
							320
					)
			);
		});
	}

	@Override
	public void shotWithBullet(Player player, ItemStack stack, PlayerGunModule capability, ItemStack bulletStack, Bullet bulletItem) {
		super.shotWithBullet(player, stack, capability, bulletStack, bulletItem);
		float mult = getMotionMultiplier(player);
		float dispersion = baseDispersionFactor(player, capability.getRecoil(), (float) DEOp.LOGIC_DIST3D_UTIL.start(player.xOld, player.yOld, player.zOld).get(player));
		bulletStack.shrink(1);
		bulletItem.onShot(player, player.level);
		applyCooldown(player);
		
		if (!player.level.isClientSide) {
			AbstractBulletEntity bullet = bulletItem.setupBullet(player.level, player, this, 60);
			bullet.addBulletDamage(12.0F);
			bullet.addBulletSpeed(3.0F);
			bullet.setupForGun(player, dispersion);
			EntityUtil.playEyeSound(player, DESounds.IS_SHOOTING_STAR_SHOT.get(), 1.4F, 1.0F + this.random.nextFloat() * 0.1F);
		} else {
			setCameraRecoil(player, 35, 0.7F * mult, 0.89F);
		}
		
		player.setXRot(player.getXRot() - 10.0F);
		player.setYRot((float) (player.getYRot() + this.random.nextGaussian() * 3.0));
		
		setRecoil(player, 88 * mult, 120, 1.0F, 0.87F);
	}

	@Override
	public Optional<TooltipComponent> getTooltipImage(ItemStack p_150902_) {
		return super.getTooltipImage(p_150902_);
	}

	@Override
    public Bullet getDefaultBullet() {
        return DEItems.BULLET.get();
    }

    @Override
    public int cooldown() {
        return 22;
    }
	
	@Override
	public float scopingMovementSpeed(ItemStack stack, Player playerEntity) {
		return 0.2F;
	}

	@Override
	public float scopingSensMult() {
		return 0.2F;
	}
	
	@Override
	public float scopingFov() {
		return 0.3F;
	}
	
	@Override
	public float dispersionFactor(Player player, float recoil, float movement) {
		float mult = getMotionMultiplier(player);
		float addition = 0;
		
		if (!isScoping(player)) {
			addition = 18.0F * mult;
		} else {
			mult = 0.0F;
		}
		
		return (Mth.clamp(recoil * 0.75F, 0.0F, 800F) + Mth.clamp(movement * 16, 0, 60)) * mult * 0.35F + addition;
	}
}
