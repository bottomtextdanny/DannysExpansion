package net.bottomtextdanny.braincell.mod.opengl_helpers;

import com.mojang.math.Matrix4f;
import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.opengl_helpers.enums.ShaderType;
import net.minecraft.resources.ResourceLocation;

import java.util.EnumMap;

public abstract class FragmentProgram<WF extends ShaderWorkflow> extends GLProgram<WF> {
    private static final String PROJ_MATRIX_NAME = "proj_mat";
    private static final String SCREN_SIZE_NAME = "screen";
    public final EnumMap<ShaderType, ResourceLocation> shaderSourceMap = new EnumMap<>(ShaderType.class);


    public FragmentProgram(WF workflow, ResourceLocation key) {
        super(workflow, key);
        try {
            ResourceLocation vertexPath = new ResourceLocation(key.getNamespace(), "shaders/vertex/" + key.getPath() + ".vert");
            ResourceLocation fragmentPath = new ResourceLocation(key.getNamespace(), "shaders/fragment/" + key.getPath() + ".frag");
            ResourceLocation geometryPath = new ResourceLocation(key.getNamespace(), "shaders/geometry/" + key.getPath() + ".geom");

            String vertexSource = loadResource(vertexPath, ShaderType.VERTEX);

            String fragmentSource = loadResource(fragmentPath, ShaderType.FRAGMENT);
            String geometrySource = loadResource(geometryPath, ShaderType.GEOMETRY);

            this.shaderSourceMap.put(ShaderType.VERTEX, vertexPath);
            this.shaderSourceMap.put(ShaderType.FRAGMENT, fragmentPath);

            int vertexID = createShader(ShaderType.VERTEX);
            int fragmentID = createShader(ShaderType.FRAGMENT);
            int geometryID = -1;


            compileShader(vertexID, vertexSource);
            compileShader(fragmentID, fragmentSource);

            boolean geomFlag = geometrySource != null;

            if (geomFlag) {
                this.shaderSourceMap.put(ShaderType.GEOMETRY, geometryPath);

                geometryID = createShader(ShaderType.GEOMETRY);

                compileShader(geometryID, geometrySource);
                attachShader(geometryID);
            }

            attachShader(vertexID);
            attachShader(fragmentID);
            linkProgram();

            if (geomFlag) {
                deleteShader(geometryID);
            }

            deleteShader(vertexID, fragmentID);
            this.uniformManager = createUniformManager();
        } catch (Exception exception) {
            workflow.invalidate();
            Braincell.client().logger.error("Danny's Expansion: Unexpected error while building {} shader. Skipping...", key);
            exception.printStackTrace();
        }
    }

    @Override
    public void flow() {
        useProgram();
        uMatrix(PROJ_MATRIX_NAME, Matrix4f.orthographic(MC.getWindow().getWidth(), (float) -MC.getWindow().getHeight(), 1000.0F, 3000.0F));
        uVector2(SCREN_SIZE_NAME, MC.getWindow().getWidth(), MC.getWindow().getHeight());
        renderSpace();
        clearProgram();
    }
}
