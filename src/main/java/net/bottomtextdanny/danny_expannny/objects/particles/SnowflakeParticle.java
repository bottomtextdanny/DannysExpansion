package net.bottomtextdanny.danny_expannny.objects.particles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.world.builtin_particles.BCParticle;
import net.bottomtextdanny.braincell.mod.world.particle_utilities.local_sprites.SpriteGroupProvider;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
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

public class SnowflakeParticle extends BCParticle {
    public static final ISimpleAnimator<Float> SIZE_ANIMATOR = new FloatAnimator(0.0F);
    public static final int RANDOM_0 = 0, RANDOM_1 = 1, RANDOM_2 = 2, RANDOM_3 = 3, RANDOM_4 = 4;
    public static final SpriteGroupProvider GROUPS = SpriteGroupProvider.Builder.create(7, 5)
            .entry(RANDOM_0, 0, 5, 6)
            .entry(RANDOM_1, 1, 5, 6)
            .entry(RANDOM_2, 2, 5, 6)
            .entry(RANDOM_3, 3, 5, 6)
            .entry(RANDOM_4, 4, 5, 6)
            .build();
    private final float yawRotOffset = this.random.nextFloat() * DEMath.FPI;
    private final float pitchRotOffset = this.random.nextFloat() * DEMath.FPI;
	private final int deltaLife;
	private final float baseSize;

    protected SnowflakeParticle(ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet handleSpriteSet) {
        super(world, x, y, z);
        this.deltaLife = this.random.nextInt(10);
        setTicksForEachFrame(20 + this.deltaLife, 12, 12);
        setSpriteGroup(GROUPS.fetchRandom(handleSpriteSet));
        setLocalSprite(0);
        this.baseSize = 0.1F + this.random.nextFloat() * 0.02F;
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
    }

    @Override
    public void tick() {
        super.tick();
        this.xd *= 0.97;
        this.yd *= 0.97;
        this.zd *= 0.97;
        this.yd -= 0.01;
        if (this.onGround) remove();
    }

    @Override
    public void preRender(PoseStack poseStack, VertexConsumer buffer, Camera renderInfo, float partialTicks) {
        if (this.ticksPassed < 4) {
            SIZE_ANIMATOR.reset();
            SIZE_ANIMATOR.setActual(this.ticksPassed);
            SIZE_ANIMATOR.start(0.0F);
            SIZE_ANIMATOR.addPoint(5.0F, this.baseSize);
            this.quadSize = SIZE_ANIMATOR.get(Easing.EASE_OUT_GAMMA, partialTicks);
        } else {
            this.quadSize = this.baseSize;
        }
    }

    @Override
    public void handleRenderRotations(float partialTicks, Camera info) {
        this.yaw = this.prevYaw = DEMath.sin((this.yawRotOffset + this.ticksPassed + partialTicks) * 0.2) * 80.0F;
        this.pitch = this.prevPitch = 90 + DEMath.sin((this.pitchRotOffset + this.ticksPassed + partialTicks) * 0.2) * 80.0F;
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
            return new SnowflakeParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}
