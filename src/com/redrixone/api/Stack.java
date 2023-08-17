package com.redrixone.api;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Stack {

    public static ItemStack createFlameSword() {
        ItemStack spada = new ItemStack(Material.DIAMOND_SWORD);
        spada.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 100);
        short durability = 1561;
        spada.setDurability(durability);
        ItemMeta meta = spada.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&bMoon&fSword &7[&4OP&7]"));
        List<String> lore = new ArrayList<String>();
        lore.add("§7");
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Kill with onehit your &4ENEMIES&7!"));
        meta.setLore(lore);
        spada.setItemMeta(meta);

        return spada;
    }

    public void spawnZombie(Player player, Location location) {
        Zombie zombie = (Zombie) player.getWorld().spawnEntity(location, EntityType.ZOMBIE);
        zombie.setBaby(false);

    }

    public static ItemStack createDiamondHelmet() {
        ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
        helmet.getItemMeta().setDisplayName("§bElmetto in diamante");
        return helmet;
    }

    public static ItemStack createBow() {
        ItemStack bow = new ItemStack(Material.BOW);
        ItemMeta meta = bow.getItemMeta();
        meta.setDisplayName("§aArco");
        bow.setItemMeta(meta);
        return bow;
    }

    public static ItemStack createLucky() {
        ItemStack sponge = new ItemStack(Material.SPONGE);
        ItemMeta meta = sponge.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&lLucky&e&lBlocks"));
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Good luck ;)"));
        meta.setLore(lore);
        sponge.setItemMeta(meta);

        return sponge;
    }

    public static void dropRandomItem(Location location, Player player) {
        int randomNum = new Random().nextInt(6);
        ItemStack item = null;
        String message = "";
        World world = location.getWorld();
        Stack stack = new Stack();

        switch (randomNum) {
            case 0:
                item = createFlameSword();
                message = "Hai ottenuto una §cSpada del drago§r!";
                item.setAmount(1);
                break;
            case 1:
                item = createDiamondHelmet();
                message = "Hai ottenuto un §bElmetto in diamante§r!";
                item.setAmount(1);
                break;
            case 2:
                item = createBow();
                message = "Hai ottenuto un §aArco§r!";
                item.setAmount(1);
                break;
            case 3:
                player.setVelocity(new Vector(0, 50, 0));
                player.playSound(location, Sound.FIREWORK_LAUNCH, 2, 1);
                break;
            case 4:
                stack.spawnZombie(player, location);
                message = "Hai ottenuto un §2Zombie§r!";
                player.sendMessage(message);
                break;
            case 5:
                item = createLucky();
                message = "Hai ottenuto &b12 §6Lucky§eBlocks§r!";
                item.setAmount(12);
                break;
            default:
                item = null;
                break;
        }

        if (item != null) {
            world.dropItem(location, item);
            player.sendMessage(message + "b");
        }

    }

}
