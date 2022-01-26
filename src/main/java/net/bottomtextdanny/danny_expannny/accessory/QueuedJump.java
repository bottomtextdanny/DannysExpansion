package net.bottomtextdanny.danny_expannny.accessory;

import net.bottomtextdanny.danny_expannny.accessory.IJumpQueuerAccessory;
import net.bottomtextdanny.danny_expannny.accessory.IQueuedJump;
import net.bottomtextdanny.danny_expannny.accessory.JumpPriority;

public class QueuedJump implements IQueuedJump {
	private boolean used;
    private final JumpPriority priority;
    private final IJumpQueuerAccessory jumpProvider;

    public QueuedJump(IJumpQueuerAccessory jumpProvider, JumpPriority priority) {
        this.jumpProvider = jumpProvider;
        this.priority = priority;
    }

    @Override
    public JumpPriority priority() {
        return this.priority;
    }
	
	@Override
	public boolean canPerform() {
		return getAccessory().canPerformJump(this);
	}

	@Override
	public void performJump() {
        this.jumpProvider.performJump(this);
	}
	
	@Override
	public void reestablish() {
        this.used = false;
	}

	public void use() {
		this.used = true;
	}

	public boolean isUsed() {
		return this.used;
	}

	@Override
    public IJumpQueuerAccessory getAccessory() {
        return this.jumpProvider;
    }
}
