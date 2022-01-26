package net.bottomtextdanny.danny_expannny.objects.items;

import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.serialization.WorldPacketData;
import net.bottomtextdanny.braincell.mod.serialization.serializers.builtin.BuiltinSerializers;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.klifour.KlifourEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class BCSpawnEggItem extends SpawnEggItem {
    private final Supplier<? extends EntityType<? extends Mob>> typeSupplier;
    private final BCSpawnEggItem.SpawnLogic spawnLogic;
    private final boolean unstable;

    public static final BCSpawnEggItem.SpawnLogic DEFAULT_LOGIC = (type, world, direction, blockPos, itemStack, player) -> {
        BlockPos blockPos1;
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.getCollisionShape(world, blockPos).isEmpty()) {
            blockPos1 = blockPos;
        } else {
            blockPos1 = blockPos.relative(direction);
        }

        if (type.spawn((ServerLevel)world, itemStack, player, blockPos1, MobSpawnType.SPAWN_EGG, true, !Objects.equals(blockPos, blockPos1) && direction == Direction.UP) != null) {
            itemStack.shrink(1);
        }
    }, SL_KLIFOUR = (type, world, direction, blockPos, itemStack, player) -> {
        BlockState blockState = world.getBlockState(blockPos);
        BlockPos blockPos1;

        if (blockState.getCollisionShape(world, blockPos).isEmpty()) {
            blockPos1 = blockPos;
        } else {
            blockPos1 = blockPos.relative(direction);
        }

        KlifourEntity entity = (KlifourEntity) type.spawn((ServerLevel)world, itemStack, player, blockPos1, MobSpawnType.SPAWN_EGG, true, !Objects.equals(blockPos, blockPos1) && direction == Direction.UP);

        if (entity != null) {
            entity.setAttachingDirection(direction.getOpposite());
            entity.sendClientMsg(KlifourEntity.ATTACH_DIRECTION_CALL_FLAG, WorldPacketData.of(BuiltinSerializers.DIRECTION, direction.getOpposite()));
            itemStack.shrink(1);
        }
    };

    public BCSpawnEggItem(int firstTint, int secondTint, boolean unstable, SpawnLogic logic, Supplier<? extends EntityType<? extends Mob>> typeSupplier) {
        super(null, firstTint, secondTint, new Properties().tab(DannysExpansion.TAB));
        Braincell.common().getEntityCoreDataDeferror().putEgg(this);
        this.typeSupplier = typeSupplier;
        this.spawnLogic = logic;
        this.unstable = unstable;
    }

    public static Builder createBuilder(int firstTint, int secondTint) {
        return new Builder(firstTint, secondTint);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        if (!(world instanceof ServerLevel)) {
            return InteractionResult.SUCCESS;
        } else {
            ItemStack itemstack = context.getItemInHand();
            BlockPos blockpos = context.getClickedPos();
            Direction direction = context.getClickedFace();
            BlockState blockstate = world.getBlockState(blockpos);
            if (blockstate.is(Blocks.SPAWNER)) {
                BlockEntity tileentity = world.getBlockEntity(blockpos);
                if (tileentity instanceof SpawnerBlockEntity spawner) {
                    BaseSpawner abstractspawner = spawner.getSpawner();
                    EntityType<?> entitytype1 = this.getType(itemstack.getTag());
                    abstractspawner.setEntityId(entitytype1);
                    tileentity.setChanged();
                    world.sendBlockUpdated(blockpos, blockstate, blockstate, 3);
                    itemstack.shrink(1);
                    return InteractionResult.CONSUME;
                }
            }
            this.spawnLogic.forSpawn(
                    this.getType(itemstack.getTag()),
                    world,
                    direction,
                    blockpos,
                    itemstack,
                    context.getPlayer());
            return InteractionResult.CONSUME;
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        HitResult raytraceresult = getPlayerPOVHitResult(worldIn, playerIn, ClipContext.Fluid.SOURCE_ONLY);
        if (raytraceresult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(itemstack);
        } else if (!(worldIn instanceof ServerLevel)) {
            return InteractionResultHolder.success(itemstack);
        } else {
            BlockHitResult blockraytraceresult = (BlockHitResult)raytraceresult;
            BlockPos blockpos = blockraytraceresult.getBlockPos();
            if (!worldIn.getBlockState(blockpos).getFluidState().isSource()) {
                return InteractionResultHolder.pass(itemstack);
            } else if (worldIn.mayInteract(playerIn, blockpos) && playerIn.mayUseItemAt(blockpos, blockraytraceresult.getDirection(), itemstack)) {
                this.spawnLogic.forSpawn(this.getType(itemstack.getTag()), worldIn, blockraytraceresult.getDirection(), blockpos, itemstack, playerIn);
                return InteractionResultHolder.success(itemstack);
            } else {
                return InteractionResultHolder.fail(itemstack);
            }
        }
    }

    @Override
    public EntityType<? extends Mob> getType(@Nullable final CompoundTag p_208076_1_) {
        return this.typeSupplier.get();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        if (this.unstable) {
            tooltip.add(new TranslatableComponent("description.dannys_expansion.unstable").setStyle(Style.EMPTY.withColor(TextColor.fromLegacyFormat(ChatFormatting.RED)).withBold(true)));
        }

    }

    @FunctionalInterface
    interface SpawnLogic {
        void forSpawn(EntityType<?> type, Level world, Direction direction, BlockPos blockPos, ItemStack itemStack, Player player);
    }

    public static final class Builder {
        private final int firstTint;
        private final int secondTint;
        private short sort = -1;
        private boolean unstable;
        private SpawnLogic logic = DEFAULT_LOGIC;
        private Supplier<? extends EntityType<? extends Mob>> typeSupplier;

        private Builder(int firstTint, int secondTint) {
            super();
            this.firstTint = firstTint;
            this.secondTint = secondTint;
        }

        public Builder unstable() {
            this.unstable = true;
            return this;
        }

        public Builder sorted(short sortValue) {
            this.sort = sortValue;
            return this;
        }

        public Builder setLogic(SpawnLogic logic) {
            this.logic = logic;
            return this;
        }

        public Builder setTypeSupplier(Supplier<? extends EntityType<? extends Mob>> typeSupplier) {
            this.typeSupplier = typeSupplier;
            return this;
        }

        public BCSpawnEggItem build() {
            return new BCSpawnEggItem(this.firstTint, this.secondTint, this.unstable, this.logic, this.typeSupplier);
        }

        public short getSort() {
            return this.sort;
        }
    }
}