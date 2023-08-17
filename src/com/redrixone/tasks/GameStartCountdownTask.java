package com.redrixone.tasks;

import com.redrixone.events.SpongeBreak;
import com.redrixone.gamemanager.GameManager;
import com.redrixone.gamemanager.GameState;
import com.redrixone.scoreboard.Scoreboard;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameStartCountdownTask extends BukkitRunnable {

    SpongeBreak spongeBreak = new SpongeBreak();

    private GameManager gameManager;
    private Scoreboard scoreboard;

    public GameStartCountdownTask(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public GameStartCountdownTask(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    private int timeLeft = 10;

    public void updateTimer(Player player, String text, int position) {
        BPlayerBoard board = Netherboard.instance().getBoard(player);
        board.set(text, position);
    }

    @Override
    public void run() {
        Bukkit.broadcastMessage("§3" + timeLeft + " §bseconds left.");
        for (Player player : Bukkit.getOnlinePlayers()) {
            updateTimer(player, "§c", 4);
            updateTimer(player, "§7Map: §bOcean", 3);
            updateTimer(player, "§7Players: §b" + Bukkit.getOnlinePlayers().size() + "§3/§b2", 2);
            updateTimer(player, "§a ", 1);
            updateTimer(player, "§3" + timeLeft + " §bseconds left.", 0);
        }

        if (gameManager.state == GameState.WAITING) {
            cancel();
        }
        timeLeft--;
        if (timeLeft <= 0) {
            gameManager.setState(GameState.ACTIVE);
            for (Player player : Bukkit.getOnlinePlayers()) {
                int spongeBlocksBroken = spongeBreak.getSpongeBlocksBroken(player.getName());
                updateTimer(player, "§c", 4);
                updateTimer(player, "§7Broken blocks: §b" + spongeBlocksBroken + "§3/§b23", 3);
                updateTimer(player, "§7Kills: §b//uccisioni", 2);
                updateTimer(player, "§r", 1);
                updateTimer(player, "§3www.§bmoon§fpixel§3.com", 0);
            }
            return;
        }


    }
}
