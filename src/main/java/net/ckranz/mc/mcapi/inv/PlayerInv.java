package net.ckranz.mc.mcapi.inv;

import net.ckranz.mc.mcapi.inv.listeners.ItemClickEvent;
import net.ckranz.mc.mcapi.inv.listeners.PlayerSelectEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerInv {
    public static void openSelectionInv(PlayerSelectEvent listener, Plugin plugin) {
        openSelectionInv(Bukkit.getServer().getWorlds().get(0), listener, plugin);
    }

    public static void openSelectionInv(World w, PlayerSelectEvent listener, Plugin plugin) {
        ArrayList<String> players = new ArrayList<>();
        for(Player p : w.getPlayers()) {
            players.add(p.getName());
        }
        Collections.sort(players);
        openSelectionInv(players, listener, plugin);
    }

    public static void openSelectionInv(List<String> players, PlayerSelectEvent listener, Plugin plugin) {
        PageInv playerInv = new PageInv(plugin);
        while(players.size() > 0) {
            ChestInv inv;
            if(players.size() > 50) {
                inv = new ChestInv("Select a player", 6, plugin);
            } else {
                inv = new ChestInv("Select a player", (players.size() + 11) / 9, plugin);
            }

            for(int i = 0; i < inv.getLines(); i++) {
                for(int j = 0; j < 9; j++) {
                    if(i != inv.getLines() - 1 || j > 1) {
                        InvItem skull = new InvItem(Material.PLAYER_HEAD, players.get(0), plugin);
                        SkullMeta meta = (SkullMeta) skull.getItemMeta();
                        meta.setOwner(players.get(0));
                        skull.setItemMeta(meta);
                        skull.addClickEvent(new ItemClickEvent() {
                            @Override
                            public void onItemClick(InventoryClickEvent e) {
                                e.setCancelled(true);

                            }
                        });
                        inv.setItem(i * 9 + j, skull);

                        players.remove(0);
                    }
                }
            }
            playerInv.addPage(inv);
        }
    }
}