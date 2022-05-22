package bottomtextdanny.dannys_expansion.content.entities.projectile.bullet;

import bottomtextdanny.dannys_expansion._base.particle.DannyParticleData;
import bottomtextdanny.dannys_expansion._util.DEParticleUtil;
import bottomtextdanny.dannys_expansion.tables.DESounds;
import bottomtextdanny.dannys_expansion._util.DEMath;
import bottomtextdanny.braincell.base.ObjectFetcher;
import bottomtextdanny.braincell.mod.capability.level.speck.ShootLighSpeck;
import bottomtextdanny.braincell.mod.graphics.point_lighting.SimplePointLight;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BulletEntity extends AbstractBulletEntity {
	
	public BulletEntity(EntityType<? extends AbstractBulletEntity> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientCallOutHandler(int flag, ObjectFetcher fetcher) {
        super.clientCallOutHandler(flag, fetcher);
    }

    @Override
    public void onDeath() {
        super.onDeath();
        
        if (!this.soundPlayed && !this.waterSplashSoundPlayer) this.playSound(DESounds.ES_BULLET_IMPACT_GENERIC.get(), 1.0F, 1.0F + 0.2F * this.random.nextFloat());
    }

    @OnlyIn(Dist.CLIENT)
    public void clientHitCallout(Vec3 hitPosition) {

        for (int i = 0; i < 4 + this.random.nextInt(2); i++) {
            DannyParticleData data = DEParticleUtil.dust(true, 0xFFFFC000, random.nextFloat(0.08F, 0.1F), 0.85F, 0.85F);

            float f = 0.25F + this.random.nextFloat() * 0.35F;
            Vec3 vec = DEMath.fromPitchYaw(getXRot() + (float) this.random.nextGaussian() * 30, getYRot() + (float) this.random.nextGaussian() * 30)
                    .scale(f);

            this.level.addParticle(data, hitPosition.x, hitPosition.y, hitPosition.z, -vec.x, -vec.y, -vec.z);
        }

        ShootLighSpeck light = new ShootLighSpeck(this.level, 0, 2, 8);

        light.setPosition(new Vec3(hitPosition.x, hitPosition.y, hitPosition.z));
        light.setLight(new SimplePointLight(new Vec3(1.0, 0.45, 0.0), 1.5F, 0.35F, 0.5F));
        light.addToLevel();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (!this.level.isClientSide) {
            sendHitMsg(result.getLocation());
        } else {
	       
        }
        super.onHitEntity(result);
    }

    @Override
    protected void onBlockHit(BlockHitResult result) {
        if (!this.level.isClientSide) {
            sendHitMsg(result.getLocation());
        } else {
	      
        }
        super.onBlockHit(result);
    }
}
