package bottomtextdanny.dannys_expansion.content.entities.projectile.bullet;

import bottomtextdanny.dannys_expansion._base.particle.DannyParticleData;
import bottomtextdanny.dannys_expansion._util.DEParticleUtil;
import bottomtextdanny.dannys_expansion.tables.DESounds;
import bottomtextdanny.dannys_expansion._util.DEMath;
import bottomtextdanny.dannys_expansion._util.DERayUtil;
import bottomtextdanny.braincell.mod.capability.level.speck.ShootLighSpeck;
import bottomtextdanny.braincell.mod.graphics.point_lighting.SimplePointLight;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AquaticBulletEntity extends AbstractBulletEntity {
	
	public AquaticBulletEntity(EntityType<? extends AbstractBulletEntity> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }
	
    @Override
    public void tick() {
        float multiplier = 1.0F;

        if (this.wasTouchingWater) {
            multiplier = 1.5F;
        }

        setBulletSpeedMult(getDefaultSpeedMult() * multiplier);
        super.tick();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientHitCallout(Vec3 hitPosition) {

        for (int i = 0; i < 4 + this.random.nextInt(2); i++) {
            DannyParticleData data = DEParticleUtil.dust(true, 0xFF00C0FF, random.nextFloat(0.08F, 0.1F), 0.85F, 0.85F);

            float f = 0.25F + this.random.nextFloat() * 0.35F;
            Vec3 vec = DEMath.fromPitchYaw(getXRot() + (float) this.random.nextGaussian() * 30, getYRot() + (float) this.random.nextGaussian() * 30)
                    .scale(f);

            this.level.addParticle(data, hitPosition.x, hitPosition.y, hitPosition.z, -vec.x, -vec.y, -vec.z);
        }

        ShootLighSpeck light = new ShootLighSpeck(this.level, 0, 2, 8);

        light.setPosition(new Vec3(hitPosition.x, hitPosition.y, hitPosition.z));
        light.setLight(new SimplePointLight(new Vec3(0.0, 0.3, 1.0), 1.5F, 0.25F, 0.6F));
        light.addToLevel();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (!this.level.isClientSide) {
            sendHitMsg(result.getLocation());
        }
        super.onHitEntity(result);

    }

    @Override
    protected void onBlockHit(BlockHitResult result) {
        if (!this.level.isClientSide) {
            sendHitMsg(result.getLocation());
        }
        super.onBlockHit(result);
    }

    @Override
    public boolean canceledMovement() {
        return false;
    }

    @Override
    public void onDeath() {
        super.onDeath();
        if (!this.soundPlayed) this.playSound(DESounds.ES_BULLET_IMPACT_GENERIC.get(), 1.0F, 1.0F + 0.2F * this.random.nextFloat());
    }

    @Override
    public HitResult rayTraceResultType() {
        return DERayUtil.bulletRaytrace(this, this::collisionParameters, DEMath.fromPitchYaw(getXRot(), getYRot()).multiply(getRealSpeed(), getRealSpeed(), getRealSpeed()), ClipContext.Fluid.NONE);
    }
}
