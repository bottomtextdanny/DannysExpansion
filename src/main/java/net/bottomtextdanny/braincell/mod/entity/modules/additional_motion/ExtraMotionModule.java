package net.bottomtextdanny.braincell.mod.entity.modules.additional_motion;

import com.google.common.collect.Lists;
import net.bottomtextdanny.braincell.mod.entity.modules.EntityModule;
import net.bottomtextdanny.dannys_expansion.core.Util.ExternalMotion;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ExtraMotionModule extends EntityModule<Entity, ExtraMotionProvider> {
    private final List<ExternalMotion> customMotions;
    private Vec3 additionalMotion = Vec3.ZERO;

    public ExtraMotionModule(Entity entity) {
        super(entity);
        this.customMotions = Lists.newLinkedList();
    }

    public void travelHook() {
        Vec3 additionalHolder = Vec3.ZERO;

        for (ExternalMotion m : this.customMotions) {
            additionalHolder = additionalHolder.add(m.getAcceleratedMotion());
            m.tick();
        }

        this.additionalMotion = additionalHolder;
    }

    public final void addMotion(ExternalMotion motion) {
        this.customMotions.add(motion);
    }

    public Vec3 getAdditionalMotion() {
        return this.additionalMotion;
    }
}
