package com.fighter.cutebees.commands;

import com.fighter.cutebees.custom_file_manager.WorldEnable;
import com.fighter.cutebees.main.Main;
import com.fighter.cutebees.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;


public class Commands implements CommandExecutor {

    private Utils util = new Utils();

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
                    return true;
                }
                sender.sendMessage(util.pch(Main.getInstance().getConfig().getString("Messages.no-permission")));
                return true;
            }
            if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
                if (sender.hasPermission("cutebees.reload") || sender.hasPermission("cutebees.admin")) {
                    try {
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


        }
        sender.sendMessage(util.pch(Main.getInstance().getConfig().getString("Messages.unknown-subcommand")));
        return true;
    }
}
