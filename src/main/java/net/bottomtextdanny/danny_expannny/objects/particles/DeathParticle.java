package net.bottomtextdanny.danny_expannny.objects.particles;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class DeathParticle extends DannyParticle {
    public float rotSpeed;
    public float prevScale;
    public float scale;

    public DeathParticle(ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet handleSpriteSet) {
        super(world, x, y, z, 0.0D, 0.0D, 0.0D, handleSpriteSet);
        this.lifetime = 1;
        setFrameTicks(40, 0);
        this.quadSize = 0.2F;
        this.setSpriteFromAge(handleSpriteSet);
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;

        this.rotSpeed = 1.0F;
        this.scale = 1.0F;
    }

    @Override
    public void tick() {
        super.tick();

        this.prevScale = this.scale;

        if (this.lifeTime > 36) {
            this.scale -= 0.2F;
        } else {
            this.scale = 1.0F;
        }

        this.yd *= 0.95;
        this.rotSpeed = Mth.clamp(this.rotSpeed - 0.025F, 0.0F, 1.0F);
    }

    @Override
    public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
        float life = this.lifeTime + partialTicks;
        float rotation = DEMath.sin(life * 0.4F);

        this.roll = rotation * this.rotSpeed * 45 * radian;
        this.oRoll = this.roll;

        this.quadSize = Mth.lerp(partialTicks, this.prevScale, this.scale) * 0.2F;

        super.render(buffer, renderInfo, partialTicks);
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
            return new DeathParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}
