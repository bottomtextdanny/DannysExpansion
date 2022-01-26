package net.bottomtextdanny.danny_expannny.vertex_models.living_entities.slimes;

import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slime.mundane_slime.MundaneSlimeEntity;

public class FlowerSlimeModel extends DannySlimeModel<MundaneSlimeEntity> {
	private final BCVoxel flower1;
	private final BCVoxel flower2;
	
	public FlowerSlimeModel() {
		super(10.0F, 8.0F);
        this.texWidth = 128;
        this.texHeight = 128;

        this.flower1 = new BCVoxel(this);
        this.flower1.setPos(5.0F, -8.0F, 0.0F);
        this.body.addChild(this.flower1);
		setRotationAngle(this.flower1, -2.3562F, 1.5708F, 0.0F);


        this.flower2 = new BCVoxel(this);
        this.flower2.setPos(0.0F, 0.0F, 0.0F);
        this.flower1.addChild(this.flower2);
		setRotationAngle(this.flower2, 0.0F, 0.0F, 0.7854F);
        this.flower2.texOffs(0, 36).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, 0.0F, false);
		
		setupDefaultState();
	}
}
