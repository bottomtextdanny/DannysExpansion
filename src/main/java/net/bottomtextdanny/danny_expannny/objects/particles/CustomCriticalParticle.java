package net.bottomtextdanny.danny_expannny.objects.particles;

import com.google.common.collect.Sets;
import net.bottomtextdanny.braincell.mod.world.builtin_particles.BCParticle;
import net.bottomtextdanny.braincell.mod.world.particle_utilities.local_sprites.SpriteGroupProvider;
import net.bottomtextdanny.dannys_expansion.core.Util.particle.DannyParticleData;
import net.minecraft.Util;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Set;

public class CustomCriticalParticle extends BCParticle {
    public static final int SPRITE_DATA_IDX = 0;
    public static final int FROZEN_IDX = 0, LAVA_IDX = 1, SAND_IDX = 2;
    private static final Set<Integer> LIT_LOOKUP = Util.make(() -> {
        Set<Integer> set = Sets.newIdentityHashSet();
        set.add(LAVA_IDX);
        return set;
    });
    public static final SpriteGroupProvider GROUPS = SpriteGroupProvider.SingularBuilder.create(3)
            .entry(FROZEN_IDX)
            .entry(LAVA_IDX)
            .entry(SAND_IDX)
            .build();
    private final int cachedSpriteIndex;

    protected CustomCriticalParticle(ClientLevel world, double x, double y, double z, double speedX, double speedY, double speedZ, SpriteSet handleSpriteSet, int spriteIndex) {
        super(world, x, y, z);
        this.lifetime = Math.max((int)(6.0D / (Math.random() * 0.8D + 0.6D)), 1);
        this.cachedSpriteIndex = spriteIndex;
        setTicksForEachFrame(this.lifetime);
        setSpriteGroup(GROUPS.get(handleSpriteSet, spriteIndex));
        setLocalSprite(0);
        this.friction = 0.7F;
        this.gravity = 0.5F;
        this.xd *= 0.1F;
        this.yd *= 0.1F;
        this.zd *= 0.1F;
        this.xd += speedX * 0.2D;
        this.yd += speedY * 0.2D;
        this.zd += speedZ * 0.2D;
        this.quadSize *= 0.75F;
        this.hasPhysics = false;
        float f = isLit() ? 1.0F : (float)(Math.random() * (double)0.9F + (double)0.1F);
        this.rCol = f;
        this.gCol = f;
        this.bCol = f;
    }

    @Override
    public void tick() {
        super.tick();
        if (!isLit()) {
            this.gCol = (float) ((double) this.gCol * 0.98D);
            this.bCol = (float) ((double) this.bCol * 0.98D);
        }
    }

    public float getQuadSize(float p_105938_) {
        return this.quadSize * Mth.clamp(((float)this.age + p_105938_) / (float)this.lifetime * 32.0F, 0.0F, 1.0F);
    }

    protected boolean isLit() {
        return LIT_LOOKUP.contains(this.cachedSpriteIndex);
    }

    @Override
    protected int getLightColor(float p_107249_) {
        return isLit() ? 15728880 : super.getLightColor(p_107249_);
    }

    public ParticleRenderType getRenderType() {
        return isLit() ? ParticleRenderType.PARTICLE_SHEET_LIT : ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<DannyParticleData> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet p_i50634_1_) {
            this.spriteSet = p_i50634_1_;
        }

        public Particle createParticle(DannyParticleData typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new CustomCriticalParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet, typeIn.fetch(SPRITE_DATA_IDX));
        }
    }
}
