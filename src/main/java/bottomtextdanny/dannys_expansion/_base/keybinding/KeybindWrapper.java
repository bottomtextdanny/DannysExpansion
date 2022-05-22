package bottomtextdanny.dannys_expansion._base.keybinding;


import net.minecraft.client.KeyMapping;

public class KeybindWrapper {
    final KeyMapping keyBinding;
    public boolean prevState;
    public boolean state;

    public KeybindWrapper(KeyMapping key) {
        this.keyBinding = key;
    }

    public void tick() {
        this.prevState = this.state;
        this.state = this.keyBinding.isDown();
    }

    public boolean onPressAction() {
        return !this.prevState && this.state;
    }

    public boolean onUnpressAction() {
        return this.prevState && !this.state;
    }

    public boolean onHoldingAction() {
        return this.prevState && this.state;
    }

    public KeyMapping getKeyBinding() {
        return this.keyBinding;
    }
}
