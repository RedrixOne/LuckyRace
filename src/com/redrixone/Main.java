package com.redrixone;

import com.redrixone.events.SpongeBreak;
import com.redrixone.events.Test;
import com.redrixone.gamemanager.GameManager;
import com.redrixone.scoreboard.Scoreboard;
import com.redrixone.testcommands.ResetTest;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private GameManager gameManager;

    @Override
    public void onEnable() {
        System.out.println("LuckyRace | Game enabled");

        Bukkit.getServer().getPluginManager().registerEvents(new Scoreboard(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new Test(new GameManager(this)), this);
        Bukkit.getServer().getPluginManager().registerEvents(new SpongeBreak(), this);

        //Registrazione comandi
        //getCommand("mapres").setExecutor(new ResetTest());

        //Registazione canale bungeecord
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");


        Scoreboard scoreboard = new Scoreboard(this);
        scoreboard.initScoreboard(20);
    }

    @Override
    public void onDisable() {
        System.out.println("LuckyRace | Game disabled");
    }

    public GameManager getGameManager() {
        return gameManager;
    }
}
