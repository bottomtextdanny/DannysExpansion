package net.bottomtextdanny.danny_expannny.capabilities.player;

import com.google.common.collect.ImmutableList;
import net.bottomtextdanny.danny_expannny.objects.accessories.StackAccessory;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class HandAccessories {
    public static final ImmutableList<Function<PlayerAccessoryModule, StackAccessory>> ACCESSORY_FOR_HAND = ImmutableList.of(
            module -> module.getHandManager().main,
            module -> module.getHandManager().off
    );
    public static final ImmutableList<Function<PlayerAccessoryModule, StackAccessory>> ACCESSORYO_FOR_HAND = ImmutableList.of(
            module -> module.getHandManager().mainO,
            module -> module.getHandManager().offO
    );
    public static final ImmutableList<BiConsumer<PlayerAccessoryModule, StackAccessory>> SET_BY_HAND = ImmutableList.of(
            (module, newStack) -> module.getHandManager().setMain(newStack),
            (module, newStack) -> module.getHandManager().setOff(newStack)
    );
    private StackAccessory mainO;
    private StackAccessory offO;
    private StackAccessory main;
    private StackAccessory off;

    public HandAccessories() {
        super();
        this.mainO = StackAccessory.EMPTY;
        this.offO = StackAccessory.EMPTY;
        this.main = StackAccessory.EMPTY;
        this.off = StackAccessory.EMPTY;
    }

    public void updatePrevious() {
        this.mainO = this.main;
        this.offO = this.off;
    }

    public void setMain(StackAccessory mainAccessory) {
        this.main = mainAccessory;
    }

    public void setOff(StackAccessory offAccessory) {
        this.off = offAccessory;
    }

    public StackAccessory getMain() {
        return this.main;
    }

    public StackAccessory getOff() {
        return this.off;
    }
}
