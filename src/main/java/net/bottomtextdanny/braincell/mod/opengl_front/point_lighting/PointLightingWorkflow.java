package net.bottomtextdanny.braincell.mod.opengl_front.point_lighting;

import net.bottomtextdanny.braincell.mod.opengl_helpers.ShaderBuffer;
import net.bottomtextdanny.braincell.mod.opengl_helpers.ShaderWorkflow;
import net.bottomtextdanny.braincell.mod.opengl_helpers.TextureBuffer;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL42.*;

public class PointLightingWorkflow extends ShaderWorkflow {
    public static final int MAX_LIGHTS = 256;
    public static final int MAX_LIGHTS_PER_TILE = 50;
    public static final int GRID_SCALE_X = 24;
    public static final int GRID_SCALE_Y = 14;
    public static final boolean DEBUG_ENABLED = false;
    private final PointLightingPixelProgram lightRenderingProgram;
    private final PointLightingCompProgram frustumCalcProgram;
    public final ShaderBuffer tileInformationBlock;
    public final TextureBuffer debugBuffer;
    private final List<IPointLight> lights = new ArrayList<>();
    private int lastRenderedLights;
    private int lightCounter;

    public PointLightingWorkflow() {
        this.lightRenderingProgram = new PointLightingPixelProgram(this, this.lights);
        this.frustumCalcProgram = new PointLightingCompProgram(this, this.lights);

        this.debugBuffer = new TextureBuffer(GRID_SCALE_X, GRID_SCALE_Y);
        this.tileInformationBlock = new ShaderBuffer(GRID_SCALE_X * GRID_SCALE_Y * MAX_LIGHTS_PER_TILE + GRID_SCALE_X * GRID_SCALE_Y);
    }

    @Override
    protected void execute() {
        this.lightCounter = this.lastRenderedLights;

        this.frustumCalcProgram.flow();
        glMemoryBarrier(GL_BUFFER_UPDATE_BARRIER_BIT | GL_PIXEL_BUFFER_BARRIER_BIT);
        this.lightRenderingProgram.flow();

        this.lastRenderedLights = 0;

        clearLights();
    }

    @Override
    protected void tick() {
    }

    public boolean addLight(IPointLight light) {
        if (this.lights.size() < MAX_LIGHTS) {
            this.lastRenderedLights++;
            return this.lights.add(light);
        }
        return false;
    }

    public void clearLights() {
        this.lights.clear();
    }

    public boolean drawingLights() {
        return !this.lights.isEmpty();
    }

    public int lightsRendered() {
        return this.lightCounter;
    }

    @Override
    protected boolean shouldApply() {
        return true;
    }
}
