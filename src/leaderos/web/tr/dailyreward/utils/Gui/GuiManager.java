package leaderos.web.tr.dailyreward.utils.Gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import leaderos.web.tr.dailyreward.Main;
import leaderos.web.tr.dailyreward.utils.Manager;

public class GuiManager {
	
	public static ItemStack defaultItem(List<String> lore, String name, Material itemmat, boolean status)
	{
		
    	List<String> newList = new ArrayList<String>();
    	
    	for (String string : lore)
    	{
    		
    		newList.add(string.replace("&", "§"));
    		
    	}
    	ItemStack item = new ItemStack(itemmat);
    	
    	ItemMeta meta = item.getItemMeta();
    	
    	meta.setDisplayName(Main.color(name));
    	
    	if (status == true)
    	{
    		
    		meta.addEnchant(Enchantment.DURABILITY, 1, true);
    		
        	meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        	
    	}
    	
    	meta.setLore(newList);
    	
    	item.setItemMeta(meta);
    	
    	return item;
    	
	}
	
	public static ItemStack helpChest()
	{
		
		ItemStack help = defaultItem(Manager.getLore("Lang", "Gui.rewardHelp.lore"), Manager.getText("Lang", "Gui.rewardHelp.name"), Material.BOOK, false);
		
		return help;
		
	}

}
