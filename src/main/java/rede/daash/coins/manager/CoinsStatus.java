package rede.daash.coins.manager;

import org.bukkit.Bukkit;
import rede.daash.coins.DaashCoins;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CoinsStatus {
    public static void criarTabelaCoinsStatus() {
        try {
            PreparedStatement stm = DaashCoins.con
                    .prepareStatement("CREATE TABLE IF NOT EXISTS CoinsStatus(Jogador varChar(18), Status varChar(80));");
            stm.executeUpdate();
            Bukkit.getConsoleSender().sendMessage("§bDaashCoinsStatus » §aTabela de coins_status criada com sucesso.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean existeConta(String jogador) {
        try {
            PreparedStatement stm = DaashCoins.con.prepareStatement("SELECT * FROM CoinsStatus WHERE Jogador = ?");
            stm.setString(1, jogador);
            ResultSet rs = stm.executeQuery();
            boolean resultado = rs.next();
            return resultado;
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getConsoleSender()
                    .sendMessage("§bDaashCoinsStatus » §cNão foi possível verificar se o jogador existe na tabela de coins status.");
        }
        return false;
    }

    public static void criarContaDeJogador(String jogador) {
        try {
            PreparedStatement stm = DaashCoins.con.prepareStatement("INSERT INTO CoinsStatus VALUES (?, ?);");
            stm.setString(1, jogador);
            stm.setString(2, "Habilitado");
            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("§bDaashCoinsStatus » §cNão foi possível criar a conta desse jogador.");
        }
    }

    public static String getStatus(String jogador) {
        try {
            PreparedStatement stm = DaashCoins.con.prepareStatement("SELECT * FROM CoinsStatus WHERE Jogador = ?");
            stm.setString(1, jogador);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getString("Status");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("§bDaashCoinsStatus » §cNão foi possível pegar o status desse jogador.");
        }
        return null;
    }

    public static void setarStatus(String jogador, String status) {

        try {

            PreparedStatement prepareStat = DaashCoins.con
                    .prepareStatement("UPDATE CoinsStatus SET Status = ? WHERE Jogador = ?");
            prepareStat.setString(1, status  );
            prepareStat.setString(2, jogador);
            prepareStat.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("§bDaashCoinsStatus » §cO status não conseguiu ser alterado.");
        }
    }
}
