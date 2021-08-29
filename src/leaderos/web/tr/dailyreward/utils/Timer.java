package leaderos.web.tr.dailyreward.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import leaderos.web.tr.dailyreward.Main;

public class Timer {
	
	public static String getToTake() {
		long toNextDay = getNextDayUnix() - (System.currentTimeMillis()/1000);
		return String.format("%02d" + Manager.getText("Lang","timeFormat.hour") 
		+ " %02d" + Manager.getText("Lang", "timeFormat.minute") + " %02d" + Manager.getText("Lang", "timeFormat.hour"),
				toNextDay / 3600, (toNextDay % 3600) / 60, toNextDay % 60);
	}
	
	@SuppressWarnings("deprecation")
	public static long getNextDayUnix() {
		Date dt = new Date();
		dt.setHours(0);
		dt.setMinutes(0);
		dt.setSeconds(0);
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.DATE, 1);
		dt = c.getTime();
		return dt.getTime() / 1000L;
	}
	
    private static SimpleDateFormat format=new SimpleDateFormat("HH:mm");
	
	
	
    public static boolean hasTime()
    {
    	
		 if(Timer.format.format(new Date()).equals("00:00"))
		 {
			 return true;
		 }
		 
		 else return false;
		 
    }
    
    public static BukkitRunnable taskid;
	public static void taskAgain()
	{
		
		 if (taskid != null)
		 {
			 
			 taskid.cancel();
			 
	         taskid = null;
	         
	     }
		 
		 taskid = new BukkitRunnable()
		 {
			 
	            @Override
	            public synchronized void cancel() throws IllegalStateException
	            {
	                super.cancel();
	            }
	            
	            public void run()
	            {
	                if (Timer.hasTime()) {
	                	runix();
	                	taskid.cancel();
	                }
	            }
	            
	     };taskid.runTaskTimerAsynchronously(Main.instance, 800L, 800L);
	     
	}
	
	
    public static void runix()
    {
    	
    	Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () ->
		{
			
			for (Player s : Bukkit.getOnlinePlayers())
				RewardManager.dailyUpdate(s);
			
			Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
				taskAgain();
			}, 2000L);
			
		});
    	
    }

}
