package net.ckranz.mc.mcapi.inv.listeners;

import org.bukkit.event.inventory.InventoryDragEvent;

public abstract class ItemDragEvent {
    public abstract void onItemDrag(InventoryDragEvent e);
}