package net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.mundane_slime;

import net.bottomtextdanny.braincell.mod.structure.client_sided.variant_data.VariantRenderingData;
import net.bottomtextdanny.danny_expannny.objects.items.GelItem;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class MundaneSlimeBigForm extends MundaneSlimeForm {

    public MundaneSlimeBigForm() {
        super();
    }

    @Override
    public void applyAttributeBonuses(MundaneSlime entityIn) {
        AttributeInstance attribute = entityIn.getAttribute(Attributes.MAX_HEALTH);
        attribute.setBaseValue(attribute.getBaseValue() * 1.3F);
        attribute = entityIn.getAttribute(Attributes.ATTACK_DAMAGE);
        attribute.setBaseValue(attribute.getBaseValue() * 1.2F);
    }

    @Nullable
    @Override
    public EntityDimensions boxSize() {
        return EntityDimensions.fixed(1.0F, 0.8F);
    }

    @Override
    public GelItem.Model gelModel(MundaneSlime entity) {
        return MundaneSlime.COLORED_GEL_MODELS.get(entity.colorVariant.get());
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    protected VariantRenderingData<MundaneSlime> createRenderingHandler() {
        return new BigSlimeRenderingData();
    }
}
