package net.bottomtextdanny.dannys_expansion.common.Entities.living;

import net.bottomtextdanny.braincell.mod.entity.modules.looped_walk.LoopedWalkModule;
import net.bottomtextdanny.braincell.mod.world.builtin_entities.ModuledMob;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.common.Entities.ai.goals.FollowTargetGoal;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class FoamieEntity extends ModuledMob {
	
	public FoamieEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
		super(type, worldIn);
	}
	
	public static AttributeSupplier.Builder Attributes() {
		return Monster.createMonsterAttributes()
			.add(Attributes.MAX_HEALTH, 10.0D)
			.add(Attributes.FOLLOW_RANGE, 30.0D)
			.add(Attributes.MOVEMENT_SPEED, 0.25D)
			.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
			.add(Attributes.ATTACK_DAMAGE, 7.0D);
	}

	@Override
	protected void commonInit() {
		super.commonInit();
        this.loopedWalkModule = new LoopedWalkModule(this);
	}

	protected void registerExtraGoals() {
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(2, new FollowTargetGoal(this, 1.2d));
		this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1d));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, true, null));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, 10, true, true, null));
	}
	
	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return true;
	}
	
	@Nullable
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DESounds.ES_FOAMSHROOM_GENERIC_HURT.get();
	}
	
	@Nullable
	@Override
	protected SoundEvent getDeathSound() {
		return DESounds.ES_FOAMSHROOM_GENERIC_DEATH.get();
	}
	
	@Override
	public float getLoopWalkMultiplier() {
		return 0.53F;
	}

	@Override
	public float hurtLoopLimbSwingFactor() {
		return 1.6F;
	}
}
