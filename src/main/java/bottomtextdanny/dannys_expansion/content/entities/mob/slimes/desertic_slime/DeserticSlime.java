package bottomtextdanny.dannys_expansion.content.entities.mob.slimes.desertic_slime;

import bottomtextdanny.dannys_expansion.content.entities.mob.slimes.AbstractSlime;
import bottomtextdanny.dannys_expansion.content.entities.mob.slimes.data.SlimeHopAnimationInput;
import bottomtextdanny.dannys_expansion.content.entities.mob.slimes.data.SlimeHopMovementInput;
import bottomtextdanny.dannys_expansion.content.entities.mob.slimes.data.SlimeHopSoundInput;
import bottomtextdanny.dannys_expansion.content.entities._modules.critical_effect_provider.SandCriticalProvider;
import bottomtextdanny.dannys_expansion.content.items.material.GelItem;
import bottomtextdanny.braincell.base.value_mapper.RandomIntegerMapper;
import bottomtextdanny.braincell.mod.entity.modules.variable.Form;
import bottomtextdanny.braincell.mod.entity.modules.variable.IndexedFormManager;
import bottomtextdanny.braincell.mod.entity.modules.variable.IndexedVariableModule;
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
		xpReward = 5;
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
