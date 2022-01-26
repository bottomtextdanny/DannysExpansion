package net.bottomtextdanny.danny_expannny.structure.client_sided.gun_rendering;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public record ScopeRenderingData(ResourceLocation[] scopeTexturePaths, int scopeSize) {}
