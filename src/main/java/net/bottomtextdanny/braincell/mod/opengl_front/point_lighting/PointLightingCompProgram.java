package net.bottomtextdanny.braincell.mod.opengl_front.point_lighting;

import com.mojang.math.Matrix4f;
import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.opengl_helpers.ComputationProgram;
import net.bottomtextdanny.braincell.mod.opengl_helpers.UniformManager;
import net.bottomtextdanny.braincell.mod.opengl_helpers.enums.ShaderType;
import net.bottomtextdanny.braincell.underlying.util.pair.Pair;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL43.GL_SHADER_STORAGE_BUFFER;

public class PointLightingCompProgram extends ComputationProgram<PointLightingWorkflow> {
    public static final int POSITION_RADIUS_POINTER = 0;
    private final List<? extends IPointLight> lightList;

    public PointLightingCompProgram(PointLightingWorkflow workflow, List<? extends IPointLight> lightList) {
        super(PointLightingWorkflow.GRID_SCALE_X, PointLightingWorkflow.GRID_SCALE_Y, 1, workflow, new ResourceLocation(Braincell.ID, "point_lighting"));
        this.lightList = lightList;
    }

    @Override
    public UniformManager createUniformManager() {
        return new UniformManager(this,
                Pair.of("lights[].position_rad", PointLightingWorkflow.MAX_LIGHTS)
        );
    }

    @Override
    protected String[] getSourceTransformers(ShaderType type) {
        if (type == ShaderType.COMPUTATION) {
            return new String[]{
                    "&xgrid", String.valueOf(PointLightingWorkflow.GRID_SCALE_Y),
                    "&ygrid", String.valueOf(PointLightingWorkflow.GRID_SCALE_Y),
                    "&x1ingrid", String.valueOf(1.0F / PointLightingWorkflow.GRID_SCALE_X),
                    "&y1ingrid", String.valueOf(1.0F / PointLightingWorkflow.GRID_SCALE_Y),
                    "&xhalf", String.valueOf((PointLightingWorkflow.GRID_SCALE_X - 1.0F) / 2.0F),
                    "&yhalf", String.valueOf((PointLightingWorkflow.GRID_SCALE_Y - 1.0F) / 2.0F),
                    "&g_square", String.valueOf(PointLightingWorkflow.GRID_SCALE_X * PointLightingWorkflow.GRID_SCALE_Y),
                    "&max_lights", String.valueOf(PointLightingWorkflow.MAX_LIGHTS),
                    "&region_lights", String.valueOf(PointLightingWorkflow.MAX_LIGHTS_PER_TILE),
                    "&debugt", PointLightingWorkflow.DEBUG_ENABLED ? "imageStore(debug, pixel_coords, vec4(lightOffset *  0.2, 0.0, 0.0, 1));" : "",
                    "&debugf", PointLightingWorkflow.DEBUG_ENABLED ? "imageStore(debug, pixel_coords, vec4(0.0, 0.0, 0.0, 1));" : ""
            };
        }
        return super.getSourceTransformers(type);
    }

    @Override
    protected void renderSpace() {
        int lightAmount = this.workflow.lightsRendered();
        Matrix4f projection = Braincell.client().getRenderingHandler().getProjectionMatrix().copy();

        Matrix4f projMatInv = projection.copy();

        projMatInv.invert();

        Matrix4f view = Braincell.client().getRenderingHandler().getModelViewMatrix();

        bindBuffer(this.workflow.tileInformationBlock);
        this.workflow.tileInformationBlock.resetData();

        uTextureBinding("depth", Braincell.client().getRenderingHandler().getWorldDepthFramebuffer().getDepthID());
        this.workflow.debugBuffer.bind(2);

        uInteger("light_count", lightAmount);
        uMatrix("view", view);
        uMatrix("inv_proj", projMatInv);
        uMatrix("proj", projection);

        for (int i = 0; i < lightAmount; i++) {
            IPointLight light = this.lightList.get(i);
            uVector4(this.uniformManager.retrieveLocations(POSITION_RADIUS_POINTER)[i], light.position().subtract(MC.gameRenderer.getMainCamera().getPosition()), light.radius());
        }

        dispatchProgram();
        glBindBuffer(GL_SHADER_STORAGE_BUFFER, 0);
    }
}
