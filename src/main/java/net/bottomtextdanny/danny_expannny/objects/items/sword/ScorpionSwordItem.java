package net.bottomtextdanny.danny_expannny.objects.items.sword;

import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.BigItemRenderer;
import net.bottomtextdanny.dannys_expansion.common.crumbs.CrumbRoot;
import net.bottomtextdanny.dannys_expansion.common.crumbs.content.TestTickableCrumb;
import net.bottomtextdanny.dannys_expansion.core.data.DannyItemTier;
import net.bottomtextdanny.dannys_expansion.core.interfaces.item.IBigItemModelLoader;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;

import java.util.function.Consumer;

public class ScorpionSwordItem extends SwordItem implements IBigItemModelLoader {

    public ScorpionSwordItem(Properties builderIn) {
        super(DannyItemTier.SCORPION_SWORD, 3, -2.5F, builderIn.fireResistant());
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(BigItemRenderer.AS_PROPERTY);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (!worldIn.isClientSide) {
            TestTickableCrumb test = new TestTickableCrumb(CrumbRoot.TEST_TICKABLE, worldIn);
            test.setup(playerIn.getX(), playerIn.getY(), playerIn.getZ());
        }
        return super.use(worldIn, playerIn, handIn);
    }
}
