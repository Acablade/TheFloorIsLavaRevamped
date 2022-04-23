package me.acablade.lavyukseliyor.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.UUID;

public class PlaceableBlock implements Workload{

    private final UUID worldID;
    private final int x,y,z;
    private final Material type;

    public PlaceableBlock(UUID uuid, int x, int y, int z, Material type){
        this.worldID = uuid;
        this.x = x;
        this.y = y;
        this.z = z;
        this.type = type;
    }

    @Override
    public void compute() {
        Block block = Bukkit.getWorld(worldID).getBlockAt(x,y,z);
        if(block.getType()!=type) block.setType(type, false);
    }
}
