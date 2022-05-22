package bottomtextdanny.dannys_expansion.content.items.sword;

import bottomtextdanny.dannys_expansion.content.items.ItemSweepAnimation;
import bottomtextdanny.dannys_expansion.tables.DEItemTier;
import bottomtextdanny.dannys_expansion.tables._client.DEParticles;
import bottomtextdanny.dannys_expansion.tables.DESounds;
import bottomtextdanny.dannys_expansion._base.particle.DannyParticleData;
import bottomtextdanny.dannys_expansion._util.EntityUtil;
import bottomtextdanny.braincell.mod.capability.player.accessory.AccessoryKey;
import bottomtextdanny.braincell.mod.capability.player.accessory.StackAccessory;
import bottomtextdanny.braincell.mod.capability.player.accessory.StackAccessoryProvider;
import bottomtextdanny.braincell.mod.rendering.BigItemRenderer;
import bottomtextdanny.braincell.mod._base.registry.item_extensions.IBigItemModelLoader;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
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

public class EclipseItem extends SwordItem implements IBigItemModelLoader, StackAccessoryProvider, ItemSweepAnimation {

    public EclipseItem(Properties builderIn) {
        super(DEItemTier.ECLIPSE, 4, -2.5F, builderIn.fireResistant());
    }

	@OnlyIn(Dist.CLIENT)
	@Override
	public void initializeClient(Consumer<IItemRenderProperties> consumer) {
		consumer.accept(BigItemRenderer.AS_PROPERTY);
	}

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
    	return super.use(worldIn, playerIn, handIn);
    }
	
	@Override
	public void sweepParticleHook(Player player) {
		double d0 = -Mth.sin(player.getYRot() * ((float) Math.PI / 180F));
		double d1 = Mth.cos(player.getYRot() * ((float) Math.PI / 180F));
		if (player.level instanceof ServerLevel) {
			((ServerLevel) player.level).sendParticles(getSweepParticle(player), player.getX() + d0 * 1.3, player.getY(0.5D), player.getZ() + d1 * 1.3, 0, d0, 0.0D, d1, 0.0D);
			EntityUtil.playEyeSound(player, DESounds.IS_ECLIPSE_SWEEP.get(), 1.0F, 1.0F + 0.1F * player.getRandom().nextInt(3));
		}
	}
	
	@Override
	public ParticleOptions getSweepParticle(Player player) {
		return new DannyParticleData(DEParticles.ECLIPSE_SWEEP, player.yHeadRot);
	}

	@Override
	public AccessoryKey<? extends StackAccessory> getKey() {
		return null;
	}
}
