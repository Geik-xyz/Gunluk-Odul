package leaderos.web.tr.dailyreward.commands;

import java.io.File;
import java.io.IOException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import leaderos.web.tr.dailyreward.Main;
import leaderos.web.tr.dailyreward.utils.Gui.GunlukGui;

public class Commands implements CommandExecutor {
	
	@SuppressWarnings("unused")
	private Main plugin;
	public Commands(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("günlüködül")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				
				if (args.length == 0)
					GunlukGui.create(player);
				
				else
				{
					
					if (player.isOp())
					{
						
						if (args[0].equalsIgnoreCase("reload"))
						{
							
							Main.rewards.clear();
							
							Main.instance.reloadConfig();
							
							Main.RewardsInserter();
							
							File langFile = new File("plugins/GunlukOdul/Lang.yml");
							
							FileConfiguration lang = YamlConfiguration.loadConfiguration(langFile);
							
							try {lang.save(langFile);} catch (IOException e) {}
							
							player.sendMessage(Main.color("&aConfig başarıyla reload edildi."));
							
						}
						
					}
					
				}
				
			}
			
		}
		
		return false;
		
	}

}
