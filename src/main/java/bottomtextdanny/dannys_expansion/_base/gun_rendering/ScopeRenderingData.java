package bottomtextdanny.dannys_expansion._base.gun_rendering;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public record ScopeRenderingData(ResourceLocation[] scopeTexturePaths, int scopeSize) {}
