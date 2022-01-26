package net.bottomtextdanny.danny_expannny.objects.entities.mob.slime.mundane_slime;

import net.bottomtextdanny.braincell.mod.entity.modules.variable.*;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slime.AbstractSlimeEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.Timer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;

import java.time.LocalDate;
import java.time.temporal.ChronoField;

public class MundaneSlimeEntity extends AbstractSlimeEntity {
	public static final Form<MundaneSlimeEntity> NORMAL_FORM = new MundaneSlimeNormalForm();
	public static final Form<MundaneSlimeEntity> GOLDEN_FORM = new MundaneSlimeGoldenForm();
	public static final Form<MundaneSlimeEntity> BIG_FORM = new MundaneSlimeBigForm();
	public static final Form<MundaneSlimeEntity> OLD_FORM = new MundaneSlimeOldForm();
	public static final Form<MundaneSlimeEntity> FLOWER_FORM = new MundaneSlimeFlowerForm();
	public static final Form<MundaneSlimeEntity> SPOOKY_FORM = new MundaneSlimeSpookyForm();
	public static final IndexedFormManager FORMS = IndexedFormManager.builder()
			.add(NORMAL_FORM)
			.add(BIG_FORM)
			.add(GOLDEN_FORM)
			.add(OLD_FORM)
			.add(FLOWER_FORM)
			.add(SPOOKY_FORM).create();

    public MundaneSlimeEntity(EntityType<? extends MundaneSlimeEntity> type, Level worldIn) {
        super(type, worldIn);
        this.hopDelay = new Timer(35);
		
    }

    public static AttributeSupplier.Builder Attributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.FOLLOW_RANGE, 20.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, -0.4D)
                .add(Attributes.ATTACK_DAMAGE, 2.5D);
    }

	@Override
	protected void commonInit() {
		super.commonInit();
		this.variableModule = new IndexedVariableModule(this, FORMS);
	}

	@Override
    public float getGelAmount() {
        return 0.7F;
    }
	
	@Override
	public boolean isSurfaceSlime() {
		return true;
	}
	
	@Override
	public Form<?> chooseVariant() {
    	int randomVal0 = this.random.nextInt(299);
		
    	if (randomVal0 == 2) {
		    int randomVal1 = this.random.nextInt(14);
		    if (randomVal1 == 2) {
		    	return OLD_FORM;
		    } else {
		    	return GOLDEN_FORM;
		    }
	    } else {
		    LocalDate localdate = LocalDate.now();
		    int month = localdate.get(ChronoField.MONTH_OF_YEAR);
		    int day = localdate.get(ChronoField.DAY_OF_MONTH);
		
		    if (month == 5 && day == 21 || this.level.getBiomeManager().getBiome(blockPosition()).getRegistryName() == Biomes.FLOWER_FOREST.getRegistryName() && this.random.nextFloat() < 0.33F) {
			    return FLOWER_FORM;
		    } else if (month == 10 && this.random.nextFloat() < 0.25F) {
			    return SPOOKY_FORM;
		    }
		    
		    if (this.random.nextFloat() < 0.12F) {
			    return BIG_FORM;
		    }
	    }
    	return NORMAL_FORM;
	}

}
