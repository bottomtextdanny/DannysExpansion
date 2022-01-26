package net.bottomtextdanny.braincell.mod.structure.client_sided;

import net.bottomtextdanny.braincell.mod.structure.AbstractModSide;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public final class BCClientSide extends AbstractModSide {
    public final Logger logger;
    private final ModelLoaderHandler extraModelLoaders;
    private final ParticleFactoryDeferror particleFactoryDeferror;
    private final RenderingHandler renderingHandler;
    private final PostprocessingHandler postprocessingHandler;
    private final EntityRendererDeferror entityRendererDeferror;
    private final MaterialManager materialManager;

    private BCClientSide(String modId) {
        super(modId);
        MinecraftForge.EVENT_BUS.addListener(this::tick);
        this.logger = LogManager.getLogger(String.join(modId, "(client content)"));
        this.extraModelLoaders = new ModelLoaderHandler();
        this.particleFactoryDeferror = new ParticleFactoryDeferror();
        this.renderingHandler = new RenderingHandler();
        this.postprocessingHandler = new PostprocessingHandler();
        this.entityRendererDeferror = new EntityRendererDeferror();
        this.materialManager = new MaterialManager();
    }

    public static BCClientSide with(String modId) {
        return new BCClientSide(modId);
    }

    private void tick(TickEvent.ClientTickEvent event) {
        this.postprocessingHandler.tick();
    }

    @Override
    public void modLoadingCallOut() {
        this.extraModelLoaders.sendListeners();
        this.particleFactoryDeferror.sendListeners();
        this.entityRendererDeferror.sendListeners();
        this.materialManager.sendListeners();
    }

    @Override
    public void postModLoadingPhaseCallOut() {}

    public ModelLoaderHandler getExtraModelLoaders() {
        return this.extraModelLoaders;
    }

    public ParticleFactoryDeferror getParticleFactoryDeferror() {
        return this.particleFactoryDeferror;
    }

    public RenderingHandler getRenderingHandler() {
        return this.renderingHandler;
    }

    public PostprocessingHandler getPostprocessingHandler() {
        return this.postprocessingHandler;
    }

    public EntityRendererDeferror getEntityRendererDeferror() {
        return this.entityRendererDeferror;
    }

    public MaterialManager getMaterialManager() {
        return this.materialManager;
    }
}
