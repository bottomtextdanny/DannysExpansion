package net.bottomtextdanny.braincell.mod.entity.psyche.data;

import java.io.DataInput;
import java.util.function.Supplier;

public interface SupplierActionInput<T> extends ActionInput, Supplier<T> {
}
