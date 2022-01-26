package net.bottomtextdanny.danny_expannny.objects.particles;

import net.bottomtextdanny.danny_expannny.DEOp;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class MGBigBlobParticle extends DannyParticle {

    protected MGBigBlobParticle(ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet handleSpriteSet) {
        super(world, x, y, z, xSpeed, ySpeed, zSpeed, handleSpriteSet);
        this.lifetime = 1;
        setFrameTicks(200, 0);
        this.quadSize = 0.14F;
        this.setSpriteFromAge(handleSpriteSet);
        this.xd = xSpeed;
        this.yd = ySpeed - 0.3F;
        this.zd = zSpeed;
    }

    @Override
    public void tick() {
        super.tick();
        this.xd *= 0.8F;
        this.yd *= 0.95F;
        this.zd *= 0.8F;

        if (this.lifeTime > 20) {
            if (DEOp.LOGIC_DIST3D_UTIL.start(this.xo, this.yo, this.zo).get(this.x, this.y, this.z) > 0.05) {
                Vec3 easedPos = DEMath.getVecLerp(0.5F, new Vec3(this.x, this.y, this.z), new Vec3(this.x + this.xd, this.y - 0.9, this.z + this.zd));
                this.level.addParticle(ParticleTypes.SMOKE, this.x, this.y, this.z, 0, 0, 0);

                if (this.random.nextInt(15) == 1) {
                    this.level.addParticle(ParticleTypes.LAVA, this.x, this.y, this.z, 0, 0.3, 0);

                }

                this.level.addParticle(ParticleTypes.SMOKE, easedPos.x, easedPos.y, easedPos.z, 0, 0, 0);

            } else {
                for (int i = 0; i < 7; i++) {
                    this.level.addParticle(ParticleTypes.SMOKE, this.x, this.y, this.z, this.random.nextGaussian() * 0.4, 0.4, this.random.nextGaussian() * 0.4);

                }
                remove();
            }
            move(0.0, -0.9, 0.0);
        }
        if (this.collidedY) {

            for (int i = 0; i < 7; i++) {
                this.level.addParticle(ParticleTypes.SMOKE, this.x, this.y, this.z, this.random.nextGaussian() * 0.4, 0.4, this.random.nextGaussian() * 0.4);

            }

            remove();
        }
    }

    public int getLightColor(float partialTick) {
        return 15728880;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet p_i50634_1_) {
            this.spriteSet = p_i50634_1_;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new MGBigBlobParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}
