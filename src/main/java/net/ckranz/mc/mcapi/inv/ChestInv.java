package net.ckranz.mc.mcapi.inv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import net.ckranz.mc.mcapi.inv.listeners.InvCloseEvent;
import net.ckranz.mc.mcapi.inv.listeners.InvOpenEvent;
import net.ckranz.mc.mcapi.inv.listeners.ItemClickEvent;
import net.ckranz.mc.mcapi.inv.listeners.ItemDragEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class ChestInv implements Inventory {
    private final Inventory inv;
    private final String title;
    private final Plugin plugin;
    private final ArrayList<HumanEntity> players = new ArrayList<>();

    public ChestInv(String title, int lines, Plugin plugin) {
        inv = Bukkit.createInventory(null, lines * 9, title);
        this.title = title;
        this.plugin = plugin;
    }

    public int getLines() {
        return inv.getSize() / 9;
    }

    @Override
    public int getSize() {
        return inv.getSize();
    }

    @Override
    public int getMaxStackSize() {
        return inv.getMaxStackSize();
    }

    @Override
    public void setMaxStackSize(int i) {
        inv.setMaxStackSize(i);
    }

    public ItemStack getItem(int i) {
        return inv.getItem(i);
    }

    @Override
    public void setItem(int i, ItemStack itemStack) {
        inv.setItem(i, itemStack);
    }

    @Override
    public HashMap<Integer, ItemStack> addItem(ItemStack... itemStacks) throws IllegalArgumentException {
        return inv.addItem(itemStacks);
    }

    @Override
    public HashMap<Integer, ItemStack> removeItem(ItemStack... itemStacks) throws IllegalArgumentException {
        return inv.removeItem(itemStacks);
    }

    @Override
    public ItemStack[] getContents() {
        return inv.getContents();
    }

    @Override
    public void setContents(ItemStack[] itemStacks) throws IllegalArgumentException {
        inv.setContents(itemStacks);
    }

    @Override
    public ItemStack[] getStorageContents() {
        return inv.getStorageContents();
    }

    @Override
    public void setStorageContents(ItemStack[] itemStacks) throws IllegalArgumentException {
        inv.setStorageContents(itemStacks);
    }

    @Override
    public boolean contains(Material material) throws IllegalArgumentException {
        return inv.contains(material);
    }

    @Override
    public boolean contains(ItemStack itemStack) {
        return inv.contains(itemStack);
    }

    @Override
    public boolean contains(Material material, int i) throws IllegalArgumentException {
        return inv.contains(material, i);
    }

    @Override
    public boolean contains(ItemStack itemStack, int i) {
        return inv.contains(itemStack, i);
    }

    @Override
    public boolean containsAtLeast(ItemStack itemStack, int i) {
        return inv.containsAtLeast(itemStack, i);
    }

    @Override
    public HashMap<Integer, ? extends ItemStack> all(Material material) throws IllegalArgumentException {
        return inv.all(material);
    }

    @Override
    public HashMap<Integer, ? extends ItemStack> all(ItemStack itemStack) {
        return inv.all(itemStack);
    }

    @Override
    public int first(Material material) throws IllegalArgumentException {
        return inv.first(material);
    }

    @Override
    public int first(ItemStack itemStack) {
        return inv.first(itemStack);
    }

    @Override
    public int firstEmpty() {
        return inv.firstEmpty();
    }

    @Override
    public boolean isEmpty() {
        return inv.isEmpty();
    }

    @Override
    public void remove(Material material) throws IllegalArgumentException {
        inv.remove(material);
    }

    @Override
    public void remove(ItemStack itemStack) {
        inv.remove(itemStack);
    }

    @Override
    public void clear(int i) {
        inv.clear(i);
    }

    @Override
    public void clear() {
        inv.clear();
    }

    @Override
    public List<HumanEntity> getViewers() {
        return inv.getViewers();
    }

    @Override
    public InventoryType getType() {
        return inv.getType();
    }

    @Override
    public InventoryHolder getHolder() {
        return inv.getHolder();
    }

    @Override
    public ListIterator<ItemStack> iterator() {
        return inv.iterator();
    }

    @Override
    public ListIterator<ItemStack> iterator(int i) {
        return inv.iterator(i);
    }

    @Override
    public Location getLocation() {
        return inv.getLocation();
    }


    public void addOpenEvent(InvOpenEvent e) {
        plugin.getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onInventoryOpen(InventoryOpenEvent event) {
                if(event.getInventory().equals(inv)) {
                    e.onInventoryOpen(event);
                }
                if(!event.isCancelled()) {
                    players.add(event.getPlayer());
                }
            }
        }, plugin);
    }

    public void addCloseEvent(InvCloseEvent e) {
        plugin.getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onInventoryClose(InventoryCloseEvent event) {
                if(players.contains(event.getPlayer())) {
                    e.onInventoryClose(event);
                }
                players.remove(event.getPlayer());
            }
        }, plugin);
    }

    public void addClickEvent(ItemClickEvent e) {
        plugin.getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onItemClick(InventoryClickEvent event) {
                if(players.contains(event.getWhoClicked())) {
                    if(event.getClickedInventory() != null && event.getCurrentItem() != null) {
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
                if(players.contains(event.getWhoClicked())) {
                    if(event.getCursor() != null) {
                        e.onItemDrag(event);
                    }
                }
            }
        }, plugin);
    }
}