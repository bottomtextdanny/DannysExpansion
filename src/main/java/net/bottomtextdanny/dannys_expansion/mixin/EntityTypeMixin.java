package net.bottomtextdanny.dannys_expansion.mixin;

import net.bottomtextdanny.danny_expannny.objects.items.BCSpawnEggItem;
import net.bottomtextdanny.danny_expannny.objects.items.SpecialKiteItem;
import net.bottomtextdanny.dannys_expansion.core.Util.mixin.IEntityTypeExt;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityType.class)
public class EntityTypeMixin implements IEntityTypeExt {
    private int dannys_expansion$entityId;
    private SpecialKiteItem dannys_expansion$fixedKite;
    private BCSpawnEggItem dannys_expansion$egg;

    @Override
    public int DE_getRegId() {
        return this.dannys_expansion$entityId;
    }

    @Override
    public void DE_setRegId(int newId) {
        this.dannys_expansion$entityId = newId;
    }

    @Override
    public SpecialKiteItem DE_getFixedKite() {
        return this.dannys_expansion$fixedKite;
    }

    @Override
    public void DE_setFixedKite(SpecialKiteItem newKite) {
        this.dannys_expansion$fixedKite = newKite;
    }
}
