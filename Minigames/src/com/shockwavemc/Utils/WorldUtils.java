package com.shockwavemc.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

public class WorldUtils {

	public static void createWorld(String name) {
		WorldCreator wc = new WorldCreator(name);
		wc.generator(new VoidGenerator());
		wc.createWorld();
	}

	public static void copyWorld(File source, File target){
		try {
	        ArrayList<String> ignore = new ArrayList<String>(Arrays.asList("uid.dat", "session.dat"));
	        if(!ignore.contains(source.getName())) {
	           if(source.isDirectory()) {
	                if(!target.exists())
	                target.mkdirs();
	                String files[] = source.list();
	                for (String file : files) {
	                    File srcFile = new File(source, file);
	                    File destFile = new File(target, file);
	                   copyWorld(srcFile, destFile);
	               }
	            } else {
	                InputStream in = new FileInputStream(source);
	               OutputStream out = new FileOutputStream(target);
	                byte[] buffer = new byte[1024];
	                int length;
	                while ((length = in.read(buffer)) > 0)
	                   out.write(buffer, 0, length);
	                in.close();
	                out.close();
	            }
	        }
		} catch (IOException e) {}
	}
	
	public static boolean deleteWorld(World w) {
		File path = w.getWorldFolder();
		Bukkit.unloadWorld(w, false);
	      if(path.exists()) {
	          File files[] = path.listFiles();
	          for(int i=0; i<files.length; i++) {
	              if(files[i].isDirectory()) {
	                  deleteWorld(files[i]);
	              } else {
	                  files[i].delete();
	              }
	          }
	      }
	      return(path.delete());
	}
	
	public static boolean deleteWorld(File path) {
	      if(path.exists()) {
	          File files[] = path.listFiles();
	          for(int i=0; i<files.length; i++) {
	              if(files[i].isDirectory()) {
	                  deleteWorld(files[i]);
	              } else {
	                  files[i].delete();
	              }
	          }
	      }
	      return(path.delete());
	}
	
	
	
	
	public static class VoidGenerator extends ChunkGenerator {
		@Override
		public List<BlockPopulator> getDefaultPopulators(World world) {
			return new ArrayList<BlockPopulator>(); // Empty list
	       }
	 
		@Override
		public boolean canSpawn(World world, int x, int z) {
			return true;
		}
	 
		@Override
		public byte[][] generateBlockSections(World world, Random random, int chunkx, int chunkz, ChunkGenerator.BiomeGrid biomes) {
			return new byte[world.getMaxHeight() / 16][];
		}
	}
}
