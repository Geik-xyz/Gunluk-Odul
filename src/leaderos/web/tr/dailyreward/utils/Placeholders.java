package leaderos.web.tr.dailyreward.utils;

import org.bukkit.entity.Player;

import leaderos.web.tr.dailyreward.Main;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class Placeholders extends PlaceholderExpansion {
	@SuppressWarnings("unused")
	private Main plugin;
    
    public String getIdentifier() {
        return "dailyreward";
    }

    public String getPlugin() {
        return null;
    }


    /*
     The author of the Placeholder
     This cannot be null
     */
    public String getAuthor() {
        return "Geik";
    }

    /*
     Same with #getAuthor() but for versioon
     This cannot be null
     */

    public String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player p, String identifier)
    {
    	
    	if (!RewardManager.cache.containsKey(p.getName()))
    		return "";
    	
    	if (identifier.equals("odul_alma"))
    		return String.valueOf(RewardManager.cache.get(p.getName()).getTotalLoot());
    	if (identifier.equalsIgnoreCase("sure"))
    	{
    		
    		if (RewardManager.canPlayerTakeLoot(p.getName()))
    			return Manager.getText("Lang", "Placeholder.canTake");
    		
    		else
    			return Timer.getToTake();
    		
    	}
    	
    	if (identifier.equalsIgnoreCase("bugun"))
    		return String.valueOf(RewardManager.cache.get(p.getName()).getStreak()+1);

        return null;
        
    }
    
}

