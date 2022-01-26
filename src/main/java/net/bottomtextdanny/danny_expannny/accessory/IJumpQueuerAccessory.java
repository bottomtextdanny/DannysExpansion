package net.bottomtextdanny.danny_expannny.accessory;

public interface IJumpQueuerAccessory extends IAccessory {

    boolean canPerformJump(IQueuedJump jumpType);

    void performJump(IQueuedJump jumpType);

    IQueuedJump[] provideJumps();
}
