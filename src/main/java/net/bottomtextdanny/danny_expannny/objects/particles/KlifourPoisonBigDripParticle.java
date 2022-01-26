package net.bottomtextdanny.danny_expannny.objects.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class KlifourPoisonBigDripParticle extends DannyParticle {

    protected KlifourPoisonBigDripParticle(ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet handleSpriteSet) {
        super(world, x, y, z, xSpeed, ySpeed, zSpeed, handleSpriteSet);
        this.lifetime = 1;
        setFrameTicks(28, 0);
        this.quadSize = 0.1F;
        this.setSpriteFromAge(handleSpriteSet);
        this.xd = xSpeed;
        this.yd = ySpeed - 0.1F;
        this.zd = zSpeed;
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
            return new KlifourPoisonBigDripParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}
