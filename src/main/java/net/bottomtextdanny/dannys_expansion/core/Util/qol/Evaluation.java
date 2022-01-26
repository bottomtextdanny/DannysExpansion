package net.bottomtextdanny.dannys_expansion.core.Util.qol;

import com.google.common.collect.Lists;

import java.util.LinkedList;
import java.util.function.Consumer;
import java.util.function.Function;

public final class Evaluation<A, B> extends AbstractEvaluation {

	private final LinkedList<Consumer<Evaluator<A, B>>> evaluatorList;
	private final String name;
	private final Function<A, B> evalFactory;
	
	private Evaluation(String name, Function<A, B> evalFactory, LinkedList<Consumer<Evaluator<A, B>>> commonPredicates) {
		super();
		this.name = name;
		this.evalFactory = evalFactory;
		this.evaluatorList = commonPredicates;
		evaluations.add(this);
	}
	
	
	public static <C, D> Evaluation<C, D> create(String name, Function<C, D> evalFactory, Function<LinkedList<Consumer<Evaluator<C, D>>>, LinkedList<Consumer<Evaluator<C, D>>>> commonPredicates) {
		if (built) throw new UnsupportedOperationException("Can't create evaluations after building!");

		return new Evaluation<>(name, evalFactory, commonPredicates.apply(Lists.newLinkedList()));
	}
	
	public static <C, D> Evaluation<C, D> create(String name, Function<C, D> evalFactory) {
		if (built) throw new UnsupportedOperationException("Can't create evaluations after building!");

		return new Evaluation<C, D>(name, evalFactory, Lists.newLinkedList());
	}
	
	public void addTest(Consumer<Evaluator<A, B>> pred) {
		this.evaluatorList.add(pred);
	}
	
	public B test(A subject) {
		Evaluator<A, B> current = Evaluator.of(subject, this.evalFactory.apply(subject));
		for (Consumer<Evaluator<A, B>> cons :
                this.evaluatorList) {
			cons.accept(current);
			if (current.cancelled) break;
		}
	
		return current.get();
	}
	
	public static class Evaluator<R, L> {
		private boolean cancelled;
		private L value;
		private final R evaluated;
		
		private Evaluator(R newEvaluated, L newValue) {
			super();
			this.evaluated = newEvaluated;
			this.value = newValue;
		}
		
		public static <R1, L1> Evaluator<R1, L1> of (R1 newEvaluated, L1 newValue) {
			return new Evaluator<>(newEvaluated, newValue);
		}
		
		public void stopEvaluation() {
			this.cancelled = true;
		}
		
		public void set(L value) {
			this.value = value;
		}
		
		public L get() {
			return this.value;
		}
		
		public R getEvaluated() {
			return this.evaluated;
		}
	}
}
