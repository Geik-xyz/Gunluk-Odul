package leaderos.web.tr.dailyreward.utils.objects;

import java.util.List;

import org.bukkit.Material;

public class Rewards {
	
	String DataName;
	
	String DisplayName;
	
	List<String> loreUp;
	
	Requirement requirements;
	
	FireworkObject firework;
	
	Material material;
	
	Material tookedMaterial;
	
	List<String> commands;
	
	String requirementFail;
	
	/**
	 * @author Geik
	 * @param DataName
	 * @param DisplayName
	 * @param loreUp
	 * @param loreDown
	 * @param requirements
	 * @param firework
	 * @param material
	 * @param tookedMaterial
	 */
	public Rewards(String DataName, String DisplayName, List<String> loreUp, Requirement requirements, FireworkObject firework, Material material, Material tookedMaterial,
			List<String> commands, String requirementFail)
	{
		
		this.DataName = DataName;
		
		this.DisplayName = DisplayName;
		
		this.loreUp = loreUp;
		
		this.requirements = requirements;
		
		this.firework = firework;
		
		this.material = material;
		
		this.tookedMaterial = tookedMaterial;
		
		this.commands = commands;
		
		this.requirementFail = requirementFail;
		
	}
	
	public String getDataName() {
		return DataName;
	}
	
	public String getDisplayName() {
		return DisplayName;
	}
	
	public List<String> getLore() {
		return loreUp;
	}
	
	public Requirement getRequirements() {
		return requirements;
	}
	
	public FireworkObject getFirework() {
		return firework;
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public Material getTookedMaterial() {
		return tookedMaterial;
	}
	
	public List<String> getCommands() {
		return commands;
	}
	
	public String getFailMessage() {
		return requirementFail;
	}

}
