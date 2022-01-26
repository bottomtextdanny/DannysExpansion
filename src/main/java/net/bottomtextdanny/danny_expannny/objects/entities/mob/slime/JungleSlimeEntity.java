package net.bottomtextdanny.danny_expannny.objects.entities.mob.slime;

import net.bottomtextdanny.dannys_expansion.core.Util.Timer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class JungleSlimeEntity extends AbstractSlimeEntity {

    public JungleSlimeEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        this.hopDelay = new Timer(72);
        this.hopHeight = 1.0F;
        this.horizontalHopSpeed = 0.09F;
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
	public boolean isSurfaceSlime() {
		return true;
	}
}
