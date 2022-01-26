package net.bottomtextdanny.dannys_expansion.core.base.pl;

import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.opengl_front.point_lighting.IPointLight;
import net.bottomtextdanny.braincell.mod.opengl_front.point_lighting.SimplePointLight;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.EntityUtil;
import net.bottomtextdanny.danny_expannny.capabilities.CapabilityHelper;
import net.bottomtextdanny.danny_expannny.capabilities.world.LevelCapability;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class PhysicalLight {
	protected IPointLight light;
	private Vec3 position = Vec3.ZERO;
	public double prevX, prevY, prevZ;
	private boolean removed;
	protected final Level world;
	public int ticksExisted;
	public boolean isAreaLoaded;
	
	public PhysicalLight(Level world) {
		super();
		this.world = world;
	}
	
	public void tick() {

        this.isAreaLoaded = true;
		
		if (!this.removed) {

            this.prevX = this.position.x;
            this.prevY = this.position.y;
            this.prevZ = this.position.z;
            this.ticksExisted++;
		}
	}
	
	public void render() {
		if (this.light != null) {
			if (Braincell.client().getRenderingHandler().getClipping().isSphereInFrustum((float) this.position.x, (float) this.position.y, (float) this.position.z, this.light.radius())) {
				Braincell.client().getPostprocessingHandler().getLightingWorkflow().addLight(new SimplePointLight(EntityUtil.easedPos(this, DEUtil.PARTIAL_TICK), this.light.color(), this.light.radius(), this.light.brightness(), this.light.lightupFactor()));
			}
		}
	}
	
	public void processRender() {
		//if (isAreaLoaded) {
			render();
		//}
	}
	
	public void setLight(IPointLight light) {
		this.light = light;
	}
	
	public IPointLight getLight() {
		return this.light;
	}
	
	public void setPosition(Vec3 position) {
		this.position = position;
	}
	
	public double getPosX() {
		return this.position.x;
	}
	
	public double getPosY() {
		return this.position.y;
	}
	
	public double getPosZ() {
		return this.position.z;
	}
	
	public boolean isRemoved() {
		return this.removed;
	}
	
	public void remove() {
		this.removed = true;
	}
	
	public void addToWorld() {
		LevelCapability capability = CapabilityHelper.get(this.world, LevelCapability.CAPABILITY);
		capability.getPhysicalLightModule().add(this);
	}
}
