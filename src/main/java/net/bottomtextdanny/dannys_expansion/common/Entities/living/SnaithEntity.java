package net.bottomtextdanny.dannys_expansion.common.Entities.living;

import net.bottomtextdanny.braincell.mod.serialization.WorldPacketData;
import net.bottomtextdanny.braincell.mod.serialization.serializers.builtin.BuiltinSerializers;
import net.bottomtextdanny.braincell.mod.world.builtin_entities.ModuledMob;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.Timer;
import net.bottomtextdanny.braincell.underlying.misc.ObjectFetcher;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class SnaithEntity extends ModuledMob {
     public Timer toRestTimer;
    public Timer nextAttackTimer;
    public Vec3 tempMoveVec = Vec3.ZERO;

    public SnaithEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        this.toRestTimer = new Timer(80);
        this.nextAttackTimer = new Timer(120, baseBound -> DEMath.intRandomOffset(baseBound, 0.3F));
    }

    @Override
    protected void registerExtraGoals() {
        super.registerGoals();
    }

    public static AttributeSupplier.Builder Attributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.FOLLOW_RANGE, 25.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D);
    }


    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public void setDeltaMovement(double x, double y, double z) {
        super.setDeltaMovement(x, y, z);

    }

    @Override
    public void move(MoverType typeIn, Vec3 pos) {

        this.tempMoveVec.add(pos);
        super.move(typeIn, pos);

    }


    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientCallOutHandler(int flag, ObjectFetcher fetcher) {
        super.clientCallOutHandler(flag, fetcher);
        if (flag == 0) {
            pushEntitiesAway(fetcher.get(0, Vec3.class));
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level.isClientSide()) {
            sendClientMsg(0, WorldPacketData.of(BuiltinSerializers.VEC3, this.tempMoveVec));
            pushEntitiesAway(this.tempMoveVec);
            this.tempMoveVec = Vec3.ZERO;
        }
        this.checkInsideBlocks();
    }

    @Override
    public void baseTick() {

        super.baseTick();
    }

    protected void pushEntitiesAway(Vec3 mot) {
        AABB collisionAABB = this.getBoundingBox().move(getAllowedMovement(mot));
        List<Entity> entities = this.level.getEntities(this, collisionAABB);

        for(Entity entity : entities) {

            if(entity.isPickable() && entity.isPushable()) {
                if (entity instanceof ServerPlayer) {
                    continue;
                }

                AABB entityAABB = entity.getBoundingBox();

                double dx = Math.max(collisionAABB.minX - entityAABB.maxX, entityAABB.minX - collisionAABB.maxX);
                double dz = Math.max(collisionAABB.minZ - entityAABB.maxZ, entityAABB.minZ - collisionAABB.maxZ);

                Vec3 vec;

                if (Math.abs(dz) < Math.abs(dx)) {
                    vec = new Vec3(0, -0.01D, (dz - 0.005D) * -Math.signum(this.getZ() - entity.getZ()));
                } else {
                    vec = new Vec3(-(dx - 0.005D) * -Math.signum(this.getX() - entity.getZ()), -0.01D, 0);
                }

                entity.move(MoverType.SELF, vec);

                if (entity instanceof LocalPlayer) {
                    entity.hasImpulse = false;
                    entity.hurtMarked = true;
                    entity.push(0, 0, 0);
                }
            }
        }
    }

    public Vec3 getAllowedMovement(Vec3 vec) {
	    AABB aabb = this.getBoundingBox();
	    List<VoxelShape> list = this.level.getEntityCollisions(this, aabb.expandTowards(vec));
	    Vec3 vec3 = vec.lengthSqr() == 0.0D ? vec : collideBoundingBox(this, vec, aabb, this.level, list);
	    boolean flag = vec.x != vec3.x;
	    boolean flag1 = vec.y != vec3.y;
	    boolean flag2 = vec.z != vec3.z;
	    boolean flag3 = this.onGround || flag1 && vec.y < 0.0D;
	    if (this.maxUpStep > 0.0F && flag3 && (flag || flag2)) {
		    Vec3 vec31 = collideBoundingBox(this, new Vec3(vec.x, this.maxUpStep, vec.z), aabb, this.level, list);
		    Vec3 vec32 = collideBoundingBox(this, new Vec3(0.0D, this.maxUpStep, 0.0D), aabb.expandTowards(vec.x, 0.0D, vec.z), this.level, list);
		    if (vec32.y < (double)this.maxUpStep) {
			    Vec3 vec33 = collideBoundingBox(this, new Vec3(vec.x, 0.0D, vec.z), aabb.move(vec32), this.level, list).add(vec32);
			    if (vec33.horizontalDistanceSqr() > vec31.horizontalDistanceSqr()) {
				    vec31 = vec33;
			    }
		    }
		
		    if (vec31.horizontalDistanceSqr() > vec3.horizontalDistanceSqr()) {
			    return vec31.add(collideBoundingBox(this, new Vec3(0.0D, -vec31.y + vec.y, 0.0D), aabb.move(vec31), this.level, list));
		    }
	    }
	
	    return vec3;
    }

    @Override
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand) {
        setDeltaMovement(new Vec3(0.5, 0, 0));
        return super.interactAt(player, vec, hand);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    @Override
    public void knockback(double strength, double ratioX, double ratioZ) {
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void doPush(Entity entityIn) {
        //ClientInstance.chatMsg("tomfo");
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean isAffectedByPotions() {
        return false;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }
    
    //SETTERS

    //CLASSES
}
