package bottomtextdanny.de_json_generator.types.extension;

import bottomtextdanny.de_json_generator._gen.DannyGenerator;
import bottomtextdanny.de_json_generator.GenUtils;
import bottomtextdanny.de_json_generator.types.*;
import bottomtextdanny.de_json_generator.types.base.Generator;
import bottomtextdanny.de_json_generator.types.base.GeneratorWMother;

import java.io.IOException;

public class WoodGenExt extends GenUtils implements IGenExtension<Generator> {
    private boolean noCraft;

    public WoodGenExt supressCrafting() {
        this.noCraft = true;
        return this;
    }

    @Override
    public void generate(Generator base) throws IOException {
    	if (base instanceof GeneratorWMother) {
    		GeneratorWMother<?> baseMother = (GeneratorWMother<?>) base;
			if (baseMother instanceof DoorGenerator) {
				if (!this.noCraft) doRecipe(rs_door("dannys_expansion:" + baseMother.getMotherName()).group("wooden_door").bake());
				DannyGenerator.WOODEN_DOORS.add(baseMother.getName());
			}

			else if (baseMother instanceof SlabGenerator) {
				if (!this.noCraft) doRecipe(rs_slab("dannys_expansion:" + baseMother.getMotherName()).group("wooden_slab").bake());
				DannyGenerator.WOODEN_SLABS.add(baseMother.getName());
			}

			else if (baseMother instanceof StairsGenerator) {
				if (!this.noCraft) doRecipe(rs_stairs("dannys_expansion:" + baseMother.getMotherName()).group("wooden_stairs").bake());
				DannyGenerator.WOODEN_STAIRS.add(baseMother.getName());
			}

			else if (baseMother instanceof ButtonGenerator) {
				if (!this.noCraft) doRecipe(rs_buttonlike("dannys_expansion:" + baseMother.getMotherName()).group("wooden_button").bake());
				DannyGenerator.WOODEN_BUTTONS.add(baseMother.getName());
			}

			else if (baseMother instanceof FenceGenerator) {
				if (!this.noCraft) doRecipe(rs_wooden_fence("dannys_expansion:" + baseMother.getMotherName()).group("wooden_fence").bake());
				DannyGenerator.WOODEN_FENCES.add(baseMother.getName());
				DannyGenerator.FORGE_WOODEN_FENCES.add(baseMother.getName());
			}

			else if (baseMother instanceof FenceGateGenerator) {
				if (!this.noCraft) doRecipe(rs_wooden_fence_gate("dannys_expansion:" + baseMother.getMotherName()).group("wooden_fence_gate").bake());
				DannyGenerator.FORGE_WOODEN_FENCE_GATES.add(baseMother.getName());
			}

			else if (baseMother instanceof PressurePlateGenerator) {
				if (!this.noCraft) doRecipe(rs_pressure_plate("dannys_expansion:" + baseMother.getMotherName()).group("wooden_pressure_plate").bake());
				DannyGenerator.WOODEN_PRESSURE_PLATES.add(baseMother.getName());
			}

			else if (baseMother instanceof PressurePlateGenerator) {
				if (!this.noCraft) doRecipe(rs_pressure_plate("dannys_expansion:" + baseMother.getMotherName()).group("wooden_trapdoor").bake());
				DannyGenerator.WOODEN_TRAPDOORS.add(baseMother.getName());
			}
		} else if (base instanceof ChestGenerator) {
        	if (!this.noCraft) {
        		if (((ChestGenerator)base).isTrapped()) {
					doRecipe(rs_trapped_chest(base.getMaterial()));
				} else {
					doRecipe(rs_chest(base.getMaterial()));
				}
			}
            DannyGenerator.FORGE_WOODEN_CHESTS.add(base.getName());
        }

        this.noCraft = false;
    }
}
