package com.fighter.listeners;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.fighter.BeeAttack.main.main;

public class CustomDrop implements Listener {

	private main main;

	public CustomDrop(main main) {
		this.main = main;
	}

	@SuppressWarnings({ "deprecation" })
	@EventHandler
	public void onCustomBeeDrop(EntityDeathEvent e) {

		World world = Bukkit.getServer().getWorld("world");
		
		List<String> worldEnable = main.getConfig().getStringList("WorldEnable");

		if (!worldEnable.contains(world.getName())) {
			return;
		} else {

			LivingEntity entity = e.getEntity();

			if (!(entity.getType() == EntityType.BEE)) {

				entity = null;

			} else {

				e.setDroppedExp(main.getConfig().getInt("CustomBee.ExpDrop"));
				ArrayList<ItemStack> drops = new ArrayList<ItemStack>();

				Random random = new Random();

				int i = random.nextInt(100);

				for (String chance : main.getConfig().getConfigurationSection("CustomBee.CustomDrops.Drops")
						.getKeys(false)) {

					if (main.getConfig().getBoolean("CustomBee.CustomDrops.Drops." + chance + ".enable")) {

						List<String> d = main.getConfig()
								.getStringList("CustomBee.CustomDrops.Drops." + chance + ".item");

						if (i < main.getConfig().getDouble("CustomBee.CustomDrops.Drops." + chance + ".chance")) {

							for (String drop : d) {

								ItemStack item = new ItemStack(Material.getMaterial(drop));
								if (main.getConfig()
										.getBoolean("CustomBee.CustomDrops.Drops." + chance + ".enchantment.enable")) {

									item.addUnsafeEnchantment(
											Enchantment.getByName(main.getConfig().getString(
													"CustomBee.CustomDrops.Drops." + chance + ".enchantment.enchant")),
											main.getConfig().getInt(
													"CustomBee.CustomDrops.Drops." + chance + ".enchantment.level"));

								}

								item.setAmount(
										main.getConfig().getInt("CustomBee.CustomDrops.Drops." + chance + ".amount"));

								ItemMeta meta = item.getItemMeta();

								Collection<? extends String> display = (Collection<? extends String>) main.getConfig()
										.getStringList("CustomBee.CustomDrops.Drops." + chance + ".name");
								for (String displayname : display) {

									meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayname));

								}

								ArrayList<String> lista = new ArrayList<String>();

								Collection<? extends String> lore = main.getConfig()
										.getStringList("CustomBee.CustomDrops.Drops." + chance + ".lore");
								for (String lores : lore) {
									lista.add(ChatColor.translateAlternateColorCodes('&', lores));

									meta.setLore(lista);
								}

								item.setItemMeta(meta);

								drops.add(item);
								e.getDrops().clear();
								e.getDrops().addAll(drops);

							}

						}

					}
				}

			}

		}
	}
}
