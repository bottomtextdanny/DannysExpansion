package net.bottomtextdanny.dannys_expansion.compatibility;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DannysExpansion.ID)
public class CuriosAPICompatibility {
	
	
	
    public static void attachCuriosCapability(AttachCapabilitiesEvent<ItemStack> evt) {
//	    ItemStack stack = evt.getObject();
//
//	    if (stack.getItem() instanceof AccessoryItem) {
//		    final LazyOptional<ICurio> curio = LazyOptional.of(() -> new AccessoryCurio(stack));
//		    evt.addCapability(CuriosCapability.ID_ITEM, new ICapabilityProvider() {
//
//			    @Nonnull
//			    @Override
//			    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap,
//				    @Nullable Direction side) {
//				    return CuriosCapability.ITEM.orEmpty(cap, curio);
//			    }
//		    });
//		    evt.addListener(curio::invalidate);
//	    }
    }
    
//    public static class AccessoryCurio implements ICurio {
//        private final ItemStack stack;
//
//        AccessoryCurio(ItemStack stack) {
//            this.stack = stack;
//        }
//
//        /**
//         * Determines if the ItemStack can be equipped into a slot.
//         *
//         * @param identifier   The {@link top.theillusivec4.curios.api.type.ISlotType} identifier of the slot being equipped into
//         * @param livingEntity The wearer of the ItemStack
//         * @return True if the ItemStack can be equipped/put in, false if not
//         */
//        @Override
//        public boolean canEquip(String identifier, LivingEntity livingEntity) {
//            return true;
//        }
//
//        @Override
//        public boolean canRightClickEquip() {
//            return true;
//        }
//
//        @Override
//        public void curioTick(String identifier, int index, LivingEntity livingEntity) {
//        }
//
//        @Override
//        public void onEquip(String identifier, int index, LivingEntity livingEntity) {
//            if (livingEntity instanceof Player) {
//                Player player = (Player) livingEntity;
//                DEPlayerCapability dannyCapability = EntityUtil.getDannyCap(player).orElse(null);
//
//                dannyCapability.getAccessories().getAccessoryInstances().set(index, ItemUtil.getAccessory(stack.getItem()));
//                if (!player.world.isRemote()) {
//
//                    new MSGCuriosAccessory(player.getEntityId(), Registry.ITEM.getId(stack.getItem()), 0).sendTo(PacketDistributor.ALL.with(() -> null));
//                }
//            }
//        }
//
//        @Override
//        public void onUnequip(String identifier, int index, LivingEntity livingEntity) {
//            if (livingEntity instanceof Player) {
//                Player player = (Player) livingEntity;
//                DEPlayerCapability dannyCapability = EntityUtil.getDannyCap(player).orElse(null);
//
//                dannyCapability.getAccessories().removeStackFromSlot(index);
//                if (!player.world.isRemote()) new MSGCuriosAccessory(player.getEntityId(), Registry.ITEM.getId(stack.getItem()), 1).sendTo(PacketDistributor.ALL.with(() -> null));
//
//            }
//        }
//
//        @Override
//        public boolean canSync(String identifier, int index, LivingEntity livingEntity) {
//            return true;
//        }
//    }
}
