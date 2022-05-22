package bottomtextdanny.dannys_expansion.content._client.particles;

import bottomtextdanny.dannys_expansion._base.animation.FloatAnimator;
import bottomtextdanny.dannys_expansion._base.animation.ISimpleAnimator;
import bottomtextdanny.dannys_expansion._base.particle.DannyParticleData;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SquigBubbleParticle extends BCParticle {
	private final int deltaLife;
	private final float baseSize;

    protected SquigBubbleParticle(ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet handleSpriteSet) {
        super(world, x, y, z);
        lifetime = 1;
        this.deltaLife = this.random.nextInt(50);
        setSpriteGroup(SimpleSpriteGroup.of(handleSpriteSet, 1));
        setLocalSprite(0);
        setTicksForEachFrame(50 + this.deltaLife);
        this.baseSize = 1.0F + this.random.nextFloat() * 0.3F;
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
    }

    @Override
    public void preRender(PoseStack poseStack, VertexConsumer buffer, Camera renderInfo, float partialTicks) {
        ISimpleAnimator<Float> animator = new FloatAnimator(this.ticksPassed + partialTicks);
        
        animator.start(1.0F);
        animator.addPoint(this.deltaLife, 1.0F);
        animator.addPoint(30.0F, 0.0F);

        this.alpha = animator.get(Easing.EASE_OUT_GAMMA, partialTicks);

        animator.reset(0.0F);
	    animator.addPoint(5.0F, 1.0F);
	    animator.addPoint(this.deltaLife, 1.0F);
	    animator.addPoint(25.0F, 0.0F);
        this.quadSize = animator.get(Easing.LINEAR, partialTicks) * 0.2F * this.baseSize;
    }
    
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<DannyParticleData> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet p_i50634_1_) {
            this.spriteSet = p_i50634_1_;
        }

        public Particle createParticle(DannyParticleData typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new SquigBubbleParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}
