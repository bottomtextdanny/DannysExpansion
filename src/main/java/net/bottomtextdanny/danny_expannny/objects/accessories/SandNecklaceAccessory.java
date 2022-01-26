package net.bottomtextdanny.danny_expannny.objects.accessories;

import net.bottomtextdanny.danny_expannny.object_tables.DEEntities;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.MummySoulEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.Timer;
import net.bottomtextdanny.danny_expannny.accessory.AccessoryKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class SandNecklaceAccessory extends CoreAccessory {
    private Timer soulTimer;

    public SandNecklaceAccessory(AccessoryKey<?> key, Player player) {
        super(key, player);
    }

    @Override
    public void prepare(int index) {
        super.prepare(index);
        this.soulTimer = new Timer(120);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.soulTimer.hasEnded()) {
            List<Mob> list = this.player.level.getEntitiesOfClass(Mob.class, this.player.getBoundingBox().inflate(0.7));
            if (!list.isEmpty()) {
                Mob mobEntity = list.get(0);
                if (mobEntity.getTarget() == this.player) {
                    MummySoulEntity mummySoulEntity = new MummySoulEntity(DEEntities.MUMMY_SOUL.get(), this.player.level);
                    float yaw = DEMath.getTargetYaw(this.player, mobEntity);

                    float f0 = DEMath.sin(yaw * ((float) Math.PI / 180F));
                    float f1 = DEMath.cos(yaw * ((float) Math.PI / 180F));
                    float f2 = DEMath.sin((Mth.wrapDegrees(yaw) + -90) * ((float) Math.PI / 180F));
                    float f3 = DEMath.cos((Mth.wrapDegrees(yaw) + -90) * ((float) Math.PI / 180F));

                    mummySoulEntity.absMoveTo(this.player.getX() - 0.9 * -f0 - 0.5 * -f2, this.player.getY() + 1, this.player.getZ() - 0.9 * f1 - 0.5 * f3, yaw, 0);
                    mummySoulEntity.setCaster(this.player);
                    mummySoulEntity.setPlayerMode(true);
                    this.player.level.addFreshEntity(mummySoulEntity);
                    this.soulTimer.reset();
                }
            }
        } else {
            this.soulTimer.tryUp();
        }
    }
}
