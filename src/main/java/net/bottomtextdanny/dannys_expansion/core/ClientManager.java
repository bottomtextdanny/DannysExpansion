package net.bottomtextdanny.dannys_expansion.core;

import com.mojang.blaze3d.pipeline.RenderTarget;
import net.bottomtextdanny.danny_expannny.DEOp;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.network.clienttoserver.MSGOpenAccessoriesMenu;
import net.bottomtextdanny.danny_expannny.rendering.InventoryButton;
import net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow.ap.*;
import net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow.iaf.*;
import net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow.zans.*;
import net.bottomtextdanny.danny_expannny.ClientInstance;
import net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow.CompatArrowRenderer;
import net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow.am.AMSharkArrowRenderer;
import net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow.mm.MMDartRenderer;
import net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow.np.NPBananarrowRenderer;
import net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow.sar.SARMischiefArrowRenderer;
import net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow.sw.SWArrowRenderer;
import net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow.sw.SWBoltRenderer;
import net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow.sw.SWExplosiveArrowRenderer;
import net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow.sw.SWSpectralBoltRenderer;
import net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow.vanilla.VanillaArrowRenderer;
import net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow.vanilla.VanillaSpectralArrowRenderer;
import net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow.wr.WRGeodeArrowRenderer;
import net.bottomtextdanny.danny_expannny.objects.items.gun.GunItem;
import net.bottomtextdanny.dannys_expansion.core.Packets.DENetwork;
import net.bottomtextdanny.danny_expannny.objects.items.gun.IScopingGun;
import net.bottomtextdanny.danny_expannny.capabilities.CapabilityHelper;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerGunModule;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerHelper;
import net.bottomtextdanny.danny_expannny.capabilities.world.LevelCapability;
import net.bottomtextdanny.dannys_expansion.core.config.ClientConfigurationHandler;
import net.bottomtextdanny.dannys_expansion.core.events.KeybindHandler;
import net.bottomtextdanny.dannys_expansion.core.interfaces.IDannyHoldable;
import net.bottomtextdanny.dannys_expansion.core.interfaces.IHoldable;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.TickEvent;

import java.util.Optional;

@Deprecated
@OnlyIn(Dist.CLIENT)
public class ClientManager extends AbstractModManager {
    public final AmbianceManager ambianceManager = new AmbianceManager();
    public  double
            cRenderDispersion,
            cPrevRenderDispersion,
            cDispersion;
    public float
            cDPrevPitch,
            cDPrevYaw,
            cDPitch,
            cDYaw,
            cPrevItemUseCount,
            cRecoil,
            cPrevRecoil,
            cRecoilSubtract,
            cRecoilMult,
            cPitchRecoil,
            cPitchPrevRecoil,
            cPitchRecoilSubtract,
            cPitchRecoilMult;
    public int
            cRollDirection,
            cGunRetrieveFactor;

    public ClientManager(String id) {
        super(id);
    }
	
	public static Minecraft mc() {
		return Minecraft.getInstance();
	}
	
	public static RenderTarget mainTarget() {
		return mc().getMainRenderTarget();
	}

    public void handleClientTick(TickEvent.PlayerTickEvent event) {
        LocalPlayer player = ClientInstance.player();
        if (player != null && event.player.level.isClientSide()) {
            if (event.phase.equals(TickEvent.Phase.END)) {
                boolean hasGun = false;
                this.cPrevItemUseCount = player.getTicksUsingItem();
                this.cPitchPrevRecoil = this.cPitchRecoil;
                this.cPrevRecoil = this.cRecoil;
                this.cPrevRenderDispersion = this.cRenderDispersion;

                for (InteractionHand hand : InteractionHand.values()) {
                    Item itemInHand = player.getItemInHand(hand).getItem();
                    if (!hasGun && itemInHand instanceof GunItem<?> gunInHand) {
                        gunInHand.updateClientDispersion(
                                player, this.cRecoil,
                                (float) DEOp.LOGIC_DIST3D_UTIL.start(player.xOld, player.yOld, player.zOld).get(player));
                        hasGun = true;
                    }
                }

                if (this.cRecoil > 0.0F) this.cRecoil = Mth.clamp(this.cRecoil * this.cRecoilMult - this.cRecoilSubtract, 0.0F, Float.MAX_VALUE);
                this.cPitchRecoil = Mth.clamp(this.cPitchRecoil * this.cPitchRecoilMult - this.cPitchRecoilSubtract, 0.0F, Float.MAX_VALUE);
                this.cRenderDispersion += (this.cDispersion - this.cRenderDispersion) * 0.6;
            }
        }
    }

