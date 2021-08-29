package leaderos.web.tr.dailyreward.utils.objects;

public class Cache {
	
	String lastTake;
	
	long lastTakeUnix;
	
	int totalLoot;
	
	int streak;
	
	public Cache(String lastTake, long lastTakeUnix, int totalLoot, int streak)
	{
		
		this.lastTake = lastTake;
		
		this.lastTakeUnix = lastTakeUnix;
		
		this.totalLoot = totalLoot;
		
		this.streak = streak;
		
	}
	
	public String getLastTake()
	{
		return lastTake;
	}

	public long getLastTakeUnix()
	{
		return lastTakeUnix;
	}
	
	public int getTotalLoot()
	{
		return totalLoot;
	}
	
	public int getStreak()
	{
		return streak;
	}
	
	
	
	
	public void setLastTake(String lastTake)
	{
		this.lastTake = lastTake;
	}
	
	public void setLastTakeUnix(long lastTakeUnix)
	{
		this.lastTakeUnix = lastTakeUnix;
	}
	
	public void setTotalLoot(int totalLoot)
	{
		this.totalLoot = totalLoot;
	}
	
	public void setStreak(int streak)
	{
		this.streak = streak;
	}
	
}
