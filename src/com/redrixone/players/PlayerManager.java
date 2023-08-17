package com.redrixone.players;

import com.redrixone.gamemanager.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerManager {

    private GameManager gameManager;

    public PlayerManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void Kits() {
        Bukkit.getOnlinePlayers().stream().filter(player -> player.getGameMode() == GameMode.SURVIVAL).forEach(this::giveKit);
    }

    public void Teleport() {
        Bukkit.getOnlinePlayers().stream().filter(player -> player.getGameMode() == GameMode.SURVIVAL).forEach(this::coords);
    }

    public void coords(Player player) {
        Location redSpawn = new Location(Bukkit.getWorld("world"), -452, 30, -118);
        Location blueSpawn = new Location(Bukkit.getWorld("world"), -445, 30, -118);
        if (gameManager.blueTeam.contains(player)) {
            player.teleport(blueSpawn);
        }
        if (gameManager.redTeam.contains(player)) {
            player.teleport(redSpawn);
        }
    }

    public void giveKit(Player player) {
        player.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
    }

}
