package net.bottomtextdanny.danny_expannny.objects.particles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.world.builtin_particles.BCParticle;
import net.bottomtextdanny.braincell.mod.world.particle_utilities.local_sprites.SimpleSpriteGroup;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.FloatAnimator;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.ISimpleAnimator;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class IcyCircularSmokeParticle extends BCParticle {
    public static final ISimpleAnimator<Float> SIZE_ANIMATOR = new FloatAnimator(0.0F);
    private final float baseSize;

    protected IcyCircularSmokeParticle(ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet handleSpriteSet) {
        super(world, x, y, z);
        this.lifetime = 8;
        setSimpleFrameTicks();
        setSpriteGroup(SimpleSpriteGroup.of(handleSpriteSet, 8));
        setLocalSprite(0);
        this.baseSize = 0.8F + this.random.nextFloat() * 0.04F;
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void preRender(PoseStack poseStack, VertexConsumer buffer, Camera renderInfo, float partialTicks) {
        SIZE_ANIMATOR.reset();
        SIZE_ANIMATOR.setActual(this.ticksPassed);
        SIZE_ANIMATOR.start(0.0F);
        SIZE_ANIMATOR.addPoint(2.0F, this.baseSize);
        SIZE_ANIMATOR.addPoint(5.0F, this.baseSize);
        SIZE_ANIMATOR.addPoint(2.0F, 0.0F);
        this.quadSize = SIZE_ANIMATOR.get(Easing.EASE_OUT_GAMMA, partialTicks);
    }

    @Override
    public void handleRenderRotations(float partialTicks, Camera info) {
        this.yaw = 0.0F;
        this.pitch = -90.0F;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public boolean shouldCull() {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet p_i50634_1_) {
            this.spriteSet = p_i50634_1_;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new IcyCircularSmokeParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}
