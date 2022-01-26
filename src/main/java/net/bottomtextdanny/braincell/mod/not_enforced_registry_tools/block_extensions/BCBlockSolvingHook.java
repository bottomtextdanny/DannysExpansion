package net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.block_extensions;

import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.base.util.Connection;
import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.ModDeferringManager;
import net.bottomtextdanny.braincell.mod.structure.common_sided.SolvingHook;
import net.bottomtextdanny.braincell.mod.world.block_utilities.ChestMaterialProvider;
import net.minecraft.world.level.block.Block;

public class BCBlockSolvingHook<T extends Block> implements SolvingHook<T> {

    @Override
    public void execute(T object, ModDeferringManager solving) {
        if (object instanceof ExtraBlockRegisterer) {
            ExtraBlockRegisterer<T> extraRegister = (ExtraBlockRegisterer<T>) object;
            if (extraRegister.executeSideRegistry()) extraRegister.registerExtra(object, solving);
        }
        Connection.doClientSide(() -> {
            if (object instanceof ChestMaterialProvider chestMaterialProvider) {
                Braincell.client().getMaterialManager().deferMaterialsForChest(chestMaterialProvider);
            }
        });
    }
}
