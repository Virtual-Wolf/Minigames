package com.shockwavemc.Utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
 
public class ItemMetaUtils {
	public static ItemStack setItemName(ItemStack it, String name) {
		ItemMeta m = it.getItemMeta();
		m.setDisplayName(name);
		it.setItemMeta(m);
		return it;
	}

	public static ItemStack setLore(ItemStack it, String... lore) {
		ItemMeta m = it.getItemMeta();
		ArrayList<String> s = new ArrayList<String>();
		for (String l : lore) {
			s.add(l);
		}
		m.setLore(s);
		it.setItemMeta(m);
		return it;
	}

	public static boolean hasLore(ItemStack it) {
		ItemMeta m = it.getItemMeta();
		return m.hasLore();
	}

	public static List<String> getLore(ItemStack it) {
		ItemMeta m = it.getItemMeta();
		return m.getLore();
	}

	public static boolean hasCustomName(ItemStack it) {
		try {
			getItemName(it).replace("", "");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static ItemStack addEnchantment(ItemStack it, Enchantment e, int level) {
		ItemMeta m = it.getItemMeta();
		m.addEnchant(e, level, false);
		it.setItemMeta(m);
		return it;
	}

	public static String getItemName(ItemStack it) {
		ItemMeta m = it.getItemMeta();
		return m.getDisplayName();
	}

	public static ItemStack setLeatherColor(ItemStack it, Color c) {
		LeatherArmorMeta m = (LeatherArmorMeta) it.getItemMeta();
		m.setColor(c);
		it.setItemMeta(m);
		return it;
	}

	public static ItemStack setHeadName(String skullOwner) {
		String displayName = skullOwner + "'(s) head";
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwner(skullOwner);
        if (displayName != null) {
            skullMeta.setDisplayName(ChatColor.RESET + displayName);
        }
        skull.setItemMeta(skullMeta);
        return skull;
	}

}

