package net.bottomtextdanny.braincell.mod.world.builtin_entities;

import net.bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManager;
import net.bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManagerProvider;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityData;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityDataReference;
import net.bottomtextdanny.braincell.mod.entity.serialization.RawEntityDataReference;
import net.bottomtextdanny.braincell.mod.serialization.WorldPacketData;
import net.bottomtextdanny.braincell.mod.serialization.serializers.builtin.BuiltinSerializers;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.object_tables.DEBlocks;
import net.bottomtextdanny.danny_expannny.object_tables.items.DEBuildingItems;
import net.bottomtextdanny.braincell.underlying.misc.ObjectFetcher;
import net.bottomtextdanny.braincell.mod.world.builtin_items.BCBoatItem;
import net.bottomtextdanny.dannys_expansion.core.interfaces.entity.ClientManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public class BCBoat extends Boat implements BCDataManagerProvider, ClientManager {
	public static final int UPDATE_TEXTURE_CALL = 0;
	public static final EntityDataReference<Item> DATA_REF = BCDataManager.attribute(BCBoat.class,
			RawEntityDataReference.of(
					BuiltinSerializers.ITEM,
					() -> Items.AIR,
					"boat_item"
			));
	private final EntityData<Item> boatItem;
	private final BCDataManager deDataManager;

	
	public BCBoat(EntityType<? extends Boat> type, Level world) {
		super(type, world);
		this.deDataManager = new BCDataManager(this);
		this.boatItem = bcDataManager().addSyncedData(EntityData.of(DATA_REF));
	}

	public void setBoatItem(BCBoatItem item) {
        this.boatItem.set(item);
	}

	@Override
	public void clientCallOutHandler(int flag, ObjectFetcher fetcher) {
		if (flag == UPDATE_TEXTURE_CALL) {
			setClientBoatItem(fetcher.get(0, Integer.class));
		}
	}

	@Override
	public void afterClientDataUpdate() {
		BCDataManagerProvider.super.afterClientDataUpdate();
	}

	protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
		this.lastYd = this.getDeltaMovement().y;
		if (!this.isPassenger()) {
			if (onGroundIn) {
				if (this.fallDistance > 3.0F) {
					if (this.status != Boat.Status.ON_LAND) {
						this.fallDistance = 0.0F;
						return;
					}

					this.causeFallDamage(this.fallDistance, 1.0F, DamageSource.FALL);
					if (!this.level.isClientSide && !this.isRemoved()) {
						this.remove(RemovalReason.KILLED);
						if (this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
							ItemEntity plankEntity = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), new ItemStack(getBCBoatType().materialItem.get(), 3));
							ItemEntity stickEntity = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), new ItemStack(Items.STICK, 2));

							plankEntity.setDefaultPickUpDelay();
							stickEntity.setDefaultPickUpDelay();

							if (captureDrops() != null) {
								captureDrops().add(plankEntity);
								captureDrops().add(stickEntity);
							}
							else {
								this.level.addFreshEntity(plankEntity);
								this.level.addFreshEntity(stickEntity);
							}
						}
					}
				}
				this.fallDistance = 0.0F;
			} else if (!this.level.getFluidState(this.blockPosition().below()).is(FluidTags.WATER) && y < 0.0D) {
				this.fallDistance = (float)((double)this.fallDistance - y);
			}

		}
	}

	protected void setLastYd(double newLastYd) {
		this.lastYd = newLastYd;
	}

	protected double getLastYd() {
		return this.lastYd;
	}

	public void updateClientTexture() {
		if (!this.level.isClientSide()) {
			sendClientMsg(UPDATE_TEXTURE_CALL, PacketDistributor.TRACKING_ENTITY.with(() -> this), WorldPacketData.of(BuiltinSerializers.INTEGER, Item.getId(this.boatItem.get())));
		}
	}

	@OnlyIn(Dist.CLIENT)
	private void setClientBoatItem(int transientId) {
		this.boatItem.set(Registry.ITEM.byId(transientId));
	}

	public BCBoatType getBCBoatType() {
		return this.boatItem.get() instanceof BCBoatItem boatItem ? boatItem.getType() : BCBoatType.INVALID;
	}

	@Override
	public Item getDropItem() {
		return getBCBoatType().materialItem.get();
	}
	
	@Override
	public BCDataManager bcDataManager() {
		return this.deDataManager;
	}
	
	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	public enum Type {
		NONE(() -> Blocks.AIR, () -> Items.OAK_PLANKS, "nope"),
		FOAMSHROOM(DEBlocks.FOAMSHROOM_PLANKS, DEBuildingItems.FOAMSHROOM_BOAT, "foamshroom_boat"),
		FANCY_FOAMSHROOM(DEBlocks.FOAMSHROOM_FANCY_PLANKS, DEBuildingItems.FOAMSHROOM_FANCY_BOAT, "foamshroom_fancy_boat");
		
		public final ResourceLocation texturePath;
		public final Supplier<? extends Block> material;
		public final Supplier<? extends Item> asItem;
		
		Type(Supplier<? extends Block> material, Supplier<? extends Item> item, String name) {
			this.texturePath = new ResourceLocation(DannysExpansion.ID, "textures/entity/boat/" + name + ".png");
			this.material = material;
			this.asItem = item;
		}
	}
}
