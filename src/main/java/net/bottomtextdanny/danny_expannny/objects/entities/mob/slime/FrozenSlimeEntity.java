package net.bottomtextdanny.danny_expannny.objects.entities.mob.slime;

import net.bottomtextdanny.danny_expannny.objects.entities.modules.critical_effect_provider.FrozenCriticalProvider;
import net.bottomtextdanny.danny_expannny.objects.items.GelItem;
import net.bottomtextdanny.dannys_expansion.core.Util.Timer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class FrozenSlimeEntity extends AbstractSlimeEntity implements FrozenCriticalProvider {

    public FrozenSlimeEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        this.hopDelay = new Timer(60);
        this.hopHeight = 0.8F;
        this.horizontalHopSpeed = 0.09F;
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
    public int getGelVariant() {
        return GelItem.ICE_MODEL;
    }
	
	@Override
	public boolean isSurfaceSlime() {
		return true;
	}
}