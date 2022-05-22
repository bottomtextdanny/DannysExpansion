package bottomtextdanny.dannys_expansion._base.lazy_recipe.type;

import bottomtextdanny.dannys_expansion._util.DEStringUtil;
import com.google.common.collect.Streams;
import com.mojang.blaze3d.platform.InputConstants;
import bottomtextdanny.braincell.base.BCLerp;
import bottomtextdanny.braincell.mod.network.Connection;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TagIngredient extends LazyRegistryIngredient {
	private List<Item> cachedItemTagContent = List.of();
    private final TagKey<Item> itemTag;
    private String cachedNormalizedKey;
	private Component cachedHiddenComponent;
	private int screenItemPointer;
    private final int count;
	private int leftCountPointer;
	private int inventoryCountPointer;
	private int countColor;

    public TagIngredient(TagKey<Item> itemTag1, int count1) {
    	super(null);
        this.itemTag = itemTag1;
        this.count = count1;

		Connection.doClientSide(() -> {
		    this.cachedItemTagContent = Streams.stream(Registry.ITEM.getTagOrEmpty(this.itemTag)).map(Holder::value).collect(Collectors.toList());

			ResourceLocation tagID = this.itemTag.location();

            this.cachedNormalizedKey = DEStringUtil.normalizeKey(tagID.getPath());
      
		    String keyStr = new TranslatableComponent(Minecraft.getInstance().options.keyShift.getKey().getName()).getString();
		    String decodedInfoText = DEStringUtil.translationWithReplacements("ingredient_tooltip.tag_content_hidden", "@tag", this.cachedNormalizedKey).getText();
		    
		    String[] split = decodedInfoText.split("@key", 2);
		
		    if (split.length > 1) {
                this.cachedHiddenComponent = new TextComponent(split[0]).withStyle(ChatFormatting.GRAY)
				    .append(new TextComponent(keyStr).withStyle(ChatFormatting.YELLOW))
				    .append(new TextComponent(split[1]).withStyle(ChatFormatting.GRAY));
		    } else {
		    	decodedInfoText = decodedInfoText.replace("@key", keyStr);
                this.cachedHiddenComponent = new TextComponent(decodedInfoText).withStyle(ChatFormatting.GRAY);
		    }
	    });
    }
	
	@Override
	public void collectResources(ItemStack inventoryStack) {
		if (!inventoryStack.isDamaged() && inventoryStack.is(this.itemTag)) {
			this.inventoryCountPointer += inventoryStack.getCount();
		}
	}

	@Override
	public boolean isItemStackRelevant(ItemStack stack) {
		return stack.is(this.itemTag);
	}

	@Override
	public List<ItemStack> getRelevantIemStacks() {
		return this.cachedItemTagContent.stream().map(item -> new ItemStack(item, this.count)).collect(Collectors.toList());
	}

	@Override
	public boolean iterationOnTake(Inventory inventory, ItemStack iteration) {
		if (iteration.is(this.itemTag)) {
			if (iteration.getCount() < this.leftCountPointer) {
				inventory.removeItem(iteration);
				this.leftCountPointer -= iteration.getCount();
			} else {
				iteration.setCount(iteration.getCount() - this.leftCountPointer);
				this.leftCountPointer = 0;
			}
		}
		return this.leftCountPointer <= 0;
	}

	@Override
	public LazyIngredientState resetCollectionData() {
		final int oldCountPointer = this.inventoryCountPointer;
		this.inventoryCountPointer = 0;
		if (oldCountPointer >= this.count) {
			return LazyIngredientState.MATCH;
		}  else if (oldCountPointer > 0) {
			return LazyIngredientState.UNFULFILLED_MATCH;
		} else {
			return LazyIngredientState.NO_MATCH;
		}
	}

	@Override
	public void resetConsumeData() {
		this.leftCountPointer = this.count;
	}

	@Override
	public boolean filter(Item filterBy) {
		return this.cachedItemTagContent.contains(filterBy);
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public Item screenItem() {
    	if (cachedItemTagContent != null && cachedItemTagContent.size() > 0) {
            this.screenItemPointer = (this.screenItemPointer + 1) % (15 * cachedItemTagContent.size() - 1);
		    return cachedItemTagContent.get(this.screenItemPointer / 15);
	    }
    	
    	return Items.BARRIER;
	}

	@Override
	public List<Component> tooltip(TooltipFlag flag) {
		List<Component> tooltips = new ArrayList<>();

		tooltips.add(DEStringUtil.translationWithReplacements("ingredient_tooltip.tag", "@tag", this.cachedNormalizedKey));

		if (!InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), Minecraft.getInstance().options.keyShift.getKey().getValue())) {
			tooltips.add(this.cachedHiddenComponent);
		} else {
			if (cachedItemTagContent != null && cachedItemTagContent.size() > 0) {
				cachedItemTagContent.forEach(item -> {
					tooltips.add(new TextComponent(item.getName(item.getDefaultInstance()).getString()).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
				});
			}
		}

		return tooltips;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public int getCountDisplayColor() {
		return this.countColor;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void onScreenModification(Inventory inventory) {
		int countInInventory = 0;

		for (ItemStack stack : inventory.items) {

			if (stack.is(this.itemTag) && !stack.isDamaged()) {
				countInInventory += stack.getCount();
				if (countInInventory >= this.count) {
					return;
				}
			}
		}
		if (countInInventory >= this.count) {
			this.countColor = 0x00FF00;
		} else if (countInInventory > 0) {
			double step = (double)countInInventory / (double)this.count;
			this.countColor = (int) BCLerp.get(step, 256.0, 196.0) << 16;
			this.countColor += (int)BCLerp.get(step, 64.0, 256.0) << 8;
		} else {
			this.countColor = 0xDD0000;
		}
	}
	
	public TagKey<Item> getItemTag() {
        return this.itemTag;
    }

    public int getCount() {
        return this.count;
    }

	public CompoundTag serialize() {
        CompoundTag compound = new CompoundTag();

        ResourceLocation mabyTagID = this.itemTag.location();
        
        if (mabyTagID != null) {
	        compound.putString("tag", mabyTagID.toString());
	        compound.putInt("count", this.count);
	        return compound;
        }
        return compound;
    }

    public TagIngredient copy() {
        return  new TagIngredient(getItemTag(), getCount());
    }
}
