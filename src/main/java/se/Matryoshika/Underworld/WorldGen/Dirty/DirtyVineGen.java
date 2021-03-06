package se.Matryoshika.Underworld.WorldGen.Dirty;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraftforge.fml.common.IWorldGenerator;
import se.Matryoshika.Underworld.Content.ContentRegistry;
import se.Matryoshika.Underworld.Content.Blocks.BlockHangVine;
import se.Matryoshika.Underworld.Utils.ConfigHandler;
import se.Matryoshika.Underworld.WorldGen.WorldTypeCaves;
import net.minecraft.block.Block;
import net.minecraft.block.BlockVine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenVines;

public class DirtyVineGen implements IWorldGenerator{
	
	private Block blockForGen;
	
	public static List biomes = new ArrayList();

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		if (!world.provider.isSurfaceWorld()){
			return;
		}
		if(!(world.getWorldType() instanceof WorldTypeCaves) && !ConfigHandler.forceUnderworld){
			return;
		}
		
		
		int blockX = chunkX * 16 + random.nextInt(16);
		int blockZ = chunkZ * 16 + random.nextInt(16);
		
		int target = canGenerate(world, blockX, 100, blockZ);
		if (target != 0)
			generateStructure(world, blockX, target, blockZ, random);
		
	}
	private int canGenerate(World world, int x, int y, int z) {
		for(int dy = 0; dy < 10; dy++){
			if(world.isAirBlock(new BlockPos(x,y+dy,z)) && !world.isAirBlock(new BlockPos(x,y+dy+1,z))){
				return y+dy;
			}
		}
		return 0;
	}
	
	
private void generateStructure(World world, int x, int y, int z, Random rand) {
		
		Random genRand = new Random();
		Random shortCircuit = new Random();
		
		if(genRand.nextInt(4) == 0){
			
			
			for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL.facings())
            {
				IBlockState iblockstate = ContentRegistry.BlockHangVine.getDefaultState().withProperty(BlockHangVine.NORTH, Boolean.valueOf(enumfacing == EnumFacing.NORTH)).withProperty(BlockHangVine.EAST, Boolean.valueOf(enumfacing == EnumFacing.EAST)).withProperty(BlockHangVine.SOUTH, Boolean.valueOf(enumfacing == EnumFacing.SOUTH)).withProperty(BlockHangVine.WEST, Boolean.valueOf(enumfacing == EnumFacing.WEST));
				if(!world.isAirBlock(new BlockPos(x, y, z))){
					return;
				}
				world.setBlockState(new BlockPos(x, y, z), iblockstate, 2);
				for(int dy = 1; dy < 50; dy++){
					
					if(world.isAirBlock(new BlockPos(x, y-dy, z))){
						world.setBlockState(new BlockPos(x, y-dy, z), iblockstate, 2);
					}
					else{
						break;
					}
					if(shortCircuit.nextInt(50) == 0){
						break;
					}
				}
            }
		}
	}
}
