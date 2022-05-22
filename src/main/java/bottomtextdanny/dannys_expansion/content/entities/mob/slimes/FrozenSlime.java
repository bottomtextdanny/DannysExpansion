package bottomtextdanny.dannys_expansion.content.entities.mob.slimes;

import bottomtextdanny.dannys_expansion.content.entities.mob.slimes.data.SlimeHopAnimationInput;
import bottomtextdanny.dannys_expansion.content.entities.mob.slimes.data.SlimeHopMovementInput;
import bottomtextdanny.dannys_expansion.content.entities.mob.slimes.data.SlimeHopSoundInput;
import bottomtextdanny.dannys_expansion.content.entities._modules.critical_effect_provider.FrozenCriticalProvider;
import bottomtextdanny.dannys_expansion.content.items.material.GelItem;
import bottomtextdanny.braincell.base.value_mapper.RandomIntegerMapper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class FrozenSlime extends AbstractSlime implements FrozenCriticalProvider {
    public static final SlimeHopMovementInput HOP_MOVEMENT = new SlimeHopMovementInput(
            0.8F,
            0.09F,
            RandomIntegerMapper.of(60)
    );

    public FrozenSlime(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        xpReward = 4;
    }

    public static AttributeSupplier.Builder Attributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 25.0D)
                .add(Attributes.FOLLOW_RANGE, 20.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    @Override
    public float getGelAmount() {
        return 0.4F;
    }

    @Override
    public GelItem.Model getGelVariant() {
        return GelItem.Model.FROZEN;
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
	public boolean isSurfaceSlime() {
		return true;
	}
}
