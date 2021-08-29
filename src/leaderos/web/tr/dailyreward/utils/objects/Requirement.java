package leaderos.web.tr.dailyreward.utils.objects;

import leaderos.web.tr.dailyreward.utils.enums.RequirementType;

public class Requirement {
	
	RequirementType type;
	
	Object value;
	
	String DataName;
	
	public Requirement(RequirementType type, Object value, String DataName)
	{
		
		this.type = type;
		
		this.value = value;
		
		this.DataName = DataName;
		
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
	
}
