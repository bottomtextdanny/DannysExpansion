package net.bottomtextdanny.danny_expannny.objects.particles;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DannyDustParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    private DannyDustParticle(DannyDustParticleData data, ClientLevel p_i232396_1_, double p_i232396_2_, double p_i232396_4_, double p_i232396_6_, double xSpeed, double ySpeed, double zSpeed, SpriteSet p_i232396_10_) {
        super(p_i232396_1_, p_i232396_2_, p_i232396_4_, p_i232396_6_, 0.0D, 0.0D, 0.0D);
        this.lifetime = data.getTime();
        this.rCol = data.getRed();
        this.gCol = data.getGreen();
        this.bCol = data.getBlue();
        this.quadSize =  0.05F;
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
        this.hasPhysics = false;

        if (this.age++ >= this.lifetime) {
            this.remove();
        }


        float f = (float) this.age / (float) this.lifetime - 0.6F;
        if (f > 0) {
            this.setSprite(this.sprites.get((int) (f * 5.1F), 3));
        } else {
            this.setSprite(this.sprites.get(0, 3));
        }

        this.move(this.xd * (this.lifetime - this.age) / this.lifetime, 0.01 + this.yd * (this.lifetime - this.age) / this.lifetime, this.zd * (this.lifetime - this.age) / this.lifetime);
    }

    @Override
    public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
        super.render(buffer, renderInfo, partialTicks);
    }

    public int getLightColor(float partialTick) {
        return 15728880;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<DannyDustParticleData> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet p_i50634_1_) {
            this.spriteSet = p_i50634_1_;
        }

        public Particle createParticle(DannyDustParticleData typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new DannyDustParticle(typeIn, worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}
