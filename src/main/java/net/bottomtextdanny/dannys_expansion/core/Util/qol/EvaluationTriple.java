package net.bottomtextdanny.dannys_expansion.core.Util.qol;

import com.google.common.collect.Lists;

import java.util.LinkedList;
import java.util.function.Consumer;
import java.util.function.Function;

public final class EvaluationTriple<A0, A1, A2, B> extends AbstractEvaluation {
	private final LinkedList<Consumer<Evaluator<A0, A1, A2, B>>> evaluatorList;
	private final String name;
	private final TriFunction<A0, A1, A2, B> evalFactory;
	
	private EvaluationTriple(String name, TriFunction<A0, A1, A2, B> evalFactory, LinkedList<Consumer<Evaluator<A0, A1, A2, B>>> commonPredicates) {
		super();
		this.name = name;
		this.evalFactory = evalFactory;
		this.evaluatorList = commonPredicates;
		evaluations.add(this);
	}
	
	public static <C0, C1, C2, D> EvaluationTriple<C0, C1, C2, D> create(String name, TriFunction<C0, C1, C2, D> evalFactory, Function<LinkedList<Consumer<Evaluator<C0, C1, C2, D>>>, LinkedList<Consumer<Evaluator<C0, C1, C2, D>>>> commonPredicates) {
		if (built) throw new UnsupportedOperationException("Can't create evaluations after building!");
		
		return new EvaluationTriple<>(name, evalFactory, commonPredicates.apply(Lists.newLinkedList()));
	}
	
	public static <C0, C1, C2, D> EvaluationTriple<C0, C1, C2, D> create(String name, TriFunction<C0, C1, C2, D> evalFactory) {
		if (built) throw new UnsupportedOperationException("Can't create evaluations after building!");
		
		return new EvaluationTriple<C0, C1, C2, D>(name, evalFactory, Lists.newLinkedList());
	}
	
	public void addTest(Consumer<Evaluator<A0, A1, A2, B>> pred) {
		this.evaluatorList.add(pred);
	}
	
	public B test(A0 subject0, A1 subject1, A2 subject2) {
		Evaluator<A0, A1, A2, B> current = Evaluator.of(subject0, subject1, subject2, this.evalFactory.apply(subject0, subject1, subject2));
		for (Consumer<Evaluator<A0, A1, A2, B>> cons :
                this.evaluatorList) {
			cons.accept(current);
			if (current.cancelled) break;
		}
	
		return current.get();
	}
	
	public static class Evaluator<R0, R1, R2, L> {
		private boolean cancelled;
		private L value;
		private final R0 evaluated0;
		private final R1 evaluated1;
		private final R2 evaluated2;
		
		private Evaluator(R0 newEvaluated0, R1 newEvaluated1, R2 newEvaluated2, L newValue) {
			super();
			this.evaluated0 = newEvaluated0;
			this.evaluated1 = newEvaluated1;
			this.evaluated2 = newEvaluated2;
			this.value = newValue;
		}
		
		public static <R0, R1, R2, L1> Evaluator<R0, R1, R2, L1> of (R0 newEvaluated0, R1 newEvaluated1, R2 newEvaluated2, L1 newValue) {
			return new Evaluator<>(newEvaluated0, newEvaluated1, newEvaluated2, newValue);
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
		
		public R2 getEvaluated2() {
			return this.evaluated2;
		}
	}
}
