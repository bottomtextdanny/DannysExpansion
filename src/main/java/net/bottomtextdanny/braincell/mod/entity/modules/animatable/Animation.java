package net.bottomtextdanny.braincell.mod.entity.modules.animatable;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public interface Animation<T extends AnimationData> extends AnimationGetter {
	NullAnimation NULL = new NullAnimation();

	Supplier<T> dataForPlay();

    int progressTick(int duration, AnimationHandler<?> handler);

    boolean goal(int progression, AnimationHandler<?> handler);

	default void onStart(AnimationHandler<?> handler) {}

	default void onEnd(AnimationHandler<?> handler) {}

	@Override
	default int size() {
		return 1;
	}

	@Override
	default Animation<?> getAnimation(int index) {
		return this;
	}

	int getDuration();
	
	int getIndex();
	
	void setIndex(int index, BCAnimationToken token);
	
	boolean isFrom(int identifier);

	@SuppressWarnings("unchecked")
	default T getData(AnimationHandler<?> handler) {
		return handler.isPlaying(this) ? (T)handler.getData() : null;
	}

	class NullAnimation implements Animation<AnimationData> {

		public NullAnimation() {
			super();
		}

		@Override
		public Supplier<AnimationData> dataForPlay() {
			return () -> AnimationData.NULL;
		}

		@Override
		public int progressTick(int duration, AnimationHandler<?> data) {
			return 0;
		}

		@Override
		public boolean goal(int prog, AnimationHandler<?> data) {
			return false;
		}

		@Override
		public int getDuration() {
			return 0;
		}

		@Override
		public boolean isFrom(int identifier) {
			return false;
		}

		@Override
		public int getIndex() {
			return -1;
		}

		@Override
		public void setIndex(int index, BCAnimationToken token) {
		}
	}
}
