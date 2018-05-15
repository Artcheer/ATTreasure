/**
 * 
 */
package me.artcheer.attreasure.tasks;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import me.artcheer.attreasure.config.ConfigManager;
import me.artcheer.attreasure.config.MessagesConfig;
import me.artcheer.attreasure.eventos.EventoController;
import me.artcheer.attreasure.main.ATTreasure;

/**
 * @author Artcheer
 *
 */
public class DicasInEventTask {
	private boolean isAtivo = false;
	private BukkitTask bt;
	private EventoController controller;

	public DicasInEventTask(EventoController e) {
		this.controller = e;
	}

	public void run() {
		isAtivo = true;
		List<String> dicas = MessagesConfig.getDicas();

		final int count[] = { 0 };
		bt = Bukkit.getServer().getScheduler().runTaskTimer(ATTreasure.getInstance(), () -> {
            Bukkit.broadcastMessage(dicas.get(count[0]).replace("{Biome}", controller.getChestTreasure().getChest().getBlock().getBiome().name())
            		.replace("{X}", String.valueOf(controller.getChestTreasure().getChest().getLocation().getBlockX()))
            		.replace("{Y}", String.valueOf(controller.getChestTreasure().getChest().getLocation().getBlockY()))
            		.replace("{Z}", String.valueOf(controller.getChestTreasure().getChest().getLocation().getBlockZ())));
			if (count[0] == dicas.size()-1) {
                bt.cancel();
                isAtivo = false;
            }
            count[0]++;
        }, 120*20, ConfigManager.getDelayDicas()*1200);
	}

	public boolean isAtivo() {
		return isAtivo;
	}

	public void setAtivo(boolean isAtivo) {
		this.isAtivo = isAtivo;
	}

	public BukkitTask getBukkitTask() {
		return bt;
	}

}
