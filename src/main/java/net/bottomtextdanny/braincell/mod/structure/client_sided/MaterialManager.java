package net.bottomtextdanny.braincell.mod.structure.client_sided;

import com.google.common.collect.Lists;
import net.bottomtextdanny.braincell.mod.world.block_utilities.ChestMaterialProvider;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public final class MaterialManager {
    private final List<ResourceLocation> deferredChestLocations = Lists.newArrayList();
    private final List<Material> chestMaterials = Lists.newArrayList();

    public MaterialManager() {
        super();
    }

    public void sendListeners() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener((TextureStitchEvent.Pre event) -> {
            if (event.getAtlas().location().equals(Sheets.CHEST_SHEET)) {
                this.deferredChestLocations.forEach(event::addSprite);
            }
        });
    }

    public Material getChestMaterial(ChestMaterialProvider provider, ChestMaterial type) {
        return this.chestMaterials.get(provider.getChestMaterialSlot() + type.ordinal());
    }

    public void deferMaterialsForChest(ChestMaterialProvider chestSpriteProvider) {
        ResourceLocation[] texturePaths = chestSpriteProvider.chestTexturePaths();
        chestSpriteProvider.setChestMaterialSlot(this.chestMaterials.size());
        generateChestBit(texturePaths[ChestMaterialProvider.SINGLE_SHIFT]);
        generateChestBit(texturePaths[ChestMaterialProvider.LEFT_SHIFT]);
        generateChestBit(texturePaths[ChestMaterialProvider.RIGHT_SHIFT]);
    }

    private void generateChestBit(ResourceLocation chestTexturePath) {
        this.deferredChestLocations.add(chestTexturePath);
        Material material = new Material(Sheets.CHEST_SHEET, chestTexturePath);
        this.chestMaterials.add(material);
    }
}
