package bottomtextdanny.dannys_expansion._util;

import bottomtextdanny.braincell.mod.world.builtin_entities.ModuledMob;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

@Deprecated
public class EntityUtil {

    public static void particleAt(Level world, ParticleOptions type, int particleCount, double x, double y, double z, double xOffset, double yOffset, double zOffset, double speed) {
        if(world instanceof ServerLevel) {
            ((ServerLevel) world).sendParticles(type, x, y, z, particleCount, xOffset, yOffset, zOffset, speed);
        }
    }

    public static Vec3 easedPos(Entity entity, float partialTicks) {
        double easedX = Mth.lerp(partialTicks, entity.xo, entity.getX());
        double easedY = Mth.lerp(partialTicks, entity.yo, entity.getY());
        double easedZ = Mth.lerp(partialTicks, entity.zo, entity.getZ());

        return new Vec3(easedX, easedY, easedZ);
    }

    public static void playEyeSound(Entity player, SoundEvent sound, float volume, float pitch) {
	    player.level.playSound(null, player.getX(), player.getY(), player.getZ(), sound, SoundSource.PLAYERS, volume, pitch);
    }

    public static void postDragonEndCreatureTick(ModuledMob entity) {
        entity.setRemainingFireTicks(entity.getRemainingFireTicks() - 1);
    }
}
