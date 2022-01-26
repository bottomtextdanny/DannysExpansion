package net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex;

import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public abstract class BCSimpleModel extends Model implements BCModel {
    public int texWidth, texHeight;

    public BCSimpleModel(Function<ResourceLocation, RenderType> p_103110_) {
        super(p_103110_);
    }

    public int getTexWidth() {
        return this.texWidth;
    }

    public int getTexHeight() {
        return this.texHeight;
    }
}
