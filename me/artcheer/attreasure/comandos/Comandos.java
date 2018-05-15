package me.artcheer.attreasure.comandos;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import me.artcheer.attreasure.config.ConfigManager;
import me.artcheer.attreasure.config.ConfigManager.LocationType;
import me.artcheer.attreasure.config.MessagesConfig;
import me.artcheer.attreasure.eventos.EventoController;
import me.artcheer.attreasure.main.ATTreasure;

/**
 * @author Artcheer
 *
 */
public class Comandos implements CommandExecutor {
	public static EventoController controller = new EventoController();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
		// Comando Main Sem Argumentos
		if (args.length == 0) {
			if (lb.equalsIgnoreCase("att") || lb.equalsIgnoreCase("attreasure")) {
				// Admin Comandos
				if (sender.hasPermission("att.admin")) {
					sender.sendMessage("§6>>> §f§lComandos:");
					sender.sendMessage("§3/att iniciar §f- Iniciar o Evento.");
					sender.sendMessage("§3/att cancelar §f- Cancelar o Evento.");
					sender.sendMessage("§3/att entrar §f- Participar do Evento.");
					sender.sendMessage("§3/att sair §f- Sair do Evento.");
					sender.sendMessage("§3/att tp bau §f- Teleportar-se ao baú do evento.");
					sender.sendMessage("§3/att tp entrada §f- Teleportar-se a entrada do evento.");
					sender.sendMessage("§3/att reload §f- Recarrega os arquivos de configuração.");
					return true;
				} else {
					// User Comandos
					sender.sendMessage("§6>>> §f§lComandos:");
					sender.sendMessage("§3/att entrar §f- Participar do Evento");
					sender.sendMessage("§3/att sair §f- Sair do Evento");
					return true;
				}
			}
		}
		// Sub-Comandos ( 1 Argumento )
		else if (args.length == 1 && lb.equalsIgnoreCase("att")) {
			// Comando >> /att iniciar
			if (args[0].equalsIgnoreCase("iniciar")) {
				if (sender.hasPermission("att.admin")) {
					if (!controller.isEventoAtivo()) {
						if (ConfigManager.getLocation(LocationType.LOBBY) == null) {
							sender.sendMessage("§c[!]§f Nenhum Lobby foi setado §c(§6§o/att set lobby§c)");
							return true;
						} else if (ConfigManager.getLocation(LocationType.ENTRADA) == null) {
							sender.sendMessage("§c[!]§f Nenhum Local de Entrada foi setado §c(§6§o/att set entrada§c)");
							return true;
						} else if (ConfigManager.getLocation(LocationType.SAIDA) == null) {
							sender.sendMessage("§c[!]§f Nenhum Local de Saida foi setado §c(§6§o/att set saida§c)");
							return true;
						} else {
							controller.startEvent();
							return true;
						}
					} else {
						sender.sendMessage(MessagesConfig.jaIniciado);
						return true;
					}
				} else {
					sender.sendMessage(MessagesConfig.semPermissao);
					return true;
				}
			}
			// Comando >> /att cancelar
			if (lb.equalsIgnoreCase("att") && args[0].equalsIgnoreCase("cancelar")) {
				if (sender.hasPermission("att.admin")) {
					if (controller.isEventoAtivo()) {
						Bukkit.broadcastMessage(MessagesConfig.eventoCancelado);
						controller.cancelEvent();
						return true;
					} else {
						sender.sendMessage(MessagesConfig.semEvento);
						return true;
					}
				} else {
					sender.sendMessage(MessagesConfig.semPermissao);
					return true;
				}
			}
			// Comando >> /att entrar
			if (lb.equalsIgnoreCase("att") && args[0].equalsIgnoreCase("entrar")) {
				if (controller.getChamadas().isAtivo()) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						if (!controller.getJogadores().contains(p)) {
							controller.getJogadores().add(p);
							p.teleport(ConfigManager.getLocation(LocationType.LOBBY));
							for (Player pl : controller.getJogadores()) {
								pl.sendMessage(MessagesConfig.entrouNoEvento.replace("{Player}", p.getName()));
							}
							return true;
						} else {
							p.sendMessage(MessagesConfig.jaEstaNoEvento);
							return true;
						}
					} else {
						sender.sendMessage("§c[!]§f Esse comando não pode ser usado pelo CONSOLE");
						return true;
					}
				} else if (controller.isEventoAtivo()) {
					sender.sendMessage(MessagesConfig.emAndamento);
					return true;
				} else {
					sender.sendMessage(MessagesConfig.semEvento);
					return true;
				}
			}
			// Comando >> /att sair
			if (lb.equalsIgnoreCase("att") && args[0].equalsIgnoreCase("sair")) {
				if (controller.isEventoAtivo()) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						if (controller.getJogadores().contains(p)) {
							controller.getJogadores().remove(p);
							p.teleport(ConfigManager.getLocation(LocationType.SAIDA));
							for (Player pl : controller.getJogadores()) {
								pl.sendMessage(MessagesConfig.saiuDoEvento.replace("{Player}", p.getName()));
							}
							if ((!controller.getChamadas().isAtivo()) && controller.getJogadores().size() == 0) {
								Bukkit.broadcastMessage(MessagesConfig.eventoCanceladoParticipantes);
								controller.cancelEvent();
								return true;
							}
							return true;
						} else {
							p.sendMessage(MessagesConfig.naoEstaNoEvento);
							return true;
						}
					} else {
						sender.sendMessage("§c[!]§f Esse comando não pode ser usado pelo CONSOLE");
						return true;
					}
				} else {
					sender.sendMessage(MessagesConfig.semEvento);
					return true;
				}
			}
			// Comando >> /att reload
			if (lb.equalsIgnoreCase("att") && args[0].equalsIgnoreCase("reload")) {
				if (sender.hasPermission("att.admin")) {
					try {
						ATTreasure.getInstance().reloadConfig();
						MessagesConfig.messages.reload();
						MessagesConfig.loadMensagens();
						sender.sendMessage("§a[!]§f Config's Recarregadas com Sucesso");
					} catch (Exception e) {
						sender.sendMessage("§c[!]§f Houve um problema ao recarregar a config, verifique o CONSOLE");
						e.printStackTrace();
					}
				} else {
					sender.sendMessage(MessagesConfig.semPermissao);
					return true;
				}
			}

		} // < -- Fim Comandos com 1 Argumento -- >

		// Comandos com 2 Argumentos
		else if (args.length == 2) {
			if (lb.equalsIgnoreCase("att") && args[0].equalsIgnoreCase("tp")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					if (p.hasPermission("att.admin")) {
						// Comando >> /att tp bau
						if (args[1].equals("bau")) {
							if (controller.isEventoAtivo() && (!controller.getChamadas().isAtivo())) {
								Location locBau = controller.getChestTreasure().getChest().getLocation();
								if (locBau != null && locBau.getBlock().getType() == Material.CHEST) {
									if (controller.getChestTreasure().getChest() != null) {
										p.sendMessage(">> Teleportando...");
										p.teleport(controller.getChestTreasure().getChest().getLocation());
										return true;
									}
								}
							} else {
								p.sendMessage("Evento não está ativo, não existe baú para onde ir.");
								return true;
							}
						}
						// Comando >> /att tp entrada
						else if (args[1].equalsIgnoreCase("entrada")) {
							if (ConfigManager.getLocation(LocationType.ENTRADA) != null) {
								p.teleport(ConfigManager.getLocation(LocationType.ENTRADA));
								return true;
							}
						}
						// Comando >> /att tp saida
						else if (args[1].equalsIgnoreCase("saida")) {
							if (ConfigManager.getLocation(LocationType.SAIDA) != null) {
								p.teleport(ConfigManager.getLocation(LocationType.SAIDA));
								return true;
							}
							// Comando >> /att tp lobby
						} else if (args[1].equalsIgnoreCase("lobby")) {
							if (ConfigManager.getLocation(LocationType.LOBBY) != null) {
								p.teleport(ConfigManager.getLocation(LocationType.LOBBY));
								return true;
							}
						} else {
							p.sendMessage("                            ");
							p.sendMessage("§c/att tp §abau");
							p.sendMessage("§c/att tp §aentrada");
							p.sendMessage("§c/att tp §asaida");
							p.sendMessage("§c/att tp §alobby");
							return true;
						}
					}else{
						sender.sendMessage(MessagesConfig.semPermissao);
						return true;
					}
				} else {
					sender.sendMessage("§c[!]§f Esse comando não pode ser usado pelo CONSOLE");
					return true;
				}
			} else if (lb.equalsIgnoreCase("att") && args[0].equalsIgnoreCase("set")) {
				if (sender instanceof Player && sender.hasPermission("att.admin")) {
					Player p = (Player) sender;
					if (args[1].equalsIgnoreCase("lobby")) {
						ConfigManager.setLocation(p.getLocation(), LocationType.LOBBY);
						p.sendMessage("§a[!]§f Lobby setado com sucesso.");
						return true;
					} else if (args[1].equalsIgnoreCase("entrada")) {
						ConfigManager.setLocation(p.getLocation(), LocationType.ENTRADA);
						p.sendMessage("§a[!]§f Entrada setada com sucesso.");
						return true;
					} else if (args[1].equalsIgnoreCase("saida")) {
						ConfigManager.setLocation(p.getLocation(), LocationType.SAIDA);
						p.sendMessage("§a[!]§f Saida setada com sucesso.");
						return true;
					} else if (args[1].equalsIgnoreCase("bau")) {
						Inventory i = Bukkit.createInventory(null, InventoryType.PLAYER, "§3§lBaú do Evento");
						i.addItem(ConfigManager.getItensBauEvento());
						p.openInventory(i);
						return true;
					} else {
						p.sendMessage("                            ");
						p.sendMessage("§c/att set §abau");
						p.sendMessage("§c/att set §aentrada");
						p.sendMessage("§c/att set §asaida");
						p.sendMessage("§c/att set §alobby");
					}
				} else {
					sender.sendMessage(MessagesConfig.semPermissao);
					return true;
				}

			}
		}
		return false;
	}

}
