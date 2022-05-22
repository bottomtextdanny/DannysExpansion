package bottomtextdanny.dannys_expansion.content.accessories;

import bottomtextdanny.dannys_expansion.tables.DESounds;
import bottomtextdanny.dannys_expansion._util.DEMath;
import bottomtextdanny.braincell.base.ObjectFetcher;
import bottomtextdanny.braincell.mod.capability.player.accessory.*;
import bottomtextdanny.braincell.mod.capability.player.accessory.extensions.FinnFall;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.mutable.MutableFloat;

public class _EndBalloonAccessory extends CoreAccessory implements FinnFall, IJumpQueuerAccessory {
	public final QueuedJump endBalloonImpulse = new QueuedJump(this, JumpPriority.INSTANT);
	
    public _EndBalloonAccessory(AccessoryKey<?> key, Player player) {
        super(key, player);
    }
	
	@Override
    public void tick() {
        super.tick();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void accessoryClientManager(int flag, ObjectFetcher fetcher) {
        if (flag == 0) {
       //     this.player.level.addParticle(new DannyParticleData(DEParticles.TUMEFEND_HOP), this.player.getX(), this.player.getY(), this.player.getZ(), 0D, -0.1D, 0D);
        } else if (flag == 1) {
        	if (this.player instanceof LocalPlayer) {
        		
		        Vec3 mot = this.player.getDeltaMovement();
		        Vec3 prevPos = new Vec3(this.player.xOld, 0, this.player.zOld);
		        Vec3 pos = this.player.position();
		
		        double dist = DEMath.getHorizontalDistance(prevPos.x, prevPos.z, pos.x, pos.z);
		        Vec3 look = DEMath.fromPitchYaw(0, DEMath.getTargetYaw(prevPos.x, prevPos.z, pos.x, pos.z));
                this.player.setDeltaMovement(mot.x + look.x * Math.min(1, dist), Math.max(0.8, mot.y), mot.z + look.z * Math.min(1, dist));
                this.player.hasImpulse = true;
	        }
        }
    }

    @Override
    public void fallDamageMultModifier(float baseDistance, MutableFloat fallDamage, MutableFloat fallDistance) {
        fallDistance.setValue(Math.max(fallDistance.getValue() - 4.5F, 0.0F));
    }

    @Override
    public int fallModificationPriority() {
        return FALL_PRIORITY_MED;
    }

    @Override
    public boolean canPerformJump(IQueuedJump jumpType) {
        return !this.endBalloonImpulse.isUsed() && !this.player.isVisuallySwimming();
    }

    @Override
    public void performJump(IQueuedJump jumpType) {
        if (!this.player.level.isClientSide()) {
	        triggerClientActionToTracking(0);
	        triggerClientActionSpecific(1, (ServerPlayer) this.player);
        }

        this.player.level.playSound(null, this.player.getX(), this.player.getEyeY(), this.player.getZ(), DESounds.ES_TUMEFEND_HOP.get(), SoundSource.NEUTRAL, 0.7F, 1.0F + this.player.getRandom().nextFloat() * 0.1F);
        this.player.hasImpulse = true;
        this.endBalloonImpulse.use();
    }


    @Override
    public IQueuedJump[] provideJumps() {
        return new IQueuedJump[]{this.endBalloonImpulse};
    }
	
	@Override
	public String getGeneratedDescription() {
		return "Allows the holder an extra jump.";
	}
}
