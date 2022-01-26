package net.bottomtextdanny.dannys_expansion.core.events;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = DannysExpansion.ID, value = Dist.CLIENT)
public class ClientEventHandler {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void handleClientTick(TickEvent.PlayerTickEvent event) {
        DannysExpansion.clientManager().handleClientTick(event);
    }

    @SubscribeEvent
    public static void clientWorldTickHandler(TickEvent.ClientTickEvent event) {
        DannysExpansion.clientManager().clientWorldTickHandler(event);
    }

	@OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void cameraEvent(EntityViewRenderEvent.CameraSetup event) {
        DannysExpansion.clientManager().cameraEvent(event);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void initGUI(ScreenEvent.InitScreenEvent.Post event) {
        DannysExpansion.clientManager().initGUI(event);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onMovementInput(MovementInputUpdateEvent event) {
        DannysExpansion.clientManager().onMovementInput(event);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void clickInputEvent(InputEvent.ClickInputEvent event) {
        DannysExpansion.clientManager().clickInputEvent(event);

    }

	@SubscribeEvent
	public static void renderHand(RenderHandEvent event) {
        DannysExpansion.clientManager().renderHand(event);
	}

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onPlace(BlockEvent.EntityPlaceEvent event) {

    }
}
