package net.bottomtextdanny.danny_expannny.capabilities.player;

public enum MiniAttribute {
	GUN_ACCURACY(0.0F, -10.0F, 10.0F),//
	GUN_CADENCE(0.0F, -10.0F, 10.0F),//
	GUN_DAMAGE_MLT(100.0F, 0.0F, 1000.0F),//
	GUN_DAMAGE_ADD(0.0F, -1024.0F, 1024.0F),//
	BULLET_SPEED_MLT(100.0F, 0.0F, 500.0F),//
	ARCHERY_SPEED(0.0F, -10.0F, 10.0F),//
	ARCHERY_DAMAGE_MLT(100.0F, 0.0F, 1000.0F),//
	ARCHERY_DAMAGE_ADD(0.0F, -1024.0F, 1024.0F),//
	ARROW_SPEED_MLT(100.0F, 0.0F, 500.0F);//
	
	public final float baseValue;
	public final float clampMin;
	public final float clampMax;
	
	MiniAttribute(float baseValue, float clampMin, float clampMax) {
		this.baseValue = baseValue;
		this.clampMin = clampMin;
		this.clampMax = clampMax;
	}
}
