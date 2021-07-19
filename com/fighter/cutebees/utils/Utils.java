package com.fighter.cutebees.utils;

import com.fighter.cutebees.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Utils {

    public String hexColor(String string) {

        Pattern p = Pattern.compile("<#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})>");

        Matcher m = p.matcher(string);

        while (m.find()) {
            string = string.replace("<#" + m.group(1) + ">", net.md_5.bungee.api.ChatColor.of("#" + m.group(1)).toString());
        }

        string = ChatColor.translateAlternateColorCodes('&', string);

        return string;
    }

    public String pch(String s) {

        if (versionCheckerForHex(Arrays.asList("1.16", "1.17"))) {
            s = hexColor(s);
        }
        s = s.replace("%prefix%", Objects.requireNonNull(Main.getInstance().getConfig().getString("Messages.prefix")));
        s = ChatColor.translateAlternateColorCodes('&', s);

        return s;
    }

    public boolean versionCheckerForHex(List<String> versions) {
        for (int i = 0; i < versions.size(); i++) {
            String version = versions.get(i);
            if (Bukkit.getServer().getBukkitVersion().contains(version)) {
                return true;
            }
        }
        return false;
    }

    public List<String> pch(List<String> lores) {

        if (versionCheckerForHex(Arrays.asList("1.16", "1.17"))) {
            lores = lores.stream().map(this::hexColor).collect(Collectors.toList());
        }
        lores = lores.stream().map(s -> s.replace("%prefix%", Objects.requireNonNull(Main.getInstance().getConfig().getString("Messages.prefix")))).collect(Collectors.toList());
        return lores.stream().map(s -> ChatColor.translateAlternateColorCodes('&', s)).collect(Collectors.toList());
    }
}
