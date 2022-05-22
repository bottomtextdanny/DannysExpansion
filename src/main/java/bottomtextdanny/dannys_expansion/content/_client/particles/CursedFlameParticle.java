package bottomtextdanny.dannys_expansion.content._client.particles;

import bottomtextdanny.dannys_expansion._base.particle.DannyParticleData;
import bottomtextdanny.dannys_expansion._util.DEParticleUtil;
import bottomtextdanny.dannys_expansion._base.animation.FloatAnimator;
import bottomtextdanny.dannys_expansion._base.animation.ISimpleAnimator;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import bottomtextdanny.braincell.base.Easing;
import bottomtextdanny.braincell.mod.world.builtin_particles.BCParticle;
import bottomtextdanny.braincell.mod.world.particle_utilities.local_sprites.SimpleSpriteGroup;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class CursedFlameParticle extends BCParticle {
    public static final ISimpleAnimator<Float> SIZE_ANIMATOR = new FloatAnimator(0.0F);

    protected CursedFlameParticle(ClientLevel world,
                                  double x, double y, double z,
                                  double xSpeed, double ySpeed, double zSpeed,
                                  SpriteSet handleSpriteSet) {
        super(world, x, y, z);
        this.lifetime = 40;
        setSpriteGroup(SimpleSpriteGroup.of(handleSpriteSet, 8));
        setLocalSprite(0);
        setSimpleFrameTicks(1);
        setDefaultSize(0.3F);
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
        this.roll = new Random().nextFloat(360.0F);
    }

    @Override
    public void tick() {
        super.tick();
        this.xd *= 0.95;
        this.yd *= 0.95;
        this.zd *= 0.95;
        this.yd -= 0.005;

        oRoll = roll;
        this.roll += 0.2;
        if (random.nextInt(30) == 0) {
            DannyParticleData childData =
                    DEParticleUtil.dust(true, 0x90FF00, quadSize / 2, 0.95F, 0.9F);

            for (int i = 0; i < 3; i++) {
                double cxd = xd + random.nextGaussian() * 1.35F * xd;
                double cyd = yd + random.nextGaussian() * 1.35F * yd;
                double czd = zd + random.nextGaussian() * 1.35F * zd;

                level.addParticle(childData, x, y, z, cxd, cyd, czd);
            }
            remove();
        }
    }

    @Override
    public void preRender(PoseStack poseStack, VertexConsumer buffer, Camera renderInfo, float partialTicks) {
        SIZE_ANIMATOR.reset();
        SIZE_ANIMATOR.setActual(this.ticksPassed);
        SIZE_ANIMATOR.start(0.0F);
        SIZE_ANIMATOR.addPoint(5.0F, getDefaultSize());
        SIZE_ANIMATOR.addPoint(26.0F, 0.0F);
        this.quadSize = SIZE_ANIMATOR.get(Easing.EASE_OUT_GAMMA, partialTicks);
    }

    @Override
    protected int getLightColor(float tickOffset) {
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
