package bottomtextdanny.dannys_expansion.tables._client;

import bottomtextdanny.dannys_expansion.content._client.rendering.ister.*;
import net.minecraftforge.client.IItemRenderProperties;

public final class DERenderProperties {
    public static final IItemRenderProperties SPECIAL_MODELS = new SpecialModelsRenderProperties();
    public static final IItemRenderProperties GUN = new GunRenderProperties();
    public static final IItemRenderProperties DANNY_CHEST = new DannyChestRenderProperties();
    public static final IItemRenderProperties BOW = new BowRenderProperties();
    public static final IItemRenderProperties BIG_BOW = new BigBowRenderProperties();
}
