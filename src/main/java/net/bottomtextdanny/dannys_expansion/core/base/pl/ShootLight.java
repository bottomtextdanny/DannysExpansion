package net.bottomtextdanny.dannys_expansion.core.base.pl;

import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.opengl_front.point_lighting.SimplePointLight;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.EntityUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShootLight extends PhysicalLight {
	private final int startTicks, staticTicks, endTicks;

	private float alphaScale, prevAlphaScale;
	
	public ShootLight(Level world, int startTicks, int staticTicks, int endTicks) {
		super(world);
		this.startTicks = startTicks;
		this.staticTicks = staticTicks;
		this.endTicks = endTicks;
	}
	
	@Override
	public void tick() {
		super.tick();
        this.prevAlphaScale = this.alphaScale;

		if (this.ticksExisted < this.startTicks) {
            this.alphaScale = (float) this.ticksExisted / (float) this.startTicks;
		} else if (this.ticksExisted < this.staticTicks + this.startTicks) {
            this.alphaScale = 1.0F;
		} else {
            this.alphaScale = 1.0F - (float)(this.ticksExisted - this.startTicks - this.staticTicks) / (float) this.endTicks;
		}
	}
	
	@Override
	public void render() {
		if (this.light != null) {
			
			float alphaLrp = Mth.lerp(DEUtil.PARTIAL_TICK, this.prevAlphaScale, this.alphaScale);
			if (alphaLrp > 0.0F) {
				
			
				if (Braincell.client().getRenderingHandler().getClipping().isSphereInFrustum((float) getPosX(), (float) getPosY(), (float) getPosZ(), this.light.radius() * alphaLrp)) {
					Braincell.client().getPostprocessingHandler().getLightingWorkflow().addLight(new SimplePointLight(EntityUtil.easedPos(this, DEUtil.PARTIAL_TICK), this.light.color(), this.light.radius() * alphaLrp, this.light.brightness() * alphaLrp, this.light.lightupFactor() * alphaLrp));
				}
			}
		}
	}
}
