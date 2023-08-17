package com.redrixone.events;

import com.redrixone.gamemanager.GameManager;
import com.redrixone.gamemanager.GameState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Test implements Listener {

    private GameManager gameManager;

    public Test(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (Bukkit.getOnlinePlayers().size() == 2) {
            gameManager.setState(GameState.STARTING);
        } else  {
            gameManager.setState(GameState.WAITING);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        gameManager.setState(GameState.WAITING);
    }

}
