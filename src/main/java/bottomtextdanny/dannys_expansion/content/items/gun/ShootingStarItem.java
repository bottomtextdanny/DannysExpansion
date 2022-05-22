package bottomtextdanny.dannys_expansion.content.items.gun;

import bottomtextdanny.dannys_expansion._base.gun_rendering.GunRenderingManager;
import bottomtextdanny.dannys_expansion._util.tooltip.*;
import bottomtextdanny.dannys_expansion.content.items.bullet.Bullet;
import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion._base.gun_rendering.ScopeRenderingData;
import bottomtextdanny.dannys_expansion._base.gun_rendering.SimpleGunModelRenderer;
import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerGunModule;
import bottomtextdanny.dannys_expansion.content._client.model.guns.ShootingStarModel;
import bottomtextdanny.dannys_expansion.tables.DESounds;
import bottomtextdanny.dannys_expansion.tables.items.DEItems;
import bottomtextdanny.dannys_expansion.content.entities.projectile.bullet.AbstractBulletEntity;
import bottomtextdanny.dannys_expansion._util.EntityUtil;
import bottomtextdanny.braincell.base.vector.DistanceCalc3;
import bottomtextdanny.braincell.mod.network.Connection;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Optional;

public class ShootingStarItem extends GunItem<Bullet> implements ScopingGun {
	public static final float RECOIL = 22.0F;
	public static final float MAX_RECOIL = 30.0F;
	public static final float DAMAGE = 12.0F;
	public static final float ADDITIONAL_BULLET_SPEED = 3.0F;
	public static final int FIRE_RATE = 22;
	public static final float SCOPING_SENS = 0.2F;
	public static final float SCOPING_ZOOM = 0.3F;
	public static final float SCOPING_MOVEMENT = 0.2F;

	public ShootingStarItem(Properties properties) {
        super(Bullet.class, properties);
    }

	@Override
	public TooltipWriter createTooltipInfo() {
		return TooltipTable.builder()
				.block(TooltipWriter.component(new TextComponent("")))
				.block(TooltipBlock.builder()
						.header(TooltipWriter.trans("description.dannys_expansion.statistics",
								StringSuppliers.translatable("description.dannys_expansion.gun"),
								Style.EMPTY.applyFormats(ChatFormatting.GRAY),
								Style.EMPTY.applyFormats(ChatFormatting.GRAY)))
						.add(TooltipData.DAMAGE.message(StringSuppliers.float_(DAMAGE))
								.withStyle(ChatFormatting.DARK_GREEN))
						.add(TooltipData.RATE_OF_FIRE.message(StringSuppliers.ticksToSeconds(FIRE_RATE))
								.withStyle(ChatFormatting.DARK_GREEN))
						.add(TooltipBlock.builder()
								.condition(TooltipCondition.HOLD_SHIFT)
								.add(TooltipData.ADDITIONAL_BULLET_SPEED.message(StringSuppliers.float_(ADDITIONAL_BULLET_SPEED))
										.withStyle(ChatFormatting.DARK_GRAY))
								.add(TooltipData.BULLET_SPEED_MULTIPLIER.message(StringSuppliers.float_(1.0F))
										.withStyle(ChatFormatting.DARK_GRAY))
								.add(TooltipData.RECOIL.message(StringSuppliers.float_(RECOIL))
										.withStyle(ChatFormatting.DARK_GRAY))
								.add(TooltipData.MAX_RECOIL.message(StringSuppliers.float_(MAX_RECOIL))
										.withStyle(ChatFormatting.DARK_GRAY))
								.add(TooltipData.SCOPING_ZOOM.message(StringSuppliers.float_(SCOPING_ZOOM))
										.withStyle(ChatFormatting.DARK_GRAY))
								.add(TooltipData.SCOPING_SENSIBILITY_MULTIPLIER.message(StringSuppliers.float_(SCOPING_SENS))
										.withStyle(ChatFormatting.DARK_GRAY))
								.add(TooltipData.SCOPING_MOVEMENT.message(StringSuppliers.float_(SCOPING_MOVEMENT))
										.withStyle(ChatFormatting.DARK_GRAY))
								.build())
						.build())
				.build();
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	protected void renderRegistry() {
		Connection.doClientSide(() -> {
			GunRenderingManager gunRenderData = DannysExpansion.client().getGunRenderData();
			gunRenderData.putRenderer(this,
					SimpleGunModelRenderer.create(
							new ResourceLocation(DannysExpansion.ID, "textures/models/item/shooting_star.png"),
							new ShootingStarModel()
					));
			gunRenderData.putScopeResources(this,
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
	public void shotWithBullet(Player player, ItemStack stack, PlayerGunModule module, ItemStack bulletStack, Bullet bulletItem) {
		float movement = (float) DistanceCalc3.EUCLIDEAN.distance(
				player.xOld, player.yOld, player.zOld,
				player.getX(), player.getY(), player.getZ());
		float dispersion = baseDispersionFactor(player, module.getRecoil(), movement);
		float mult = getMotionMultiplier(player);

		if (!player.isCreative()) bulletStack.shrink(1);

		bulletItem.onShot(player, player.level);
		applyCooldown(player);
		
		if (module.getGunScoping() == ItemStack.EMPTY) mult *= 4.0F;

		if (!player.level.isClientSide) {
			AbstractBulletEntity bullet = bulletItem.setupBullet(player.level, player, this, 60);
			bullet.addBulletDamage(DAMAGE);
			bullet.addBulletSpeed(ADDITIONAL_BULLET_SPEED);
			bullet.setupForGun(player, dispersion);
			EntityUtil.playEyeSound(player, DESounds.IS_SHOOTING_STAR_SHOT.get(), 1.4F, 1.0F + this.random.nextFloat() * 0.1F);
		} else {
			setCameraRecoil(player, 8 * mult, 0.2F * mult, 0.89F);
		}
		
		player.setXRot(player.getXRot() - 1.5F * mult);
		player.setYRot((float) (player.getYRot() + this.random.nextGaussian() * 1.5 * mult));
		
		setRecoil(player, RECOIL * mult, MAX_RECOIL * mult, 0.25F * mult, 0.87F);
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
        return FIRE_RATE;
    }
	
	@Override
	public float scopingMovementSpeed(ItemStack stack, Player playerEntity) {
		return SCOPING_MOVEMENT;
	}

	@Override
	public float scopingSensMult() {
		return SCOPING_SENS;
	}
	
	@Override
	public float scopingFov() {
		return SCOPING_ZOOM;
	}
	
	@Override
	public float dispersionFactor(Player player, float recoil, float movement) {
		float mult = getMotionMultiplier(player);
		float addition = 0;
		
		if (!isScoping(player))
			addition = 18.0F * mult;
		else mult = 0.0F;
		
		return (Mth.clamp(recoil * 0.75F, 0.0F, 800F) + Mth.clamp(movement * 16, 0, 60)) * mult * 0.35F + addition;
	}
}
