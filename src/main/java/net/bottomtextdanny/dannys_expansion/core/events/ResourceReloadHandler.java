package net.bottomtextdanny.dannys_expansion.core.events;

import net.bottomtextdanny.danny_expannny.network.servertoclient.MSGUpdateRecipes;
import net.bottomtextdanny.danny_expannny.objects.items.SpecialKiteItem;
import net.bottomtextdanny.dannys_expansion.core.Packets.DENetwork;
import net.bottomtextdanny.dannys_expansion.core.Util.mixin.IEntityTypeExt;
import net.bottomtextdanny.dannys_expansion.core.misc.LazyRecipeManager;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.FORGE)
public class ResourceReloadHandler {
    public static LazyRecipeManager recipeManager = new LazyRecipeManager("workbench_recipes");

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void resourceReloadEvent(AddReloadListenerEvent event) {
        event.addListener(recipeManager);
    }

    @SubscribeEvent
    public static void playerLoggedEvent(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getPlayer().level.isClientSide()) {
            new MSGUpdateRecipes(ResourceReloadHandler.recipeManager.getRecipes())
                    .sendTo(PacketDistributor.PLAYER.with(() -> (ServerPlayer)event.getPlayer()));
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {

        if (!event.getEntityLiving().level.isClientSide && event.getSource().getEntity() instanceof Player) {
            SpecialKiteItem mabyKite = ((IEntityTypeExt)event.getEntityLiving().getType()).DE_getFixedKite();

            if (mabyKite != null) {
                ServerPlayer playerSource = (ServerPlayer) event.getSource().getEntity();
                int statNumber = playerSource.getStats().getValue(Stats.ENTITY_KILLED.get(event.getEntityLiving().getType())) + 1;

                if (statNumber % mabyKite.getRewardNumber() == 0) {
                    playerSource.displayClientMessage(new TextComponent("you have killed " + statNumber + " ").setStyle(Style.EMPTY.withColor(TextColor.fromLegacyFormat(ChatFormatting.GREEN))).append(new TranslatableComponent(event.getEntity().getType().getDescriptionId())).setStyle(Style.EMPTY.withColor(TextColor.fromLegacyFormat(ChatFormatting.GOLD))).append(new TextComponent("(s)!")).setStyle(Style.EMPTY.withColor(TextColor.fromLegacyFormat(ChatFormatting.GREEN))), true);

                    ItemStack kite = new ItemStack(mabyKite);

                    kite.setCount(1);

                    ItemEntity itemEntity = new ItemEntity(event.getEntityLiving().level, event.getEntityLiving().getX(), event.getEntityLiving().getY(), event.getEntityLiving().getZ(), kite);

                    event.getEntityLiving().level.addFreshEntity(itemEntity);
                }
            }
        }
    }
}
