package com.fighter.cutebees.utils;

import com.fighter.cutebees.main.Main;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Utils {

    public String pch(String s) {

        s = s.replace("%prefix%", Objects.requireNonNull(Main.getInstance().getConfig().getString("Messages.prefix")));
        s = ChatColor.translateAlternateColorCodes('&', s);

        return s;
    }

    public List<String> pch(List<String> lores) {
        return lores.stream().map(s -> ChatColor.translateAlternateColorCodes('&', s)).collect(Collectors.toList());
    }
}
