package com.randrdevelopment.propertygroup.regions;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public class VisualTools {
	public static Set<int[]> showRegion(World world, Location p1, Location p2)
    {
		Block newBlock;
		
        Set<int[]> result = new HashSet<int[]>();

        int x1 = p1.getBlockX(); int y1 = p1.getBlockY(); int z1 = p1.getBlockZ();
        int x2 = p2.getBlockX(); int z2 = p2.getBlockZ();

        int[] buffer;
        
        for (int i = x1; i <= x2; i++)
        {
            buffer = new int[] {i, y1, z1, world.getBlockTypeIdAt(i, y1, z1)};
            result.add(buffer);
            newBlock = world.getBlockAt(i, y1, z1);
            newBlock.setTypeId(20);
            
            buffer = new int[] {i, y1, z2, world.getBlockTypeIdAt(i, y1, z2)};
            result.add(buffer);
            newBlock = world.getBlockAt(i, y1, z2);
            newBlock.setTypeId(20);
        }
        for (int k = z1+1; k <= z2-1; k++)
        {
            buffer = new int[] {x1, y1, k, world.getBlockTypeIdAt(x1, y1, k)};
            result.add(buffer);
            newBlock = world.getBlockAt(x1, y1, k);
            newBlock.setTypeId(20);
            
            buffer = new int[] {x2, y1, k, world.getBlockTypeIdAt(x2, y1, k)};
            result.add(buffer);
            newBlock = world.getBlockAt(x2, y1, k);
            newBlock.setTypeId(20);
        }
        return result;
    }
}
