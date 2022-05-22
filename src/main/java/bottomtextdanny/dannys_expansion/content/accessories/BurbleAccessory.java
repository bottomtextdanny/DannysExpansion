package bottomtextdanny.dannys_expansion.content.accessories;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.tables.DEEntities;
import bottomtextdanny.dannys_expansion.tables._client.DEParticles;
import bottomtextdanny.dannys_expansion.tables.DESounds;
import bottomtextdanny.dannys_expansion.content.entities.projectile.SquigBubbleEntity;
import bottomtextdanny.dannys_expansion._base.particle.DannyParticleData;
import bottomtextdanny.braincell.base.ObjectFetcher;
import bottomtextdanny.braincell.base.pair.Pair;
import bottomtextdanny.braincell.base.scheduler.IntScheduler;
import bottomtextdanny.braincell.mod.network.Connection;
import bottomtextdanny.braincell.mod.capability.player.accessory.AccessoryKey;
import bottomtextdanny.braincell.mod.capability.player.accessory.MiniAttribute;
import bottomtextdanny.braincell.mod.capability.player.accessory.ModifierType;
import bottomtextdanny.braincell.mod.capability.player.accessory.extensions.FinnFall;
import bottomtextdanny.braincell.mod.capability.player.accessory.extensions.FinnHurt;
import bottomtextdanny.braincell.mod._base.serialization.WorldPacketData;
import bottomtextdanny.braincell.mod._base.serialization.builtin.BuiltinSerializers;
import net.minecraft.client.Options;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.List;

public class BurbleAccessory extends CoreAccessory implements FinnFall, FinnHurt {
	private IntScheduler bubbleCooldown;
	private boolean spacing;
	
	public BurbleAccessory(AccessoryKey<?> key, Player player) {
		super(key, player);
	}

	@Override
	public void prepare(int index) {
		super.prepare(index);
		this.bubbleCooldown = IntScheduler.simple(80);
	}

	@Override
	public void populateModifierData(List<Pair<ModifierType, Double>> modifierList, List<Pair<MiniAttribute, Float>> lesserModifierList) {}
	
	@Override
	public void tick() {
		super.tick();

        this.bubbleCooldown.incrementFreely(1);
		
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
		boolean jumping = DannysExpansion.client()
				.getKeybindHandler().keyBindJump.onHoldingAction();

		this.spacing = jumping;

		triggerServerAction(0,
				WorldPacketData.of(BuiltinSerializers.BOOLEAN, jumping));
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
	public void fallDamageMultModifier(float baseDistance, MutableFloat fallDamage, MutableFloat fallDistance) {
		if (this.player.isShiftKeyDown() && this.spacing) fallDistance.setValue(0.0F);
	}

	@Override
	public int fallModificationPriority() {
		return FALL_CANCEL_PRIORITY;
	}

	@Override
	public String getGeneratedDescription() {
		return "generates a homing bubble when you get hit by any entity.";
	}
}
