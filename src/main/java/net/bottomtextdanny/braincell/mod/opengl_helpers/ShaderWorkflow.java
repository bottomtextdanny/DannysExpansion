package net.bottomtextdanny.braincell.mod.opengl_helpers;

import net.minecraft.client.Minecraft;

public abstract class ShaderWorkflow {
    public static final Minecraft MC = Minecraft.getInstance();
    protected boolean invalidated;

    protected abstract void execute();

    protected abstract void tick();

    public void _processFrame() {
        if (!this.invalidated && shouldApply()) {
            execute();
        }
    }

    public void _processTick() {
        if (!this.invalidated && shouldApply()) {
            tick();
        }
    }

    protected abstract boolean shouldApply();

    public void invalidate() {
        this.invalidated = false;
    }

    public boolean isInvalid() {
        return this.invalidated;
    }
}
