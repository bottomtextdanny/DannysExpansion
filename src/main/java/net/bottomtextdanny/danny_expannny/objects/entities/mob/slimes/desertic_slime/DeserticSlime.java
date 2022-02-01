package net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.desertic_slime;

import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.AbstractSlime;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.data.SlimeHopAnimationInput;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.data.SlimeHopMovementInput;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.data.SlimeHopSoundInput;
import net.bottomtextdanny.danny_expannny.objects.entities.modules.critical_effect_provider.SandCriticalProvider;
import net.bottomtextdanny.braincell.mod.entity.modules.variable.*;
import net.bottomtextdanny.danny_expannny.objects.items.GelItem;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.RandomIntegerMapper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class DeserticSlime extends AbstractSlime implements SandCriticalProvider {
	private static final float MUMMY_CHANCE = 0.05F;
	public static final Form<DeserticSlime> NORMAL_FORM = new DeserticSlimeNormalForm();
	public static final Form<DeserticSlime> MUMMY_FORM = new DeserticSlimeMummyForm();
	public static final IndexedFormManager FORMS =
			IndexedFormManager.builder()
					.add(NORMAL_FORM)
					.add(MUMMY_FORM)
					.create();
	public static final SlimeHopMovementInput HOP_MOVEMENT = new SlimeHopMovementInput(
			0.5F,
			0.1F,
			RandomIntegerMapper.of(40)
	);

    public DeserticSlime(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
    }

	@Override
	protected void commonInit() {
		super.commonInit();
		this.variableModule = new IndexedVariableModule(this, FORMS);
	}

	public static AttributeSupplier.Builder Attributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.FOLLOW_RANGE, 20.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.2D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D);
    }

    @Override
    public float getGelAmount() {
        return 0.4F;
    }

    @Override
    public GelItem.Model getGelVariant() {
        return variableModule().getForm() == MUMMY_FORM ? GelItem.Model.MUMMY : GelItem.Model.DESERTIC;
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
	
	@Override
	public Form<?> chooseVariant() {
		return this.random.nextFloat() > MUMMY_CHANCE ? NORMAL_FORM : MUMMY_FORM;
	}

}
