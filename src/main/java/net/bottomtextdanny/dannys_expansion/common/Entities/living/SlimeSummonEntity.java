package net.bottomtextdanny.dannys_expansion.common.Entities.living;

import net.bottomtextdanny.braincell.mod.base.misc.timer.IntScheduler;
import net.bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManager;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityData;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityDataReference;
import net.bottomtextdanny.braincell.mod.entity.serialization.RawEntityDataReference;
import net.bottomtextdanny.braincell.mod.serialization.serializers.builtin.BuiltinSerializers;
import net.bottomtextdanny.braincell.mod.serialization.serializers.braincell.BCSerializers;
import net.bottomtextdanny.braincell.mod.world.builtin_entities.ModuledMob;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.AbstractSlime;
import net.bottomtextdanny.braincell.underlying.misc.ObjectFetcher;
import net.bottomtextdanny.danny_expannny.objects.entities.SummonEntity;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public abstract class SlimeSummonEntity extends AbstractSlime implements SummonEntity {
    public static final int DEFAULT_MAX_LIFE_TICKS = 100;
    public static final int DEFAULT_USELESS_TIME_BOUND = 100;
    public static final EntityDataReference<IntScheduler.Simple> USELESS_TIMER_REF =
            BCDataManager.attribute(SlimeSummonEntity.class,
                    RawEntityDataReference.of(
                            BCSerializers.INT_SCHEDULER,
                            () -> IntScheduler.simple(DEFAULT_USELESS_TIME_BOUND),
                            "useless_timer")
            );
    public static final EntityDataReference<Entity> SUMMONER_REF =
            BCDataManager.attribute(SlimeSummonEntity.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.ENTITY_REFERENCE,
                            () -> null,
                            "summoner")
            );
    public static final EntityDataReference<Integer> LIFE_TICKS_REF =
            BCDataManager.attribute(SlimeSummonEntity.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.INTEGER,
                            () -> 0,
                            "life_ticks")
            );
    public static final EntityDataReference<Integer> MAX_LIFE_TICKS_REF =
            BCDataManager.attribute(SlimeSummonEntity.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.INTEGER,
                            () -> DEFAULT_MAX_LIFE_TICKS,
                            "max_life_ticks")
            );
    public static final EntityDataReference<Boolean> TAMED_REF =
            BCDataManager.attribute(SlimeSummonEntity.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.BOOLEAN,
                            () -> false,
                            "tamed")
            );
    protected final EntityData<Entity> summoner;
    protected final EntityData<Boolean> tamed;
    protected final EntityData<Integer> life_ticks;
    protected final EntityData<Integer> max_life_ticks;
    protected final EntityData<IntScheduler.Simple> useless_timer; 
    public ModuledMob summonerEntity;


    public SlimeSummonEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        this.summoner = bcDataManager().addNonSyncedData(EntityData.of(SUMMONER_REF));
        this.tamed = bcDataManager().addNonSyncedData(EntityData.of(TAMED_REF));
        this.life_ticks = bcDataManager().addNonSyncedData(EntityData.of(LIFE_TICKS_REF));
        this.max_life_ticks = bcDataManager().addNonSyncedData(EntityData.of(MAX_LIFE_TICKS_REF));
        this.useless_timer = bcDataManager().addNonSyncedData(EntityData.of(USELESS_TIMER_REF));    }

    @Override
    public void tick() {
        super.tick();

        if (isTamed()) {

            if (!this.level.isClientSide()) {

                if (this.isAlive()) {
                    if (this.life_ticks.get() < this.max_life_ticks.get()) {

                        this.life_ticks.set(this.life_ticks.get() + 1);
                    } else {

                        if (this.mainHandler.isPlayingNull()) {
                            hurt(DamageSource.MAGIC, getMaxHealth());

                        }

                    }

                    if (getSummoner() == null || !getSummoner().isAlive()) {

                        if (this.mainHandler.isPlayingNull()) {
                            hurt(DamageSource.MAGIC, getMaxHealth());

                        }
                    } else {
                        if (getSummoner().hasAttackTarget()) {

                            setTarget(getSummoner().getTarget());
                        } else {
                            if (hasAttackTarget()) {
                                setTarget(null);
                            }


                            this.useless_timer.get().advance();

                            if (this.useless_timer.get().hasEnded()) {

                                if (this.mainHandler.isPlayingNull()) {
                                    hurt(DamageSource.MAGIC, getMaxHealth());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void die(DamageSource cause) {
        super.die(cause);
        sendClientMsg(0);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientCallOutHandler(int flag, ObjectFetcher fetcher) {
        super.clientCallOutHandler(flag, fetcher);
        if (flag == 0) {
            if (getDeathParticle() != null) {
                for (int i = 0; i < 7; i++) {
                    double d0 = this.random.nextGaussian() * 0.02D;
                    double d1 = this.random.nextGaussian() * 0.02D + 0.05F;
                    double d2 = this.random.nextGaussian() * 0.02D;
                    this.level.addParticle(getDeathParticle(), this.getRandomX(0.1D), this.getRandomY(), this.getRandomZ(0.1D), d0, d1, d2);
                }
            }
        }
    }

    public abstract ParticleOptions getDeathParticle();

    public void setSummoner(ModuledMob livingEntity) {
        this.summoner.set(livingEntity);
    }

    public void setTamed(boolean v) {
        this.tamed.set(v);
    }

    @Nullable
    public ModuledMob getSummoner() {
        if (this.summonerEntity == null) {
            if (this.summoner.get() instanceof ModuledMob && this.summoner.get().isAlive()) {

                this.summonerEntity = (ModuledMob) this.summoner.get();
            }
        }

        return this.summonerEntity;
    }

    public boolean isTamed() {
        return this.tamed.get();
    }

}
