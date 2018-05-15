package me.artcheer.attreasure.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import me.artcheer.attreasure.config.ConfigManager;
import me.artcheer.attreasure.config.ConfigManager.LocationType;
import me.artcheer.attreasure.config.MessagesConfig;
import me.artcheer.attreasure.eventos.EventoController;
import me.artcheer.attreasure.main.ATTreasure;

/**
 *
 * Cria chamadas para o evento, ao terminar de enviar as mensagens chama o
 * evento ChamadasFinalizadas onde os participantes serão teleportados para o
 * local do partida.
 *
 * ~ @Artcheer
 */
public class ChamadasTask {
	private boolean isAtivo = false;
	private BukkitTask preEvent;
	private BukkitScheduler bs = Bukkit.getServer().getScheduler();
	private EventoController controller;

	public ChamadasTask(EventoController e) {
		this.controller = e;
	}

	public void run() {
		isAtivo = true;
		final int count[] = { 5 };
		preEvent = bs.runTaskTimer(ATTreasure.getInstance(), new Runnable() {
			@Override
			public void run() {
				for(String s: MessagesConfig.getChamadas()){
					Bukkit.broadcastMessage(s.replace("{Num}", String.valueOf(count[0])));
				}

				if (count[0] <= 0) {
					if (controller.getJogadores().size() >= ConfigManager.getMinimoParticipantes()) {
						for (Player p : controller.getJogadores()) {
							p.teleport(ConfigManager.getLocation(LocationType.ENTRADA));
						}
						controller.getChestTreasure().createChest();
						controller.getDicas().run();
					} else {
						controller.cancelEvent();
						Bukkit.broadcastMessage(MessagesConfig.eventoCanceladoParticipantes);
					}
					setAtivo(false);
					preEvent.cancel();
				}
				count[0]--;
			}
		}, 0, ConfigManager.getDelayChamada()*1200);
	}

	public boolean isAtivo() {
		return isAtivo;
	}
	public void setAtivo(boolean ativo){
		this.isAtivo = ativo;
	}
		
	public BukkitTask getPreEvent() {
		return preEvent;
	}
}
