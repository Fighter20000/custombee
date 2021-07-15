package com.fighter.cutebees.listeners;

import com.fighter.cutebees.custom_file_manager.WorldEnable;
import com.fighter.cutebees.main.Main;
import com.fighter.cutebees.utils.Utils;
import com.fighter.cutebees.utils.itembuild.Flag;
import com.fighter.cutebees.utils.itembuild.ItemBuild;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
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
    private final Utils util = new Utils();


    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDropItem(EntityDeathEvent event) {
        LivingEntity livingEntity = event.getEntity();
        Random r = new Random();
        int r_chance = r.nextInt(99) + 1;

        for (World world : Bukkit.getServer().getWorlds()) {
            List<String> worldEnable = WorldEnable.getWorldConfig().getStringList("world-enable");
            if (livingEntity instanceof Bee) {
                event.getDrops().clear();
                for (String key : main.getConfig().getConfigurationSection("custom-bee-drops").getKeys(false)) {
                    int chance = main.getConfig().getInt("custom-bee-drops." + key + ".chance");

                    if (r_chance <= chance && worldEnable.contains(world.getName())) {

                        ItemBuild customDrop = new ItemBuild(Material.getMaterial(Objects.requireNonNull(Main.getInstance().getConfig().getString("custom-bee-drops." + key + ".item-id"))), main.getConfig().getInt("custom-bee-drops." + key + ".amount"));

                        List<String> enchants = main.getConfig().getStringList("custom-bee-drops." + key + ".enchantments");
                        List<String> item_lore = main.getConfig().getStringList("custom-bee-drops." + key + ".lore");
                        String item_display_name = main.getConfig().getString("custom-bee-drops." + key + ".display-name");
                        if (!enchants.isEmpty()) {
                            for (int i = 0; i < enchants.size(); i++) {
                                String enchant = enchants.get(i);
                                String[] darab = enchant.split(":");
                                customDrop.setEnchant(Enchantment.getByKey(NamespacedKey.minecraft(darab[0].toLowerCase(Locale.ROOT))), Integer.parseInt(darab[1]));
                            }
                        } else {
                            customDrop.removeEnchantments();
                        }

                        customDrop.setLore(item_lore != null && !item_lore.isEmpty() ? util.pch(item_lore) : null);

                        customDrop.setName(item_display_name != null ? util.pch(item_display_name) : customDrop.getMaterial().name());

                        List<String> flags = main.getConfig().getStringList("custom-bee-drops." + key + ".item-flags");
                        if (flags.contains("HIDE_ALL_FLAG") && !flags.isEmpty()) {
                            customDrop.setFlag(Flag.HIDE_ALL_FLAG);
                        } else if (flags.contains("HIDE_ENCHANTS")) {
                            customDrop.setFlag(Flag.HIDE_ENCHANTS);
                        } else if (flags.contains("HIDE_ATTRIBUTES")) {
                            customDrop.setFlag(Flag.HIDE_ATTRIBUTES);
                        } else if (flags.contains("HIDE_DESTROYS")) {
                            customDrop.setFlag(Flag.HIDE_DESTROYS);
                        } else if (flags.contains("HIDE_PLACED_ON")) {
                            customDrop.setFlag(Flag.HIDE_PLACED_ON);
                        } else if (flags.contains("HIDE_POTION_EFFECTS")) {
                            customDrop.setFlag(Flag.HIDE_POTION_EFFECTS);
                        } else if (flags.contains("HIDE_UNBREAKABLE")) {
                            customDrop.setFlag(Flag.HIDE_UNBREAKABLE);
                        } else {
                            return;
                        }

                        event.getDrops().add(customDrop.convertToItemStack());

                    }
                }

            }
        }
    }


}
