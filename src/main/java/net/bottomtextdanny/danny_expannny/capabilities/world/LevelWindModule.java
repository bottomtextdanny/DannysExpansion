package net.bottomtextdanny.danny_expannny.capabilities.world;

import net.bottomtextdanny.danny_expannny.network.servertoclient.world.MSGUpdateWindState;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.braincell.mod.capability_helper.CapabilityModule;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;


public class LevelWindModule extends CapabilityModule<Level, LevelCapability> {
    public static final String DIRECTION_BIAS_TAG = "direction_bias";
    public static final String WIND_DIRECTION_TAG = "wind_direction";
    public static final String WIND_STRENGTH_TAG = "wind_strength";
    public static final String WIND_DIRECTION_QUERY_TAG = "wind_direction_query";
    public static final String WIND_STOPPED_TAG = "wind_stopped";
    private Direction biasedTowards = Direction.NORTH;
    private float windDirection;
    private float windStrength;
    private boolean isWindStopped;
    private float windDirectionQuery;

    public LevelWindModule(LevelCapability capability) {
        super("wind", capability);
    }

    public void tick() {
        if (!getHolder().isClientSide) {
            this.windStrength = calculateWindStrength();
            this.windDirection = DEMath.approachDegrees(this.windStrength * 0.4F, this.windDirection, this.windDirectionQuery);
            if (this.windDirection == this.windDirectionQuery) {
                this.windDirectionQuery = DEMath.wrapDegrees(this.biasedTowards.toYRot() + (float)getHolder().random.nextGaussian() * (this.windStrength + 4.0F) * 8.0F);
            }
        }
    }

    private float calculateWindStrength() {
        if (getHolder().isClientSide) return 0.0F;
        else if (getHolder().isThundering()) return 15.0F;
        else if (getHolder().isRaining()) return 10.0F;
        else return 7.5F;
    }

    public void updateFromPacket(MSGUpdateWindState windPacket) {
        this.windDirection = windPacket.getWindDirection();
        this.windStrength = windPacket.getWindStrength();
        this.isWindStopped = windPacket.isWindStopped();
    }

    public float getWindDirection() {
        return this.windDirection;
    }

    public float getWindStrength() {
        return this.windStrength;
    }

    public boolean isWindStopped() {
        return this.isWindStopped;
    }

    @Override
    public void serializeNBT(CompoundTag nbt) {
        nbt.putByte(DIRECTION_BIAS_TAG, (byte)this.biasedTowards.get2DDataValue());
        nbt.putFloat(WIND_DIRECTION_TAG, this.windDirection);
        nbt.putFloat(WIND_STRENGTH_TAG, this.windStrength);
        nbt.putFloat(WIND_DIRECTION_QUERY_TAG, this.windDirectionQuery);
        nbt.putBoolean(WIND_STOPPED_TAG, this.isWindStopped);
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.biasedTowards = Direction.from2DDataValue(nbt.getByte(DIRECTION_BIAS_TAG));
        this.windDirection = nbt.getFloat(WIND_DIRECTION_TAG);
        this.windStrength = nbt.getFloat(WIND_STRENGTH_TAG);
        this.windDirectionQuery = nbt.getFloat(WIND_DIRECTION_QUERY_TAG);
        this.isWindStopped = nbt.getBoolean(WIND_STOPPED_TAG);
    }
}
