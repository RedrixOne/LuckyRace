package com.redrixone.events;

import com.redrixone.api.Stack;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class SpongeBreak implements Listener {

    Map<String, Integer> spongeBlocksBroken = new HashMap<>();


    //Evento per la rottura del LuckyBlock
    @EventHandler
    public void blockBreakEvent(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        String playerName = player.getName();
        if (block.getType() == Material.SPONGE) {
            int previousCount = spongeBlocksBroken.getOrDefault(playerName, 0);
            spongeBlocksBroken.put(playerName, previousCount + 1);
            block.setType(Material.AIR);
            Location location = block.getLocation();
            World world = block.getWorld();
            ItemStack diamond = new ItemStack(Material.DIAMOND);
            Stack.dropRandomItem(location, player);
        }
    }

    public int getSpongeBlocksBroken(String playerName) {
        return spongeBlocksBroken.getOrDefault(playerName, 0);
    }

}
