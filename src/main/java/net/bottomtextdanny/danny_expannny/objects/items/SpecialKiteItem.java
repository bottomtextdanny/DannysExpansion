package net.bottomtextdanny.danny_expannny.objects.items;

import net.bottomtextdanny.danny_expannny.object_tables.DEEntities;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.danny_expannny.objects.entities.kite.KiteEntity;
import net.bottomtextdanny.danny_expannny.objects.entities.kite.KiteKnotEntity;
import net.bottomtextdanny.danny_expannny.objects.entities.kite.SpecialKiteEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.KiteStitcherManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class SpecialKiteItem extends BaseKiteItem {
    private boolean locked;
    private final int rewardNumber;
    @Nullable
    private Supplier<? extends EntityType<?>> cachedEntity;
    @Nullable
    private ResourceLocation entityKeyId;
    private String textureName;
    private final String cordTextureId = "minecraft:textures/block/white_wool";
    private boolean fullbright;
    private final byte model;

    public SpecialKiteItem(ResourceLocation entity, int goal, String id, byte model, boolean fullbrightLayer, Item.Properties properties) {
        super(properties);
        this.textureName = id;
        this.fullbright = fullbrightLayer;
        this.model = model;
        this.rewardNumber = goal;
        this.entityKeyId = entity;
    }

    public SpecialKiteItem(Supplier<? extends EntityType<?>> type, int goal, String id, byte model, boolean fullbrightLayer, Item.Properties properties) {
        super(properties);
        this.textureName = id;
        this.fullbright = fullbrightLayer;
        this.model = model;
        this.rewardNumber = goal;
        setTypeAndLock(type);
    }


    public SpecialKiteItem(int goal, byte model) {
        super(new Properties());
        this.model = model;
        this.rewardNumber = goal;
        KiteStitcherManager.getDeferredStitching().add(this);
    }

    public SpecialKiteItem(EntityType<?> type, int goal, String id, byte model, boolean fullbrightLayer, Item.Properties properties) {
        super(properties);
        this.textureName = id;
        this.fullbright = fullbrightLayer;
        this.model = model;
        this.rewardNumber = goal;
        setTypeAndLock(() -> type);
    }



    public InteractionResult useOn(UseOnContext context) {

        BlockPos blockpos = context.getClickedPos();
        Direction direction = context.getClickedFace();
        BlockPos blockpos1 = blockpos.relative(direction);
        Player playerentity = context.getPlayer();
        ItemStack itemstack = context.getItemInHand();
        if (playerentity != null && !this.canPlace(playerentity, direction, itemstack, blockpos1)) {
            return InteractionResult.FAIL;
        } else {

            if (KiteKnotEntity.onValidSurface(context.getLevel(), blockpos)) {
                if (!context.getLevel().isClientSide) {
                    KiteKnotEntity knot = new KiteKnotEntity(DEEntities.KITE_KNOT.get(), context.getLevel());
                    knot.setupPositionAndItemstack(blockpos, itemstack);
                    context.getLevel().addFreshEntity(knot);
                    itemstack.shrink(1);
                    context.getLevel().playSound(null, blockpos.getX() + 0.5, blockpos.getY() + 0.5, blockpos.getZ() + 0.5, DESounds.ES_KITE_ATTACH.get(), SoundSource.NEUTRAL, 1.0F, 1.0F + context.getLevel().random.nextFloat() * 0.1F);
                }


                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.CONSUME;
            }
        }
    }

    protected boolean canPlace(Player playerIn, Direction directionIn, ItemStack itemStackIn, BlockPos posIn) {
        return !directionIn.getAxis().isVertical() && playerIn.mayUseItemAt(posIn, directionIn, itemStackIn);
    }

    public Supplier<? extends EntityType<?>> getCachedEntity() {
        return this.cachedEntity;
    }

    public void setTypeAndLock(Supplier<? extends EntityType<?>> cachedEntity) {
        if (!this.locked) {
            this.cachedEntity = cachedEntity;
            this.locked = true;
        }
    }

    public ResourceLocation getEntityKeyId() {
        return this.entityKeyId;
    }

    public String getTextureName() {
        return this.textureName;
    }

    public int getRewardNumber() {
        return this.rewardNumber;
    }


    public byte getModel() {
        return this.model;
    }

    public String getCordTextureId() {
        return this.cordTextureId;
    }

    public boolean isFullbright() {
        return this.fullbright;
    }


    public void setTextureName(String textureName) {
        this.textureName = textureName;
    }

    public SpecialKiteItem bright() {
        this.fullbright = true;
        return this;
    }

    @Override
    public KiteEntity createKite(Level world, ItemStack stack) {
        SpecialKiteEntity entity = SpecialKiteEntity.provideSpecial(DEEntities.SPECIAL_KITE.get(), world, stack);
        entity.itemstack = stack.copy().split(1);
        return entity;
    }

    enum Model {
        S,
        SW,
        B,
        BW
    }
}
