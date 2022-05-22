package bottomtextdanny.dannys_expansion.content.items.gun;

import bottomtextdanny.dannys_expansion._util.tooltip.*;
import bottomtextdanny.dannys_expansion.content.items.bullet.Bullet;
import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion._base.gun_rendering.SimpleGunModelRenderer;
import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerGunModule;
import bottomtextdanny.dannys_expansion.content._client.model.guns.HandgunModel;
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
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class HandgunItem extends GunItem<Bullet> {
	public static final float RECOIL = 45.0F;
	public static final float MAX_RECOIL = 70.0F;
	public static final float DAMAGE = 2.0F;
	public static final int FIRE_RATE = 4;

	public HandgunItem(Properties properties) {
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
								.add(TooltipData.BULLET_SPEED_MULTIPLIER.message(StringSuppliers.float_(1.0F))
										.withStyle(ChatFormatting.DARK_GRAY))
								.add(TooltipData.RECOIL.message(StringSuppliers.float_(RECOIL))
										.withStyle(ChatFormatting.DARK_GRAY))
								.add(TooltipData.MAX_RECOIL.message(StringSuppliers.float_(MAX_RECOIL))
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
							new ResourceLocation(DannysExpansion.ID, "textures/models/item/handgun.png"),
							HandgunModel.create()
					));
		});
	}

	@Override
	public void shotWithBullet(Player player, ItemStack stack,
							   PlayerGunModule module, ItemStack bulletStack,
							   Bullet bulletItem) {
		float movement = (float) DistanceCalc3.EUCLIDEAN.distance(
				player.xOld, player.yOld, player.zOld,
				player.getX(), player.getY(), player.getZ());
		float dispersion = baseDispersionFactor(player, module.getRecoil(), movement);
		float mult = getMotionMultiplier(player);

		bulletItem.onShot(player, player.level);

		if (!player.isCreative()) bulletStack.shrink(1);

		applyCooldown(player);
		
		if (!player.level.isClientSide) {
			AbstractBulletEntity bullet = bulletItem.setupBullet(player.level, player, this, 60);
			
			bullet.addBulletDamage(DAMAGE);
			bullet.setupForGun(player, dispersion);
			EntityUtil.playEyeSound(player, DESounds.IS_PISTOL_SHOT.get(), 1.0F, 1.0F + this.random.nextFloat() * 0.1F);
		} else {
			setVisualRecoil(player, 12.0F * mult, 0.6F * mult, 0.9F);
		}
		
		setRecoil(player, RECOIL * mult, MAX_RECOIL, 4.0F, 0.9F);
	}
	
    @Override
    public int cooldown() {
        return FIRE_RATE;
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
