package bottomtextdanny.dannys_expansion._base.lazy_recipe.type.item_predicates;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public interface ItemStackPredicate {

    ItemStackPredicateType<?> getType();

    boolean test(ItemStack itemStack);

    @OnlyIn(Dist.CLIENT)
    List<Component> tooltip(TooltipFlag flag);

    @OnlyIn(Dist.CLIENT)
    List<Item> getDisplayableItems();

    void serialize(CompoundTag tag);
}
