package net.bottomtextdanny.braincell.mod.opengl_helpers;

import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.opengl_helpers.enums.ShaderType;
import net.minecraft.resources.ResourceLocation;

import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL43.glDispatchCompute;

public abstract class ComputationProgram<WF extends ShaderWorkflow> extends GLProgram<WF> {
    private int xThreads, yThreads, zThreads;
    private ResourceLocation shaderSource;

    public ComputationProgram(int xThreads, int yThreads, int zThreads, WF workflow, ResourceLocation key) {
        super(workflow, key);
        try {
            this.xThreads = xThreads;
            this.yThreads = yThreads;
            this.zThreads = zThreads;

            ResourceLocation computationPath = new ResourceLocation(key.getNamespace(), "shaders/computation/" + key.getPath() + ".comp");

            String computationSource = loadResource(computationPath, ShaderType.COMPUTATION);

            this.shaderSource = computationPath;

            int computationID = createShader(ShaderType.COMPUTATION);

            compileShader(computationID, computationSource);
            attachShader(computationID);
            linkProgram();
            deleteShader(computationID);
            this.uniformManager = createUniformManager();
        } catch (Exception exception) {
            workflow.invalidate();
            Braincell.client().logger.error("Danny's Expansion: Unexpected error while building {} shader. Skipping...", key);
            exception.printStackTrace();
        }
    }

    public int getXThreads() {
        return this.xThreads;
    }

    public int getYThreads() {
        return this.yThreads;
    }

    public int getZThreads() {
        return this.zThreads;
    }

    public ResourceLocation getShaderSource() {
        return this.shaderSource;
    }

    @Override
    public void useProgram() {
        glUseProgram(this.programID);
    }

    protected void dispatchProgram() {
        glDispatchCompute(this.xThreads, this.yThreads, this.zThreads);
    }

    @Override
    public void flow() {
        useProgram();
        renderSpace();
        clearProgram();
    }
}
