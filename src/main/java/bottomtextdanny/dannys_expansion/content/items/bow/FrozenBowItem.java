package bottomtextdanny.dannys_expansion.content.items.bow;

import bottomtextdanny.dannys_expansion.tables.DESounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FrozenBowItem extends DEBowItem implements IBowModelLoader {

    public FrozenBowItem(Properties properties) {
        super(false, properties);
    }

    @Override
    public void init() {
        damageFactor = 0.8F;
        speedFactor = 1.2F;
        nockMovementSpeed = 0.5F;
    }

    @Override
    public SoundEvent getShootSound(Level world, Player player, ItemStack stack) {
        return DESounds.IS_FROZEN_BOW_SHOT.get();
    }

    @Override
    public boolean automatic() {
        return false;
    }
}
