package net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation.recipe;

import com.google.common.collect.Sets;
import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.platform.InputConstants;
import net.bottomtextdanny.braincell.mod.base.annotations.OnlyHandledOnClient;
import net.bottomtextdanny.braincell.mod.base.util.Connection;
import net.bottomtextdanny.danny_expannny.ClientInstance;
import net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation.LazyRegistryIngredient;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.MutableLatch;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.SerializationTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class TagIngredient extends LazyRegistryIngredient {
	public static final Supplier<TagIngredient> BROKEN = () -> new TagIngredient(Tag.fromSet(Sets.newHashSet(Items.BARRIER)), Short.MAX_VALUE);
	public static final MutableLatch<List<Item>> cachedItemTagContent = MutableLatch.empty();
    public final Tag<Item> itemTag;
    public String cachedNormalizedKey;
	@OnlyHandledOnClient
	public Component cachedHiddenComponent;
	@OnlyHandledOnClient
    public int screenItemPointer;
    private final int count;
	private int leftCountPointer;
	private int inventoryCountPointer;
	private @OnlyHandledOnClient int countColor;

    public TagIngredient(Tag<Item> itemTag1, int count1) {
    	super(LazyDeserializers.TAG_DESERIALIZER);
        this.itemTag = itemTag1;
        this.count = count1;

		Connection.doClientSide(() -> {
		    cachedItemTagContent.setLocked(this.itemTag.getValues());

			ResourceLocation tagID = SerializationTags.getInstance().getIdOrThrow(Registry.ITEM_REGISTRY, this.itemTag, () -> {
				return new IllegalStateException("Unknown item tag");
			});

            this.cachedNormalizedKey = DEUtil.normalizeKey(tagID.getPath());
      
		    String keyStr = new TranslatableComponent(Minecraft.getInstance().options.keyShift.getKey().getName()).getString();
		    String decodedInfoText = DEUtil.translationWithReplacements("ingredient_tooltip.tag_content_hidden", "@tag", this.cachedNormalizedKey).getText();
		    
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
		return this.itemTag.getValues().stream().map(item -> new ItemStack(item, this.count)).collect(Collectors.toList());
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
		return this.itemTag.contains(filterBy);
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public Item screenItem() {
    	if (cachedItemTagContent.get() != null && cachedItemTagContent.get().size() > 0) {
            this.screenItemPointer = (this.screenItemPointer + 1) % (15 * cachedItemTagContent.get().size() - 1);
		    return cachedItemTagContent.get().get(this.screenItemPointer / 15);
	    }
    	
    	return Items.BARRIER;
	}

	@Override
	public List<Component> tooltip(TooltipFlag flag) {
		List<Component> tooltips = new ArrayList<>();

		tooltips.add(DEUtil.translationWithReplacements("ingredient_tooltip.tag", "@tag", this.cachedNormalizedKey));

		if (!InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), Minecraft.getInstance().options.keyShift.getKey().getValue())) {
			tooltips.add(this.cachedHiddenComponent);
		} else {
			if (cachedItemTagContent.get() != null && cachedItemTagContent.get().size() > 0) {
				cachedItemTagContent.get().forEach(item -> {
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
					this.countColor = 0x00FF00;
					return;
				}
			}
		}
		this.countColor = 0xFFFFFF;
	}
	
	public Tag<Item> getItemTag() {
        return this.itemTag;
    }

    public int getCount() {
        return this.count;
    }

	public CompoundTag serialize() {
        CompoundTag compound = new CompoundTag();

        ResourceLocation mabyTagID = SerializationTags.getInstance().getIdOrThrow(Registry.ITEM_REGISTRY, this.itemTag, () -> {
			return new IllegalStateException("Unknown item tag");
		});
        
        if (mabyTagID != null) {
	        compound.putString("tag", mabyTagID.toString());
	        compound.putInt("count", this.count);
	        return compound;
        }
        return compound;
    }
	
	public TagIngredient deserialize(CompoundTag compound) {
		
		String tagID = compound.getString("tag");
    	
    	if (tagID.isEmpty()) return BROKEN.get();

		Tag<Item> tag = SerializationTags.getInstance().getTagOrThrow(Registry.ITEM_REGISTRY, new ResourceLocation(tagID), p_151262_ -> {
			return new JsonSyntaxException("Unknown item tag '" + p_151262_ + "'");
		});

        if (tag == null || tag.getValues().isEmpty()) return BROKEN.get();
        
		int desCount;
        desCount = compound.getInt("count");

        return new TagIngredient(tag, desCount);
    }

    public TagIngredient copy() {
        return  new TagIngredient(getItemTag(), getCount());
    }
}
