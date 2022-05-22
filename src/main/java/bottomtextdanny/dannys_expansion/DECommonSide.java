package bottomtextdanny.dannys_expansion;

import bottomtextdanny.dannys_expansion._base.sensitive_hooks.CapabilityManager;
import bottomtextdanny.dannys_expansion._base.lazy_recipe.LazyRecipeManager;
import bottomtextdanny.dannys_expansion._base.network.DEPacketInitialization;
import bottomtextdanny.dannys_expansion._base.network.servertoclient.MSGUpdateRecipes;
import bottomtextdanny.dannys_expansion._base.lazy_recipe.type.LazyIngredientDeserializers;
import bottomtextdanny.dannys_expansion._base.sensitive_hooks.SpawnHooks;
import bottomtextdanny.dannys_expansion.content.commands.DECommandManager;
import bottomtextdanny.dannys_expansion.content.items.arrow.DEArrowItem;
import bottomtextdanny.braincell.mod._base.AbstractModSide;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.PacketDistributor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class DECommonSide extends AbstractModSide {
    public final Logger logger;
    private final LazyRecipeManager lazyRecipeManager;

    public DECommonSide(String modId) {
        super(modId);
        this.logger = LogManager.getLogger(modId + "(common content)");
        this.lazyRecipeManager = new LazyRecipeManager("lazy_recipes");
    }

    @Override
    public void modLoadingCallOut() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        DEPacketInitialization.initializeNetworkPackets();

        CapabilityManager.sendHooks();

        forgeBus.addListener((AddReloadListenerEvent e) -> {
            e.addListener(this.lazyRecipeManager);
        });
        forgeBus.addListener((PlayerEvent.PlayerLoggedInEvent e) -> {
            if (!e.getPlayer().level.isClientSide()) {
                new MSGUpdateRecipes(this.lazyRecipeManager.getAllRecipes())
                        .sendTo(PacketDistributor.PLAYER.with(() -> (ServerPlayer)e.getPlayer()));
            }
        });
        modBus.addListener((FMLCommonSetupEvent event) -> {
            DECommandManager.setup();

            DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior() {
                public ItemStack execute(BlockSource source, ItemStack stack) {
                    Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
                    EntityType<?> entitytype = ((SpawnEggItem) stack.getItem()).getType(stack.getTag());
                    entitytype.spawn(source.getLevel(), stack, null, source.getPos().relative(direction), MobSpawnType.DISPENSER, direction != Direction.UP, false);
                    stack.shrink(1);
                    return stack;
                }
            };
            for (final DEArrowItem arrow : DEArrowItem.DANNY_ARROWS) {
                DispenserBlock.registerBehavior(arrow, defaultDispenseItemBehavior);
            }
            DEArrowItem.DANNY_ARROWS.clear();
        });

        forgeBus.addListener(SpawnHooks::applySpawnToBiomes);
    }

    @Override
    public void postModLoadingPhaseCallOut() {
        LazyIngredientDeserializers.loadClass();
    }

    public LazyRecipeManager getLazyRecipeManager() {
        return lazyRecipeManager;
    }
}
