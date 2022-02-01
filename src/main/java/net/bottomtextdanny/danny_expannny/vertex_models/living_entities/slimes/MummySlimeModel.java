package net.bottomtextdanny.danny_expannny.vertex_models.living_entities.slimes;

import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.desertic_slime.DeserticSlime;

public class MummySlimeModel extends DannySlimeModel<DeserticSlime> {
	
	public MummySlimeModel() {
		super(12.0F, 9.0F);
        this.texWidth = 128;
        this.texHeight = 128;

        this.body.texOffs(0, 34).addBox(0.0F, -9.0F, 6.0F, 0.0F, 9.0F, 8.0F, 0.0F, false);
		
		setupDefaultState();
	}
}
