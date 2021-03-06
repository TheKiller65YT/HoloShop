package us.github.thekiller65yt.HoloShop;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;

public class HoloShop extends JavaPlugin implements Listener {
	
	private Economy econ = null;
	private ShopClick shopClick = null;

	
	@Override
	  public void onEnable() {
		shopClick = new ShopClick(this);
		
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(shopClick, this);
		
		if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
	  }
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("holoshop")) { // If the player typed /basic then do the following, note: If you only registered this executor for one command, you don't need this
			if (args[0] == "create") {
				Player p = (Player) sender;
				Hologram newHolo = new Hologram(p.getLocation(), "test", econ);
				shopClick.activeHolo.put(newHolo.getLocation(), newHolo);
			}
			return true;
		} //If this has happened the function will return true. 
	        // If this hasn't happened the value of false will be returned.
		return false; 
	}
	
	private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
	
}
 