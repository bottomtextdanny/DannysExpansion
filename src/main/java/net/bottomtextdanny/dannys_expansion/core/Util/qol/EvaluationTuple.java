package net.bottomtextdanny.dannys_expansion.core.Util.qol;

import com.google.common.collect.Lists;

import java.util.LinkedList;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public final class EvaluationTuple<A0, A1, B> extends AbstractEvaluation {
	private final LinkedList<Consumer<Evaluator<A0, A1, B>>> evaluatorList;
	private final String name;
	private final BiFunction<A0, A1, B> evalFactory;
	
	private EvaluationTuple(String name, BiFunction<A0, A1, B> evalFactory, LinkedList<Consumer<Evaluator<A0, A1, B>>> commonPredicates) {
		super();
		this.name = name;
		this.evalFactory = evalFactory;
		this.evaluatorList = commonPredicates;
		evaluations.add(this);
	}
	
	public static <C0, C1, D> EvaluationTuple<C0, C1, D> create(String name, BiFunction<C0, C1, D> evalFactory, Function<LinkedList<Consumer<Evaluator<C0, C1, D>>>, LinkedList<Consumer<Evaluator<C0, C1, D>>>> commonPredicates) {
		if (built) throw new UnsupportedOperationException("Can't create evaluations after building!");

		return new EvaluationTuple<>(name, evalFactory, commonPredicates.apply(Lists.newLinkedList()));
	}
	
	public static <C0, C1, D> EvaluationTuple<C0, C1, D> create(String name, BiFunction<C0, C1, D> evalFactory) {
		if (built) throw new UnsupportedOperationException("Can't create evaluations after building!");

		return new EvaluationTuple<C0, C1, D>(name, evalFactory, Lists.newLinkedList());
	}
	
	public void addTest(Consumer<Evaluator<A0, A1, B>> pred) {
		this.evaluatorList.add(pred);
	}
	
	public B test(A0 subject0, A1 subject1) {
		Evaluator<A0, A1, B> current = Evaluator.of(subject0, subject1, this.evalFactory.apply(subject0, subject1));
		for (Consumer<Evaluator<A0, A1, B>> cons :
                this.evaluatorList) {
			cons.accept(current);
			if (current.cancelled) break;
		}
	
		return current.get();
	}
	
	public static class Evaluator<R0, R1, L> {
		private boolean cancelled;
		private L value;
		private final R0 evaluated0;
		private final R1 evaluated1;
		
		private Evaluator(R0 newEvaluated0, R1 newEvaluated1, L newValue) {
			super();
			this.evaluated0 = newEvaluated0;
			this.evaluated1 = newEvaluated1;
			this.value = newValue;
		}
		
		public static <R0, R1, L1> Evaluator<R0, R1, L1> of (R0 newEvaluated0, R1 newEvaluated1, L1 newValue) {
			return new Evaluator<>(newEvaluated0, newEvaluated1, newValue);
		}
		
		public void cancelEvaluation() {
			this.cancelled = true;
		}
		
		public void set(L value) {
			this.value = value;
		}
		
		public L get() {
			return this.value;
		}
		
		public R0 getEvaluated0() {
			return this.evaluated0;
		}
		
		public R1 getEvaluated1() {
			return this.evaluated1;
		}
	}
}
