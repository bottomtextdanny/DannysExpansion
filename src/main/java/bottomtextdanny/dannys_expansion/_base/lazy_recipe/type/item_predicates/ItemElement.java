package bottomtextdanny.dannys_expansion._base.lazy_recipe.type.item_predicates;

import bottomtextdanny.dannys_expansion._util._client.CMC;
import bottomtextdanny.dannys_expansion._base.lazy_recipe.type.ItemStackPredicateTypes;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Collections;
import java.util.List;

public final class ItemElement implements ItemStackPredicate {
    private final Item item;
    private final List<Item> displayedItem;

    public ItemElement(Item item) {
        super();
        this.item = item;
        this.displayedItem = Collections.singletonList(item);
    }

    @Override
    public ItemStackPredicateType<?> getType() {
        return ItemStackPredicateTypes.ITEM;
    }

    @Override
    public boolean test(ItemStack itemStack) {
        return itemStack.getItem() == this.item;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public List<Component> tooltip(TooltipFlag flag) {
        return new ItemStack(this.item).getTooltipLines(CMC.player(), flag);
    }

    @Override
    public List<Item> getDisplayableItems() {
        return this.displayedItem;
    }

    @Override
    public void serialize(CompoundTag tag) {
        tag.putInt("item", Registry.ITEM.getId(this.item));
    }
}
