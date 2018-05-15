package me.artcheer.attreasure.eventos;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import me.artcheer.attreasure.chest.ChestTreasure;
import me.artcheer.attreasure.config.ConfigManager;
import me.artcheer.attreasure.config.ConfigManager.LocationType;
import me.artcheer.attreasure.tasks.ChamadasTask;
import me.artcheer.attreasure.tasks.DicasInEventTask;

/**
 * Classe originalmente criada para controlar todo o evento Treasure
 * Ela deve começar, controlar e finalizar o evento.
 * 
 * 1- Falta adicionar locais para de entrar e saida para o evento
 * 2 - Falta interligar as classes de tasks e essa pra que elas funcionem
 * em conjunto, e em perfeita harmonia.
 *
 * @author Artcheer
 *
 */
public class EventoController {
	private boolean eventoAtivo = false;
	private List<Player> jogadores = new ArrayList<>();
	private ChamadasTask chamadas;
	private DicasInEventTask dicas;
	private ChestTreasure chestTreasure;
	
	public EventoController() {
		this.chamadas = new ChamadasTask (this);
		this.dicas = new DicasInEventTask (this);
		this.chestTreasure = new ChestTreasure ();
	}
	
	
	public void startEvent(){
		chamadas.run();
		this.eventoAtivo = true;

	}
	
	public void cancelEvent(){
		if(this.chamadas.isAtivo()) {
			this.chamadas.getPreEvent().cancel();
		}
		if(this.dicas.isAtivo()){
			this.dicas.getBukkitTask().cancel();
		}
		this.eventoAtivo = false;
		for(Player p: this.jogadores){
			p.teleport(ConfigManager.getLocation(LocationType.SAIDA));
		}
		this.jogadores.clear();
		this.chestTreasure.destroy ();
	}
	
	public boolean isEventoAtivo() {
		return eventoAtivo;
	}

	public void setEventoAtivo(boolean eventoAtivo) {
		this.eventoAtivo = eventoAtivo;
	}

	public List<Player> getJogadores() {
		return jogadores;
	}

	public ChamadasTask getChamadas() {
		return chamadas;
	}

	
	public DicasInEventTask getDicas() {
		return dicas;
	}

	public ChestTreasure getChestTreasure() {
		return chestTreasure;
	}
	
	
}
