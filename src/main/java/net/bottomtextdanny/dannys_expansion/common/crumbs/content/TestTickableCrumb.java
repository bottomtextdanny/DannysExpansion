package net.bottomtextdanny.dannys_expansion.common.crumbs.content;

import net.bottomtextdanny.dannys_expansion.common.crumbs.Crumb;
import net.bottomtextdanny.dannys_expansion.common.crumbs.CrumbRoot;
import net.bottomtextdanny.dannys_expansion.common.crumbs.crumb_extensions.CrumbSerializer;
import net.bottomtextdanny.dannys_expansion.common.crumbs.crumb_extensions.CrumbTicker;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public class TestTickableCrumb extends Crumb implements CrumbTicker, CrumbSerializer {
    public static final String LIFE_TAG = "life";
    private int life;

    public TestTickableCrumb(CrumbRoot<?> root, Level level) {
        super(root, level);
        this.life = 200;
    }

    @Override
    public void tick() {
        if (!this.level.isClientSide()) {
            ((ServerLevel)this.level).sendParticles(ParticleTypes.SMOKE, this.getPosition().x, this.getPosition().y, this.getPosition().z, 1, 0.0D, 0.0D, 0.0D, 0.0D);

            if (--this.life <= 0) {
                remove();
            }
        }
    }

    @Override
    public void readAdditional(CompoundTag tag) {
        this.life = tag.getInt(LIFE_TAG);
    }

    @Override
    public void writeAdditional(CompoundTag tag) {
        tag.putInt(LIFE_TAG, this.life);
    }
}
