package com.shockwavemc.Utils;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

public class VoidGenerator extends ChunkGenerator {
	
	 byte getBlock(int x, int y, int z, byte[][] chunk) {
	        if (chunk[y >> 4] == null)
	                return 0;
	        if (!(y <= 256 && y >= 0 && x <= 16 && x >= 0 && z <= 16 && z >= 0))
	                return 0;
	        try {
	                return chunk[y >> 4][((y & 0xF) << 8) | (z << 4) | x];
	        } catch (Exception e) {
	                e.printStackTrace();
	                return 0;
	        }
	    }
	    void setBlock(int x, int y, int z, byte[][] chunk, Material material) {
	        //if the Block section the block is in hasn't been used yet, allocate it
	        if (chunk[y >> 4] == null)
	            chunk[y >> 4] = new byte[16 * 16 * 16];
	        if (!(y <= 256 && y >= 0 && x <= 16 && x >= 0 && z <= 16 && z >= 0))
	            return;
	        try {
	            chunk[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = (byte) material
	                    .getId();
	        } catch (Exception e) {
	            // do nothing
	        }
	    }

	 @Override
	    public byte[][] generateBlockSections(World world, Random rand, int ChunkX,
	            int ChunkZ, BiomeGrid biome) {
	       
	        SimplexOctaveGenerator gen = new SimplexOctaveGenerator(world,8);
	        gen.setScale(1/12);
	        byte[][] chunk = new byte[world.getMaxHeight() / 16][];
	       
	        for (int x=0; x<16; x++) {
	            for (int z=0; z<16; z++) {
	                setBlock(x,0,z,chunk,Material.AIR);
	            }
	        }
	        for(int x=0; x<16; x++){
	            for(int z=0;z<16;z++){
	                double whatever = gen.noise(x+ChunkX*16, z+ChunkZ*16, 0.5, 0.5);
	                for(int y=1;y<50+whatever;y++){
	                    setBlock(x,y,z,chunk,Material.AIR);
	                   
	                }
	            }
	        }
	        return chunk;
	    }

}
