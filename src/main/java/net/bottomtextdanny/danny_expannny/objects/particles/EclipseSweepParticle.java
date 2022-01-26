package net.bottomtextdanny.danny_expannny.objects.particles;

import net.bottomtextdanny.braincell.mod.world.builtin_particles.BCParticle;
import net.bottomtextdanny.braincell.mod.world.particle_utilities.local_sprites.SimpleSpriteGroup;
import net.bottomtextdanny.dannys_expansion.core.Util.particle.DannyParticleData;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class EclipseSweepParticle extends BCParticle {

    protected EclipseSweepParticle(ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, float yaw, SpriteSet handleSpriteSet) {
        super(world, x, y, z);
        this.lifetime = 8;
        setSimpleFrameTicks();
        setSpriteGroup(SimpleSpriteGroup.of(handleSpriteSet, 8));
        setLocalSprite(0);
        setCompressed(4);
        this.quadSize = 1.2F;
	    this.xd = xSpeed;
	    this.yd = ySpeed;
	    this.zd = zSpeed;
	    this.pitch = 90.0F;
	    this.yaw = yaw;
    }
	
	@Override
	public void handleRenderRotations(float partialTicks, Camera info) {
	}
	
	public int getLightColor(float partialTick) {
		return 15728880;
	}
	
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_LIT;
	}

    @Override
    public boolean shouldCull() {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<DannyParticleData> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet p_i50634_1_) {
            this.spriteSet = p_i50634_1_;
        }

        @Nullable
        @Override
        public Particle createParticle(DannyParticleData typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new EclipseSweepParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, (float)typeIn.getData().get(0), this.spriteSet);
        }
    }
}
