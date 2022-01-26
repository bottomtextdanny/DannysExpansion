package net.bottomtextdanny.braincell.mod.entity.modules.variable;

import net.bottomtextdanny.braincell.mod.base.annotations.OnlyHandledOnClient;
import net.bottomtextdanny.braincell.mod.base.util.Connection;
import net.bottomtextdanny.braincell.mod.structure.client_sided.variant_data.VariantRenderingData;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public abstract class Form<E extends LivingEntity> {
    @OnlyHandledOnClient
    private final Object rendering;

    public Form() {
        super();
        this.rendering = Connection.makeClientSideUnknown(() -> createRenderingHandler());
    }

    protected abstract VariantRenderingData<E> createRenderingHandler();

    @SuppressWarnings("unchecked")
    public final void applyAttributeBonusesRaw(Mob entityIn) {
        applyAttributeBonuses((E) entityIn);
    }

    public void applyAttributeBonuses(E entityIn) {
    }

    @Nullable
    public EntityDimensions boxSize() {
        return null;
    }

    @SuppressWarnings("unchecked")
    @OnlyIn(Dist.CLIENT)
    public VariantRenderingData<E> getRendering() {
        return (VariantRenderingData<E>)this.rendering;
    }
}
