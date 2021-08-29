package leaderos.web.tr.dailyreward.utils.effects;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkCosmetic {
	
	 public static void spawn(Location location, int amount) {
		 Location loc = location;
		 Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
		 FireworkMeta fwm = fw.getFireworkMeta();

		 fwm.setPower(2);
		 fwm.addEffect(FireworkEffect.builder().withColor(Color.LIME).flicker(true).build());

		 fw.setFireworkMeta(fwm);
		 fw.detonate();

		 for(int i = 0;i<amount; i++){
			 Firework fw2 = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
			 fw2.setFireworkMeta(fwm);}
	}

}
