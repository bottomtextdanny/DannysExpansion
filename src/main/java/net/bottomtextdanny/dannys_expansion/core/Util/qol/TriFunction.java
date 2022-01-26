package net.bottomtextdanny.dannys_expansion.core.Util.qol;

import java.util.Objects;
import java.util.function.Function;

public interface TriFunction<T1, T2, T3, R> {

	R apply(T1 t, T2 u, T3 u1);
	
    default<V> TriFunction<T1, T2, T3, V> andThen(Function<? super R,?extends V> after){
	Objects.requireNonNull(after);
	return(T1 t, T2 u, T3 u1)->after.apply(apply(t,u, u1));
	}
}
