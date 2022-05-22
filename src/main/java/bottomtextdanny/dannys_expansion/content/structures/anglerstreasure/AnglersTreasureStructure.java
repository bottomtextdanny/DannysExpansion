package bottomtextdanny.dannys_expansion.content.structures.anglerstreasure;

import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

public class AnglersTreasureStructure extends StructureFeature<NoneFeatureConfiguration> {

	public AnglersTreasureStructure() {
		super(NoneFeatureConfiguration.CODEC, PieceGeneratorSupplier.simple(PieceGeneratorSupplier.checkForBiomeOnTop(Heightmap.Types.WORLD_SURFACE_WG), AnglersTreasureStructure::generatePieces));
	}

	@Override
	public GenerationStep.Decoration step() {
		return GenerationStep.Decoration.SURFACE_STRUCTURES;
	}

	private static void generatePieces(StructurePiecesBuilder p_197129_, PieceGenerator.Context<NoneFeatureConfiguration> p_197130_) {
		AnglersTreasurePiece piece = new AnglersTreasurePiece(p_197130_.random(), p_197130_.chunkPos().getBlockX(2), p_197130_.chunkPos().getBlockZ(2));
		p_197129_.addPiece(piece);

		p_197129_.moveInsideHeights(p_197130_.random(), 48, 70);
	}
}
