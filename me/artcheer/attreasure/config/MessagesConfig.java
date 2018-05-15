package me.artcheer.attreasure.config;

import java.util.ArrayList;
import java.util.List;

import me.artcheer.attreasure.main.ATTreasure;


/**
 *
 * @author Artcheer
 */
public class MessagesConfig {

    public static final ATTreasure instance = ATTreasure.getInstance();
    public static final YamlHelper messages = new YamlHelper("messages.yml", instance);
    
    
    public static String tag;
    public static String entrouNoEvento;
    public static String saiuDoEvento;
    public static String semEvento;
    public static String eventoCancelado;
    public static String eventoCanceladoParticipantes;
    public static String jaEstaNoEvento;
    public static String naoEstaNoEvento;
    public static String jaIniciado;
    public static String emAndamento;
    private static List<String> chamadas;
    private static List<String> dicas;
    public static String semTeleport;
    public static String semPermissao;
    public static String playerProximo;
    public static String deathMsg;
    public static String winnerMsg;
    private static List<String> winnerMsgGlobal;
    
    /*
     * Aparentemente as String's de Arquivos de configuração do bukkit tem algum problema ao ser
     * carrega juntamente com um .replaceAll() numa String Publica e Estática, dando um nullPointer e
     * uma Exception de inicialização.
     * 
     * A solução que achei foi carregar as mensages e depois usar o replace pra evitar o erro.
     * 
     * >> @Artcheer
     */
    public static void loadMensagens(){
    	
    	messages.reload();
        tag = messages.getString("Messages.Tag");
        entrouNoEvento = MessagesConfig.tag + messages.getString("Messages.Evento.Entrou");
        saiuDoEvento = MessagesConfig.tag + messages.getString("Messages.Evento.Saiu");
        semEvento = MessagesConfig.tag + messages.getString("Messages.Evento.SemEvento");
        eventoCancelado = MessagesConfig.tag + messages.getString("Messages.Evento.Cancelado");
        eventoCanceladoParticipantes = MessagesConfig.tag + messages.getString("Messages.Evento.CanceladoParticipantes");
        jaEstaNoEvento = MessagesConfig.tag + messages.getString("Messages.Evento.JaEstaNoEvento");
        naoEstaNoEvento = MessagesConfig.tag + messages.getString("Messages.Evento.NaoEstaNoEvento");
        jaIniciado = MessagesConfig.tag + messages.getString("Messages.Evento.JaIniciado");
        emAndamento = MessagesConfig.tag + messages.getString("Messages.Evento.EmAndamento");
        chamadas = messages.getStringList("Messages.Evento.Chamada");
        dicas = messages.getStringList("Messages.Evento.Dicas");
        semTeleport = MessagesConfig.tag + messages.getString("Messages.Outros.SemTeleport");
        semPermissao = MessagesConfig.tag + messages.getString("Messages.Outros.SemPermissao");
        playerProximo = MessagesConfig.tag + messages.getString("Messages.Outros.PlayerProximo");
        deathMsg = MessagesConfig.tag + messages.getString("Messages.Outros.DeathMsg");
        winnerMsg = messages.getString("Messages.Outros.WinnerMsg");
        winnerMsgGlobal = messages.getStringList("Messages.Outros.WinnerMsgGlobal");
        
        tag = tag.replaceAll("&","§");
        entrouNoEvento = entrouNoEvento.replaceAll("&","§");
        saiuDoEvento = saiuDoEvento.replaceAll("&","§");
        semEvento = semEvento.replaceAll("&","§");
        eventoCancelado = eventoCancelado.replaceAll("&","§");
        eventoCanceladoParticipantes = eventoCanceladoParticipantes.replaceAll("&","§");
        jaEstaNoEvento = jaEstaNoEvento.replaceAll("&","§");
        naoEstaNoEvento = naoEstaNoEvento.replaceAll("&","§");
        jaIniciado = jaIniciado.replaceAll("&","§");
        emAndamento = emAndamento.replaceAll("&","§");
        semTeleport = semTeleport.replaceAll("&","§");
        semPermissao = semPermissao.replaceAll("&","§");
        playerProximo = playerProximo.replaceAll("&","§");
        deathMsg = deathMsg.replaceAll("&","§");
        winnerMsg = winnerMsg.replaceAll("&", "§");
    }

	public static List<String> getChamadas() {
		List<String> aux = new ArrayList<>();
		for(String s: chamadas){
			aux.add(s.replace("&", "§"));
		}
		return aux;
	}

	public static List<String> getDicas() {
		List<String> aux = new ArrayList<>();
		for(String s: dicas){
			aux.add(s.replace("&", "§"));
		}
		return aux;
	}


	public static List<String> getWinnerMsgGlobal() {
		List<String> aux = new ArrayList<>();
		for(String s: winnerMsgGlobal){
			aux.add(s.replace("&", "§"));
		}
		return aux;
	}
    
}
