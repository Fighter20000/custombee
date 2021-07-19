package com.fighter.cutebees.commands;

import com.fighter.cutebees.custom_file_manager.CustomItemFile;
import com.fighter.cutebees.custom_file_manager.WorldEnable;
import com.fighter.cutebees.listeners.EntityBeeDeathDropListener;
import com.fighter.cutebees.main.Main;
import com.fighter.cutebees.utils.Utils;
import com.fighter.cutebees.utils.itembuild.ItemBuild;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class Commands implements CommandExecutor {

    private Utils util = new Utils();
    private final FileConfiguration itemConfig = CustomItemFile.getItemConfig();
    public final static ArrayList<ItemBuild> createdItems = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(util.pch(Main.getInstance().getConfig().getString("Messages.help-usage")));
            return true;
        }
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("help")) {
                if (sender.hasPermission("cutebees.help") || sender.hasPermission("cutebees.admin")) {
                    sender.sendMessage(util.pch("&a/" + cmd.getName() + " help - this page"));
                    sender.sendMessage(util.pch("&a/" + cmd.getName() + " reload - reload configuration"));
                    sender.sendMessage(util.pch("&a/" + cmd.getName() + " enable <world> - enable plugin(function) in each world"));
                    sender.sendMessage(util.pch("&a/" + cmd.getName() + " disable <world> - disable plugin(function) in each world"));
                    sender.sendMessage(util.pch("&a/" + cmd.getName() + " create customitem - create custom drops for bees"));
                    sender.sendMessage(util.pch("&a/" + cmd.getName() + " customitems  - Custom items in a GUI"));
                    return true;
                }
                sender.sendMessage(util.pch(Main.getInstance().getConfig().getString("Messages.no-permission")));
                return true;
            }
            if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
                if (sender.hasPermission("cutebees.reload") || sender.hasPermission("cutebees.admin")) {
                    try {
                        WorldEnable.reloadConfig();
                        CustomItemFile.reloadConfig();
                        Main.instance.reloadConfig();
                        sender.sendMessage(util.pch(Main.getInstance().getConfig().getString("Messages.reload")));
                        return true;

                    } catch (Exception e) {
                        System.out.println(String.join("\n", util.pch(Main.getInstance().getConfig().getString("Messages.wrong-configuration"))));
                        e.printStackTrace();
                        return true;
                    }
                }
                sender.sendMessage(util.pch(Main.getInstance().getConfig().getString("Messages.no-permission")));
                return true;
            }
            if (args[0].equalsIgnoreCase("enable")) {
                if (sender.hasPermission("cutebees.enableworld") || sender.hasPermission("cutebees.admin")) {
                    if (args.length <= 1) {
                        sender.sendMessage(util.pch(Main.getInstance().getConfig().getString("Messages.enable-world-usage")));
                        return true;
                    }
                    String world = args[1];
                    List<String> worlds_enable = WorldEnable.getWorldConfig().getStringList("world-enable");
                    if (!worlds_enable.contains(world)) {
                        worlds_enable.add(args[1]);
                        WorldEnable.getWorldConfig().set("world-enable", worlds_enable);
                        sender.sendMessage(util.pch(Main.getInstance().getConfig().getString("Messages.enable-world")));
                        WorldEnable.saveConfig();
                        return true;
                    }
                    sender.sendMessage(util.pch(Main.getInstance().getConfig().getString("Messages.world-already-exists")));
                    return true;

                }
                sender.sendMessage(util.pch(Main.getInstance().getConfig().getString("Messages.no-permission")));
                return true;
            }
            if (args[0].equalsIgnoreCase("disable")) {
                if (sender.hasPermission("cutebees.disableworld") || sender.hasPermission("cutebees.admin")) {
                    if (args.length <= 1) {
                        sender.sendMessage(util.pch(Main.getInstance().getConfig().getString("Messages.disable-world-usage")));
                        return true;
                    }
                    String world = args[1];
                    List<String> worlds_enable = WorldEnable.getWorldConfig().getStringList("world-enable");
                    if (worlds_enable.contains(world)) {
                        worlds_enable.remove(world);
                        WorldEnable.getWorldConfig().set("world-enable", worlds_enable);
                        sender.sendMessage(util.pch(Main.getInstance().getConfig().getString("Messages.disable-world")));
                        WorldEnable.saveConfig();
                        return true;
                    }
                    sender.sendMessage(util.pch(Main.getInstance().getConfig().getString("Messages.world-not-exists")));
                    return true;

                }
                sender.sendMessage(util.pch(Main.getInstance().getConfig().getString("Messages.no-permission")));
                return true;
            }
            if (args[0].equalsIgnoreCase("create")) {
                if(sender.hasPermission("cutebees.create") || sender.hasPermission("cutebees.admin")) {
                    if (args.length == 1) {
                        sender.sendMessage(util.pch(Main.getInstance().getConfig().getString("Messages.customitems.create-usage")));
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("customitem")) {
                        if (args.length == 2) {
                            for(String key_usage : util.pch(Main.getInstance().getConfig().getStringList("Messages.customitems.item-key-usage"))) {
                                sender.sendMessage(key_usage);
                            }
                            return true;
                        }
                        if (args.length == 3) {
                            for(String material_usage : util.pch(Main.getInstance().getConfig().getStringList("Messages.customitems.material-usage"))) {
                                sender.sendMessage(material_usage);
                            }
                            return true;
                        }
                        String item_id = args[3];
                        if(Material.getMaterial(item_id) == null) {
                            sender.sendMessage(util.pch("%prefix% &cMaterial is null, make sure is it added correctly!"));
                            return true;
                        }
                        if (args.length == 4) {
                            for(String amount_usage : util.pch(Main.getInstance().getConfig().getStringList("Messages.customitems.amount-usage"))) {
                                sender.sendMessage(amount_usage);
                            }
                            return true;
                        }
                        String amount = args[4];
                        if (!NumberUtils.isNumber(amount)) {
                            sender.sendMessage(util.pch("%prefix% &cInvalid number! Please add a valid number!"));
                            return true;
                        }
                        if (args.length == 5) {
                            for(String display_usage : util.pch(Main.getInstance().getConfig().getStringList("Messages.customitems.display-name-usage"))) {
                                sender.sendMessage(display_usage);
                            }
                            return true;
                        }
                        if (args.length == 6) {
                            for(String chance_usage : util.pch(Main.getInstance().getConfig().getStringList("Messages.customitems.chance-usage"))) {
                                sender.sendMessage(chance_usage);
                            }
                            return true;
                        }
                        String chance = args[6];
                        if (!NumberUtils.isNumber(chance)) {
                            sender.sendMessage(util.pch("%prefix% &cInvalid number! Please add a valid number!"));
                            return true;
                        }

                        if (args.length < 8) {
                            String key = args[2];
                            if (!itemConfig.contains("custom-bee-drops." + key)) {

                                String display_name = args[5];

                                itemConfig.set("custom-bee-drops." + key, null);
                                itemConfig.set("custom-bee-drops." + key + ".material", item_id);
                                itemConfig.set("custom-bee-drops." + key + ".display-name", display_name);
                                itemConfig.set("custom-bee-drops." + key + ".amount", Integer.parseInt(amount));
                                itemConfig.set("custom-bee-drops." + key + ".chance", Integer.parseInt(chance));
                                itemConfig.set("custom-bee-drops." + key + ".item-flags", "[]");
                                itemConfig.set("custom-bee-drops." + key + ".enchantments", "[]");
                                itemConfig.set("custom-bee-drops." + key + ".lore", Arrays.asList("", "&eHello world", "&aHow you doing", "&5Nice custom item", ""));

                                ItemBuild customitem = new ItemBuild(Material.getMaterial(Objects.requireNonNull(
                                        itemConfig.getString("custom-bee-drops." + key + ".material"))),
                                        itemConfig.getInt("custom-bee-drops." + key + ".amount"));

                                EntityBeeDeathDropListener.loadCustomItems();
                                sender.sendMessage(util.pch(Main.getInstance().getConfig().getString("Messages.customitems.create-item")));
                                CustomItemFile.saveConfig();
                                return true;

                            }
                            sender.sendMessage(util.pch(Main.getInstance().getConfig().getString("Messages.customitems.item-exists")));
                            return true;

                        }
                        sender.sendMessage(util.pch(Main.getInstance().getConfig().getString("Messages.unknown-subcommand")));
                        return true;
                    }
                }

            }

            if (args[0].equalsIgnoreCase("customitems")) {
                if (sender instanceof Player) {
                    if (sender.hasPermission("cutebees.customitems") || sender.hasPermission("cutebees.admin")) {
                        Inventory inventory = Bukkit.createInventory((InventoryHolder) sender, 54, "Custom Items - §e§lLEFT CLICK to get item");
                        for (int i = 0; i < createdItems.size(); i++) {
                            inventory.setItem(i, createdItems.get(i).convertToItemStack());
                        }
                        ((Player) sender).openInventory(inventory);
                        sender.sendMessage(util.pch(Main.getInstance().getConfig().getString("Messages.customitems.open-inventory")));
                        return true;

                    }
                    sender.sendMessage(util.pch(Main.getInstance().getConfig().getString("Messages.no-permission")));
                    return true;
                }
                sender.sendMessage(util.pch("%prefix% &cYou need to be a player to use this command!"));
                return true;
            }

        }
        sender.sendMessage(util.pch(Main.getInstance().getConfig().getString("Messages.unknown-subcommand")));
        return true;
    }
}
