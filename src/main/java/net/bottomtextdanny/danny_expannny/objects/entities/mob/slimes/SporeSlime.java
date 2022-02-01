package net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes;

import net.bottomtextdanny.braincell.mod.base.misc.timer.IntScheduler;
import net.bottomtextdanny.danny_expannny.object_tables.DEParticles;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.data.SlimeHopAnimationInput;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.data.SlimeHopMovementInput;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.data.SlimeHopSoundInput;
import net.bottomtextdanny.danny_expannny.objects.items.GelItem;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.SlimeSummonEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.RandomIntegerMapper;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class SporeSlime extends SlimeSummonEntity {
    public static final SlimeHopMovementInput HOP_MOVEMENT = new SlimeHopMovementInput(
            1.5F,
            0.08F,
            RandomIntegerMapper.of(52)
    );

    public SporeSlime(EntityType<? extends SporeSlime> type, Level worldIn) {
        super(type, worldIn);
        this.max_life_ticks.set(200);
        this.useless_timer.set(IntScheduler.simple(50));
    }

    public static AttributeSupplier.Builder Attributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.FOLLOW_RANGE, 20.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    @Override
    public float getGelAmount() {
        return 0;
    }

    @Override
    public GelItem.Model getGelVariant() {
        return GelItem.Model.GREEN;
    }

    @Override
    public SlimeHopSoundInput hopSoundsInput() {
        return DEFAULT_SOUND_INPUT;
    }

    @Override
    public SlimeHopAnimationInput hopAnimationInput() {
        return DEFAULT_HOP_ANIMATION_INPUT;
    }

    @Override
    public SlimeHopMovementInput hopInput() {
        return HOP_MOVEMENT;
    }

    @Override
    public ParticleOptions getDeathParticle() {
        return DEParticles.DEATH_SPORER.get();
    }
}
