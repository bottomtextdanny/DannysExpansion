package bottomtextdanny.dannys_expansion.content.entities.projectile;

import bottomtextdanny.dannys_expansion.tables._client.DEParticles;
import bottomtextdanny.dannys_expansion.tables.DESounds;
import bottomtextdanny.dannys_expansion._util.DEMath;
import bottomtextdanny.dannys_expansion._util.DERayUtil;
import bottomtextdanny.dannys_expansion._base.particle.DannyParticleData;
import bottomtextdanny.braincell.base.ObjectFetcher;
import bottomtextdanny.braincell.base.scheduler.IntScheduler;
import bottomtextdanny.braincell.mod.network.Connection;
import bottomtextdanny.braincell.mod.entity.modules.additional_motion.ExternalMotion;
import bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationArray;
import bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationGetter;
import bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationHandler;
import bottomtextdanny.braincell.mod.entity.modules.animatable.SimpleAnimation;
import bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManager;
import bottomtextdanny.braincell.mod.entity.serialization.EntityData;
import bottomtextdanny.braincell.mod.entity.serialization.EntityDataReference;
import bottomtextdanny.braincell.mod.entity.serialization.RawEntityDataReference;
import bottomtextdanny.braincell.mod._base.serialization.WorldPacketData;
import bottomtextdanny.braincell.mod._base.serialization.builtin.BuiltinSerializers;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SquigBubbleEntity extends SpellEntity {
	public static final float DEFAULT_POP_DAMAGE = 0.5F;
	public static final EntityDataReference<Float> BUBBLE_DAMAGE_REF =
			BCDataManager.attribute(SquigBubbleEntity.class,
					RawEntityDataReference.of(
							BuiltinSerializers.FLOAT,
							() -> DEFAULT_POP_DAMAGE,
							"bubble_damage")
			);
	public static final EntityDataReference<Integer> HIT_COUNTER_REF =
			BCDataManager.attribute(SquigBubbleEntity.class,
					RawEntityDataReference.of(
							BuiltinSerializers.INTEGER,
							() -> 0,
							"hit_counter")
			);
	public static final SimpleAnimation POP = new SimpleAnimation(22);
	public static final SimpleAnimation HURT = new SimpleAnimation(12);
	public static final AnimationArray ANIMATIONS = new AnimationArray(POP, HURT);
	private final EntityData<Float> bubbleDamage;
	private final EntityData<Integer> hitCounter;
	public final AnimationHandler<SquigBubbleEntity> hurtModule = addAnimationHandler(new AnimationHandler<>(this));
	public final ExternalMotion hurtMotion;
	private final IntScheduler hitCooldownTimer;
	private LivingEntity target;
	private LivingEntity hitEntity;
	
	public SquigBubbleEntity(EntityType<? extends SpellEntity> entityTypeIn, Level worldIn) {
		super(entityTypeIn, worldIn);
		setLifeTime(200);
		this.bubbleDamage = bcDataManager().addSyncedData(EntityData.of(BUBBLE_DAMAGE_REF));
		this.hitCounter = bcDataManager().addSyncedData(EntityData.of(HIT_COUNTER_REF));
		this.hitCooldownTimer = IntScheduler.simple(10);

        this.hurtMotion = new ExternalMotion(0.95F);
	}

	@Override
	protected AnimationGetter getAnimations() {
		return ANIMATIONS;
	}

	@Override
	public void onLifeStart() {
		super.onLifeStart();
		playSound(DESounds.ES_SQUIG_BUBBLE_GENERATE.get(), 1.5F, 1.0F + 0.1F * (float) this.random.nextInt(3));
	}
	
	@Override
	public void tick() {
		super.tick();
		boolean targetFlag = this.target != null && this.target.isAddedToWorld() && this.target.isAlive();
		if (!this.level.isClientSide()) {
            this.hurtMotion.tick();
			sendClientMsg(0, WorldPacketData.of(BuiltinSerializers.VEC3, this.hurtMotion.getAcceleratedMotion()));
		} else {
			if (this.random.nextInt(8) == 2) {
				Connection.doClientSide(() -> randomParticleTick());
			}
		}
		
		if (targetFlag && !this.mainHandler.isPlaying(POP)) {
			float speedMult = 1.0F;
			
			if (getLifeTick() < 35) {
				speedMult = (float)getLifeTick() / 35.0F;
			}
			
			speedMult *= 0.23;
			
			if (isInWater()) {
				speedMult *= 0.6;
			}
			
			push(speedMult * this.forward.x, speedMult * this.forward.y, speedMult * this.forward.z);
			float yaw = (float) Mth.lerp(0.98, getYRot(), DEMath.getTargetYaw(this, this.target));
			float pitch = (float) Mth.lerp(0.98, getXRot(), DEMath.getTargetPitch(this, this.target));
			setRotations(yaw, pitch);
		}
		
		if (!this.level.isClientSide()) {
            this.hitCooldownTimer.incrementFreely(1);
			
			if (this.mainHandler.isPlaying(POP)) {
				if (this.mainHandler.getTick() == 6) {
					if (this.hitEntity != null && this.hitEntity.isAddedToWorld() && this.hitEntity.isAlive() && getCaster() != null) {
						if (castersDamage(this.hitEntity, this.bubbleDamage.get() * this.level.getDifficulty().ordinal())) {
                            this.hitEntity.invulnerableTime = 0;
						}
						
					}
					setDeath();
				}
			} else if (!targetFlag || getLifeTick() > this.maxLife - 10 || this.hitCounter.get() >= 1) {
                this.mainHandler.play(POP);
			}
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	public void randomParticleTick() {
		Vec3 pos = position();
        this.level.addParticle(new DannyParticleData(DEParticles.SQUIG_BUBBLE), pos.x + this.random.nextGaussian() * 0.6, pos.y + 0.25 + this.random.nextGaussian() * 0.6, pos.z + this.random.nextGaussian() * 0.6, 0.0, 0.0, 0.0);
	}
	
	@Override
	public float getMotionDeceleration() {
		return this.target != null && this.target.isAddedToWorld() && this.target.isAlive() ? 0.7F : 0.95F;
	}
	
	@Override
	public Vec3 finalMotion() {
		return super.finalMotion().add(this.hurtMotion.getAcceleratedMotion());
	}
	
	@Override
	public void onLifeEnd() {
		super.onLifeEnd();
		if (!this.level.isClientSide) {
			playSound(DESounds.ES_SQUIG_BUBBLE_POP.get(), 1.2F, 1.0F + 0.1F * (float) this.random.nextInt(3));
			sendClientMsg(1);
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void clientCallOutHandler(int flag, ObjectFetcher fetcher) {
		super.clientCallOutHandler(flag, fetcher);
		if (flag == 0) {
            this.hurtMotion.setMotion(fetcher.get(0, Vec3.class));
		} else if (flag == 1) {
			Vec3 pos = position();
            this.level.addParticle(new DannyParticleData(DEParticles.SQUIG_BUBBLE_POP), pos.x, pos.y + 0.25, pos.z, 0.0, 0.0, 0.0);
		}
	}
	
	@Override
	public boolean skipAttackInteraction(Entity entityIn) {
		
		
		return super.skipAttackInteraction(entityIn);
	}
	
	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (!this.level.isClientSide() && this.hitCooldownTimer.hasEnded()) {
			if (source.getEntity() != null) {
				Entity entityIn = source.getEntity();
				if (entityIn.isAddedToWorld() && entityIn.isAlive() && entityIn != getCaster()) {
                    this.hitCounter.set(this.hitCounter.get() + 1);
                    this.hurtModule.play(HURT);
					playSound(DESounds.ES_SQUIG_BUBBLE_HIT.get(), 0.7F, 1.0F + 0.1F * (float) this.random.nextInt(3));
					
					Vec3 invLook = DEMath.fromPitchYaw(-getXRot(), getYRot() + 180.0F).scale(1.1);

                    this.hurtMotion.addMotion(invLook);
                    this.hitCooldownTimer.reset();
				}
			}
			
		}
		return super.hurt(source, amount);
	}
	
	@Override
	public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand) {
		setDeath();
		return super.interactAt(player, vec, hand);
	}
	
	@Override
	public HitResult rayTraceResultType() {
		return DERayUtil.orbRaytrace(this, this::collisionParameters, finalMotion(), ClipContext.Fluid.ANY, 0.1F);
	}
	
	@Override
	protected void onBlockHit(BlockHitResult p_230299_1_) {
		super.onBlockHit(p_230299_1_);
	}
	
	@Override
	protected void onEntityHit(EntityHitResult p_213868_1_) {
		super.onEntityHit(p_213868_1_);
		
		if (!this.level.isClientSide()) {
			if (p_213868_1_.getEntity() instanceof LivingEntity) {
                this.hitEntity = (LivingEntity) p_213868_1_.getEntity();
			}
			
			if (!this.mainHandler.isPlaying(POP)) {
                this.mainHandler.play(POP);
			}
		}
	}
	
	@Override
	public boolean isPickable() {
		return !this.hitCooldownTimer.hasEnded();
	}
	
	public void setBobbleDamage(float damage) {
        this.bubbleDamage.set(damage);
	}
	
	public void setTarget(LivingEntity target) {
		this.target = target;
	}
}