    public void clientWorldTickHandler(TickEvent.ClientTickEvent event) {
        LocalPlayer player = ClientInstance.player();
        if (player != null && player.isAlive()) {
            ClientLevel world = player.clientLevel;

            LevelCapability capability = CapabilityHelper.get(world, LevelCapability.CAPABILITY);

            if (event.phase == TickEvent.Phase.START && !mc().isPaused()) {
                this.ambianceManager.processTick(world);
                capability.getPhysicalLightModule().tick();
            }
        }
    }

    public void cameraEvent(EntityViewRenderEvent.CameraSetup event) {
        LocalPlayer player = ClientInstance.player();

        if (player != null && player.isAlive()) {
            PlayerGunModule gunModule = PlayerHelper.gunModule(player);
            float pitchRecoil = (float)Mth.lerp(event.getPartialTicks(), DannysExpansion.clientManager().cPitchPrevRecoil, DannysExpansion.clientManager().cPitchRecoil);
            float rollRecoil = pitchRecoil * 0.3F * DannysExpansion.clientManager().cRollDirection;
            float offsetFactor = 0.07F;

            event.setRoll(event.getRoll() - rollRecoil);

            if (Minecraft.getInstance().options.getCameraType() == CameraType.FIRST_PERSON) {
                event.setPitch(event.getPitch() - pitchRecoil);
            }

            if (gunModule.getGunScoping().getItem() instanceof IScopingGun) {
                offsetFactor *= 1.0F - ((IScopingGun) gunModule.getGunScoping().getItem()).scopingSensMult();
            }

            this.cDPrevPitch = this.cDPitch;
            this.cDPrevYaw = this.cDYaw;
            this.cDPitch = Mth.lerp(offsetFactor, this.cDPitch, event.getPitch());
            this.cDYaw = Mth.lerp(offsetFactor, this.cDYaw, event.getYaw());
        }
    }

    public float innerScopeXOffsetI(float partialTick) {
        return Mth.lerp(partialTick, ClientInstance.player().yRotO, ClientInstance.player().getYRot()) - Mth.lerp(partialTick, this.cDPrevYaw, this.cDYaw);
    }

    public float innerScopeYOffsetI(float partialTick) {
        return Mth.lerp(partialTick, ClientInstance.player().xRotO, ClientInstance.player().getXRot()) - Mth.lerp(partialTick, this.cDPrevPitch, this.cDPitch);
    }

    public void initGUI(ScreenEvent.InitScreenEvent.Post event) {
        if (event.getScreen() instanceof InventoryScreen) {
            if (ClientInstance.player() != null && ClientInstance.player().isAlive()) {
                event.addListener(new InventoryButton((InventoryScreen) event.getScreen(), 138, event.getScreen().height / 2 - 22, 18, 18, new TranslatableComponent("container.danny_accessories"), button -> {
                    new MSGOpenAccessoriesMenu().sendToServer();
                }, button -> {
                    button.renderCustomImage = true;

                    button.customTextures = new ResourceLocation[] {
                            new ResourceLocation(DannysExpansion.ID, "textures/gui/screen/accessories_button.png"),
                            new ResourceLocation(DannysExpansion.ID, "textures/gui/screen/accessories_button_mouse_over.png")
                    };
                }));
            }
        } else  if (event.getScreen() instanceof CreativeModeInventoryScreen) {
            CreativeModeInventoryScreen screen = (CreativeModeInventoryScreen) event.getScreen();


            if (ClientInstance.player() != null) {
                event.addListener(new InventoryButton((EffectRenderingInventoryScreen<?>) event.getScreen(), 138, event.getScreen().height / 2 - 38, 18, 18, new TranslatableComponent("container.danny_accessories"), button -> {
                    new MSGOpenAccessoriesMenu().sendToServer();
                }, button -> {
                    button.renderCustomImage = true;
                    button.customTextures = new ResourceLocation[] {
                            new ResourceLocation(DannysExpansion.ID, "textures/gui/screen/accessories_button.png"),
                            new ResourceLocation(DannysExpansion.ID, "textures/gui/screen/accessories_button_mouse_over.png")
                    };
                }).hideIf(button -> screen.getSelectedTab() != CreativeModeTab.TAB_INVENTORY.getId()));
            }
        }
    }

