package com.redrixone.gamemanager;

import com.redrixone.Main;
import com.redrixone.players.PlayerManager;
import com.redrixone.scoreboard.Scoreboard;
import com.redrixone.tasks.GameStartCountdownTask;
import fr.minuskube.netherboard.Netherboard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    public static List<Player> redTeam = new ArrayList<>();
    public static List<Player> blueTeam = new ArrayList<>();
    private Main plugin;
    private Scoreboard scoreboard;
    public GameState state;
    private final PlayerManager playerManager;
    private GameStartCountdownTask gameStartCountdownTask;

    public GameManager(Main plugin) {
        this.plugin = plugin;

        this.playerManager = new PlayerManager(this);
    }

    public void assignTeams() {
        List<Player> onlinePlayers = (List<Player>) Bukkit.getOnlinePlayers();
        int numPlayers = onlinePlayers.size();

        for (int i = 0; i < numPlayers; i++) {
            Player player = onlinePlayers.get(i);
            if (i%2 == 0) {
                redTeam.add(player);
                player.sendMessage("Sei stato assegnato al colore rosso");
                player.setPlayerListName("§4§lR §c" + player.getName());
                player.setDisplayName("§c" + player.getName());
            } else {
                blueTeam.add(player);
                player.sendMessage("Sei stato assegnato al colore blu");
                player.setPlayerListName("§1§lB §9" + player.getName());
                player.setDisplayName("§9" + player.getName());
            }
        }
    }

    public void setState(GameState state) {
        if (this.state == GameState.ACTIVE && state == GameState.STARTING) return;
        if (this.state == state) return;

        this.state = state;

        switch (state) {
            case WAITING:
                Bukkit.broadcastMessage("Test");
                //Leave default setups
                //Set cooldown to default
                //assign teams type (minerale)
                break;
            case STARTING:
                assignTeams();
                this.gameStartCountdownTask = new GameStartCountdownTask(this);
                this.gameStartCountdownTask.runTaskTimer(plugin, 0, 20);
                //start timer
                //start cooldown
                //change board
                break;
            case ACTIVE:
                if (this.gameStartCountdownTask != null) this.gameStartCountdownTask.cancel();
                Bukkit.broadcastMessage("Game Started!");
                playerManager.Kits();
                playerManager.Teleport();
                //teletrasporto di tutti i giocatori alle rispettive isole
                //consegnare i kit default a tutti i giocatori (Poi gestiti tramite gli shop per armature etc..)
                //Cambiare la scoreboard
                //attivare i generatori
                break;
            case END:
                //display dei rispettivi messaggi in caso di vincita o di perdita
                //teletrasporto di tutti i giocatori al server lobby
                //listener reset mappa etc..
                break;
        }
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }
}
