package net.bottomtextdanny.danny_expannny.objects.entities.kite;

import net.bottomtextdanny.danny_expannny.objects.items.SpecialKiteItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SpecialKiteEntity extends KiteEntity {
    public String cachedTextureId = "none";
    public String cachedCordTextureId = "minecraft:textures/block/white_wool";
    public byte cachedModel;
    public boolean cachedIsFullbright;

    public SpecialKiteEntity(EntityType<? extends KiteEntity> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    public static SpecialKiteEntity provideSpecial(EntityType<? extends KiteEntity> entityTypeIn, Level worldIn, ItemStack kiteItemstack) {
        SpecialKiteEntity kiteInst = new SpecialKiteEntity(entityTypeIn, worldIn);

        kiteInst.itemstack = kiteItemstack;

        if (kiteInst.itemstack != null) {

            kiteInst.updateData();
        }

        return kiteInst;
    }

    @Override
    public void updateData() {
        SpecialKiteItem sKiteItem = getSpecialItem();
        this.cachedTextureId = sKiteItem.getTextureName();
        this.cachedCordTextureId = sKiteItem.getCordTextureId();
        this.cachedIsFullbright = sKiteItem.isFullbright();
        this.cachedModel = sKiteItem.getModel();
    }

    public SpecialKiteItem getSpecialItem() {
        return (SpecialKiteItem) this.itemstack.getItem();
    }
}
