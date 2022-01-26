package net.bottomtextdanny.danny_expannny.objects.accessories;

import net.bottomtextdanny.braincell.mod.base.util.Connection;
import net.bottomtextdanny.braincell.mod.serialization.WorldPacketData;
import net.bottomtextdanny.braincell.mod.serialization.serializers.builtin.BuiltinSerializers;
import net.bottomtextdanny.danny_expannny.object_tables.DEEntities;
import net.bottomtextdanny.danny_expannny.object_tables.DEParticles;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.SquigBubbleEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.Pair;
import net.bottomtextdanny.dannys_expansion.core.Util.Timer;
import net.bottomtextdanny.dannys_expansion.core.Util.particle.DannyParticleData;
import net.bottomtextdanny.danny_expannny.accessory.AccessoryKey;
import net.bottomtextdanny.danny_expannny.accessory.ModifierType;
import net.bottomtextdanny.braincell.underlying.misc.ObjectFetcher;
import net.bottomtextdanny.danny_expannny.accessory.modules.FinnFall;
import net.bottomtextdanny.danny_expannny.accessory.modules.FinnHurt;
import net.bottomtextdanny.danny_expannny.capabilities.player.MiniAttribute;
import net.bottomtextdanny.dannys_expansion.core.events.KeybindHandler;
import net.minecraft.client.Options;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.List;

public class BurbleAccessory extends CoreAccessory implements FinnFall, FinnHurt {
	private Timer bubbleCooldown;
	private boolean spacing;
	
	public BurbleAccessory(AccessoryKey<?> key, Player player) {
		super(key, player);
	}

	@Override
	public void prepare(int index) {
		super.prepare(index);
		this.bubbleCooldown = new Timer(80);
	}

	@Override
	public void populateModifierData(List<Pair<ModifierType, Double>> modifierList, List<Pair<MiniAttribute, Float>> lesserModifierList) {
		modifierList.add(Pair.of(ModifierType.MOVEMENT_SPEED_MLT, 100.0D));
		modifierList.add(Pair.of(ModifierType.ATTACK_KNOCKBACK_ADD, 5.0D));
		lesserModifierList.add(Pair.of(MiniAttribute.GUN_CADENCE, 10.0F));
		lesserModifierList.add(Pair.of(MiniAttribute.GUN_DAMAGE_ADD, -1.0F));
	}
	
	@Override
	public void tick() {
		super.tick();

        this.bubbleCooldown.tryUp();
		
		if (this.player.isShiftKeyDown() && this.spacing) {
			if (!this.player.level.isClientSide()) {
                this.player.fallDistance = 0.0F;
			} else {
				if (this.player.getDeltaMovement().y < 0.0) {

                    this.player.setDeltaMovement(this.player.getDeltaMovement().multiply(1.0, 0.5, 1.0));
				}
				if (this.random.nextInt(6) == 2) {

					Connection.doClientSide(() -> randomParticleTick());
				}
			}
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	public void randomParticleTick() {
		double d0 = this.random.nextGaussian() * (this.player.getBoundingBox().getXsize() + 0.15) / 2 + this.player.getX();
		double d1 = this.random.nextFloat() * this.player.getBoundingBox().getYsize() + 0.1 + this.player.getY();
		double d2 = this.random.nextGaussian() * (this.player.getBoundingBox().getXsize() + 0.15) / 2 + this.player.getZ();

        this.player.level.playLocalSound(d0, d1, d2, DESounds.ES_SQUIG_BUBBLE_GENERATE.get(), SoundSource.PLAYERS, 0.1F, 2.6F + 0.1F * (float) this.random.nextInt(3), false);
        this.player.level.addParticle(new DannyParticleData(DEParticles.SQUIG_BUBBLE), d0, d1, d2, 0.0F, 0.0F, 0.0F);
	}
	
	@Override
	public void onHurt(DamageSource source, MutableObject<Float> amount) {
		
		if (this.bubbleCooldown.hasEnded() && source.getEntity() instanceof LivingEntity && source.getEntity() != this.player) {
			LivingEntity living = (LivingEntity) source.getEntity();
			Vec3 pos = this.player.position();
			
			SquigBubbleEntity bubble = new SquigBubbleEntity(DEEntities.SQUIG_BUBBLE.get(), this.player.level);
			bubble.setCaster(this.player);
			bubble.setBobbleDamage(2.0F + this.random.nextFloat() * 0.5F);
			bubble.setTarget(living);
			bubble.absMoveTo(pos.x, this.player.getEyeY(), pos.z, 0.0F, 0.0F);
            this.player.level.addFreshEntity(bubble);
            this.bubbleCooldown.reset();
		}
	}
	
	@Override
	public void accessoryServerManager(int flag, ObjectFetcher objectFetcher) {
		super.accessoryServerManager(flag, objectFetcher);
		if (flag == 0) {
			if (objectFetcher.get(0) instanceof Boolean bool) {
                this.spacing = bool;
			}
		}
	}
	
	@Override
	public void keyHandler(Options settings) {
		super.keyHandler(settings);
		triggerServerAction(0, WorldPacketData.of(
				BuiltinSerializers.BOOLEAN,
				this.spacing = KeybindHandler.keyBindJump.onHoldingAction()));
	}
	
	@Override
	public double getFinalSpeedMultiplier() {
		return this.player.isShiftKeyDown() && this.spacing ? super.getFinalSpeedMultiplier() + 10.5 : super.getFinalSpeedMultiplier();
	}
	
	@Override
	public double getFinalAttackDamageAddition() {
		return super.getFinalAttackDamageAddition() + 4;
	}
	
	@Override
	public boolean cancelFall(float distance, float damageMult) {
		return this.player.isShiftKeyDown() && this.spacing;
	}
	
	@Override
	public String getGeneratedDescription() {
		return "test :]";
	}
}
