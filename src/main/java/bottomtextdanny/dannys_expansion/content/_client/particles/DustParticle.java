package bottomtextdanny.dannys_expansion.content._client.particles;

import bottomtextdanny.dannys_expansion._base.particle.DannyParticleData;
import bottomtextdanny.dannys_expansion._util.DEParticleUtil;
import bottomtextdanny.braincell.base.BCMath;
import bottomtextdanny.braincell.mod._base.BCStaticData;
import bottomtextdanny.braincell.mod.world.builtin_particles.BCParticle;
import bottomtextdanny.braincell.mod.world.particle_utilities.local_sprites.SimpleSpriteGroup;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class DustParticle extends BCParticle {
    private final int colorCode;
    private float quadSizeO;
    private boolean lit;
    private float movementReduction;
    private float sizeReduction;

    protected DustParticle(int colorCode, float size, float movementReduction, float sizeReduction,
                           boolean lit, ClientLevel world, double x, double y, double z,
                           double xSpeed, double ySpeed, double zSpeed,
                           SpriteSet handleSpriteSet) {
        super(world, x, y, z);
        this.lit = lit;
        this.movementReduction = movementReduction;
        this.sizeReduction = sizeReduction;
        this.lifetime = 40;
        this.colorCode = colorCode;
        alpha = 1;
        rCol = (colorCode >> 16 & 255) / 255.0F;
        gCol = (colorCode >> 8 & 255) / 255.0F;
        bCol = (colorCode & 255) / 255.0F;
        quadSize = size;
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;

        setSpriteGroup(SimpleSpriteGroup.of(handleSpriteSet, 1));
        setLocalSprite(0);
        setTicksForEachFrame(lifetime);
    }

    @Override
    public void tick() {
        quadSizeO = quadSize;

        super.tick();

        this.xd *= movementReduction;
        this.yd *= movementReduction;
        this.zd *= movementReduction;
        quadSize *= sizeReduction;

        float halfSize = quadSize / (float) BCMath.SQRT_2;

        if (halfSize > 0.007F && random.nextFloat() < 0.008F) {
            DannyParticleData childData =
                    DEParticleUtil.dust(lit, colorCode, halfSize, movementReduction, sizeReduction);

            for (int i = 0; i < 2; i++) {
                double cxd = xd + random.nextGaussian() * 0.15F * xd;
                double cyd = yd + random.nextGaussian() * 0.15F * yd;
                double czd = zd + random.nextGaussian() * 0.15F * zd;

                level.addParticle(childData, x, y, z, cxd, cyd, czd);
            }

            remove();
        }

        if (quadSize < 0.008F) {
            remove();
        }
    }

    @Override
    protected int getLightColor(float p_107249_) {
        return isLit() ? 15728880 : super.getLightColor(p_107249_);
    }

    public ParticleRenderType getRenderType() {
        return isLit() ? ParticleRenderType.PARTICLE_SHEET_LIT : ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public float getQuadSize(float idk) {
        return Mth.lerp(BCStaticData.partialTick(), quadSizeO, quadSize);
    }

    protected boolean isLit() {
        return lit;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<DannyParticleData> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet p_i50634_1_) {
            this.spriteSet = p_i50634_1_;
        }

        @Nullable
        @Override
        public Particle createParticle(DannyParticleData typeIn, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            List<Object> data = typeIn.getData();
            return new DustParticle((int) data.get(0), (float)data.get(1), (float)data.get(2), (float)data.get(3), (boolean)data.get(4), level, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}
