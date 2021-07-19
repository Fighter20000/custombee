package com.fighter.cutebees.listeners;

import com.fighter.cutebees.commands.Commands;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class CustomInventoryListener implements Listener {


    @EventHandler
    public void onCustomInventoryOpen(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!event.getView().getTitle().equalsIgnoreCase("Custom Items - §e§lLEFT CLICK to get item")) return;
        if (event.getView().getTitle().equalsIgnoreCase("Custom Items - §e§lLEFT CLICK to get item"))
            event.setCancelled(true);



        if (event.getView().getTitle().equalsIgnoreCase("Custom Items - §e§lLEFT CLICK to get item") &&
                event.getClick() == ClickType.LEFT &&
                event.getSlotType() != InventoryType.SlotType.QUICKBAR && event.getInventory().getType() == InventoryType.CHEST &&
                event.getClickedInventory() != event.getView().getBottomInventory() && event.getCurrentItem() != null) {

            player.getInventory().addItem(Commands.createdItems.get(event.getSlot()).convertToItemStack());
        }
    }

}
