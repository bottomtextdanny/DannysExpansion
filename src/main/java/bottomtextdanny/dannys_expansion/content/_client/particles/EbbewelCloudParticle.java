package bottomtextdanny.dannys_expansion.content._client.particles;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class EbbewelCloudParticle extends DannyParticle {
    float prevTone;
    float tone;

    public EbbewelCloudParticle(ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet handleSpriteSet) {
        super(world, x, y, z, 0.0D, 0.0D, 0.0D, handleSpriteSet);
        this.lifetime = 7;
        this.rCol = 0;
        this.gCol = 224F / 255F;
        this.bCol = 180F / 255F;
        this.alpha = 0.6F;
        this.tone = 1;
        setFrameTicks(30 + Mth.floor(new Random().nextFloat() * 30), 3, 3, 2, 2, 1, 1, 1);
        this.quadSize = 0.5F;
        this.setSpriteFromAge(handleSpriteSet);
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;

    }

    @Override
    public void tick() {
        super.tick();
        this.prevTone = this.tone;
        this.tone -= 0.0050F;
    }

    @Override
    public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
        this.gCol = 224F / 255F * Mth.lerp(partialTicks, this.prevTone, this.tone);
        this.bCol = 180F / 255F * Mth.lerp(partialTicks, this.prevTone, this.tone);
        super.render(buffer, renderInfo, partialTicks);
    }

    public int getLightColor(float partialTick) {
        return 15728880;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
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
            return new EbbewelCloudParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}
