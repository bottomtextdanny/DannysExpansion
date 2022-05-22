package bottomtextdanny.dannys_expansion.content._client.rendering.entity.mob.hollow_armor;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content._client.model.entities.living_entities.HollowArmorModel;
import bottomtextdanny.dannys_expansion.content.entities.mob.hollow_armor.HollowArmor;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class HollowArmorRenderer extends MobRenderer<HollowArmor, HollowArmorModel> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/hollow_armor/hollow_armor.png");
    private static final ResourceLocation TEXTURES_OLD = new ResourceLocation(DannysExpansion.ID, "textures/entity/hollow_armor/hollow_armor_old.png");

    public HollowArmorRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public HollowArmorRenderer(EntityRendererProvider.Context manager) {
        super(manager, new HollowArmorModel(), 0.4F);
        this.addLayer(new HollowArmorBladeLayer(this));
        this.addLayer(new HollowArmorItemLayer(this));
    }

    @Override
    protected float getFlipDegrees(HollowArmor entityLivingBaseIn) {
        return 0.0F;
    }

    public ResourceLocation getTextureLocation(HollowArmor entity) {
        return entity.getDisplayName().getString().equalsIgnoreCase("daniel") ? TEXTURES_OLD : TEXTURES;
    }
}
