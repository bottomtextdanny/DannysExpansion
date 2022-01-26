package net.bottomtextdanny.danny_expannny.objects.world_gen.structures.cavetreasure;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;

public class CaveTreasureStructure extends StructureFeature<NoneFeatureConfiguration> {
	public CaveTreasureStructure(Codec<NoneFeatureConfiguration> p_197165_, PieceGeneratorSupplier<NoneFeatureConfiguration> p_197166_) {
		super(p_197165_, p_197166_);
	}

//    public CaveTreasureStructure(Codec<NoneFeatureConfiguration> codec) {
//        super(codec);
//    }
//
//    public String getFeatureName() {
//        return DannyEx.MOD_ID + ":cave_treasure";
//    }
//
//    public StructureStartFactory getStartFactory() {
//        return CaveTreasureStructure.Start::new;
//    }
//
//    @Override
//    public GenerationStep.Decoration step() {
//        return GenerationStep.Decoration.UNDERGROUND_STRUCTURES;
//    }
//
//    public static class Start extends StructureStart<NoneFeatureConfiguration> {
//        public Start(StructureFeature<NoneFeatureConfiguration> structure, ChunkPos pos, int references, long seed) {
//            super(structure, pos, references, seed);
//        }
//
//        @Override
//        public void generatePieces(RegistryAccess registries, ChunkGenerator chunkGen, StructureManager templateManagerIn, ChunkPos chunkPos, Biome biome, NoneFeatureConfiguration p_230364_7_, LevelHeightAccessor level) {
//            CaveTreasurePiece anglersTreasurePiece = new CaveTreasurePiece(chunkPos.x, chunkPos.z);
//            this.pieces.add(anglersTreasurePiece);
//        }
//
//
//    }



}