    public void onMovementInput(MovementInputUpdateEvent event) {
        if (ClientInstance.player() == null || !ClientInstance.player().isAlive()) return;
        Item item = ClientInstance.player().getUseItem().getItem();
        float mult = 1.0F;

        if (item instanceof IDannyHoldable) {
            mult = ((IDannyHoldable)item).holdMovementSpeed() * 5;
        }

        PlayerGunModule gunModule = PlayerHelper.gunModule(ClientInstance.player());
        if (gunModule.getGunScoping().getItem() instanceof IScopingGun scopingGun) {
            mult = scopingGun.scopingMovementSpeed(gunModule.getGunScoping(), ClientInstance.player());
        }

        event.getInput().forwardImpulse *= mult;
        event.getInput().leftImpulse *= mult;
    }

    public void clickInputEvent(InputEvent.ClickInputEvent event) {
        if (ClientInstance.player() == null || !ClientInstance.player().isAlive()) return;
        Player player = ClientInstance.player();
        Item item = player.getItemInHand(InteractionHand.OFF_HAND).getItem();

        if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof GunItem || KeybindHandler.holdableUsed) {
            event.setSwingHand(false);
        }

        for (InteractionHand hand : InteractionHand.values()) {
            if (event.getKeyMapping() == Minecraft.getInstance().options.keyAttack && player.getItemInHand(hand).getItem() instanceof GunItem) {
                event.setCanceled(true);
                event.setSwingHand(false);
                return;
            }
        }

