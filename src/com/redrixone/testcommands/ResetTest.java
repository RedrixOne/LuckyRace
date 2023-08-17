package com.redrixone.testcommands;

import org.apache.commons.io.FileUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.Seekable;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.FileUtil;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.bukkit.Bukkit.getServer;

public class ResetTest implements CommandExecutor {

    Plugin plugin =getServer().getPluginManager().getPlugin("LuckyRace");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String laber, String[] args) {
        if (!(sender instanceof Player)) {
            File sourceFolder = new File(getServer().getWorldContainer(), "map_reset");
            File destFolder = new File(getServer().getWorldContainer(), "world");
            try {
                FileUtils.deleteDirectory(destFolder);
                Files.copy(sourceFolder.toPath(), destFolder.toPath());
            } catch (IOException e) {
                System.out.println("Impossibile reimpostare la mappa del mondo.");
                e.printStackTrace();
                return true;
            }
        }

        Player player = (Player) sender;

        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF("lobby");
        } catch (IOException e) {
            e.printStackTrace();
        }

        player.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());

        File sourceFolder = new File(getServer().getWorldContainer(), "map_reset");
        File destFolder = new File(getServer().getWorldContainer(), "world");

        if (!sourceFolder.exists() || !sourceFolder.isDirectory()) {
            player.sendMessage("La cartella \"map_reset\" non esiste o non è una directory.");
            return true;
        }

        // Elimina il mondo corrente
        File currentWorldFolder = new File(getServer().getWorldContainer(), "world");
        deleteFiles(currentWorldFolder);

        // Copia il mondo dalla cartella "map_reset" alla directory principale del server
        try {
            FileUtils.deleteDirectory(destFolder);
            Files.copy(sourceFolder.toPath(), destFolder.toPath());
        } catch (IOException e) {
            player.sendMessage("Impossibile reimpostare la mappa del mondo.");
            e.printStackTrace();
            return true;
        }

        player.sendMessage("Mappa del mondo reimpostata con successo.");
        return true;
    }

    private void deleteFiles(File folder) {
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteFiles(file);
                }
            }
        }
        folder.delete();
    }

    private void copyFolder(File sourceFolder, File destFolder) throws IOException {
        if (sourceFolder.isDirectory()) {
            // Crea la cartella di destinazione se non esiste
            if (!destFolder.exists()) {
                destFolder.mkdir();
            }

            // Copia tutti i file nella cartella sorgente nella cartella di destinazione
            String[] files = sourceFolder.list();
            if (files != null) {
                for (String file : files) {
                    File srcFile = new File(sourceFolder, file);
                    File destFile = new File(destFolder, file);
                    copyFolder(srcFile, destFile);
                }
            }
        } else {
            // Copia il file dalla cartella sorgente alla cartella di destinazione
            java.nio.file.Files.copy(sourceFolder.toPath(), destFolder.toPath());
        }
    }
}
