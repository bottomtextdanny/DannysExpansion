package net.bottomtextdanny.danny_expannny.accessory;

public interface IQueuedJump {

	boolean canPerform();

    void performJump();
    
    void reestablish();

    JumpPriority priority();

    IJumpQueuerAccessory getAccessory();
}
