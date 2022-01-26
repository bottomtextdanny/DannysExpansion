package net.bottomtextdanny.danny_expannny.objects.particles;

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

public class SandCloudParticle extends DannyParticle {
    float prevRedColor;
    float redColor;

    public SandCloudParticle(ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet handleSpriteSet) {
        super(world, x, y, z, 0.0D, 0.0D, 0.0D, handleSpriteSet);
        this.lifetime = 7;
        this.rCol = 240F / 255F;
        this.gCol = 120F / 255F;
        this.bCol = 60F / 255F;
        this.alpha = 0.6F;
        this.redColor = 1;
        this.prevRedColor = 1;
        setFrameTicks(20 + Mth.floor(new Random().nextFloat() * 20), 3, 3, 2, 2, 1, 1, 1);
        this.quadSize = 0.5F;
        this.setSpriteFromAge(handleSpriteSet);
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;

    }

    @Override
    public void tick() {
        super.tick();
        this.prevRedColor = this.redColor;
        this.redColor -= 0.0030F;
    }

    @Override
    public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
        this.rCol = 240F / 255F * Mth.lerp(partialTicks, this.prevRedColor, this.redColor);
        super.render(buffer, renderInfo, partialTicks);
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
            return new SandCloudParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}
