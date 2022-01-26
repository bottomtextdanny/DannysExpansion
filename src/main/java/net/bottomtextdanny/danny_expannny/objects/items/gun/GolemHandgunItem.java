package net.bottomtextdanny.danny_expannny.objects.items.gun;

import net.bottomtextdanny.braincell.mod.base.util.Connection;
import net.bottomtextdanny.braincell.mod.opengl_front.point_lighting.SimplePointLight;
import net.bottomtextdanny.danny_expannny.DEOp;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.object_tables.items.DEItems;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.danny_expannny.structure.client_sided.gun_rendering.SimpleGunModelRenderer;
import net.bottomtextdanny.danny_expannny.vertex_models.guns.GolemHandgunModel;
import net.bottomtextdanny.danny_expannny.objects.entities.projectile.bullet.AbstractBulletEntity;
import net.bottomtextdanny.danny_expannny.objects.items.bullet.Bullet;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.EntityUtil;
import net.bottomtextdanny.dannys_expansion.core.base.pl.ShootLight;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerGunModule;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GolemHandgunItem extends GunItem<Bullet> {

    public GolemHandgunItem(Properties properties) {
        super(Bullet.class, properties);
        this.tooltipDamage = 6.0F;
    }

	@OnlyIn(Dist.CLIENT)
	@Override
	protected void renderRegistry() {
		Connection.doClientSide(() -> {
			DannysExpansion.client().getGunRenderData().putRenderer(this,
					SimpleGunModelRenderer.create(
							new ResourceLocation(DannysExpansion.ID, "textures/models/item/golem_handgun.png"),
							new GolemHandgunModel()
					));
		});
	}

	@Override
	public void shotWithBullet(Player player, ItemStack stack, PlayerGunModule capability, ItemStack bulletStack, Bullet bulletItem) {
		super.shotWithBullet(player, stack, capability, bulletStack, bulletItem);
		EntityUtil.playEyeSound(player, DESounds.IS_GOLEM_HANDGUN_CHARGE.get(), 1.0F, 1.0F + this.random.nextFloat() * 0.1F);
	}
	
	@Override
	public boolean usageParameter(Player player, ItemStack stack, InteractionHand hand, PlayerGunModule capability) {
		return tryToGetBullet(Bullet.class, player) != null;
	}
	
	@Override
	public void usageTick(Player player, ItemStack stack, int progress) {
		super.usageTick(player, stack, progress);
		if (progress == 5) {
			float mult = 1;
			ItemStack bulletStack = tryToGetBullet(Bullet.class, player);
			
			if (bulletStack != null) {
				PlayerGunModule gunModule = PlayerHelper.gunModule(player);
				Bullet bulletItem = (Bullet) bulletStack.getItem();
				bulletStack.shrink(1);
				
				bulletItem.onShot(player, player.level);
				applyCooldown(player);
				
				if (!player.level.isClientSide) {
					float dispersion = baseDispersionFactor(player, gunModule.getRecoil(), (float) DEOp.LOGIC_DIST3D_UTIL.start(player.xOld, player.yOld, player.zOld).get(player));
					AbstractBulletEntity bullet = bulletItem.setupBullet(player.level, player, this, 60);
					
					bullet.addBulletDamage(6.0F);
					bullet.setupForGun(player, dispersion);
					EntityUtil.playEyeSound(player, DESounds.IS_HEAVY_PISTOL_SHOT.get(), 1.0F, 1.0F + this.random.nextFloat() * 0.1F);
				} else {
					setCameraRecoil(player, 35, 0.7F * mult, 0.89F);
				}
				
				setRecoil(player, 88.0F * mult, 120.0F, 1.0F, 0.87F);
			}
		}
	}
	
	
	@OnlyIn(Dist.CLIENT)
	public void runShootLight(Player player) {
		ShootLight light = new ShootLight(player.level, 0, 0, 10);
		light.setPosition(new Vec3(player.getX(), player.getEyeY() - 0.1, player.getZ()));
		light.setLight(new SimplePointLight(new Vec3(1.0, 0.5, 0.0), 2.5F, 0.6F, 0.5F));
		light.addToWorld();
	}
	
	
	@Override
    public Bullet getDefaultBullet() {
        return DEItems.BULLET.get();
    }
	
	@Override
	public float dispersionFactor(Player player, float recoil, float movement) {
		float mult = getMotionMultiplier(player);
		return (Mth.clamp(recoil * 0.75F, 0, 80) + Mth.clamp(movement * 100, 0, 60)) * mult * 0.35F;
	}
	
	@Override
    public int cooldown() {
        return 10;
    }
	
	@Override
	public int usageTime() {
		return 6;
	}
}
