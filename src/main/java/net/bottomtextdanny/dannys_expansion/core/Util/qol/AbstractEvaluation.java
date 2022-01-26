package net.bottomtextdanny.dannys_expansion.core.Util.qol;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;

public abstract class AbstractEvaluation {
	protected static List<AbstractEvaluation> evaluations = Lists.newLinkedList();
	protected static boolean built;
	
	public static void _build() {
		evaluations = ImmutableList.copyOf(evaluations);
		built = true;
	}
}
