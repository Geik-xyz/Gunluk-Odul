package leaderos.web.tr.dailyreward.utils.objects;

public class FireworkObject
{
	
	boolean enabled;
	
	int amount;
	
	public FireworkObject(boolean enabled, int amount) 
	{
		
		this.enabled = enabled;
		
		this.amount = amount;
		
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setEnabled(boolean status)
	{
		this.enabled = status;
	}
	
	public void setAmount(int amount)
	{
		this.amount = amount;
	}

}
