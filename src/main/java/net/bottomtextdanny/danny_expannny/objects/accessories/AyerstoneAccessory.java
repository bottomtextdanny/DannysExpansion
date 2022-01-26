package net.bottomtextdanny.danny_expannny.objects.accessories;

import net.bottomtextdanny.danny_expannny.object_tables.DEParticles;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.danny_expannny.accessory.AccessoryKey;
import net.bottomtextdanny.braincell.underlying.misc.ObjectFetcher;
import net.bottomtextdanny.danny_expannny.accessory.modules.FinnHit;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.Random;

public class AyerstoneAccessory extends CoreAccessory implements FinnHit {
    private int cooldownHits;
	
	public AyerstoneAccessory(AccessoryKey<?> key, Player player) {
        super(key, player);
    }

    @Override
    public void onMeleeAttack(LivingEntity entity, DamageSource source, MutableObject<Float> amount, float baseValue) {
        if (source.getDirectEntity() == source.getEntity()) {

            if (this.cooldownHits > 0) {
                if (this.random.nextFloat() <= 0.85F) {
                    Vec3 vec0 = DEMath.fromPitchYaw(this.player.getXRot(), this.player.yHeadRot).multiply(0.2, 0.2, 0.2);
                    amount.setValue(amount.getValue() + 3.0F);
                    this.player.level.playSound(null, this.player.getX() + vec0.x, this.player.getEyeY() + vec0.y, this.player.getZ() + vec0.z, DESounds.IS_AYERSTONE_HIT.get(), SoundSource.NEUTRAL, 1.2F, 1.0F + new Random().nextFloat() * 0.1F);

                    triggerClientActionToTracking(0);
                    this.cooldownHits = 0;
                }
            } else {
                this.cooldownHits++;
            }
        }
    }

    @Override
    public void accessoryClientManager(int flag, ObjectFetcher fetcher) {
        super.accessoryClientManager(flag, fetcher);

        if (flag == 0) {
            float yawOffset = -20;

            for (int i = 0; i < 4; i++) {
                Vec3 vec1 = DEMath.fromPitchYaw(this.player.getXRot(), this.player.yHeadRot + yawOffset).multiply(0.4, 0.4, 0.4);

                float xRandom = (float) this.random.nextGaussian() * 0.2F;
                float yRandom = (float) this.random.nextGaussian() * 0.2F;
                float zRandom = (float) this.random.nextGaussian() * 0.2F;

                this.player.level.addParticle(DEParticles.SAND_CLOUD.get(), this.player.getX() + xRandom, this.player.getEyeY() + yRandom, this.player.getZ() + zRandom, vec1.x, vec1.y, vec1.z);

                yawOffset += 10;
            }
        }
    }

    @Override
    public int critModulePriority() {
        return C_ADD_OP;
    }

    @Override
    public int hitModulePriority() {
        return C_ADD_OP;
    }
	
	@Override
	public String getGeneratedDescription() {
		return "5.0% chance of performing a sand attack when attacking a mob directly";
	}
}
