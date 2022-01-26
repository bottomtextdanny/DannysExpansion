package net.bottomtextdanny.danny_expannny.objects.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CursedFlameParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    private CursedFlameParticle(ClientLevel p_i232396_1_, double p_i232396_2_, double p_i232396_4_, double p_i232396_6_, double xSpeed, double ySpeed, double zSpeed, SpriteSet p_i232396_10_) {
        super(p_i232396_1_, p_i232396_2_, p_i232396_4_, p_i232396_6_, 0.0D, 0.0D, 0.0D);
        this.lifetime = 8;
        float f = this.random.nextFloat() * 0.1F + 0.9F;
        this.rCol = f;
        this.gCol = 1.0F;
        this.bCol = f;
        this.quadSize = this.random.nextFloat() * 0.03F + 0.15F;
        this.sprites = p_i232396_10_;
        this.setSpriteFromAge(p_i232396_10_);
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.setSpriteFromAge(this.sprites);
        }

        this.move(this.xd, this.yd + 0.05, this.zd);
    }

    public int getLightColor(float partialTick) {
        return 15728880;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet p_i50634_1_) {
            this.spriteSet = p_i50634_1_;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new CursedFlameParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}