        if (event.getHand() == InteractionHand.OFF_HAND) {

            if (item instanceof IHoldable && ((IHoldable)item).canBePressed(player.level, player, player.getItemInHand(InteractionHand.OFF_HAND))) {
                event.setCanceled(true);
                event.setSwingHand(false);
            }
        }
    }

    public void renderHand(RenderHandEvent event) {

        if (ClientInstance.player() != null && ClientInstance.player().isAlive()) {
            PlayerGunModule gunModule = PlayerHelper.gunModule(ClientInstance.player());
            if (gunModule.getGunScoping().getItem() instanceof IScopingGun) {
                event.setCanceled(true);
            }
        }
    }
    
    public static <E extends Entity> void registerCompatRenderer(EntityRendererProvider<E> renderer, String... entityIds) {
        for (String id : entityIds) {
            Optional<EntityType<?>> mabyType = EntityType.byString(id);
            if (mabyType.isPresent()) {
                try {
                    EntityRenderers.register((EntityType<E>) mabyType.get(), renderer);
                } catch (Exception e) {
                    DannysExpansion.LOGGER.error("Danny's Expansion couldn't achieve entity compatibility for {}, please inform Danny's Expansion developer about this", id);
                    e.printStackTrace();
                }
            }
        }
    }

    public void registerEntityRenderer() {
        if (ClientConfigurationHandler.CONFIG.wip_arrow_rendering.get()) {
            registerCompatRenderer(AMSharkArrowRenderer::new, "alexsmobs:shark_tooth_arrow");
            registerCompatRenderer(APBlazeArrowRenderer::new, "archers_paradox:blaze_arrow");
            registerCompatRenderer(APBonemealArrowRenderer::new, "archers_paradox:bonemeal_arrow");
            registerCompatRenderer(APChallengeArrowRenderer::new, "archers_paradox:challenge_arrow");
            registerCompatRenderer(APDiamondArrowRenderer::new, "archers_paradox:diamond_arrow");
            registerCompatRenderer(APDisplacementArrowRenderer::new, "archers_paradox:displacement_arrow");
            registerCompatRenderer(APEnderArrowRenderer::new, "archers_paradox:ender_arrow");
            registerCompatRenderer(APExplosiveArrowRenderer::new, "archers_paradox:explosive_arrow");
            registerCompatRenderer(APFrostArrowRenderer::new, "archers_paradox:frost_arrow");
            registerCompatRenderer(APGlowstoneArrowRenderer::new, "archers_paradox:glowstone_arrow");
            registerCompatRenderer(APLightningArrowRenderer::new, "archers_paradox:lightning_arrow");
            registerCompatRenderer(APPhantasmalArrowRenderer::new, "archers_paradox:phantasmal_arrow");
            registerCompatRenderer(APPrismarineArrowRenderer::new, "archers_paradox:prismarine_arrow");
            registerCompatRenderer(APQuartzArrowRenderer::new, "archers_paradox:quartz_arrow");
            registerCompatRenderer(APRedstoneArrowRenderer::new, "archers_paradox:redstone_arrow");
            registerCompatRenderer(APShulkerArrowRenderer::new, "archers_paradox:shulker_arrow");
            registerCompatRenderer(APSlimeArrowRenderer::new, "archers_paradox:slime_arrow");
            registerCompatRenderer(APSporeArrowRenderer::new, "archers_paradox:spore_arrow");
            registerCompatRenderer(APTrainingArrowRenderer::new, "archers_paradox:training_arrow");
            registerCompatRenderer(APVerdantArrowRenderer::new, "archers_paradox:verdant_arrow");
            registerCompatRenderer(VanillaArrowRenderer::new, "ars_nouveau:spell_arrow");
            registerCompatRenderer(m -> new CompatArrowRenderer(m, "dannys_expansion:textures/entity/arrow/faa/boom_arrow.png", null), "forbidden_arcanus:boom_arrow");
            registerCompatRenderer(m -> new CompatArrowRenderer(m, "dannys_expansion:textures/entity/arrow/faa/draco_arcanus_arrow.png", null), "forbidden_arcanus:draco_arcanus_arrow");
            registerCompatRenderer(IAFAmphithereArrowRenderer::new, "iceandfire:amphithere_arrow");
            registerCompatRenderer(IAFDragonArrowRenderer::new, "iceandfire:dragon_arrow");
            registerCompatRenderer(IAFHydraArrowRenderer::new, "iceandfire:hydra_arrow");
            registerCompatRenderer(IAFStymphalianArrowRenderer::new, "iceandfire:stymphalian_arrow");
            registerCompatRenderer(IAFTideArrowRenderer::new, "iceandfire:tide_arrow", "iceandfire:sea_serpent_arrow");
            registerCompatRenderer(NPBananarrowRenderer::new, "neapolitan:bananarrow");
            registerCompatRenderer(MMDartRenderer::new, "mowziesmobs:dart");
            registerCompatRenderer(SARMischiefArrowRenderer::new, "savageandravage:mischief_arrow");
            registerCompatRenderer(SWArrowRenderer::new, "spartanweaponry:arrow");
            registerCompatRenderer(SWExplosiveArrowRenderer::new, "spartanweaponry:arrow_explosive");
            registerCompatRenderer(SWBoltRenderer::new, "spartanweaponry:bolt");
            registerCompatRenderer(SWSpectralBoltRenderer::new, "spartanweaponry:bolt_spectral");
            registerCompatRenderer(WRGeodeArrowRenderer::new, "wyrmroost:geode_tipped_arrow");
            registerCompatRenderer(ZANSAncientArrowRenderer::new, "zarrowsandstuff:ancient_arrow");
            registerCompatRenderer(ZANSBombArrowRenderer::new, "zarrowsandstuff:bomb_arrow");
            registerCompatRenderer(ZANSFireArrowRenderer::new, "zarrowsandstuff:fire_arrow");
            registerCompatRenderer(ZANSIceArrowRenderer::new, "zarrowsandstuff:ice_arrow");
            registerCompatRenderer(ZANSLightArrowRenderer::new, "zarrowsandstuff:light_arrow");
            registerCompatRenderer(ZANSShockArrowRenderer::new, "zarrowsandstuff:shock_arrow");
            EntityRenderers.register(EntityType.ARROW, VanillaArrowRenderer::new);
            EntityRenderers.register(EntityType.SPECTRAL_ARROW, VanillaSpectralArrowRenderer::new);
        }
    }
}
