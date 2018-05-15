package me.artcheer.attreasure.eventos;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.artcheer.attreasure.comandos.Comandos;
import me.artcheer.attreasure.config.ConfigManager;
import me.artcheer.attreasure.config.ConfigManager.LocationType;
import me.artcheer.attreasure.config.MessagesConfig;
import me.artcheer.attreasure.main.ATTreasure;

public class Eventos implements Listener {
	private BukkitTask task;

	@EventHandler
	public void onOpenChest(PlayerInteractEvent e) {
		if (Comandos.controller.isEventoAtivo()) {
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (e.getClickedBlock().getType() == Material.CHEST) {
					if (e.getClickedBlock().getLocation()
							.equals(Comandos.controller.getChestTreasure().getChest().getLocation())) {
						if ((!e.getPlayer().hasPermission("att.admin") && (!e.getPlayer().isOp()))) {
							MessagesConfig.getWinnerMsgGlobal().forEach(
									s -> Bukkit.broadcastMessage(s.replace("{Player}", e.getPlayer().getName())));
							Comandos.controller.getJogadores().remove(e.getPlayer());
							for (Player p : Comandos.controller.getJogadores()) {
								p.teleport(ConfigManager.getLocation(ConfigManager.LocationType.SAIDA));
							}
							e.getPlayer().sendMessage(MessagesConfig.winnerMsg.replace("{Player}", e.getPlayer().getName()));
							Comandos.controller.setEventoAtivo(false);
							if (Comandos.controller.getDicas().isAtivo())
								Comandos.controller.getDicas().getBukkitTask().cancel();
							task = new BukkitRunnable() {
								@Override
								public void run() {
									e.getPlayer().sendMessage("§2Teleportando...");
									e.getPlayer().teleport(ConfigManager.getLocation(LocationType.SAIDA));
									Comandos.controller.cancelEvent();
									if (true)
										task.cancel();
								}
							}.runTaskTimer(ATTreasure.getInstance(), 120 * 20, 0);
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onCloseInventory(InventoryCloseEvent e) {
		if (e.getInventory().getTitle().equals("§3§lBaú do Evento")) {
			if (e.getPlayer().hasPermission("att.admin")) {
				ConfigManager.saveBauEvento(e.getInventory().getContents());
			}
		}
	}

	@EventHandler
	public void onTeleport(PlayerTeleportEvent e) {
		if (Comandos.controller.isEventoAtivo()) {
			if (Comandos.controller.getJogadores().contains(e.getPlayer())) {
				if (!e.getTo().equals(ConfigManager.getLocation(LocationType.SAIDA))) {
					if (!e.getTo().equals(ConfigManager.getLocation(LocationType.ENTRADA))) {
						if (!e.getTo().equals(ConfigManager.getLocation(LocationType.LOBBY))) {
							e.setCancelled(true);
							e.getPlayer().sendMessage(MessagesConfig.semTeleport);
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if (Comandos.controller.isEventoAtivo() && (!Comandos.controller.getChamadas().isAtivo())) {
			if (Comandos.controller.getJogadores().contains(e.getEntity())) {
				e.setDeathMessage(MessagesConfig.deathMsg.replace("{Player}", e.getEntity().getName()));
				Comandos.controller.getJogadores().remove(e.getEntity());
				if(Comandos.controller.getJogadores().size() < 1){
					Comandos.controller.cancelEvent();
				}
			}
		}
	}
}
