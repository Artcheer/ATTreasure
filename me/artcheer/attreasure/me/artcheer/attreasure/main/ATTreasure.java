/**
 * 
 */
package me.artcheer.attreasure.main;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import me.artcheer.attreasure.comandos.Comandos;
import me.artcheer.attreasure.config.ConfigManager;
import me.artcheer.attreasure.config.MessagesConfig;
import me.artcheer.attreasure.eventos.Eventos;

import java.io.File;

/**
 * @author Artcheer
 *
 */
public class ATTreasure extends JavaPlugin {
	private ConsoleCommandSender ccs;

	@Override
	public void onEnable() {
		ccs = Bukkit.getServer().getConsoleSender();
		ccs.sendMessage("===================================");
		ccs.sendMessage("Plugin ATTreasure Ativado");
		ccs.sendMessage("Desenvolvido por  §6@Artcheer");
		ccs.sendMessage("Version: " + this.getDescription().getVersion());
		ccs.sendMessage("                               ");
		try {
			if (!new File("messages.yml").exists()) {
				MessagesConfig.messages.saveDefault();
				MessagesConfig.loadMensagens();
			}
			ccs.sendMessage("Mensagens: §aOK");
			if (!new File("bau.yml").exists()) {
				ConfigManager.bauConfig.saveDefault();
			}
			ccs.sendMessage("BAU: §aOK");
			if (!new File("config.yml").exists()) {
				this.saveDefaultConfig();

			}
			ccs.sendMessage("CONFIG: §aOK");
		} catch (Exception e) {
			ccs.sendMessage("§cALGO DEU ERRADO! Procure §6@Artcheer");
			e.printStackTrace();
		}
		ccs.sendMessage("===================================");
		this.getServer().getPluginManager().registerEvents(new Eventos(), this);
		this.getCommand("att").setExecutor(new Comandos());
		this.getCommand("attreasure").setExecutor(new Comandos());
	}

	@Override
	public void onDisable() {
		ccs.sendMessage("===================================");
		ccs.sendMessage("Plugin ATTreasure Desativado");
		ccs.sendMessage("Desenvolvido por §6@Artcheer");
		ccs.sendMessage("Version: " + this.getDescription().getVersion());
		ccs.sendMessage("===================================");
	}

	public static ATTreasure getInstance() {
		return (ATTreasure) Bukkit.getPluginManager().getPlugin("AT_Treasure");
	}

}
