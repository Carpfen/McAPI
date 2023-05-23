package net.ckranz.mc.mcapi.inv;

import net.ckranz.mc.mcapi.inv.listeners.ItemClickEvent;
import net.ckranz.mc.mcapi.inv.listeners.ItemDragEvent;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public class InvItem extends ItemStack {
    private final String id;
    private final Plugin plugin;
    private final NamespacedKey nbtKey;
    private static int ids;

    public InvItem(Material material, Plugin plugin) {
        this(material, new ItemStack(material).getItemMeta().getDisplayName(), plugin);
    }

    public InvItem(String id, Material material, Plugin plugin) {
        this(id, material, new ItemStack(material).getItemMeta().getDisplayName(), plugin);
    }

    public InvItem(Material material, String itemName, Plugin plugin) {
        this(material, itemName, null, plugin);
    }

    public InvItem(String id, Material material, String itemName, Plugin plugin) {
        this(id, material, itemName, null, plugin);
    }

    public InvItem(Material material, String itemName, List<String> lore, Plugin plugin) {
        this(Integer.toString(ids), material, itemName, lore, plugin);
    }

    public InvItem(String id, Material material, String itemName, List<String> lore, Plugin plugin) {
        super(material);
        this.id = id;
        this.plugin = plugin;
        ItemMeta itemMeta = getItemMeta();
        itemMeta.setDisplayName(itemName);
        itemMeta.setLore(lore);
        nbtKey = new NamespacedKey(plugin, "ID");
        itemMeta.getPersistentDataContainer().set(nbtKey, PersistentDataType.STRING, id);
        setItemMeta(itemMeta);

        try {
            int idInt = Integer.parseInt(id);
            if(idInt >= ids) {
                ids = idInt + 1;
            }
        } catch (NumberFormatException ignored) {}
    }

    public String getId() {
        return id;
    }

    public void addClickEvent(ItemClickEvent e) {
        plugin.getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onItemClick(InventoryClickEvent event) {
                if(event.getClickedInventory() != null && event.getCurrentItem() != null) {
                    if(event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(nbtKey, PersistentDataType.STRING).equals(id)) {
                        e.onItemClick(event);
                    }
                }
            }
        }, plugin);
    }

    public void addDragEvent(ItemDragEvent e) {
        plugin.getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onItemDrag(InventoryDragEvent event) {
                if(event.getCursor() != null) {
                    if(event.getCursor().getItemMeta().getPersistentDataContainer().get(nbtKey, PersistentDataType.STRING).equals(id)) {
                        e.onItemDrag(event);
                    }
                }
            }
        }, plugin);
    }


    public static List<String> createLore(String lore) {
        return Arrays.asList(lore.split("\n"));
    }
}