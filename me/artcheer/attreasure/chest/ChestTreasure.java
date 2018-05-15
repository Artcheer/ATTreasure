/**
 * 
 */
package me.artcheer.attreasure.chest;

import me.artcheer.attreasure.config.ConfigManager;
import me.artcheer.attreasure.util.RandomLocation;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;

/**
 * @author Artcheer
 *
 */
public class ChestTreasure {
	private Chest chest;

	public ChestTreasure() {
	}

	public void createChest() {
		Location randomLocation = RandomLocation.getLocation();
		randomLocation.getBlock().setType(Material.CHEST);
		this.chest = (Chest) randomLocation.getBlock().getState();
		this.chest.getBlockInventory().addItem(ConfigManager.getItensBauEvento());
	}

	public Chest getChest() {
		return chest;
	}

	public void setChest(Chest chest) {
		this.chest = chest;
	}

	public void destroy() {
		try {
			if (this.chest != null) {
				this.chest.getInventory().clear();
				this.chest.getBlock().setType(Material.AIR);
			}
		} catch (Exception e) {
			// nothing
		}
	}

}
