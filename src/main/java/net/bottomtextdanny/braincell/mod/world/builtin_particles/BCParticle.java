package net.bottomtextdanny.braincell.mod.world.builtin_particles;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.bottomtextdanny.braincell.underlying.util.BCMath;
import net.bottomtextdanny.braincell.mod.world.particle_utilities.local_sprites.SpriteGroup;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public abstract class BCParticle extends TextureSheetParticle {
    public static final float RADIAN = BCMath.FRAD;
    protected int maxTime;
    protected int ticksPassed;
    protected SpriteGroup sprites;
	private int compressedTime = -1;
    protected int[] ticksForEachFrame;
    protected boolean onCeiling;
    public float prevYaw;
    public float yaw;
    public float prevPitch;
    public float pitch;

    protected BCParticle(ClientLevel world, double x, double y, double z) {
        super(world, x, y, z, 0.0D, 0.0D, 0.0D);
        this.rCol = 1.0F;
        this.gCol = 1.0F;
        this.bCol = 1.0F;
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        this.prevPitch = this.pitch;
        this.prevYaw = this.yaw;

        this.ticksPassed++;
        if (!isCompressed()) {
	        if (this.age < this.lifetime) {
		        getTicksForEachFrame()[this.age]--;
		        if (getTicksForEachFrame()[this.age] <= 0) {
                    this.age++;
                    if (this.age < this.lifetime) {
                        setLocalSprite(this.age);
                    }
		        }
	        } else {
		        remove();
	        }
        } else if (this.age >= this.lifetime) {
	        remove();
        }

        move(this.xd, this.yd, this.zd);
    }

    @Override
    public void move(double x, double y, double z) {
        double d0 = x;
        double d1 = y;
        double d2 = z;
        if (this.hasPhysics && (x != 0.0D || y != 0.0D || z != 0.0D)) {
	        Vec3 vector3d = Entity.collideBoundingBox(null, new Vec3(x, y, z), this.getBoundingBox(), this.level, List.of());
	        x = vector3d.x;
            y = vector3d.y;
            z = vector3d.z;
        }

        if (x != 0.0D || y != 0.0D || z != 0.0D) {
            this.setBoundingBox(this.getBoundingBox().move(x, y, z));
            this.setLocationFromBoundingbox();
        }

        this.onCeiling = d1 != y && d1 > 0.0D;

        this.onGround = d1 != y && d1 < 0.0D;
        if (d0 != x) {
            this.xd = 0.0D;
        }

        if (d2 != z) {
            this.zd = 0.0D;
        }
    }
    
    public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
    	if (isCompressed() && this.age < this.lifetime) {
    		final float div = (float) this.maxTime / this.compressedTime;
    		int previousAge = this.age;
            this.age = (int)Math.min(Math.min((this.ticksPassed + partialTicks) * div, this.maxTime), this.lifetime);

            if (this.age != previousAge) {
    			if (this.age < this.lifetime) {
                    getTicksForEachFrame()[this.age]--;
                    if (getTicksForEachFrame()[this.age] <= 1) {
                        this.age++;
                        if (this.age < this.lifetime) {
                            setLocalSprite(this.age);
                        }
                    }
			    } else {
                    this.quadSize = 0.0F;
				    remove();
			    }
		    }
	    }

        PoseStack poseStack = new PoseStack();

        handleRenderRotations(partialTicks, renderInfo);
        Vec3 vector3d = renderInfo.getPosition();
        float f = (float)(Mth.lerp(partialTicks, this.xo, this.x) - vector3d.x());
        float f1 = (float)(Mth.lerp(partialTicks, this.yo, this.y) - vector3d.y());
        float f2 = (float)(Mth.lerp(partialTicks, this.zo, this.z) - vector3d.z());
        poseStack.translate(f, f1, f2);

        poseStack.mulPose(Vector3f.YP.rotationDegrees(-this.yaw));
        poseStack.mulPose(Vector3f.XP.rotationDegrees(this.pitch));

        if (this.roll != 0.0F) {
            float roll = Mth.lerp(partialTicks, this.oRoll, this.roll);
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(roll));
        }

        preRender(poseStack, buffer, renderInfo, partialTicks);

        int j = this.getLightColor(partialTicks);

        Vector3f[] avector3f = {
                new Vector3f(-1.0F, -1.0F, 0.0F),
                new Vector3f(-1.0F, 1.0F, 0.0F),
                new Vector3f(1.0F, 1.0F, 0.0F),
                new Vector3f(1.0F, -1.0F, 0.0F)};
        Vector3f[] avector3f1 = {
                new Vector3f(-1.0F, -1.0F, 0.0F),
                new Vector3f(-1.0F, -1.0F, 0.0F),
                new Vector3f(1.0F, 1.0F, 0.0F),
                new Vector3f(1.0F, 1.0F, 0.0F)};
        float f4 = getQuadSize(partialTicks);

        for(int i = 0; i < 4; ++i) {
            Vector3f vector3f = avector3f[i];
            vector3f.transform(poseStack.last().normal());
            vector3f.mul(f4);
            vector3f.add(f, f1, f2);

            Vector3f vector3f0 = avector3f1[i];
            vector3f0.transform(poseStack.last().normal());
            vector3f0.mul(f4);
            vector3f0.add(f, f1, f2);
        }

        float minU = this.getU0();
        float maxU = this.getU1();
        float minV = this.getV0();
        float maxV = this.getV1();

        buffer.vertex(avector3f[0].x(), avector3f[0].y(), avector3f[0].z()).uv(maxU, maxV).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
        buffer.vertex(avector3f[1].x(), avector3f[1].y(), avector3f[1].z()).uv(maxU, minV).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
        buffer.vertex(avector3f[2].x(), avector3f[2].y(), avector3f[2].z()).uv(minU, minV).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
        buffer.vertex(avector3f[3].x(), avector3f[3].y(), avector3f[3].z()).uv(minU, maxV).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();

        buffer.vertex(avector3f[3].x(), avector3f[3].y(), avector3f[3].z()).uv(minU, maxV).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
        buffer.vertex(avector3f[2].x(), avector3f[2].y(), avector3f[2].z()).uv(minU, minV).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
        buffer.vertex(avector3f[1].x(), avector3f[1].y(), avector3f[1].z()).uv(maxU, minV).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
        buffer.vertex(avector3f[0].x(), avector3f[0].y(), avector3f[0].z()).uv(maxU, maxV).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
    }

    public void preRender(PoseStack poseStack, VertexConsumer buffer, Camera renderInfo, float partialTicks) {}

    public void handleRenderRotations(float partialTicks, Camera info) {
        this.yaw = this.prevYaw = info.getYRot();
        this.pitch = this.prevPitch = info.getXRot();
    }

    public final void setSpriteGroup(SpriteGroup group) {
        this.sprites = group;
    }

    @Override
    public final void setSpriteFromAge(SpriteSet spriteSet) {
        super.setSpriteFromAge(spriteSet);
    }

    protected final void setLocalSprite(int index) {
        this.sprite = this.sprites.fetch(index);
    }

    public void setTicksForEachFrame(int... ticks) {
        this.lifetime = Math.max(ticks.length, 1);
        int[] frameTicks = new int[this.lifetime];

        for (int i = 0; i < this.lifetime; i++) {
            frameTicks[i] = ticks[i];
            this.maxTime += ticks[i];
        }

        this.ticksForEachFrame = frameTicks;
    }

    public void setSimpleFrameTicks() {
        int[] frameTicks = new int[this.lifetime];

        for (int i = 0; i < this.lifetime; i++) {
            frameTicks[i] = 0;
            this.maxTime++;
        }

        this.ticksForEachFrame = frameTicks;
    }

    protected void setCompressed(int toTicks) {
        this.compressedTime = toTicks;
    }

    public int[] getTicksForEachFrame() {
        return this.ticksForEachFrame;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    private boolean isCompressed() {
        return this.compressedTime > 0;
    }

    @Override
    public boolean shouldCull() {
        return true;
    }
}
