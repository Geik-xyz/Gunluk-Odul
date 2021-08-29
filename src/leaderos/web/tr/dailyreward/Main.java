package leaderos.web.tr.dailyreward;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONException;
import org.json.JSONObject;

import leaderos.web.tr.dailyreward.commands.Commands;
import leaderos.web.tr.dailyreward.database.ConnectionPool;
import leaderos.web.tr.dailyreward.database.DatabaseQueries;
import leaderos.web.tr.dailyreward.utils.Manager;
import leaderos.web.tr.dailyreward.utils.Placeholders;
import leaderos.web.tr.dailyreward.utils.Timer;
import leaderos.web.tr.dailyreward.utils.enums.RequirementType;
import leaderos.web.tr.dailyreward.utils.objects.FireworkObject;
import leaderos.web.tr.dailyreward.utils.objects.Requirement;
import leaderos.web.tr.dailyreward.utils.objects.Rewards;

public class Main extends JavaPlugin {
	
	public static Main instance;
	
	public static List<Rewards> rewards = new ArrayList<Rewards>();
	
	
	public void onEnable()
	{
		
		instance = this;
		
		licenseCheck();
		
	}
	
	public void onDisable()
	{
		
		for (Player p : Bukkit.getOnlinePlayers())
		{
			
			DatabaseQueries.savePlayerAllCache(p.getName());
			
		}
		
	}
	
	
	public static String color(String yazirengi){return ChatColor.translateAlternateColorCodes('&', yazirengi);}
	
	private void licenseCheck()
    {
		
		Bukkit.getConsoleSender().sendMessage(Main.color("&2&l		GUNLUK ODUL 		&b"));
		
		Bukkit.getConsoleSender().sendMessage(Main.color("&3Developed by &9Geik"));
		
		Bukkit.getConsoleSender().sendMessage(Main.color("&3Discord: &9discord.gg/h283guX"));
		
		Bukkit.getConsoleSender().sendMessage(Main.color("&3Web: &9https://geik.xyz"));
		
		enableShortcut();
		
	}
	
	private static void enableShortcut()
	{
		
		Main.instance.saveDefaultConfig();
		
		Manager.FileChecker("Lang");
		
		ConnectionPool.initsqlite();
		
		DatabaseQueries.createTable();
		
		Main.instance.getCommand("günlüködül").setExecutor(new Commands(Main.instance));
		
		Bukkit.getPluginManager().registerEvents(new Listeners(Main.instance), Main.instance);
		
		if( Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"))
			new Placeholders().register();
		
		RewardsInserter();
		
		Timer.taskAgain();
		
		new Metrics(Main.instance, 7946);
		
		if (!Bukkit.getOnlinePlayers().isEmpty())
			Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () ->
			{
				
				for (Player p : Bukkit.getOnlinePlayers())
				{
					
					Listeners.insertPlayerValues(p);
					
				}
				
			});
		
	}
	
	public void safeReload()
	{
		
		rewards.clear();
		
		RewardsInserter();
		
	}
	
	public static void RewardsInserter()
	{
		
		FileConfiguration cfg = Main.instance.getConfig();
		
		for (String DataName : cfg.getConfigurationSection("Rewards").getKeys(false))
		{
			
			// MATERIAL	
			Material material = Material.DIAMOND;
			
			Material tookedMaterial = Material.MINECART;
			
			if (Material.getMaterial(cfg.getString("Rewards." + DataName + ".material")) != null)
				material = Material.getMaterial(cfg.getString("Rewards." + DataName + ".material"));
			
			if (Material.getMaterial(cfg.getString("Rewards." + DataName + ".tookedMaterial")) != null)
				tookedMaterial = Material.getMaterial(cfg.getString("Rewards." + DataName + ".tookedMaterial"));
			
			// FIREWORK
			FireworkObject firework = new FireworkObject(false, 1);
			
			if (cfg.isSet("Rewards." + DataName + ".firework.deploy"))
				firework.setEnabled(  cfg.getBoolean("Rewards." + DataName + ".firework.deploy")  );
			
			if (cfg.isSet("Rewards." + DataName + ".firework.amount"))
				firework.setAmount(  cfg.getInt("Rewards." + DataName + ".firework.amount")  );
			
			String DisplayName = Main.color(  cfg.getString("Rewards." + DataName + ".name")  );
			
			List<String> loreUp = new ArrayList<String>();
			
			for (String loreAdder : cfg.getStringList("Rewards." + DataName + ".lore"))
				loreUp.add(Main.color(loreAdder));
			
			// Requirements
			Requirement requirements = null;
			
			if (cfg.isSet("Rewards." + DataName + ".requirement"))
				for (String req : cfg.getConfigurationSection("Rewards." + DataName + ".requirement").getKeys(false))
				{
					
					RequirementType type = null;
					
					if (req.equalsIgnoreCase("permission"))
						type = RequirementType.PERMISSION;
					
					else
						type = RequirementType.PLACEHOLDER;
					
					Object value = null;
					
					if (type.equals(RequirementType.PLACEHOLDER))
						value = cfg.getInt("Rewards." + DataName + ".requirement." + req);
					
					else
						value = cfg.getString("Rewards." + DataName + ".requirement." + req);
					
					requirements = new Requirement(type, value, req);
					
				}
			
			List<String> commands = cfg.getStringList("Rewards." + DataName + ".commands");
			
			String requirementFail = null;
			
			if (requirements != null)
				requirementFail = cfg.getString("Rewards." + DataName + ".reqErrorMessage");
			
			Rewards reward = new Rewards(DataName, DisplayName, loreUp, requirements, firework, material, tookedMaterial, commands, requirementFail);
			
			rewards.add(reward);
			
		}
		
	}

}
