package bottomtextdanny.dannys_expansion.content._client.model.guns;

import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerGunModule;
import bottomtextdanny.braincell.mod._base.rendering.core_modeling.BCJoint;
import bottomtextdanny.braincell.mod.rendering.modeling.BCSimpleModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.player.Player;

public abstract class GunModel extends BCSimpleModel {
    protected BCJoint fire;

    public GunModel() {
        super(RenderType::entityCutoutNoCull);
    }

    public final void _animate(Player player, PlayerGunModule module, int useTicks) {
        runDefaultState();
        animate(player, module, useTicks);
    }

    public void animate(Player player, PlayerGunModule module, int useTicks) {}

    public BCJoint getFire() {
        return fire;
    }
}
