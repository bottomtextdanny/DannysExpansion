package bottomtextdanny.dannys_expansion._base.capabilities.item;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = DannysExpansion.ID)
public class DEStackCapability implements ICapabilitySerializable<CompoundTag> {
	private Player holder;
	
	public Player getHolder() {
		return this.holder;
	}
	
	public void setHolder(Player holder) {
		this.holder = holder;
	}
	
	public static Capability<DEStackCapability> CAP;
	
	private final LazyOptional<DEStackCapability> self = LazyOptional.of(() -> this);
	
	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
		return cap == CAP ? this.self.cast() : LazyOptional.empty();
	}
	
	@Override
	public CompoundTag serializeNBT() {
		return new CompoundTag();
	}
	
	@Override
	public void deserializeNBT(CompoundTag nbt) {
	}
}
