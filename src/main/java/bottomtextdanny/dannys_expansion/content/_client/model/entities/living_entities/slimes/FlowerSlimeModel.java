package bottomtextdanny.dannys_expansion.content._client.model.entities.living_entities.slimes;

import bottomtextdanny.dannys_expansion.content.entities.mob.slimes.mundane_slime.MundaneSlime;
import bottomtextdanny.braincell.mod._base.rendering.core_modeling.BCJoint;

public class FlowerSlimeModel extends DannySlimeModel<MundaneSlime> {
	private final BCJoint flower1;
	private final BCJoint flower2;
	
	public FlowerSlimeModel() {
		super(10.0F, 8.0F);
        this.texWidth = 128;
        this.texHeight = 128;

        this.flower1 = new BCJoint(this);
        this.flower1.setPosCore(5.0F, -8.0F, 0.0F);
        this.body.addChild(this.flower1);
		setRotationAngle(this.flower1, -2.3562F, 1.5708F, 0.0F);


        this.flower2 = new BCJoint(this);
        this.flower2.setPosCore(0.0F, 0.0F, 0.0F);
        this.flower1.addChild(this.flower2);
		setRotationAngle(this.flower2, 0.0F, 0.0F, 0.7854F);
        this.flower2.uvOffset(0, 36).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, 0.0F, false);
		
		//modelInitEnd
	}
}
