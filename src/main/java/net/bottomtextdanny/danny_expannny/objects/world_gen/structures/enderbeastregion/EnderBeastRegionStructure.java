package net.bottomtextdanny.danny_expannny.objects.world_gen.structures.enderbeastregion;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;


public class EnderBeastRegionStructure extends StructureFeature<NoneFeatureConfiguration> {
	public EnderBeastRegionStructure(Codec<NoneFeatureConfiguration> p_197165_, PieceGeneratorSupplier<NoneFeatureConfiguration> p_197166_) {
		super(p_197165_, p_197166_);
	}

//    public EnderBeastRegionStructure(Codec<NoneFeatureConfiguration> codec) {
//        super(codec);
//    }
//
//    public String getFeatureName() {
//        return DannyEx.MOD_ID + ":ender_beast_region";
//    }
//
//    public StructureStartFactory getStartFactory() {
//        return EnderBeastRegionStructure.Start::new;
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
//        public void generatePieces(RegistryAccess registries, ChunkGenerator chunkGen, StructureManager templateManagerIn, ChunkPos pos, Biome biome, NoneFeatureConfiguration p_230364_7_, LevelHeightAccessor p_163621_) {
//            EnderBeastRegionPiece anglersTreasurePiece = new EnderBeastRegionPiece(this.random, pos.x * 16, pos.z * 16);
//            this.pieces.add(anglersTreasurePiece);
//        }
//    }
}
