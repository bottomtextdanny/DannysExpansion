package net.bottomtextdanny.danny_expannny.objects.world_gen;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.SurfaceRules;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class DESurfaceTools extends SurfaceRules {
	
	public static SurfaceRules.ConditionSource freeCheck(Predicate<BlockPos> pos) {
		return new FreeCheck(pos);
	}
	
	public static SurfaceRules.RuleSource wrap(Supplier<RuleSource> source) {
		return new SupRuleSource(source);
	}
	
	record FreeCheck(Predicate<BlockPos> pred) implements SurfaceRules.ConditionSource {
		static final Codec<FreeCheck> CODEC = Codec.unit(() -> new FreeCheck(vec -> false));
		
		public Codec<? extends SurfaceRules.ConditionSource> codec() {
			return CODEC;
		}
		
		public SurfaceRules.Condition apply(final SurfaceRules.Context context) {
			return () -> FreeCheck.this.pred.test(new BlockPos(context.blockX, context.blockY, context.blockZ));
		}
	}
	
	record SupRuleSource(Supplier<RuleSource> source) implements SurfaceRules.RuleSource {
		static final Codec<SupRuleSource> CODEC = Codec.unit(() -> new SupRuleSource(() -> new RuleSource() {
			@Override
			public Codec<? extends RuleSource> codec() {
				return null;
			}
			
			@Override
			public SurfaceRule apply(Context context) {
				return null;
			}
		}));
		
		public Codec<? extends SurfaceRules.RuleSource> codec() {
			return CODEC;
		}
		
		public SurfaceRules.SurfaceRule apply(SurfaceRules.Context p_189704_) {
			return this.source.get().apply(p_189704_);
		}
	}
}
