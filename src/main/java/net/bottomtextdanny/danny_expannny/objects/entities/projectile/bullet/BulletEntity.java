package net.bottomtextdanny.danny_expannny.objects.entities.projectile.bullet;

import net.bottomtextdanny.braincell.mod.opengl_front.point_lighting.SimplePointLight;
import net.bottomtextdanny.danny_expannny.object_tables.items.DEItems;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.danny_expannny.objects.particles.DannyDustParticleData;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.braincell.underlying.misc.ObjectFetcher;
import net.bottomtextdanny.dannys_expansion.core.base.pl.ShootLight;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
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

    public void clientHitCallout(Vec3 hitPosition) {
        for (int i = 0; i < 4 + this.random.nextInt(2); i++) {
            float f = 0.5F + this.random.nextFloat() * 0.75F;
            Vec3 vec = DEMath.fromPitchYaw(getXRot() + (float) this.random.nextGaussian() * 30, getYRot() + (float) this.random.nextGaussian() * 30);
            this.level.addParticle(new DannyDustParticleData(1.0F, 0.6F, 0.0F, 1, 2 + this.random.nextInt(8)), hitPosition.x, hitPosition.y, hitPosition.z, -vec.x * f, -vec.y * f, -vec.z * f);
        }

        ShootLight light = new ShootLight(this.level, 0, 2, 8);
        light.setPosition(new Vec3(hitPosition.x, hitPosition.y, hitPosition.z));
        light.setLight(new SimplePointLight(new Vec3(1.0, 0.45, 0.0), 1.5F, 0.35F, 0.5F));
        light.addToWorld();
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
