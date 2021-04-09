package rede.daash.coins.manager;

import org.bukkit.Bukkit;
import rede.daash.coins.DaashCoins;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CoinsManager {

    public static void criarTabelaCoins() {




       
        try {
            PreparedStatement stm = DaashCoins.con
                    .prepareStatement("CREATE TABLE IF NOT EXISTS Coins(Jogador varChar(18), Quantia varChar(80));");
            stm.executeUpdate();
            Bukkit.getConsoleSender().sendMessage("§bDaashCoins » §aTabela de coins criada com sucesso.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static boolean existeConta(String jogador) {
        try {
            PreparedStatement stm = DaashCoins.con.prepareStatement("SELECT * FROM Coins WHERE Jogador = ? ");
            stm.setString(1, jogador);
            ResultSet rs = stm.executeQuery();
            boolean resultado = rs.next();
            return resultado;
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getConsoleSender()
                    .sendMessage("§bDaashCoins » §cNão foi possível verificar se o jogador existe na tabela.");
        }
        return false;
    }

    static double coinsInicial = 0.0;

    public static void criarContaDeJogador(String jogador) {
        try {
            PreparedStatement stm = DaashCoins.con.prepareStatement("INSERT INTO Coins VALUES (?, ?);");
            stm.setString(1, jogador);
            stm.setDouble(2, coinsInicial);
            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("§bDaashCoins » §cNão foi possível criar a conta desse jogador.");
        }
    }

    public static double getCoins(String jogador) {
        try {
            PreparedStatement stm = DaashCoins.con.prepareStatement("SELECT * FROM Coins WHERE Jogador = ?");
            stm.setString(1, jogador);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getDouble("Quantia");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("§bDaashCoins » §cNão foi possível pegar os coins desse jogador.");
        }
        return 0.0;
    }

    public static String getMagnata() {
        try {
            PreparedStatement stm = DaashCoins.con.prepareStatement("SELECT * FROM Coins ORDER BY Quantia DESC LIMIT 1");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getString("Jogador");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("§bDaashCoins » §cMagnata não foi encontrado.");
        }
        return null;
    }



    public static List<String> getCoinsTop() {
        List<String> tops = new ArrayList<String>();
        try {
            PreparedStatement stm = DaashCoins.con.prepareStatement("SELECT * FROM Coins ORDER BY Quantia DESC");
            ResultSet rs = stm.executeQuery();
            int i = 1;
            NumberFormat nf = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));

            while (rs.next()) {
                if (i <= 9) {
                    if (rs.getString("Jogador").equals(getMagnata())) {
                        tops.add("     §7#1  §f"
                                +
                                 getMagnata() + " §7(§2$§f" + nf.format(rs.getDouble("Quantia")) + "§7).");
                    }
                    if (!rs.getString("Jogador").contains(getMagnata())) {
                        i++;
                        tops.add("     §7#" + i + " §f " + rs.getString("Jogador") + " §7(§2$§f" + nf.format(rs.getDouble("Quantia"))
                                + "§7).");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("§bDaashCoins » §cNão foi possivel carregar o top coins.");
        }
        return tops;
    }


    public static void definirCoins(String p, double quantia) {
        try {

            PreparedStatement prepareStat = DaashCoins.con
                    .prepareStatement("UPDATE Coins SET Quantia = ? WHERE Jogador = ?");
            prepareStat.setDouble(1, quantia);
            prepareStat.setString(2, p);
            prepareStat.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("§bDaashCoins » A quantia não conseguiu ser definida.");
        }

    }

    public static void removerCoins(String p, double quantia) {
        try {

            PreparedStatement prepareStat = DaashCoins.con
                    .prepareStatement("UPDATE Coins SET Quantia = ? WHERE Jogador = ?");
            prepareStat.setDouble(1, getCoins(p) - quantia);
            prepareStat.setString(2, p);
            prepareStat.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("§bDaashCoins » A quantia não conseguiu ser removida.");
        }

    }

    public static void adicionarCoins(String args, double quantia) {
        try {

            PreparedStatement prepareStat = DaashCoins.con
                    .prepareStatement("UPDATE Coins SET Quantia = ? WHERE Jogador = ?");
            prepareStat.setDouble(1, quantia + getCoins(args));
            prepareStat.setString(2, args);
            prepareStat.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("§bDaashCoins » A quantia não conseguiu ser adicioanda.");
        }

    }

    public static Connection abrirConexão() {
        try {
            String senha = "";
            String usuario = "root";
            String servidor = "localhost";
            String port = "3306";
            String database = "walls";
            String type = "jdbc:mysql://";

 

            String url = type + servidor + ":" + port + "/" + database;
            Bukkit.getConsoleSender().sendMessage("§bDaashCoins - §aConexão bem sucessida.");
            return DriverManager.getConnection(url, usuario, senha);
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("O banco de dados de coins não foi encontrado.");
        }
        return null;
    }

}
