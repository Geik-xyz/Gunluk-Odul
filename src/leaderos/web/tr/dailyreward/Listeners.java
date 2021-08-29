package leaderos.web.tr.dailyreward;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import leaderos.web.tr.dailyreward.database.DatabaseQueries;
import leaderos.web.tr.dailyreward.utils.Manager;
import leaderos.web.tr.dailyreward.utils.RewardManager;
import leaderos.web.tr.dailyreward.utils.Title;
import leaderos.web.tr.dailyreward.utils.effects.FireworkCosmetic;
import leaderos.web.tr.dailyreward.utils.enums.RequirementType;
import leaderos.web.tr.dailyreward.utils.objects.Cache;
import leaderos.web.tr.dailyreward.utils.objects.Requirement;
import leaderos.web.tr.dailyreward.utils.objects.Rewards;
import me.clip.placeholderapi.PlaceholderAPI;

public class Listeners implements Listener {
	
	@SuppressWarnings("unused")
	private Main plugin;
	public Listeners(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void loginInsert(PlayerJoinEvent e)
	{
		
		insertPlayerValues(e.getPlayer());
			
	}
	
	public static void insertPlayerValues(Player player)
	{
		
		Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
			
			DatabaseQueries.registerPlayerToDB(player.getName());
			
			Cache playerCache = DatabaseQueries.getPlayerInformations(player.getName());
			
			RewardManager.cache.put(player.getName(), playerCache);
			
			if (Main.instance.getConfig().getString("Type").equalsIgnoreCase("streak"))
			{
				
				if (!RewardManager.isStreakValid(player.getName()))
				{
					
					DatabaseQueries.resetPlayerStreak(player.getName());
					
					RewardManager.cache.get(player.getName()).setStreak(0);
					
				}
			
			}
			
			RewardManager.sendNotifier(player);
			
		});
		
	}
	
	
	@EventHandler
	public void logoutRemover(PlayerQuitEvent e)
	{
		
		Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
			DatabaseQueries.savePlayerAllCache(e.getPlayer().getName());
		});
		
	}
	
	@EventHandler
	public void guiListener(InventoryClickEvent e)
	{
		
		if (e.getView().getTitle().contains(Main.color(  Manager.getText("Lang", "Gui.name")  )))
		{
			
			Player player = (Player) e.getWhoClicked();
			
			e.setCancelled(true);
			
			if ((e.getCurrentItem() == null) || (e.getCurrentItem().getType().equals(Material.AIR))) return;
			
			if (e.getClickedInventory().getType() == InventoryType.CHEST)
			{
				
				if (e.getCurrentItem().getItemMeta().hasEnchants())
				{
					 player.closeInventory();
					 Title.send(player, Manager.getText("Lang", "Titles.DailyReward"), Manager.getText("Lang","RewardMsg.alreadyTook"), 3);
				}
				
				if (e.getSlot() >= 11 && e.getSlot() <= 17)
				{
					
					int streak = RewardManager.cache.get(player.getName()).getStreak();
					
					int itemSlot = e.getSlot() - 11;
					
					if (itemSlot == streak && RewardManager.canPlayerTakeLoot(player.getName()))
					{
						
						Date time = new Date();
						
			            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			            
			            long unix = System.currentTimeMillis() / 1000L;
			            
			            RewardManager.cache.get(player.getName()).setLastTake(formatter.format(time));
			            
			            RewardManager.cache.get(player.getName()).setLastTakeUnix(unix);
			            
			            if (streak < 6)
			            	RewardManager.cache.get(player.getName()).setStreak(1+streak);
			            
			            else
			            	RewardManager.cache.get(player.getName()).setStreak(0);
			            
			            RewardManager.cache.get(player.getName()).setTotalLoot(
			            		RewardManager.cache.get(player.getName()).getTotalLoot()+1);
			            
			            Rewards reward = Main.rewards.get(itemSlot);
			            
			            boolean giveReward = true;
			            
			            if (reward.getRequirements() != null)
			            {
			            	
			            	Requirement requirement = reward.getRequirements();
			            	
			            	if (requirement.getRequirementType().equals(RequirementType.PERMISSION))
			            	{
			            		
			            		if (!player.hasPermission((String) requirement.getValue()))
			            			giveReward = false;
			            		
			            	}
			            	
			            	else
			            	{
			            		
			            		int playerValue = Integer.valueOf(PlaceholderAPI.setPlaceholders(player, "%" + requirement.getDataName() + "%"));
			            		
			            		if (playerValue < (int) requirement.getValue())
			            			giveReward = false;
			            		
			            	}
			            	
			            }
			            
			            if (giveReward)
			            {
			            	
			            	for (String s : reward.getCommands()) 
								 Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Main.color(s).replace("%player%", player.getName()));
							
							Title.send(player, Manager.getText("Lang", "Titles.DailyReward"), Manager.getText("Lang", "RewardMsg.takingSuccess"), 3);
							
							if (reward.getFirework().isEnabled())
								FireworkCosmetic.spawn(player.getLocation(), reward.getFirework().getAmount());
							
							player.playSound(player.getLocation(), Sound.valueOf(Main.instance.getConfig().getString("rewardSound")), 0.1F, 0.1F);
							
							player.closeInventory();
							
							if (Main.instance.getConfig().getBoolean("debug"))
								Bukkit.getConsoleSender().sendMessage(Main.color("&2GünlükÖdül &b" + player.getName() + " &7adlı üye &e" + 
									 reward.getDataName() + " &7ödülünü aldı!"));
			            	
			            }
			            
			            else
			            {
			            	
			            	player.closeInventory();
			            	
			            	try
			            	{
			            		
			            		player.sendMessage(  Main.color(reward.getFailMessage())  );
			            		
			            	}
			            	
			            	catch(NullPointerException e1) {}
			            	
			            }
			            
							
					}
					
					else
					{
						
						 player.closeInventory();
						 
						 Title.send(player, Manager.getText("Lang", "Titles.DailyReward"), Manager.getText("Lang", "RewardMsg.alreadyTook"), 3);
						 
					}
					
				}
				
			}
		
		}
		
	}

}
