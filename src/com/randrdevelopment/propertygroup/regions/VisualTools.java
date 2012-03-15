package com.randrdevelopment.propertygroup.regions;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class VisualTools {
	public static Set<int[]> showRegion(Player p, World world, Location p1, Location p2, int id, byte color)
    {
		Block newBlock;
		
        Set<int[]> result = new HashSet<int[]>();

        int x1 = p1.getBlockX(); int y1 = p1.getBlockY(); int z1 = p1.getBlockZ();
        int x2 = p2.getBlockX(); int y2 = p2.getBlockY(); int z2 = p2.getBlockZ();

        int[] buffer;
        
        for (int i = x1; i <= x2; i++)
        {
            buffer = new int[] {i, y1, z1, world.getBlockTypeIdAt(i, y1, z1)};
            result.add(buffer);
            //p.sendBlockChange(new Location(world, i, y1, z1), id, color);
            newBlock = world.getBlockAt(i, y1, z1);
            newBlock.setTypeIdAndData(id, color, false);
            
            buffer = new int[] {i, y2, z1, world.getBlockTypeIdAt(i, y2, z1)};
            result.add(buffer);
            //p.sendBlockChange(new Location(world, i, y2, z1), id, color);
            newBlock = world.getBlockAt(i, y2, z1);
            newBlock.setTypeIdAndData(id, color, false);
            
            buffer = new int[] {i, y1, z2, world.getBlockTypeIdAt(i, y1, z2)};
            result.add(buffer);
            //p.sendBlockChange(new Location(world, i, y1, z2), id, color);
            newBlock = world.getBlockAt(i, y1, z2);
            newBlock.setTypeIdAndData(id, color, false);
            
            buffer = new int[] {i, y2, z2, world.getBlockTypeIdAt(i, y2, z2)};
            result.add(buffer);
            //p.sendBlockChange(new Location(world, i, y2, z2), id, color);
            newBlock = world.getBlockAt(i, y2, z2);
            newBlock.setTypeIdAndData(id, color, false);
        }
        for (int j = y1+1; j <= y2-1; j++)
        {
            buffer = new int[] {x1, j, z1, world.getBlockTypeIdAt(x1, j, z1)};
            result.add(buffer);
            //p.sendBlockChange(new Location(world, x1, j, z1), id, color);
            newBlock = world.getBlockAt(x1, j, z1);
            newBlock.setTypeIdAndData(id, color, false);
            
            buffer = new int[] {x2, j, z1, world.getBlockTypeIdAt(x2, j, z1)};
            result.add(buffer);
            //p.sendBlockChange(new Location(world, x2, j, z1), id, color);
            newBlock = world.getBlockAt(x2, j, z1);
            newBlock.setTypeIdAndData(id, color, false);
            
            buffer = new int[] {x1, j, z2, world.getBlockTypeIdAt(x1, j, z2)};
            result.add(buffer);
            //p.sendBlockChange(new Location(world, x1, j, z2), id, color);
            newBlock = world.getBlockAt(x1, j, z2);
            newBlock.setTypeIdAndData(id, color, false);
            
            buffer = new int[] {x2, j, z2, world.getBlockTypeIdAt(x2, j, z2)};
            result.add(buffer);
            //p.sendBlockChange(new Location(world, x2, j, z2), id, color);
            newBlock = world.getBlockAt(x2, j, z2);
            newBlock.setTypeIdAndData(id, color, false);
        }
        for (int k = z1+1; k <= z2-1; k++)
        {
            buffer = new int[] {x1, y1, k, world.getBlockTypeIdAt(x1, y1, k)};
            result.add(buffer);
            //p.sendBlockChange(new Location(world, x1, y1, k), id, color);
            newBlock = world.getBlockAt(x1, y1, k);
            newBlock.setTypeIdAndData(id, color, false);
            
            buffer = new int[] {x2, y1, k, world.getBlockTypeIdAt(x2, y1, k)};
            result.add(buffer);
            //p.sendBlockChange(new Location(world, x2, y1, k), id, color);
            newBlock = world.getBlockAt(x2, y1, k);
            newBlock.setTypeIdAndData(id, color, false);
            
            buffer = new int[] {x1, y2, k, world.getBlockTypeIdAt(x1, y2, k)};
            result.add(buffer);
            //p.sendBlockChange(new Location(world, x1, y2, k), id, color);
            newBlock = world.getBlockAt(x1, y2, k);
            newBlock.setTypeIdAndData(id, color, false);
            
            buffer = new int[] {x2, y2, k, world.getBlockTypeIdAt(x2, y2, k)};
            result.add(buffer);
            //p.sendBlockChange(new Location(world, x2, y2, k), id, color);
            newBlock = world.getBlockAt(x2, y2, k);
            newBlock.setTypeIdAndData(id, color, false);
        }
        return result;
    }
}
