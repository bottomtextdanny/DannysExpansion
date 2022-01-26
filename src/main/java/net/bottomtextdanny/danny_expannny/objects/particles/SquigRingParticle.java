package net.bottomtextdanny.danny_expannny.objects.particles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.bottomtextdanny.braincell.mod.world.builtin_particles.BCParticle;
import net.bottomtextdanny.braincell.mod.world.particle_utilities.local_sprites.SpriteGroupProvider;
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

public class SquigRingParticle extends BCParticle {
    public static final int SPRITE_DATA_IDX = 0, YAW_DATA_IDX = 1, PITCH_DATA_IDX = 2;
    public static final int BLUE_IDX = 0, RED_IDX = 1, GREEN_IDX = 2, PURPLE_IDX = 3, BLACK_IDX = 4;
    public static final SpriteGroupProvider SPRITE_GROUPS = SpriteGroupProvider.SingularBuilder.create(5)
            .entry(BLUE_IDX)
            .entry(RED_IDX)
            .entry(GREEN_IDX)
            .entry(PURPLE_IDX)
            .entry(BLACK_IDX)
            .build();

    protected SquigRingParticle(ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, float yaw, float pitch, SpriteSet handleSpriteSet, int spriteIndex) {
        super(world, x, y, z);
        setTicksForEachFrame(20);
        setSpriteGroup(SPRITE_GROUPS.get(handleSpriteSet, spriteIndex));
        setLocalSprite(0);
        this.yaw = yaw;
        this.pitch = pitch;
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
    }

    @Override
    public void preRender(PoseStack poseStack, VertexConsumer buffer, Camera renderInfo, float partialTicks) {
        ISimpleAnimator<Float> animator = new FloatAnimator(this.ticksPassed).start(400F);

        animator.addPoint(this.maxTime, 0.0F);
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(animator.get(Easing.LINEAR, partialTicks)));
        animator.reset(1F);

        animator.addPoint(20, 0.0F);
        this.alpha = animator.get(Easing.EASE_OUT_GAMMA, partialTicks);

        animator.reset();

        animator.addPoint(5, 2F);
        animator.addPoint(15, 0.5F);
        this.quadSize = animator.get(Easing.LINEAR, partialTicks) * 0.75F;
    }

    public int getLightColor(float partialTick) {
        return 15728880;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void handleRenderRotations(float partialTicks, Camera info) {}

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<DannyParticleData> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet p_i50634_1_) {
            this.spriteSet = p_i50634_1_;
        }

        public Particle createParticle(DannyParticleData typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new SquigRingParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, typeIn.fetch(YAW_DATA_IDX), typeIn.fetch(PITCH_DATA_IDX), this.spriteSet, typeIn.fetch(SPRITE_DATA_IDX));
        }
    }
}
