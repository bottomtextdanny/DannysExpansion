package bottomtextdanny.dannys_expansion.content.items.gun;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion._base.gun_rendering.SimpleGunModelRenderer;
import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerGunModule;
import bottomtextdanny.dannys_expansion._util.tooltip.*;
import bottomtextdanny.dannys_expansion.content._client.model.guns.ShotgunModel;
import bottomtextdanny.dannys_expansion.tables.DESounds;
import bottomtextdanny.dannys_expansion.tables.items.DEItems;
import bottomtextdanny.dannys_expansion.content.entities.projectile.bullet.AbstractBulletEntity;
import bottomtextdanny.dannys_expansion.content.items.bullet.Bullet;
import bottomtextdanny.dannys_expansion._util.EntityUtil;
import bottomtextdanny.braincell.base.vector.DistanceCalc3;
import bottomtextdanny.braincell.mod.network.Connection;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ShotgunItem extends GunItem<Bullet> {
	public static final float RECOIL = 50.0F;
	public static final float MAX_RECOIL = 70.0F;
	public static final float DAMAGE = 18.0F;
	public static final float BULLET_SPEED_MULTIPLIER = 0.8F;
	public static final int FIRE_RATE = 20;
	public static final int PELLET_COUNT = 6;
	public static final float PELLET_DISPERSION = 8.0F;

	public ShotgunItem(Properties properties) {
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
								.add(TooltipData.ADDITIONAL_BULLET_SPEED.message(StringSuppliers.float_(0.0F))
										.withStyle(ChatFormatting.DARK_GRAY))
								.add(TooltipData.BULLET_SPEED_MULTIPLIER.message(StringSuppliers.float_(BULLET_SPEED_MULTIPLIER))
										.withStyle(ChatFormatting.DARK_GRAY))
								.add(TooltipData.RECOIL.message(StringSuppliers.float_(RECOIL))
										.withStyle(ChatFormatting.DARK_GRAY))
								.add(TooltipData.MAX_RECOIL.message(StringSuppliers.float_(MAX_RECOIL))
										.withStyle(ChatFormatting.DARK_GRAY))
								.add(TooltipData.PELLET_COUNT.message(StringSuppliers.int_(PELLET_COUNT))
										.withStyle(ChatFormatting.DARK_GRAY))
								.add(TooltipData.PELLET_DISPERSION.message(StringSuppliers.float_(PELLET_DISPERSION))
										.withStyle(ChatFormatting.DARK_GRAY))
								.build())
						.build())
				.build();
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

		if (!player.isCreative()) bulletStack.shrink(1);
		
		if (!player.level.isClientSide) {
			EntityUtil.playEyeSound(player, DESounds.IS_SHOTGUN_SHOT.get(), 2.0F, 1.0F + this.random.nextFloat() * 0.1F);

			float movement = (float) DistanceCalc3.EUCLIDEAN.distance(
					player.xOld, player.yOld, player.zOld,
					player.getX(), player.getY(), player.getZ());
			float dispersion = baseDispersionFactor(player, capability.getRecoil(), movement);
			float yDispersion = dispersion * (float) this.random.nextGaussian();
			float xDispersion = dispersion * (float) this.random.nextGaussian();

			for (int i = 0; i < PELLET_COUNT; i++) {
				AbstractBulletEntity bullet = bulletItem.setupBullet(player.level, player, this, 40);
				bullet.setBulletSpeed(bullet.getBulletSpeed() * BULLET_SPEED_MULTIPLIER);
				bullet.addBulletDamage(DAMAGE / PELLET_COUNT);
				bullet.setupForGunPellet(player, PELLET_DISPERSION, xDispersion, yDispersion);
			}
		} else {
			setCameraRecoil(player, 24.0F, 1.8F, 0.91F);
		}
		setRecoil(player, RECOIL, MAX_RECOIL, 4.0F, 1.0F);
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
    public double getExtraCrosshairDegrees(Player player) {
        return PELLET_DISPERSION;
    }

    @Override
    public int cooldown() {
        return FIRE_RATE;
    }
}
