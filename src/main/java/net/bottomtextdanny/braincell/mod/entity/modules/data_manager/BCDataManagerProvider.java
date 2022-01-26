package net.bottomtextdanny.braincell.mod.entity.modules.data_manager;

import net.bottomtextdanny.braincell.mod.entity.modules.ModuleProvider;
import net.bottomtextdanny.braincell.mod.entity.modules.variable.VariableProvider;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface BCDataManagerProvider extends ModuleProvider {

    BCDataManager bcDataManager();

    @OnlyIn(Dist.CLIENT)
    default void afterClientDataUpdate() {
        if (this instanceof Mob mob && this instanceof VariableProvider provider && provider.operatingVariableModule() && provider.variableModule().getForm().boxSize() != null) {
            mob.refreshDimensions();
        }
    }
}
