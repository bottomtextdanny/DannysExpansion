package net.bottomtextdanny.danny_expannny.objects.items.sword;

import net.bottomtextdanny.danny_expannny.object_tables.DEParticles;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.BigItemRenderer;
import net.bottomtextdanny.dannys_expansion.core.Util.particle.DannyParticleData;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.EntityUtil;
import net.bottomtextdanny.danny_expannny.accessory.AccessoryKey;
import net.bottomtextdanny.danny_expannny.object_tables.DEAccessoryKeys;
import net.bottomtextdanny.danny_expannny.objects.accessories.StackAccessory;
import net.bottomtextdanny.dannys_expansion.core.base.item.ISweepAnimation;
import net.bottomtextdanny.dannys_expansion.core.base.item.StackAccessoryProvider;
import net.bottomtextdanny.dannys_expansion.core.data.DannyItemTier;
import net.bottomtextdanny.dannys_expansion.core.interfaces.item.IBigItemModelLoader;
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

public class EclipseItem extends SwordItem implements IBigItemModelLoader, StackAccessoryProvider, ISweepAnimation {

    public EclipseItem(Properties builderIn) {
        super(DannyItemTier.ECLIPSE, 4, -2.5F, builderIn.fireResistant());
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
		return DEAccessoryKeys.ECLIPSE;
	}
}
