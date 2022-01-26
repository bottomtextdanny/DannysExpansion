package net.bottomtextdanny.braincell.mod.structure.client_sided;

import com.google.common.collect.Lists;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.List;

public final class EntityRendererDeferror {
    public static final UnsupportedOperationException EXPIRED_OPERATION_EX =
            new UnsupportedOperationException("Attempted to add deferring entry after firing deferrors");
    private final List<EntityRendererDeferring<?>> deferredRendererFactories = Lists.newLinkedList();
    private boolean closed;

    public EntityRendererDeferror() {
        super();
    }

    public void sendListeners() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener((FMLClientSetupEvent event) -> {
            this.deferredRendererFactories.forEach(EntityRendererDeferring::register);
            this.deferredRendererFactories.clear();
            this.closed = true;
        });
    }

    public void add(EntityRendererDeferring<?> entry) {
        if (this.closed) throw EXPIRED_OPERATION_EX;
        this.deferredRendererFactories.add(entry);
    }
}
