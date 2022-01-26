package net.bottomtextdanny.braincell.mod.opengl_helpers;

import static org.lwjgl.opengl.GL43.*;

public class ShaderBuffer {
	private final int id;
	
	public ShaderBuffer(int space) {
		super();
        this.id = glGenBuffers();
		
		glBindBuffer(GL_SHADER_STORAGE_BUFFER, this.id);
		glBufferData(GL_SHADER_STORAGE_BUFFER, new int[space], GL_DYNAMIC_DRAW);
		glBindBuffer(GL_SHADER_STORAGE_BUFFER, 0);
	}

	public void resetData() {
		glClearBufferData(GL_SHADER_STORAGE_BUFFER, GL_R8, GL_RED, GL_INT, (int[]) null);
	}

	public int getId() {
		return this.id;
	}
}
