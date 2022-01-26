package net.bottomtextdanny.braincell.mod.opengl_helpers;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import java.nio.IntBuffer;

@OnlyIn(Dist.CLIENT)
public final class BCFramebuffer {
    private final int id;
    public int width;
    public int height;
    private final int depthTex;
    private final int colorTex;

    public BCFramebuffer(int textureWidth, int textureHeight, boolean depth) {
        this.width = textureWidth;
        this.height = textureHeight;
        this.id = GL30.glGenFramebuffers();
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, this.id);
        this.colorTex = TextureUtil.generateTextureId();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.colorTex);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL30.GL_RGBA, this.width, this.height, 0, GL11.GL_RGBA, 5121, (IntBuffer)null);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL30.GL_TEXTURE_2D, this.colorTex, 0);
	    if (depth) {
            this.depthTex = TextureUtil.generateTextureId();
		    GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.depthTex);
		    GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_DEPTH_COMPONENT, this.width, this.height, 0, GL11.GL_DEPTH_COMPONENT, 5126, (IntBuffer) null);
		    GlStateManager._texParameter(3553, 10241, 9728);
		    GlStateManager._texParameter(3553, 10240, 9728);
		    GlStateManager._texParameter(3553, 10242, 10496);
		    GlStateManager._texParameter(3553, 10243, 10496);
		    GlStateManager._texParameter(3553, 34892, 0);
		    GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_TEXTURE_2D, this.depthTex, 0);
	    } else {
            this.depthTex = -1;
	    }
        if (GL30.glCheckFramebufferStatus(GL30.GL_FRAMEBUFFER) != GL30.GL_FRAMEBUFFER_COMPLETE) {
            System.out.println("fbo failed");
        }
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, Minecraft.getInstance().getMainRenderTarget().frameBufferId);
    }

    public int getId() {
        return this.id;
    }

    public int getColorID() {
        return this.colorTex;
    }

    public int getDepthID() {
        return this.depthTex;
    }
}
