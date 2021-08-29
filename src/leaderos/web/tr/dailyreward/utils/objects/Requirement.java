package leaderos.web.tr.dailyreward.utils.objects;

import java.util.List;

import leaderos.web.tr.dailyreward.utils.enums.RequirementType;

public class Requirement {
	
	RequirementType type;
	
	Object value;
	
	String DataName;
	
	List<String> rewards;
	
	public Requirement(RequirementType type, Object value, String DataName, List<String> rewards)
	{
		
		this.type = type;
		
		this.value = value;
		
		this.DataName = DataName;
		
		this.rewards = rewards;
		
	}
	
	public RequirementType getRequirementType() {
		return type;
	}
	
	public Object getValue() {
		return value;
	}
	
	public String getDataName()
	{
		return DataName;
	}
	
	public List<String> getRewards() {
		return rewards;
	}
	
}
