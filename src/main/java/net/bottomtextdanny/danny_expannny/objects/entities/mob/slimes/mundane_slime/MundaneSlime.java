package net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.mundane_slime;

import com.google.common.collect.ImmutableList;
import net.bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManager;
import net.bottomtextdanny.braincell.mod.entity.modules.variable.*;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityData;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityDataReference;
import net.bottomtextdanny.braincell.mod.entity.serialization.RawEntityDataReference;
import net.bottomtextdanny.braincell.mod.serialization.serializers.builtin.BuiltinSerializers;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.AbstractSlime;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.data.SlimeHopAnimationInput;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.data.SlimeHopMovementInput;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.data.SlimeHopSoundInput;
import net.bottomtextdanny.danny_expannny.objects.items.GelItem;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.RandomIntegerMapper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;

public class MundaneSlime extends AbstractSlime {
	public static final byte GREEN = 0, RED = 1, BLUE = 2, YELLOW = 3;
	public static final List<GelItem.Model> COLORED_GEL_MODELS = ImmutableList.of(
			GelItem.Model.GREEN,
			GelItem.Model.RED,
			GelItem.Model.BLUE,
			GelItem.Model.YELLOW
	);
	public static final EntityDataReference<Byte> COLOR_VARIANT_REF =
			BCDataManager.attribute(MundaneSlime.class,
					RawEntityDataReference.of(
							BuiltinSerializers.BYTE,
							() -> (byte)0,
							"color_variant")
			);
	public static final Form<MundaneSlime> MEDIUM_FORM = new MundaneSlimeMediumForm();
	public static final Form<MundaneSlime> BIG_FORM = new MundaneSlimeBigForm();
	public static final Form<MundaneSlime> GOLDEN_FORM = new MundaneSlimeGoldenForm();
	public static final Form<MundaneSlime> OLD_FORM = new MundaneSlimeOldForm();
	public static final Form<MundaneSlime> FLOWER_FORM = new MundaneSlimeFlowerForm();
	public static final Form<MundaneSlime> SPOOKY_FORM = new MundaneSlimeSpookyForm();
	public static final IndexedFormManager FORMS = IndexedFormManager.builder()
			.add(MEDIUM_FORM)
			.add(BIG_FORM)
			.add(GOLDEN_FORM)
			.add(OLD_FORM)
			.add(FLOWER_FORM)
			.add(SPOOKY_FORM).create();
	public static final SlimeHopMovementInput HOP_MOVEMENT = new SlimeHopMovementInput(
			0.7F,
			0.1F,
			RandomIntegerMapper.of(35)
	);
	public final EntityData<Byte> colorVariant;

    public MundaneSlime(EntityType<? extends MundaneSlime> type, Level worldIn) {
        super(type, worldIn);
		this.colorVariant = bcDataManager().addSyncedData(EntityData.of(COLOR_VARIANT_REF));
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
	public GelItem.Model getGelVariant() {
		return ((MundaneSlimeForm)this.variableModule().getForm()).gelModel(this);
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
    	int randomVal0 = this.random.nextInt(299);
		chooseColor();
		
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
    	return MEDIUM_FORM;
	}

	private void chooseColor() {
		this.colorVariant.set((byte) this.random.nextInt(4));
	}

}
