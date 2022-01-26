package net.bottomtextdanny.danny_expannny.objects.particles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.world.builtin_particles.BCParticle;
import net.bottomtextdanny.braincell.mod.world.particle_utilities.local_sprites.SimpleSpriteGroup;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.FloatAnimator;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.ISimpleAnimator;
import net.bottomtextdanny.dannys_expansion.core.Util.particle.DannyParticleData;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TumefendHopParticle extends BCParticle {

    protected TumefendHopParticle(ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet handleSpriteSet) {
        super(world, x, y, z);
        setTicksForEachFrame(10);
        setSpriteGroup(SimpleSpriteGroup.of(handleSpriteSet, 1));
        setLocalSprite(0);
        this.pitch = -90.0F;
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
    }

    @Override
    public void preRender(PoseStack poseStack, VertexConsumer buffer, Camera renderInfo, float partialTicks) {
        ISimpleAnimator<Float> animator = new FloatAnimator(this.ticksPassed + partialTicks).start(1.0F);

        animator.addPoint(10.0F, 0.0F);
        this.alpha = animator.get(Easing.EASE_OUT_GAMMA, partialTicks);

        animator.reset();

        animator.addPoint(2.0F, 1.0F);
        animator.addPoint(8.0F, 0.0F);
        this.quadSize = animator.get(Easing.LINEAR, partialTicks);
    }

    public int getLightColor(float partialTick) {
        return 15728880;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }


    @Override
    public void handleRenderRotations(float partialTicks, Camera info) {
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<DannyParticleData> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet p_i50634_1_) {
            this.spriteSet = p_i50634_1_;
        }

        public Particle createParticle(DannyParticleData typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new TumefendHopParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}
