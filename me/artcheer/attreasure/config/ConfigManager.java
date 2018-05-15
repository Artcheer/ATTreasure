/**
 * 
 */
package me.artcheer.attreasure.config;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import me.artcheer.attreasure.main.ATTreasure;
import me.artcheer.attreasure.util.ItemStackData;

/**
 * @author Artcheer
 *
 */
public class ConfigManager {
	private static ATTreasure instance = ATTreasure.getInstance();
	public static YamlHelper bauConfig = new YamlHelper("bau.yml", instance);

	public static void saveBauEvento(ItemStack[] itensDoBau) {
		ItemStackData data = ItemStackData.get();
		List<String> itensSerializados = new ArrayList<>();
		for (ItemStack i : itensDoBau) {
			if (i != null){
				itensSerializados.add(data.serialize(i));
			}
		}
		if(!itensSerializados.isEmpty()){
			bauConfig.set("Bau", itensSerializados);
		}
	}

	public static ItemStack[] getItensBauEvento() {
		ItemStackData data = ItemStackData.get();
		try {
			List<ItemStack> itens = new ArrayList<>();
			List<String> itensSerializados = bauConfig.getStringList("Bau");
			for (String s : itensSerializados) {
				try {
					itens.add(data.deserialize(s));
				} catch (Exception e) {
					continue;
				}
			}
			return itens.toArray(new ItemStack[itens.size()]); // BUGS ???
		} catch (Exception e) {
			return null;
		}
	}

	public static int getDelayDicas() {
		try {
			return instance.getConfig().getInt("DelayDicas");
		} catch (Exception e) {
			return 2;
		}
	}

	public static int getDelayChamada() {
		try {
			return instance.getConfig().getInt("DelayChamada");
		} catch (Exception e) {
			return 2;
		}
	}

	public static Location getLocation(LocationType type) {
		Location loc = null;
		try {
			switch (type) {
			case LOBBY:
				loc = (Location) instance.getConfig().get("Location.Lobby");
				break;
			case ENTRADA:
				loc = (Location) instance.getConfig().get("Location.Entrada");
				break;
			case SAIDA:
				loc = (Location) instance.getConfig().get("Location.Saida");
				break;
			}
			return loc;
		} catch (Exception e) {
			return null;
		}
	}

	public static void setLocation(Location loc, LocationType type) {
		switch (type) {
		case LOBBY:
			instance.getConfig().set("Location.Lobby", loc);
			break;
		case ENTRADA:
			instance.getConfig().set("Location.Entrada", loc);
			break;
		case SAIDA:
			instance.getConfig().set("Location.Saida", loc);
			break;
		}
		instance.saveConfig();
	}

	public static enum LocationType {
		LOBBY, ENTRADA, SAIDA;
	}

	public static int getMinimoParticipantes() {
		try{
		return instance.getConfig().getInt("MinimoParticipantes");
		}catch(Exception e){
			return 5;
		}
	}
}
