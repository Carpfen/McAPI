package net.ckranz.mc.mcapi.inv;

import net.ckranz.mc.mcapi.inv.listeners.ItemClickEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class PageInv {
    private final Plugin plugin;
    private final ArrayList<Inventory> pages = new ArrayList<>();

    public PageInv(Plugin plugin) {
        this.plugin = plugin;
    }

    public ArrayList<Inventory> getPages() {
        return pages;
    }

    public Inventory getPage(int page) {
        return pages.get(page);
    }

    public void addPage(Inventory inv) {
        pages.add(inv);
    }

    public void removePage(int page) {
        pages.remove(page);
    }

    public void openInventory(Player player) {
        openInventory(player, 0);
    }

    public void openInventory(Player player, int page) {
        Inventory inv = pages.get(page);
        if(pages.size() > 0) {
            if(page < pages.size() - 1) {
                InvItem item = new InvItem("rigthPage", Material.ARROW, (page + 2) + " >", plugin);
                item.addClickEvent(new ItemClickEvent() {
                    @Override
                    public void onItemClick(InventoryClickEvent e) {
                        e.setCancelled(true);
                        openInventory(player, page + 1);
                    }
                });
                inv.setItem(inv.getSize() - 1, item);
            }
            if(page > 0) {
                InvItem item = new InvItem("leftPage", Material.ARROW, "< " + page, plugin);
                item.addClickEvent(new ItemClickEvent() {
                    @Override
                    public void onItemClick(InventoryClickEvent e) {
                        e.setCancelled(true);
                        openInventory(player, page - 1);
                    }
                });
                inv.setItem(inv.getSize() - 9, item);
            }
        }
        player.openInventory(inv);
    }
}