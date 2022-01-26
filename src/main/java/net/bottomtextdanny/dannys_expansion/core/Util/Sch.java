package net.bottomtextdanny.dannys_expansion.core.Util;

import net.minecraft.world.level.Level;

import java.util.function.Consumer;
//sch
public class Sch {
	public final Consumer<Level> action;
	private int offset;
	
	private Sch(Consumer<Level> run, int time) {
		super();
		this.action = run;
		this.offset = time;
	}
	
	public static Sch of (Consumer<Level> run, int time) {
		return new Sch(run, time);
	}
	
	public void decr() {
		this.offset--;
	}
	
	public int getOffset() {
		return this.offset;
	}
}
