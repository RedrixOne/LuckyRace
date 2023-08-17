package com.redrixone.scoreboard;

import com.redrixone.Main;
import com.redrixone.gamemanager.GameManager;
import com.redrixone.gamemanager.GameState;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class Scoreboard implements Listener {

    private BukkitTask task;
    private Main plugin;

    private GameManager gameManager;

    public Scoreboard(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCreate(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Netherboard.instance().createBoard(player, "Lobby");
    }

    public void initScoreboard(int delay) {
        task = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    updateScoreboard(player);
                }
            }
        }.runTaskTimer(plugin, 0, delay);
    }

    public void updateScoreboard(Player player) {
        BPlayerBoard board = Netherboard.instance().getBoard(player);
        if (Bukkit.getOnlinePlayers().size() < 2) {
            board.set("§c ", 4);
            board.set("§7⚔︎ §fUccisioni: §b*uccisioni*", 3);
            board.set("§7☠ §fMorti: §b" + Bukkit.getOnlinePlayers().size() + "§3/§b2", 2);
            board.set("§a ", 1);
            board.set("§cWaiting for players", 0);
        }

        board.setName("§b§lLucky§f§lRace");
    }

}
