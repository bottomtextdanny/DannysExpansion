package bottomtextdanny.dannys_expansion.content.items;

import bottomtextdanny.dannys_expansion.content.accessories.CoreAccessory;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import bottomtextdanny.braincell.mod.capability.player.accessory.AccessoryAttributeModifierData;
import bottomtextdanny.braincell.mod.capability.player.accessory.AccessoryKey;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class AccessoryItem extends Item implements ItemDescriptionAutogen {
	public static final Map<AccessoryKey<?>, AccessoryAttributeModifierData> ATTRIBUTE_DATA = Maps.newHashMap();
	public static final LinkedList<Function<AccessoryItem, AccessoryAttributeModifierData>> ACCESSORY_DATA_POPULATORS = Lists.newLinkedList();
    public final AccessoryKey<? extends CoreAccessory> accessoryKey;
    private CoreAccessory dummy;

    public AccessoryItem(AccessoryKey<? extends CoreAccessory> accessoryKey, Properties properties) {
        super(properties.stacksTo(1));
        this.accessoryKey = accessoryKey;
    }

	@OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        getDummyAccessory().appendHoverText(stack, worldIn, tooltip, flagIn);
	}
    
    public CoreAccessory getDummyAccessory() {
        this.dummy = this.accessoryKey.create(null);
    	return this.dummy;
    }

	@OnlyIn(Dist.CLIENT)
	@Override
	public String specifyDescriptionPath() {
		return "accessory";
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public String getGenerationDescription() {
		return this.accessoryKey.create(null).getGeneratedDescription();
	}

}
