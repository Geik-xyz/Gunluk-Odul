package leaderos.web.tr.dailyreward.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import leaderos.web.tr.dailyreward.Main;
import leaderos.web.tr.dailyreward.utils.Gui.GunlukGui;
import leaderos.web.tr.dailyreward.utils.objects.Cache;

public class RewardManager {
	
	public static HashMap<String, Cache> cache = new HashMap<String, Cache>();
	
	public static void dailyUpdate(Player player)
	{
		
		Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
			
			Cache playerCache = RewardManager.cache.get(player.getName());
			
			if (Main.instance.getConfig().getString("Type").equalsIgnoreCase("streak")) {
				
				if (!isStreakValid(player.getName(), playerCache))
					playerCache.setStreak(0);
				
			}
			
			sendNotifier(player);
			
		});
		
	}

	public static boolean isStreakValid(String username, Cache cache)
	{
		
		try
		{
			
			Calendar calendar = Calendar.getInstance();
			
	    	SimpleDateFormat sdformat = new SimpleDateFormat("dd-MM-yyyy");
	    	
			Date lastTake = sdformat.parse(  cache.getLastTake()  );
			
			Date now = new Date();
			
			String nowString = sdformat.format(now);
			
			Date ActualNow = sdformat.parse(nowString);
			
			calendar.setTime(lastTake);
			
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			
			Date calendarDateFormat = calendar.getTime();
			
			String calendarDateToString = sdformat.format(calendarDateFormat);
			
			Date aDayAfterDate = sdformat.parse(calendarDateToString);
			
			if (aDayAfterDate.equals(ActualNow) || lastTake.equals(ActualNow))
				return true;
			
			else return false;
			
		}
		
		catch (NullPointerException | ParseException e1) {  e1.printStackTrace(); return false;  }
		
	}
	
	public static boolean canPlayerTakeLoot(String username)
	{
		
		try
		{
			
			long unix = System.currentTimeMillis() / 1000L;
			
			if (cache.get(username).getLastTakeUnix() < unix)
			{
				
	    		SimpleDateFormat sdformat = new SimpleDateFormat("dd-MM-yyyy");
	    		
	    		Date lastTake = sdformat.parse(cache.get(username).getLastTake());
	    		
	    		Date now = new Date();
	    		
	    		String nowString = sdformat.format(now);
	    		
	    		Date ActualNow = sdformat.parse(nowString);
	    		
	    		if (lastTake.compareTo(ActualNow) < 0) 
	    			return true;
	    		
	    		else return false;
	    		
	    	}
			
			else return false;
			
		}
		
		catch (NullPointerException | ParseException e1) {  return false;  }
		
	}
	
	public static void sendNotifier(Player e)
	{
		
		if (RewardManager.canPlayerTakeLoot(e.getPlayer().getName()))
		{
			
			e.sendMessage(Manager.getText("Lang", "RewardMsg.loginMsg"));
			
			if (Main.instance.getConfig().getBoolean("forceOpen"))
				Bukkit.getScheduler().runTaskLater(Main.instance, () ->
				{
						
					GunlukGui.create(e.getPlayer());
					
				}, 80L);
			
		}
		
	}
	
	
}
