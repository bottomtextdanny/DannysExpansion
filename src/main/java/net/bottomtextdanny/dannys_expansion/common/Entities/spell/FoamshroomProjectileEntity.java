package net.bottomtextdanny.dannys_expansion.common.Entities.spell;

import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationHandler;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.IAnimation;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.Animation;
import net.bottomtextdanny.danny_expannny.object_tables.DEEntities;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.FoamieEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FoamshroomProjectileEntity extends SpellEntity {
	public final Animation explode = addAnimation(new Animation(10));
    private float prevRenderRotPitch;
    private float renderRotPitch;

    public FoamshroomProjectileEntity(EntityType<? extends SpellEntity> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
        setLifeTime(400);
    }

    @Override
    public void tick() {
        super.tick();

        push(0, -0.2F, 0);

        float movement = DEMath.getDistance(this.xOld, this.yOld, this.zOld, getX(), getY(), getZ());
        this.prevRenderRotPitch = this.renderRotPitch;
	    if (movement > 0.0F) {
	        setXRot(DEMath.getTargetPitch(this.xOld, this.yOld, this.zOld, getX(), getY(), getZ()));
            this.renderRotPitch = (float)Mth.lerp(0.2, this.renderRotPitch, getXRot());
        }
	    
	    if (!this.level.isClientSide && this.mainHandler.isPlaying(this.explode) && this.mainHandler.getTick() == 8) {
		    FoamieEntity foamie = new FoamieEntity(DEEntities.FOAMIE.get(), this.level);
		    Vec3 vec = position();
		    playSound(DESounds.ES_FOAMSHROOM_POP.get(), 1.0F, 1.0F + 0.1F * this.random.nextInt(3));
		//    world.playSound(vec.x, vec.y, vec.z, DannySounds.ES_FOAMSHROOM_POP, SoundSource.HOSTILE, 1.0F, 1.0F + 0.1F * rand.nextInt(3), false);
		    foamie.absMoveTo(vec.x, vec.y, vec.z, getYRot() + 180.0F, 0.0F);
            this.level.addFreshEntity(foamie);
		    remove(RemovalReason.DISCARDED);
	    }
    }
	
	@Override
	public void animationEndCallout(AnimationHandler<?> module, IAnimation animation) {
		if (animation == this.explode) {
			remove(RemovalReason.DISCARDED);
		}
	}
	
	@Override
    protected void onBlockHit(BlockHitResult result) {
        super.onBlockHit(result);
        if (this.mainHandler.isPlayingNull()) {
            this.mainHandler.play(this.explode);
        }
        setDeltaMovement(0.0D, getDeltaMovement().y, 0.0D);
    }

    @Override
    protected void onEntityHit(EntityHitResult result) {
        super.onEntityHit(result);
	    if (this.mainHandler.isPlayingNull()) {
            this.mainHandler.play(this.explode);
	    }
    }

    @OnlyIn(Dist.CLIENT)
    public float getRenderRotPitch(float pct) {
        return Mth.lerp(pct, this.prevRenderRotPitch, this.renderRotPitch);
    }
}
