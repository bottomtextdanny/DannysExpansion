package net.bottomtextdanny.danny_expannny.objects.items.armor;

import net.bottomtextdanny.danny_expannny.object_tables.items.DEItems;
import net.bottomtextdanny.danny_expannny.rendering.DERenderProperties;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.DannyRayTraceHelper;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.ItemUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class AntiqueArmorItem extends ArmorItem {
    public int dashCooldown;
    public int dashDuration;
    public Random random = new Random();
    public AntiqueArmorDirection forwardDirection = new AntiqueArmorDirection(this, Direction.FORWARD);
    public AntiqueArmorDirection backDirection = new AntiqueArmorDirection(this, Direction.BACK);
    public AntiqueArmorDirection rightDirection = new AntiqueArmorDirection(this, Direction.RIGHT);
    public AntiqueArmorDirection leftDirection = new AntiqueArmorDirection(this, Direction.LEFT);
    Player player;

    public AntiqueArmorItem(ArmorMaterial materialIn, EquipmentSlot slot, Item.Properties p_i48534_3_) {
        super(materialIn, slot, p_i48534_3_);
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(DERenderProperties.ANTIQUE_ARMOR);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        ItemUtil.createDescription("antique_armor", tooltip, true);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {
        this.player = player;
        if (stack.getItem() == DEItems.ANTIQUE_ARMOR_CHESTPLATE.get()) {
            if (this.dashCooldown > 0) {
                this.dashCooldown--;
            }

            this.forwardDirection.tick();
            this.backDirection.tick();
            this.rightDirection.tick();
            this.leftDirection.tick();

            if (this.dashDuration > 0) {
                player.maxUpStep = 1.2F;
                Vec3 vector3d = player.getDeltaMovement();
                Vec3 vector3d1 = player.position();
                Vec3 vector3d2 = vector3d1.add(vector3d);

                EntityHitResult raytraceresult = DannyRayTraceHelper.rayTraceEntities(world, this.player, player.getBoundingBox().expandTowards(player.getDeltaMovement()).inflate(1.0D), this::canHitEntity);

                if (raytraceresult != null) {
                    LivingEntity entity = (LivingEntity)raytraceresult.getEntity();
                    Vec3 yaw = DEMath.fromPitchYaw(0, DEMath.getTargetYaw(player, entity));

                    entity.push(yaw.x * 2, 0, yaw.z * 2);
                    entity.hurt(DamageSource.mobAttack(player), 2.0F);

                    if (!player.isCreative()) {
                        player.getItemBySlot(EquipmentSlot.HEAD).hurtAndBreak(1, player, o -> {});
                        player.getItemBySlot(EquipmentSlot.CHEST).hurtAndBreak(1, player, o -> {});
                        player.getItemBySlot(EquipmentSlot.LEGS).hurtAndBreak(1, player, o -> {});
                        player.getItemBySlot(EquipmentSlot.FEET).hurtAndBreak(1, player, o -> {});
                    }
                }

                double d0 = this.random.nextGaussian() * (player.getBoundingBox().getXsize() + 0.15) / 2 ;
                double d1 = this.random.nextFloat() * player.getBoundingBox().getYsize() + 0.1;
                double d2 = this.random.nextGaussian() * (player.getBoundingBox().getXsize() + 0.15) / 2 ;

                for (int i = 0; i < 4; i++) {
                    player.level.addParticle(ParticleTypes.SMOKE, player.getX() + d0, player.getY() + d1, player.getZ() + d2, 0.0F, 0.0F, 0.0F);

                }

                this.dashDuration--;
            } else {
                player.maxUpStep = 0.6F;
            }
        }
    }

    public boolean canHitEntity(Entity entityIn) {
        return entityIn instanceof LivingEntity && !entityIn.isSpectator() && entityIn.isAlive() && entityIn != this.player;
    }

} enum Direction {
    FORWARD,
    BACK,
    RIGHT,
    LEFT
}
