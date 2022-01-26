package net.bottomtextdanny.danny_expannny.objects.particles;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.Map;

public abstract class DannyParticle extends TextureSheetParticle {
	private Map<Integer, Integer> AGE_BY_SPRITE;
    public static float radian = (float) Math.PI / 180;
    protected int lifeTime;
    protected SpriteSet handleSpriteSet;
    protected int[] frameTicks;
    protected boolean collidedY;


    public static final ParticleRenderType PARTICLE_TRANSLUCENT_NO_DEPTH = new ParticleRenderType() {

        @Override
        public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {
           // RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);


            textureManager.bindForSetup(TextureAtlas.LOCATION_PARTICLES);
            textureManager.getTexture(TextureAtlas.LOCATION_PARTICLES);
            bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
        }

        @Override
        public void end(Tesselator tessellator) {
            tessellator.end();
            RenderSystem.enableDepthTest();
            Minecraft.getInstance().textureManager.getTexture(TextureAtlas.LOCATION_PARTICLES);
            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);
        }

        @Override
        public String toString() {
            return DannysExpansion.ID + ":" + "particle_translucent_no_depth";
        }
    };

    protected DannyParticle(ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet handleSpriteSet) {
        super(world, x, y, z, 0.0D, 0.0D, 0.0D);
        this.rCol = 1.0F;
        this.gCol = 1.0F;
        this.bCol = 1.0F;
        this.handleSpriteSet = handleSpriteSet;
    }

    public void tick() {
        this.lifeTime++;

        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.age <= this.lifetime) {
            if (getFrameTicks()[this.age] > 0) {
	            getFrameTicks()[this.age]--;
	            
            } else {
                this.age++;
	            if (this.age <= this.lifetime) {
		            this.setSpriteFromAge(this.handleSpriteSet);
	            }
            }
        } else this.remove();


        this.move(this.xd, this.yd, this.zd);
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
	
	
	    this.collidedY = Math.abs(d1) >= (double) 1.0E-5F && Math.abs(y) < (double) 1.0E-5F;
	
	    this.onGround = d1 != y && d1 < 0.0D;
	    if (d0 != x) {
		    this.xd = 0.0D;
	    }
	
	    if (d2 != z) {
		    this.zd = 0.0D;
	    }
    }

    public double posX() {
        return this.x;
    }

    public double posY() {
        return this.y;
    }

    public double posZ() {
        return this.z;
    }

    public double prevPosX() {
        return this.xo;
    }

    public double prevPosY() {
        return this.yo;
    }

    public double prevPosZ() {
        return this.zo;
    }

    public int[] getFrameTicks() {
        return this.frameTicks;
    }

    public void setFrameTicks(int... ticks) {
        int[] frameTicks = new int[this.lifetime + 1];

        for (int i = 0; i <= this.lifetime; i++) {
            frameTicks[i] = ticks[i];
        }

        this.frameTicks = frameTicks;
    }

    public void setSimpleFrameTicks() {
        int[] frameTicks = new int[this.lifetime + 1];
	    frameTicks[0] = 0;
	    
        for (int i = 1; i <= this.lifetime; i++) {
            frameTicks[i] = 0;
        }
	    this.frameTicks = frameTicks;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }
}
