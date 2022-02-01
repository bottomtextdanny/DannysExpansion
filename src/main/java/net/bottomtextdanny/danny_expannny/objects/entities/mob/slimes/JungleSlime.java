package net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes;

import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.data.SlimeHopAnimationInput;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.data.SlimeHopMovementInput;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.data.SlimeHopSoundInput;
import net.bottomtextdanny.danny_expannny.objects.items.GelItem;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.RandomIntegerMapper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class JungleSlime extends AbstractSlime {
    public static final SlimeHopMovementInput HOP_MOVEMENT = new SlimeHopMovementInput(
            1.0F,
            0.09F,
            RandomIntegerMapper.of(72)
    );

    public JungleSlime(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
    }

    public static AttributeSupplier.Builder Attributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.FOLLOW_RANGE, 20.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    @Override
    public float getGelAmount() {
        return 1.0F;
    }

    @Override
    public GelItem.Model getGelVariant() {
        return GelItem.Model.MAGMA;
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
