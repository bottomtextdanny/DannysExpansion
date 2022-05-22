package bottomtextdanny.dannys_expansion.content.items.gun;

import bottomtextdanny.dannys_expansion._util.tooltip.*;
import bottomtextdanny.dannys_expansion.content.items.bullet.Bullet;
import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion._base.gun_rendering.SimpleGunModelRenderer;
import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerGunModule;
import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerHelper;
import bottomtextdanny.dannys_expansion.content._client.model.guns.GolemHandgunModel;
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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GolemHandgunItem extends GunItem<Bullet> {
	public static final float RECOIL = 88.0F;
	public static final float MAX_RECOIL = 120.0F;
	public static final float DAMAGE = 8.0F;
	public static final float ADDITIONAL_BULLET_SPEED = 1.2F;
	public static final int FIRE_RATE = 10;
	public static final int DELAY = 6;

	public GolemHandgunItem(Properties properties) {
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
						.add(TooltipData.DELAY.message(StringSuppliers.ticksToSeconds(DELAY))
								.withStyle(ChatFormatting.DARK_GREEN))
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
	public boolean usageParameter(Player player, ItemStack stack,
								  InteractionHand hand, PlayerGunModule capability) {
		return tryToGetBullet(Bullet.class, player) != null;
	}
	
	@Override
	public void usageTick(Player player, PlayerGunModule module, ItemStack stack, int progress) {
		super.usageTick(player, module, stack, progress);

		if (progress != DELAY) return;

		ItemStack bulletStack = tryToGetBullet(Bullet.class, player);

		if (bulletStack == null) return;

		Bullet bulletItem = (Bullet) bulletStack.getItem();

		PlayerGunModule gunModule = PlayerHelper.gunModule(player);

		if (!player.isCreative()) bulletStack.shrink(1);

		bulletItem.onShot(player, player.level);
		applyCooldown(player);

		if (!player.level.isClientSide) {
			float movement = (float) DistanceCalc3.EUCLIDEAN.distance(
					player.xOld, player.yOld, player.zOld,
					player.getX(), player.getY(), player.getZ());
			float dispersion = baseDispersionFactor(player, gunModule.getRecoil(), movement);

			AbstractBulletEntity bullet = bulletItem.setupBullet(player.level, player, this, 60);

			bullet.addBulletDamage(DAMAGE);
			bullet.addBulletSpeed(ADDITIONAL_BULLET_SPEED);
			bullet.setupForGun(player, dispersion);

			EntityUtil.playEyeSound(player, DESounds.IS_HEAVY_PISTOL_SHOT.get(), 1.0F, 1.0F + this.random.nextFloat() * 0.1F);
		} else {
			setVisualRecoil(player, 35, 0.7F, 0.89F);
		}

		setRecoil(player, RECOIL, MAX_RECOIL, 1.0F, 0.87F);
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
        return FIRE_RATE;
    }
	
	@Override
	public int usageTime() {
		return DELAY;
	}
}
