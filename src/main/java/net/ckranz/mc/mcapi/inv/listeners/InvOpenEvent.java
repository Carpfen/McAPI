package net.ckranz.mc.mcapi.inv.listeners;

import org.bukkit.event.inventory.InventoryOpenEvent;

public abstract class InvOpenEvent {
    public abstract void onInventoryOpen(InventoryOpenEvent e);
}