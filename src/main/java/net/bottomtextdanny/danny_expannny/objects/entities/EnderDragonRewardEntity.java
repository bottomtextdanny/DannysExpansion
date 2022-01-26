package net.bottomtextdanny.danny_expannny.objects.entities;

import net.bottomtextdanny.braincell.mod.base.util.Connection;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationHandler;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.Animation;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.LoopAnimation;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.danny_expannny.objects.sound_instances.EnderDragonRewardLoopSound;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class EnderDragonRewardEntity extends DEBaseEntity {
	public static final ResourceLocation LOOT_TABLE = new ResourceLocation(DannysExpansion.ID, "gameplay/ender_dragon_reward");
	public final AnimationHandler<EnderDragonRewardEntity> rotationLoopHandler;
	public final Animation spawn;
	public final Animation rotate;
	public final Animation giveItems;
	@OnlyIn(Dist.CLIENT)
	private EnderDragonRewardLoopSound loopSound;
	private float fxIntensity;
	private float prevFxIntensity;
	
	public EnderDragonRewardEntity(EntityType<? extends Entity> entityTypeIn, Level worldIn) {
		super(entityTypeIn, worldIn);
        this.rotationLoopHandler = addAnimationHandler(new AnimationHandler<>(this));
		this.spawn = addAnimation(new Animation(15));
		this.rotate = addAnimation(new LoopAnimation(200));
		this.giveItems = addAnimation(new Animation(60));
	}

	@Override
	protected void commonInit() {
	}

	@Override
	public void onAddedToWorld() {
		super.onAddedToWorld();
		if (!this.level.isClientSide()) {
			this.rotationLoopHandler.play(this.rotate);
			this.mainAnimationHandler.play(this.spawn);
		}
	}

	@Override
	public void tick() {
		super.tick();
		
		if (this.mainAnimationHandler.isPlaying(this.giveItems)) {
			int animTick = this.mainAnimationHandler.getTick();
			
			if (!this.level.isClientSide()) {
				if (animTick == 50) {
					ejectGifts();
				} else if (animTick == 59) {
					remove(RemovalReason.DISCARDED);
		    	}
			} else {
				Connection.doClientSide(() -> handleGiftAnimationClient(animTick));
			}
		}

		if (this.level.isClientSide) {
			handleLightEffect();
			Connection.doClientSide(() -> handleLoopSoundClient());
		}
	}

	public void ejectGifts() {
		List<ItemStack> loot = lootListUnsafe();
		float heightByHalf = getBbHeight() / 2.0F;
		ItemEntity itementity;
		Vec3 ejectVector;
		for(ItemStack stack : loot) {
			float pitch = 40.0F * DEUtil.ENTITY_LOGIC_RANDOM.nextFloat() - 30.0F;
			float yaw = 360.0F * DEUtil.ENTITY_LOGIC_RANDOM.nextFloat();
			ejectVector = Vec3.directionFromRotation(pitch, yaw).scale(0.2);
			itementity = new ItemEntity(this.level, getX() + ejectVector.x * 2.0, getY() + ejectVector.y * 2.0 + heightByHalf, getZ() + ejectVector.z * 2.0, stack);
			itementity.setNoGravity(true);
			itementity.setDeltaMovement(ejectVector);
			itementity.setPickUpDelay(20);
			itementity.hasImpulse = false;
			this.level.addFreshEntity(itementity);
		}
	}

	public void handleLightEffect() {
		this.prevFxIntensity = this.fxIntensity;
		if (this.mainAnimationHandler.isPlaying(this.spawn)) {
			this.fxIntensity = (float) this.mainAnimationHandler.getTick() / 15.0F;
		} else if (this.mainAnimationHandler.isPlaying(this.giveItems)) {
			float tick = (float)this.mainAnimationHandler.getTick();
			if (tick > 45.0F && tick <= 50.0F) {
				this.fxIntensity = 1.0F + 0.8F * (tick - 45.0F);
			} else if (tick > 50.0F && tick <= 60.0F) {
				this.fxIntensity = (10.0F - tick - 50.0F) * 0.5F;
			}
		} else if (this.tickCount > 14) {
			this.fxIntensity = 1.0F;
		}
	}

	@OnlyIn(Dist.CLIENT)
	public void handleGiftAnimationClient(int animationTick) {
		if (animationTick == 1) {
			this.level.playLocalSound(getX(), getY() + 0.5, getZ(), DESounds.ES_ENDER_DRAGON_REWARD_USE.get(), SoundSource.NEUTRAL, 0.2F, 0.2F, false);
		} else if (animationTick == 20) {
			this.level.playLocalSound(getX(), getY() + 0.5, getZ(), DESounds.ES_ENDER_DRAGON_REWARD_INIT.get(), SoundSource.NEUTRAL, 2.0F, 1.0F, false);
		}
		if (animationTick == 45) {
			this.level.playLocalSound(getX(), getY() + 0.5, getZ(), DESounds.ES_ENDER_DRAGON_REWARD_OPEN.get(), SoundSource.NEUTRAL, 3.0F, 1.0F, false);
		}
	}

	@OnlyIn(Dist.CLIENT)
	public void handleLoopSoundClient() {
		if (Minecraft.getInstance().player == null) return;
		if (this.loopSound == null || this.loopSound.isStopped() && Minecraft.getInstance().player.distanceTo(this) < 20) {
			this.loopSound = new EnderDragonRewardLoopSound(this);
			Minecraft.getInstance().getSoundManager().play(this.loopSound);
		}
	}

	@Override
	public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand) {
		if (!player.level.isClientSide && this.mainAnimationHandler.isPlayingNull()) {
			this.mainAnimationHandler.play(this.giveItems);
		}
		return super.interactAt(player, vec, hand);
	}
	
	private List<ItemStack> lootListUnsafe() {
		LootTable loottable = this.level.getServer().getLootTables().get(LOOT_TABLE);
		return loottable.getRandomItems(new LootContext.Builder((ServerLevel) this.level).withParameter(LootContextParams.THIS_ENTITY, this).withRandom(this.level.random).create(LootContextParamSet.builder().build()));
	}

	public float getFxIntensity() {
		return this.fxIntensity;
	}
	
	public float getPrevFxIntensity() {
		return this.prevFxIntensity;
	}
	
	@Override
	public boolean isPickable() {
		return true;
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag p_20052_) {

	}

	@Override
	protected void addAdditionalSaveData(CompoundTag p_20139_) {

	}
}