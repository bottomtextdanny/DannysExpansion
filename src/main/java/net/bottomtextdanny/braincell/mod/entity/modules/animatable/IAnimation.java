package net.bottomtextdanny.braincell.mod.entity.modules.animatable;

public interface IAnimation {
	
	boolean isNull();

    int progressTick(int duration);

    boolean goal(int progression);

    int getDuration();
	
	int getIndex();
	
	void setIndex(int index);
	
	boolean isWoke();
	
	void setWoke(boolean val);
	
	boolean from(int identifier);
	
	void resetInstanceValues();
}
