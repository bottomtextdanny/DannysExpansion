package bottomtextdanny.dannys_expansion.content.items.sword;

import bottomtextdanny.dannys_expansion.tables.DEItemTier;
import bottomtextdanny.dannys_expansion.content.structures.anglerstreasure.AnglersTreasurePiece;
import bottomtextdanny.braincell.mod.rendering.BigItemRenderer;
import bottomtextdanny.braincell.mod._base.registry.item_extensions.IBigItemModelLoader;
import net.minecraft.core.Vec3i;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;

import java.util.function.Consumer;

public class ScorpionSwordItem extends SwordItem implements IBigItemModelLoader {

    public ScorpionSwordItem(Properties builderIn) {
        super(DEItemTier.SCORPION_SWORD, 3, -2.5F, builderIn.fireResistant());
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(BigItemRenderer.AS_PROPERTY);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        Vec3 fVec = playerIn.getLookAngle().scale(5.0F);
        if (!worldIn.isClientSide)
            AnglersTreasurePiece.makePlot(worldIn, playerIn.blockPosition().offset(new Vec3i(fVec.x, fVec.y, fVec.z)));
        return super.use(worldIn, playerIn, handIn);
    }
}
