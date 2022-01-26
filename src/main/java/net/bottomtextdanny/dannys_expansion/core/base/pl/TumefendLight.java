package net.bottomtextdanny.dannys_expansion.core.base.pl;

import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.opengl_front.point_lighting.SimplePointLight;
import net.bottomtextdanny.braincell.mod.structure.BCStaticData;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.EntityUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Predicate;

@OnlyIn(Dist.CLIENT)
public class TumefendLight extends PhysicalLight {
	private final Predicate<PhysicalLight> killPredicate;
	private final int tickEasing;
	private int ticksAfterDeath;
	private boolean killed;
	private float alphaScale, prevAlphaScale;
	
	public TumefendLight(Level world, Predicate<PhysicalLight> pred, int tickEasing) {
		super(world);
		this.tickEasing = tickEasing;
		this.killPredicate = pred;
	}
	
	@Override
	public void tick() {
		super.tick();
        this.prevAlphaScale = this.alphaScale;
		
		if (!this.killed && this.killPredicate.test(this)) {
            this.killed = true;
		}
		
		
		if (this.ticksExisted < this.tickEasing) {
            this.alphaScale = (float) this.ticksExisted / (float) this.tickEasing;
		} else if (this.killed) {
            this.ticksAfterDeath++;
			
			if (this.ticksAfterDeath < this.tickEasing) {
                this.alphaScale = 1.0F - (float) this.ticksAfterDeath / (float) this.tickEasing;
			} else {
				remove();
			}
		} else {
            this.alphaScale = 1.0F;
		}
	}
	
	@Override
	public void render() {
		if (this.light != null) {
			
			float alphaLrp = Mth.lerp(BCStaticData.partialTick(), this.prevAlphaScale, this.alphaScale);
			if (alphaLrp > 0.0F) {
				float sinFactor = Math.min(DEMath.sin((this.ticksExisted + net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil.PARTIAL_TICK) * 0.03) + 0.1F, 1.0F);
				
				if (sinFactor > 0.0F) {
					if (Braincell.client().getRenderingHandler().getClipping().isSphereInFrustum(
									(float) getPosX(), (float) getPosY(), (float) getPosZ(),
									this.light.radius() * alphaLrp)) {
						Braincell.client().getPostprocessingHandler().getLightingWorkflow().addLight(
								new SimplePointLight(EntityUtil.easedPos(this, BCStaticData.partialTick()),
										this.light.color(),
										this.light.radius() * alphaLrp,
										this.light.brightness() * alphaLrp * sinFactor,
										this.light.lightupFactor() * alphaLrp * sinFactor));
					}
				}
			}
		}
	}
}
