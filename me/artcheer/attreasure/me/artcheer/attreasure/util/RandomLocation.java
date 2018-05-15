/**
 * 
 */
package me.artcheer.attreasure.util;

import java.util.Random;

import me.artcheer.attreasure.config.ConfigManager;
import org.bukkit.Location;

/**
 * @author Artcheer
 *
 */
public class RandomLocation {
	public static Location getLocation(){
		Random r = new Random();
		int x = r.nextInt(3000);
		int y = r.nextInt(150);
		int z = r.nextInt(3000);
		return new Location(
				ConfigManager.getLocation (ConfigManager.LocationType.ENTRADA).getWorld(),
				ConfigManager.getLocation (ConfigManager.LocationType.ENTRADA).getX()+x,
				y,
				ConfigManager.getLocation (ConfigManager.LocationType.ENTRADA).getZ()+z);
	}
}
