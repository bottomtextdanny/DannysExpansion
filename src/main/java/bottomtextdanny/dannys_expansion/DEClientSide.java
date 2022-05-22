package bottomtextdanny.dannys_expansion;

import bottomtextdanny.dannys_expansion._base.keybinding.KeybindHandler;
import bottomtextdanny.dannys_expansion._base.rendering_hooks.CrosshairRendering;
import bottomtextdanny.dannys_expansion._base.gun_rendering.GunClientData;
import bottomtextdanny.dannys_expansion._base.rendering_hooks.FovTweaking;
import bottomtextdanny.dannys_expansion._base.rendering_hooks.GuiHooks;
import bottomtextdanny.dannys_expansion._base.rendering_hooks.PlayerPoseTweaking;
import bottomtextdanny.dannys_expansion.content._client.BiomeTonemapperAgent;
import bottomtextdanny.dannys_expansion._base.gun_rendering.GunRenderingManager;
import bottomtextdanny.dannys_expansion._base.ambiance.AmbianceManager;
import bottomtextdanny.dannys_expansion._util._client.CMC;
import bottomtextdanny.braincell.mod._base.AbstractModSide;
import bottomtextdanny.braincell.mod._mod.client_sided.events.PostProcessingInitEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public final class DEClientSide extends AbstractModSide {
    public final Logger logger;
    private KeybindHandler keybindHandler;
    private GunRenderingManager gunRenderData;
    private GunClientData gunClientData;
    private AmbianceManager ambianceManager;

    public DEClientSide(String modId) {
        super(modId);
        this.logger = LogManager.getLogger(modId + "(client content)");
    }

    @Override
    public void modLoadingCallOut() {
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        this.keybindHandler = new KeybindHandler();
        this.ambianceManager = new AmbianceManager();
        this.gunRenderData = new GunRenderingManager();
        this.gunClientData = new GunClientData();

        forgeBus.addListener((PostProcessingInitEvent e) -> {
            e.getHandler().getTonemapWorkflow().addAgent(new BiomeTonemapperAgent());
        });

        forgeBus.addListener((TickEvent.ClientTickEvent e) -> {
            tick(e.phase);
        });

        forgeBus.addListener(CrosshairRendering::changeToGunCrosshairIfShould);
        forgeBus.addListener(PlayerPoseTweaking::changePoseIfShould);
        forgeBus.addListener(FovTweaking::changeFovIfShould);
        forgeBus.addListener(GuiHooks::hookButtonsIfShould);
    }

    @Override
    public void postModLoadingPhaseCallOut() {}

    private void tick(TickEvent.Phase phase) {
        if (phase == TickEvent.Phase.END) {
            LocalPlayer player = CMC.player();

            getGunRenderData().handleLevelTick();

            if (player != null && player.isAlive()) {
                ClientLevel level = player.clientLevel;

                if (Minecraft.getInstance().isPaused()) {
                    this.ambianceManager.processTick(level);
                }
            }
        }
    }

    public KeybindHandler getKeybindHandler() {
        return keybindHandler;
    }

    public GunRenderingManager getGunRenderData() {
        return this.gunRenderData;
    }

    public GunClientData getGunData() {
        return gunClientData;
    }

    public AmbianceManager getAmbianceManager() {
        return ambianceManager;
    }
}
