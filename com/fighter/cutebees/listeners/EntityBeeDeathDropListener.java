package com.fighter.cutebees.listeners;

import com.fighter.cutebees.commands.Commands;
import com.fighter.cutebees.custom_file_manager.CustomItemFile;
import com.fighter.cutebees.custom_file_manager.WorldEnable;
import com.fighter.cutebees.main.Main;
import com.fighter.cutebees.utils.Utils;
import com.fighter.cutebees.utils.itembuild.Flag;
import com.fighter.cutebees.utils.itembuild.ItemBuild;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Bee;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class EntityBeeDeathDropListener implements Listener {
    private final Main main = Main.instance;
    private final static Utils util = new Utils();
    private final static FileConfiguration itemConfig = CustomItemFile.getItemConfig();

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDropItem(EntityDeathEvent event) {
        LivingEntity livingEntity = event.getEntity();
        Random r = new Random();
        int r_chance = r.nextInt(99) + 1;

        for (World world : Bukkit.getServer().getWorlds()) {
            List<String> worldEnable = WorldEnable.getWorldConfig().getStringList("world-enable");
            if (livingEntity instanceof Bee) {
                event.getDrops().clear();
                for (int i = 0; i < Commands.createdItems.size(); i++) {
                    int chan = Commands.createdItems.get(i).getChance();
                    if (r_chance <= chan && worldEnable.contains(world.getName())) {
                        event.getDrops().add(Commands.createdItems.get(i).convertToItemStack());
                    }
                }

            }
        }
    }

    public static void loadCustomItems() {
        Commands.createdItems.removeAll(Commands.createdItems);
        for (String key: itemConfig.getConfigurationSection("custom-bee-drops").getKeys(false)) {

            List<String> enchants = itemConfig.getStringList("custom-bee-drops." + key + ".enchantments");
            List<String> item_lore = itemConfig.getStringList("custom-bee-drops." + key + ".lore");
            String item_display_name = itemConfig.getString("custom-bee-drops." + key + ".display-name");

            ItemBuild itemBuild = new ItemBuild(Material.getMaterial(Objects.requireNonNull(
                    itemConfig.getString("custom-bee-drops." + key + ".material"))),
                    itemConfig.getInt("custom-bee-drops."+key+".amount"))
                    .setLore(itemConfig.getStringList("custom-bee-drops." + key + ".lore"));

            if (!enchants.isEmpty()) {
                for (int i = 0; i < enchants.size(); i++) {
                    String enchant = enchants.get(i);
                    String[] darab = enchant.split(":");
                    itemBuild.setEnchant(Enchantment.getByKey(NamespacedKey.minecraft(darab[0].toLowerCase(Locale.ROOT))), Integer.parseInt(darab[1]));
                }
            } else {
                itemBuild.removeEnchantments();
            }
            itemBuild.setLore(item_lore != null && !item_lore.isEmpty() ? util.pch(item_lore) : null);
            itemBuild.setName(item_display_name != null ? util.pch(item_display_name) : itemBuild.getMaterial().name());

            itemBuild.setAmount(itemBuild.getAmount());

            int item_chance = itemConfig.getInt("custom-bee-drops."+key+".chance");
            itemBuild.setChance(item_chance);

            List<String> flags = itemConfig.getStringList("custom-bee-drops." + key + ".item-flags");
            if (flags.contains("HIDE_ALL_FLAG") && !flags.isEmpty()) {
                itemBuild.setFlag(Flag.HIDE_ALL_FLAG);
            } else if (flags.contains("HIDE_ENCHANTS")) {
                itemBuild.setFlag(Flag.HIDE_ENCHANTS);
            } else if (flags.contains("HIDE_ATTRIBUTES")) {
                itemBuild.setFlag(Flag.HIDE_ATTRIBUTES);
            } else if (flags.contains("HIDE_DESTROYS")) {
                itemBuild.setFlag(Flag.HIDE_DESTROYS);
            } else if (flags.contains("HIDE_PLACED_ON")) {
                itemBuild.setFlag(Flag.HIDE_PLACED_ON);
            } else if (flags.contains("HIDE_POTION_EFFECTS")) {
                itemBuild.setFlag(Flag.HIDE_POTION_EFFECTS);
            } else if (flags.contains("HIDE_UNBREAKABLE")) {
                itemBuild.setFlag(Flag.HIDE_UNBREAKABLE);
            } else {
                itemBuild.setFlag(Flag.NULL);
            }

            Commands.createdItems.add(itemBuild);

        }
    }


}
