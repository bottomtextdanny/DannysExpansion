package net.bottomtextdanny.danny_expannny.objects.accessories;

import net.bottomtextdanny.braincell.mod.opengl_front.point_lighting.SimplePointLight;
import net.bottomtextdanny.braincell.underlying.misc.ObjectFetcher;
import net.bottomtextdanny.danny_expannny.accessory.*;
import net.bottomtextdanny.danny_expannny.object_tables.DEParticles;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.core.Packets.DENetwork;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.Timer;
import net.bottomtextdanny.dannys_expansion.core.Util.particle.DannyParticleData;
import net.bottomtextdanny.dannys_expansion.core.base.pl.ShootLight;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class EbbewelAccessory extends CoreAccessory implements IJumpQueuerAccessory {
    private final IQueuedJump ebbewelImpulses = new QueuedJump(this, JumpPriority.LARGE);
    private byte impulsesUsed = 3;
    private final Timer cooldown = new Timer(60);

    public EbbewelAccessory(AccessoryKey<?> key, Player player) {
        super(key, player);
    }

    @Override
    public void prepare(int index) {
        super.prepare(index);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.impulsesUsed > 0 && this.player.level.getFluidState(new BlockPos(this.player.getX(), Mth.floor(this.player.getEyeY()), this.player.getZ())).isEmpty()) {
            this.cooldown.tryUp();
            if (this.cooldown.hasEnded()) {
                this.cooldown.reset();
                this.impulsesUsed = 0;
            }
        }
    }

    @Override
    public void accessoryClientManager(int flag, ObjectFetcher fetcher) {
        super.accessoryClientManager(flag, fetcher);
        if (flag == 0) {
            Vec3 vec0 = DEMath.fromPitchYaw(this.player.getXRot(), this.player.yHeadRot).multiply(0.1, 0.1, 0.1);

            for (int i = 0; i < 8; i++) {
                float xRandom = (float) this.random.nextGaussian() * 0.5F;
                float yRandom = (float) this.random.nextGaussian() * 0.5F;
                float zRandom = (float) this.random.nextGaussian() * 0.5F;

                this.player.level.addParticle(DEParticles.EBBEWEL_SPARK.get(), this.player.getX() + xRandom, this.player.getY() + yRandom, this.player.getZ() + zRandom, -vec0.x, -vec0.y, -vec0.z);
            }

            for (int i = 0; i < 5; i++) {
                float xRandom = (float) this.random.nextGaussian() * 0.4F;
                float yRandom = (float) this.random.nextGaussian() * 0.4F;
                float zRandom = (float) this.random.nextGaussian() * 0.4F;

                this.player.level.addParticle(DEParticles.EBBEWEL_CLOUD.get(), this.player.getX() + xRandom, this.player.getY() + yRandom, this.player.getZ() + zRandom, -vec0.x * 0.6, -vec0.y * 0.6, -vec0.z * 0.6);
            }

            this.player.level.addParticle(new DannyParticleData(DEParticles.EBBEWEL_RING, this.player.getYRot(), this.player.getXRot()), this.player.getX(), this.player.getY() + this.player.getBoundingBox().getYsize() / 2, this.player.getZ(), -vec0.x * 0.1, -vec0.y * 0.1, -vec0.z * 0.1);

            ShootLight light = new ShootLight(this.player.level, 2, 1, 20);
            light.setPosition(this.player.position());
            light.setLight(new SimplePointLight(new Vec3(0.0, 0.8, 1.0), 4.0F, 2.3F, 1.6F));
            light.addToWorld();
        }
    }

    @Override
    public boolean canPerformJump(IQueuedJump jumpType) {
        return this.player.isVisuallySwimming() && this.impulsesUsed < 3;
    }

    @Override
    public void performJump(IQueuedJump jumpType) {
        Vec3 vec0 = DEMath.fromPitchYaw(this.player.getXRot(), this.player.yHeadRot);
        this.player.push(vec0.x, vec0.y, vec0.z);
        DENetwork.sendPlayerVelocityPacket(this.player);

        this.player.level.playSound(null, this.player.getX(), this.player.getEyeY(), this.player.getZ(), DESounds.IS_EBBEWEL_IMPULSE.get(), SoundSource.NEUTRAL, 1.0F, 1.0F + new Random().nextFloat() * 0.1F);

        triggerClientActionToTracking(0);
        this.impulsesUsed++;
    }

    @Override
    public IQueuedJump[] provideJumps() {
        return new IQueuedJump[]{this.ebbewelImpulses};
    }
	
	@Override
	public String getGeneratedDescription() {
		return "Provides the holder with 3 small impulses when swimming, when the holder uses at least one impulse they can wait 3 seconds out of water to recharge them fully";
	}
}
