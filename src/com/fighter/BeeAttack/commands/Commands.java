package com.fighter.BeeAttack.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.fighter.BeeAttack.main.main;



public class Commands implements Listener, CommandExecutor {

	private main main;
	private Inventory inv;

	public Commands(main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdlabel, String[] args) {
		if (args.length == 0) {

			String perm1 = main.getConfig().getString("CustomBee.Commands.Permissions.help");
			if (sender.hasPermission(perm1) || sender.hasPermission("custombee.*") || sender.hasPermission("*")) {

				sender.sendMessage(pch(main.getConfig().getString("CustomBee.Commands.Messages.help")));
				return true;
			} else {

				sender.sendMessage(pch(main.getConfig().getString("CustomBee.Commands.PermissionError")));
				return true;
			}

		}
		if (args.length == 1) {

			if (args[0].equalsIgnoreCase("reload")) {

				String perm2 = main.getConfig().getString("CustomBee.Commands.Permissions.reload");
				if (sender.hasPermission(perm2) || sender.hasPermission("custombee.*") || sender.hasPermission("*")) {
					main.reloadConfig();
					sender.sendMessage(pch(main.getConfig().getString("CustomBee.Commands.Messages.reload")));
					return true;

				} else {

					sender.sendMessage(pch(main.getConfig().getString("CustomBee.Commands.PermissionError")));
					return true;

				}

			}

			if (args[0].equalsIgnoreCase("gui")) {
				if (sender instanceof Player) {
					Player player = (Player) sender;

					String perm3 = main.getConfig().getString("CustomBee.Commands.Permissions.reload");
					if (sender.hasPermission(perm3) || sender.hasPermission("custombee.*")
							|| sender.hasPermission("*")) {
						
						inv = Bukkit.createInventory(player, InventoryType.CHEST, "BeeSettings");
						for(Player p: Bukkit.getOnlinePlayers()) {
							
							
							this.getInventory(p);
							guiItem(Material.DIAMOND, "teszt", 
									main.getConfig().getStringList("CustomGui.Diamond.lore"));
							return true;
						}

					} else {

						sender.sendMessage(pch(main.getConfig().getString("CustomBee.Commands.PermissionError")));
						return true;

					}

				}
			}

			sender.sendMessage(pch(main.getConfig().getString("CustomBee.Commands.UsageMessage")));
			return true;
		}

		return true;
	}

	private String pch(String string) {

		string = string.replace("%prefix%", main.getConfig().getString("CustomBee.Prefix"));
		string = ChatColor.translateAlternateColorCodes('&', string);

		return string;
	}
	public static ItemStack guiItem(Material material, String displayname, List<String> lore) {

		ItemStack item = new ItemStack(material, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(displayname);
		meta.setUnbreakable(true);

		ArrayList<String> lista = new ArrayList<String>();

		for (String lores : lore) {

			lista.add(ChatColor.translateAlternateColorCodes('&', lores));

			meta.setLore(lista);
		}

		item.setItemMeta(meta);

		return item;
	}
	
	
	@EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
		
        if (e.getInventory().getHolder() != this) {
            return;
        }
        if (e.getClick().equals(ClickType.NUMBER_KEY)){
            e.setCancelled(true);
        }
        e.setCancelled(true);

        Player p = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        // Using slots click is a best option for your inventory click's
        if (e.getRawSlot() == 1) p.sendMessage("You clicked at slot " + 1);
        	
        }

	public InventoryView getInventory(Player p) {
		// TODO Auto-generated method stub
		return  p.openInventory(inv);
	}

}
