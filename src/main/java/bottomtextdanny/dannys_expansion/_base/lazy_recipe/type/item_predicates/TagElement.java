package bottomtextdanny.dannys_expansion._base.lazy_recipe.type.item_predicates;

import bottomtextdanny.dannys_expansion._util.DEStringUtil;
import bottomtextdanny.dannys_expansion._base.lazy_recipe.type.ItemStackPredicateTypes;
import com.mojang.blaze3d.platform.InputConstants;
import bottomtextdanny.braincell.mod.network.Connection;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.common.util.Lazy;

import java.util.ArrayList;
import java.util.List;

public final class TagElement implements ItemStackPredicate {
    private static final String TAG = "item";
    private final Lazy<HolderSet.Named<Item>> items;
    private final Lazy<List<Item>> displayedItems;
    private String cachedNormalizedKey;
    private Component cachedHiddenComponent;

    public TagElement(ResourceLocation tagLocation, Lazy<HolderSet.Named<Item>> tagValues) {
        super();
        this.items = tagValues;
        this.displayedItems = Lazy.of(() -> items.get().stream().map(Holder::value).toList());

        Connection.doClientSide(() -> {
            ResourceLocation tagID = tagLocation;

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
    public ItemStackPredicateType<?> getType() {
        return ItemStackPredicateTypes.TAG;
    }

    @Override
    public boolean test(ItemStack itemStack) {
        return this.items.get().contains(itemStack.getItem().builtInRegistryHolder());
    }

    @Override
    public List<Component> tooltip(TooltipFlag flag) {
        List<Component> tooltips = new ArrayList<>();

        tooltips.add(DEStringUtil.translationWithReplacements("ingredient_tooltip.tag", "@tag", this.cachedNormalizedKey));

        if (!InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), Minecraft.getInstance().options.keyShift.getKey().getValue())) {
            tooltips.add(this.cachedHiddenComponent);
        } else {
            if (!this.displayedItems.get().isEmpty()) {
                this.displayedItems.get().forEach(item -> {
                    tooltips.add(new TextComponent(item.getName(item.getDefaultInstance()).getString()).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
                });
            }
        }
        return tooltips;
    }

    @Override
    public List<Item> getDisplayableItems() {
        return this.displayedItems.get();
    }

    @Override
    public void serialize(CompoundTag tag) {
        tag.putString(TAG, this.items.get().key().location().toString());
    }
}
