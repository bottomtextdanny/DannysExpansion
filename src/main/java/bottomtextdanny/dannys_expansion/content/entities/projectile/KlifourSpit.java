package bottomtextdanny.dannys_expansion.content.entities.projectile;

import bottomtextdanny.dannys_expansion.tables.DESounds;
import bottomtextdanny.dannys_expansion.tables._client.DEParticles;
import bottomtextdanny.braincell.base.ObjectFetcher;
import bottomtextdanny.braincell.mod._base.serialization.WorldPacketData;
import bottomtextdanny.braincell.mod._base.serialization.builtin.BuiltinSerializers;
import bottomtextdanny.braincell.mod.network.Connection;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class KlifourSpit extends DEProjectile {
    public static final int COLLISION_CLIENT_CALL = 0;
    public static final float MOVEMENT_SPEED = 1.2F;

    public KlifourSpit(EntityType<? extends Projectile> type, Level level) {
        super(type, level);
    }

    @Override
    public void commonInit() {}

    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide) {
            setDeltaMovement(getLookAngle().scale(MOVEMENT_SPEED));
        } else {
            Connection.doClientSide(() -> clientTick());
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void clientTick() {
        if (this.random.nextInt(2) == 0) {
            this.level.addParticle(
                    DEParticles.KLIFOUR_POISON_BUBBLE.get(),
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    this.random.nextGaussian() * 0.1D,
                    this.random.nextGaussian() * 0.1D,
                    this.random.nextGaussian() * 0.1D);
        }
        if (this.random.nextInt(2) == 0) {
            this.level.addParticle(
                    DEParticles.KLIFOUR_POISON_DRIP.get(),
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    this.random.nextGaussian() * 0.3D,
                    this.random.nextGaussian() * 0.3D,
                    this.random.nextGaussian() * 0.3D);
        }
    }

    @Override
    public void clientCallOutHandler(int flag, ObjectFetcher fetcher) {
        if (flag == COLLISION_CLIENT_CALL) {
            Vec3 location = fetcher.get(0);

            for (int i = 0; i < 10; i++) {
                this.level.addParticle(
                        DEParticles.KLIFOUR_POISON_BIG_DRIP.get(),
                        location.x(),
                        location.y() + 0.01D,
                        location.z(),
                        this.random.nextGaussian() * 0.3D,
                        this.random.nextGaussian() * 0.3D,
                        this.random.nextGaussian() * 0.3D);
            }
        }
    }

    @Override
    protected void onHit(HitResult result) {
        if (!this.level.isClientSide && result.getType() != HitResult.Type.MISS) {
            sendClientMsg(COLLISION_CLIENT_CALL, WorldPacketData.of(BuiltinSerializers.VEC3, result.getLocation()));
        }
        super.onHit(result);
    }

    @Override
    protected void onBlockHit(BlockHitResult result) {
        super.onBlockHit(result);
        removeProjectile();
    }

    @Override
    protected void onEntityHit(EntityHitResult result, Entity entity) {
        if (entity instanceof LivingEntity living) {
            boolean effectiveHurt = living.hurt(DamageSource.indirectMobAttack(this, getCaster()), (float)getCasterAttribute(Attributes.ATTACK_DAMAGE));

            if (effectiveHurt && this.random.nextFloat() < 0.3F * this.level.getDifficulty().getId()) {
                living.addEffect(new MobEffectInstance(MobEffects.POISON, 30));
            }
            removeProjectile();
        }
    }

    @Override
    protected void removeCallOut() {
        playSound(DESounds.ES_KLIFOUR_SPIT_IMPACT.get(), 1.0F, 0.9F + this.random.nextFloat() * 0.2F);
    }

    @Override
    public int baseLifetime() {
        return 60;
    }
}
