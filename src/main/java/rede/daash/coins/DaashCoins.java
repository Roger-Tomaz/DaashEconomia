package rede.daash.coins;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import rede.daash.coins.comandos.CoinsCommands;
import rede.daash.coins.eventos.CoinsEventos;
import rede.daash.coins.manager.CoinsManager;
import rede.daash.coins.manager.CoinsStatus;

import java.sql.Connection;

public final class DaashCoins extends JavaPlugin {

    public static Connection con = CoinsManager.abrirConexão();

    public static DaashCoins instance;

    public static DaashCoins getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        CoinsManager.abrirConexão();
        CoinsManager.criarTabelaCoins();
        CoinsStatus.criarTabelaCoinsStatus();
        instance = this;

        getCommand("coins").setExecutor(new CoinsCommands());
        Bukkit.getPluginManager().registerEvents(new CoinsEventos(), this);

    }

    @Override
    public void onDisable() {
        try {
            con.close();
        } catch (Exception e) {
        }
    }
}
