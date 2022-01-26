package net.bottomtextdanny.braincell.mod.opengl_helpers;


import com.mojang.blaze3d.platform.TextureUtil;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL43.*;

public class TextureBuffer {
	private final int texId;
	private final int width;
    private final int height;
	
	public TextureBuffer(int width, int height) {
		this.width = width;
		this.height = height;
        this.texId = TextureUtil.generateTextureId();
		glBindTexture(GL_TEXTURE_2D, this.texId);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA32F, width, height, 0, GL_RGBA, GL_FLOAT, (ByteBuffer)null);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		
	}
	
	public void bind(int unit) {
		glBindImageTexture(unit, this.texId, 0, false, 0, GL_READ_WRITE, GL_RGBA32F);
	}
	
	public int getId() {
		return this.texId;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
}
