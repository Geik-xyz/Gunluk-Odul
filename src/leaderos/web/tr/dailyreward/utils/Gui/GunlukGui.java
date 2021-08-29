package leaderos.web.tr.dailyreward.utils.Gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import leaderos.web.tr.dailyreward.Main;
import leaderos.web.tr.dailyreward.utils.Manager;
import leaderos.web.tr.dailyreward.utils.RewardManager;
import leaderos.web.tr.dailyreward.utils.Timer;
import leaderos.web.tr.dailyreward.utils.objects.Rewards;

public class GunlukGui {
	
	@SuppressWarnings("deprecation")
	public static void create(Player player) {
		// Inventory holder
		Inventory gui = Bukkit.getServer().createInventory(player, 27, (Main.color( Manager.getText("Lang", "Gui.name")) ));
		
		List<Rewards> rewards = Main.rewards;
		
		if (!rewards.isEmpty() && rewards != null)
		{
			
			int playerLootID = RewardManager.cache.get(player.getName()).getStreak();
			
			for (int i = 0; i < rewards.size(); i++)
			{
				
				Material itemMaterial = rewards.get(i).getMaterial();
				
				boolean hasGlow = false;
				
				String status = Manager.getText("Lang", "Gui.rewardCannot");
				
				if (playerLootID == i)
				{
					
					if (RewardManager.canPlayerTakeLoot(player.getName()))
						status = Manager.getText("Lang", "Gui.rewardATM");
					
					else
						status = "&c" + Timer.getToTake();
					
				}
				
				else if (playerLootID > i)
				{
					
					status = Manager.getText("Lang", "Gui.rewardTooked");
					
					itemMaterial = rewards.get(i).getTookedMaterial();
					
					hasGlow = true;
					
				}
				
				String name = rewards.get(i).getDisplayName();
				
				List<String> lore = new ArrayList<String>();
				
				for (String loreAdder : rewards.get(i).getLore())
					lore.add(loreAdder.replace("{status}", status));
				
				ItemStack toSet = GuiManager.defaultItem(lore, name, itemMaterial, hasGlow);
				
				gui.setItem(11+i, toSet);
				
			}
			
		}
		
		gui.setItem(9, GuiManager.helpChest());
		
		ItemStack fillItem = new ItemStack(Material.getMaterial(Manager.getText("Lang", "fillItem.material")), 1);
		
		if (Manager.isSet("Lang", "fillItem.damage"))
		fillItem.setDurability((short) Manager.getInt("Lang", "fillItem.damage"));
		
		for (int i = 0; i<=18; i++)
		{
			
			if (i == 9)
				gui.setItem(1+i, fillItem);
			
			else if (i < 9)
				gui.setItem(i, fillItem);
			
			else
				gui.setItem(8+i, fillItem);
			
		}
		
		player.openInventory(gui);
		
	}

}
