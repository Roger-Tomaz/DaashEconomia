package rede.daash.coins.eventos;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import rede.daash.coins.manager.CoinsManager;
import rede.daash.coins.manager.CoinsStatus;

public class CoinsEventos implements Listener {

    @EventHandler
    public void join(PlayerJoinEvent e) {
        if(!CoinsManager.existeConta(e.getPlayer().getName())) {
            CoinsManager.criarContaDeJogador(e.getPlayer().getName());
        }
    }

    @EventHandler
    public void join2(PlayerJoinEvent e) {
        if(!CoinsStatus.existeConta(e.getPlayer().getName())) {
            CoinsStatus.criarContaDeJogador(e.getPlayer().getName());
        }
    }

}
