package net.bottomtextdanny.danny_expannny.objects.items;

import net.bottomtextdanny.danny_expannny.object_tables.DEEntities;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.danny_expannny.objects.entities.TestDummyEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class TestDummyItem extends Item {

    public TestDummyItem(Item.Properties builder) {
        super(builder);
    }

    /**
     * Called when this item is used when targetting a Block
     */
    public InteractionResult useOn(UseOnContext context) {
        Direction direction = context.getClickedFace();
        if (direction == Direction.DOWN) {
            return InteractionResult.FAIL;
        } else {
            Level world = context.getLevel();
            BlockPlaceContext blockitemusecontext = new BlockPlaceContext(context);
            BlockPos blockpos = blockitemusecontext.getClickedPos();
            ItemStack itemstack = context.getItemInHand();
            Vec3 vector3d = Vec3.atBottomCenterOf(blockpos);
            AABB axisalignedbb = DEEntities.TEST_DUMMY.get().getDimensions().makeBoundingBox(vector3d.x(), vector3d.y(), vector3d.z());
            if (world.noCollision(null, axisalignedbb) && world.getEntities(null, axisalignedbb).isEmpty()) {
                if (world instanceof ServerLevel) {
                    ServerLevel serverworld = (ServerLevel)world;
                    TestDummyEntity testDummy = DEEntities.TEST_DUMMY.get().create(serverworld, itemstack.getTag(), null, context.getPlayer(), blockpos, MobSpawnType.SPAWN_EGG, true, true);
                    if (testDummy == null) {
                        return InteractionResult.FAIL;
                    }

                    float f = Mth.wrapDegrees(context.getHorizontalDirection().toYRot());
                    testDummy.moveTo(testDummy.getX(), testDummy.getY(), testDummy.getZ(), f, 0.0F);

                    world.addFreshEntity(testDummy);
                    world.playSound(null, testDummy.getX(), testDummy.getY(), testDummy.getZ(), DESounds.ES_TEST_DUMMY_PLACE.get(), SoundSource.BLOCKS, 0.75F, 1F + testDummy.getRandom().nextFloat() * 0.1F);
                }

                itemstack.shrink(1);
                return InteractionResult.sidedSuccess(world.isClientSide);
            } else {
                return InteractionResult.FAIL;
            }
        }
    }
}
