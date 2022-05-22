package bottomtextdanny.dannys_expansion.content.entities.projectile;

import bottomtextdanny.dannys_expansion._base.particle.DannyParticleData;
import bottomtextdanny.dannys_expansion._util.DEMath;
import bottomtextdanny.dannys_expansion._util.DEParticleUtil;
import bottomtextdanny.dannys_expansion._util.DERayUtil;
import bottomtextdanny.dannys_expansion.tables.DESounds;
import bottomtextdanny.braincell.base.ObjectFetcher;
import bottomtextdanny.braincell.mod._base.serialization.WorldPacketData;
import bottomtextdanny.braincell.mod._base.serialization.builtin.BuiltinSerializers;
import bottomtextdanny.braincell.mod.capability.level.speck.ShootLighSpeck;
import bottomtextdanny.braincell.mod.graphics.point_lighting.SimplePointLight;
import bottomtextdanny.braincell.mod.network.Connection;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CursedFireball extends DEProjectile {
    public static final int COLLISION_CLIENT_CALL = 0;
    public static final float MOVEMENT_SPEED = 1.5F;

    public CursedFireball(EntityType<? extends Projectile> type, Level level) {
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
            DannyParticleData data = DEParticleUtil.dust(true, 0xFFC0FF00, random.nextFloat(0.1F, 0.15F), 0.9F, 0.9F);

            this.level.addParticle(
                    data,
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    this.random.nextGaussian() * 0.4D,
                    this.random.nextGaussian() * 0.4D,
                    this.random.nextGaussian() * 0.4D);
        }
    }

    @Override
    public void clientCallOutHandler(int flag, ObjectFetcher fetcher) {
        if (flag == COLLISION_CLIENT_CALL) {
            Vec3 location = fetcher.get(0);

            for (int i = 0; i < 5; i++) {
                DannyParticleData data = DEParticleUtil.dust(true, 0xFFC0FF00, random.nextFloat(0.12F, 0.16F), 0.85F, 0.85F);
                float f = 0.25F + this.random.nextFloat() * 0.35F;
                Vec3 vec = DEMath.fromPitchYaw(getXRot() + (float) this.random.nextGaussian() * 30, getYRot() + (float) this.random.nextGaussian() * 30)
                        .scale(f);

                this.level.addParticle(data, location.x, location.y, location.z, -vec.x, -vec.y, -vec.z);
            }

            ShootLighSpeck light = new ShootLighSpeck(this.level, 0, 2, 8);

            light.setPosition(new Vec3(location.x, location.y, location.z));
            light.setLight(new SimplePointLight(new Vec3(0.2, 1.0, 0.0), 1.5F, 0.35F, 0.5F));
            light.addToLevel();
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
        if (entity instanceof LivingEntity living && (getCaster() == null || living != getCaster())) {
            boolean effectiveHurt = living.hurt(DamageSource.indirectMobAttack(this, getCaster()), (float)getCasterAttribute(Attributes.ATTACK_DAMAGE));
            removeProjectile();
        }
    }

    @Override
    public HitResult rayTraceResultType() {
        return DERayUtil.orbRaytrace(this, this::collisionParameters, getDeltaMovement(), ClipContext.Fluid.NONE, 0.25F);
    }

    @Override
    protected void removeCallOut() {
        playSound(DESounds.ES_CURSED_FIREBALL_HIT.get(), 1.0F, 0.9F + this.random.nextFloat() * 0.2F);
    }

    @Override
    public int baseLifetime() {
        return 80;
    }
}
