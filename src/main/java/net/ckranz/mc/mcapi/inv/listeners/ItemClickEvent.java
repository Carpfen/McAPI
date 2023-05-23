package net.ckranz.mc.mcapi.inv.listeners;

import org.bukkit.event.inventory.InventoryClickEvent;

public abstract class ItemClickEvent {
    public abstract void onItemClick(InventoryClickEvent e);
}