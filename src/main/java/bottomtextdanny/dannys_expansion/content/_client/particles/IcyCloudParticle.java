package bottomtextdanny.dannys_expansion.content._client.particles;

import bottomtextdanny.dannys_expansion._base.animation.FloatAnimator;
import bottomtextdanny.dannys_expansion._base.animation.ISimpleAnimator;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import bottomtextdanny.braincell.base.Easing;
import bottomtextdanny.braincell.mod.world.builtin_particles.BCParticle;
import bottomtextdanny.braincell.mod.world.particle_utilities.local_sprites.SpriteGroupProvider;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class IcyCloudParticle extends BCParticle {
    public static final ISimpleAnimator<Float> SIZE_ANIMATOR = new FloatAnimator(0.0F);
    public static final int RANDOM_0 = 0, RANDOM_1 = 1, RANDOM_2 = 2;
    public static final SpriteGroupProvider GROUPS = SpriteGroupProvider.Builder.create(9, 3)
            .entry(RANDOM_0, 0, 1, 2, 1, 0)
            .entry(RANDOM_1, 3, 4, 5, 4, 3)
            .entry(RANDOM_2, 6, 7, 8, 7, 6)
            .build();
    private final float baseSize;
    private final int deltaLife;

    protected IcyCloudParticle(ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet handleSpriteSet) {
        super(world, x, y, z);
        lifetime = 5;
        this.deltaLife = this.random.nextInt(5);
        setSpriteGroup(GROUPS.fetchRandom(handleSpriteSet));
        setLocalSprite(0);
        setTicksForEachFrame(3, 3, 4 + this.deltaLife, 3, 3);
        this.baseSize = 0.18F + this.random.nextFloat() * 0.2F;
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
    }

    @Override
    public void tick() {
        super.tick();
        this.xd *= 0.95;
        this.yd *= 0.95;
        this.zd *= 0.95;
        this.yd -= 0.005;

        if (this.ticksPassed == 12) {
            for (int i = 0; i < 3; i++) {
                this.level.addAlwaysVisibleParticle(
                        ParticleTypes.SNOWFLAKE,
                        this.x,
                        this.y,
                        this.z,
                        this.random.nextGaussian() * 0.1D,
                        this.random.nextGaussian() * 0.1D,
                        this.random.nextGaussian() * 0.1D);
            }
        }
    }

    @Override
    public void preRender(PoseStack poseStack, VertexConsumer buffer, Camera renderInfo, float partialTicks) {
        SIZE_ANIMATOR.reset();
        SIZE_ANIMATOR.setActual(this.ticksPassed);
        SIZE_ANIMATOR.start(0.0F);
        SIZE_ANIMATOR.addPoint(5.0F, this.baseSize);
        SIZE_ANIMATOR.addPoint(5.0F + this.deltaLife, this.baseSize);
        SIZE_ANIMATOR.addPoint(5.0F, 0.0F);
        this.quadSize = SIZE_ANIMATOR.get(Easing.EASE_OUT_GAMMA, partialTicks);
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
            return new IcyCloudParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}
