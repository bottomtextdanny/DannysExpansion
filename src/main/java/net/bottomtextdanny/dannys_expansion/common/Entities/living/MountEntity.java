package net.bottomtextdanny.dannys_expansion.common.Entities.living;

import net.bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManager;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityData;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityDataReference;
import net.bottomtextdanny.braincell.mod.entity.serialization.RawEntityDataReference;
import net.bottomtextdanny.braincell.mod.serialization.serializers.builtin.BuiltinSerializers;
import net.bottomtextdanny.braincell.mod.world.builtin_entities.ModuledMob;
import net.bottomtextdanny.dannys_expansion.core.interfaces.entity.IDannyMount;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public abstract class MountEntity extends ModuledMob implements IDannyMount {
    public static final EntityDataReference<Integer> ABILITY_TIMER_REF =
            BCDataManager.attribute(MountEntity.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.INTEGER,
                            () -> 0,
                            "ability_timer")
            );
    private final EntityData<Integer> ability_timer;
    protected int progress;
    protected int prevProgress;
    protected boolean progressIsIncreasing;

    public MountEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        this.ability_timer = bcDataManager().addSyncedData(EntityData.of(ABILITY_TIMER_REF));
    }

    @Override
    public void tick() {
        super.tick();
        this.prevProgress = this.progress;

        if (usesProgressBar()) {
            if (this.progressIsIncreasing) {
                this.progress = Mth.clamp(this.progress + progressAddition(), 0, 1000);
            } else {
                this.progress = Mth.clamp(this.progress + progressSubtraction(), 0, 1000);
            }

            this.progressIsIncreasing = false;
        }

        if (usesAbilityBar()) {
            if (getAbilityTimer() < 10000) {
                setAbilityTimer(Mth.clamp(getAbilityTimer() + abilityTimerAddition(), 0, 10000));
            }
        }
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    public void setRiddenBy(Player player) {
        player.setYRot(this.getYRot());
        player.setXRot(this.getXRot());
        player.startRiding(this);
    }

    @Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    public int progressAddition() {
        return 50;
    }

    public int progressSubtraction() {
        return -progressAddition();
    }

    public int abilityTimerAddition() {
        return 40;
    }

    public boolean progressIncreasingParams() {
        return !progShouldWaitforRestart() || getProgressDifference() > -1;
    }

    @Override
    public void doAct() {

    }

    @Override
    public void doAbility() {
        setAbilityTimer(0);
    }

    public float getProgress01() {
        return (float) this.progress / 1000;
    }

    public float getPrevProgress01() {
        return (float) this.prevProgress / 1000;
    }

    public int getAbilityTimer() {
        return this.ability_timer.get();
    }

    public boolean usesProgressBar() {
        return true;
    }

    public boolean usesAbilityBar() {
        return true;
    }

    public boolean progShouldWaitforRestart() {
        return true;
    }

    public void setAbilityTimer(int i) {
        this.ability_timer.set(i);
    }

    public void setProgressIsIncreasing(boolean progressIsIncreasing) {
        this.progressIsIncreasing = progressIsIncreasing;
    }

    public int getProgressDifference() {
        return this.progress - this.prevProgress;
    }
}
