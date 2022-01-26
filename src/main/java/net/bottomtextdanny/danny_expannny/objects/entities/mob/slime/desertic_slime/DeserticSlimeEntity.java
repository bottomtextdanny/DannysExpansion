package net.bottomtextdanny.danny_expannny.objects.entities.mob.slime.desertic_slime;

import net.bottomtextdanny.danny_expannny.objects.entities.mob.slime.AbstractSlimeEntity;
import net.bottomtextdanny.danny_expannny.objects.entities.modules.critical_effect_provider.SandCriticalProvider;
import net.bottomtextdanny.braincell.mod.entity.modules.variable.*;
import net.bottomtextdanny.danny_expannny.objects.items.GelItem;
import net.bottomtextdanny.dannys_expansion.core.Util.Timer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class DeserticSlimeEntity extends AbstractSlimeEntity implements SandCriticalProvider {
	private static final float MUMMY_CHANCE = 0.05F;
	public static final Form<DeserticSlimeEntity> NORMAL_FORM = new DeserticSlimeNormalForm();
	public static final Form<DeserticSlimeEntity> MUMMY_FORM = new DeserticSlimeMummyForm();
	public static final IndexedFormManager FORMS =
			IndexedFormManager.builder()
					.add(NORMAL_FORM)
					.add(MUMMY_FORM)
					.create();

    public DeserticSlimeEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        this.hopDelay = new Timer(40).ended();
        this.hopHeight = 0.5F;
        this.horizontalHopSpeed = 0.1F;
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
    public int getGelVariant() {
        return variableModule().getForm() == MUMMY_FORM ? GelItem.MUMMY_MODEL : GelItem.DESERTIC_MODEL;
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
