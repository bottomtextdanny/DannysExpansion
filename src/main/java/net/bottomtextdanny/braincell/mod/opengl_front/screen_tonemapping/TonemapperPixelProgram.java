package net.bottomtextdanny.braincell.mod.opengl_front.screen_tonemapping;

import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.opengl_helpers.FragmentProgram;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.minecraft.resources.ResourceLocation;

public class TonemapperPixelProgram extends FragmentProgram<TonemapWorkflow> {

    public TonemapperPixelProgram(TonemapWorkflow workflow) {
        super(workflow, new ResourceLocation(Braincell.ID, "tonemapper"));
    }

    @Override
    protected void renderSpace() {
        uTextureBinding("diffuse", MAIN_TARGET.getColorTextureId());
        uVector3("exponent", this.workflow.getOutChannelMultiplier());
        uFloat("desaturator", this.workflow.getOutDesaturation());
        renderScreenQuad();
    }
}
