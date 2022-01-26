package net.bottomtextdanny.danny_expannny.rendering;

import net.bottomtextdanny.danny_expannny.rendering.ister.*;
import net.minecraftforge.client.IItemRenderProperties;

public final class DERenderProperties {
    public static final IItemRenderProperties SPECIAL_MODELS = new SpecialModelsRenderProperties();
    public static final IItemRenderProperties KITE = new KiteRenderProperties();
    public static final IItemRenderProperties GUN = new GunRenderProperties();
    public static final IItemRenderProperties DANNY_CHEST = new DannyChestRenderProperties();
    public static final IItemRenderProperties BOW = new BowRenderProperties();
    public static final IItemRenderProperties BIG_BOW = new BigBowRenderProperties();
    public static final IItemRenderProperties ANTIQUE_ARMOR = new AntiqueArmorRenderProperties();
}
