package net.bottomtextdanny.braincell.mod.structure.client_sided;

import net.bottomtextdanny.braincell.mod.opengl_front.point_lighting.PointLightingWorkflow;
import net.bottomtextdanny.braincell.mod.opengl_front.screen_tonemapping.TonemapWorkflow;
import net.bottomtextdanny.braincell.mod.opengl_helpers.GLProgram;

public final class PostprocessingHandler {
    private TonemapWorkflow tonemapWorkflow;
    private PointLightingWorkflow lightingWorkflow;

    public void frame() {
        if (this.lightingWorkflow != null) {
            this.lightingWorkflow._processFrame();
        } else {
            this.lightingWorkflow = new PointLightingWorkflow();
        }
        if (this.tonemapWorkflow != null) {
            this.tonemapWorkflow._processFrame();
        } else {
            this.tonemapWorkflow = new TonemapWorkflow();
        }

        GLProgram.renderScreenQuad();
    }

    public void tick() {
        if (this.lightingWorkflow != null) {
            this.lightingWorkflow._processTick();
        }
        if (this.tonemapWorkflow != null) {
            this.tonemapWorkflow._processTick();
        }
    }

    public PointLightingWorkflow getLightingWorkflow() {
        return this.lightingWorkflow;
    }

    public TonemapWorkflow getTonemapWorkflow() {
        return this.tonemapWorkflow;
    }
}
