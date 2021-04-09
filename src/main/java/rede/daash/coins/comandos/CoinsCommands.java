package rede.daash.coins.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rede.daash.coins.manager.Coins;
import rede.daash.coins.manager.CoinsEnum;
import rede.daash.coins.manager.CoinsManager;
import rede.daash.coins.manager.CoinsStatus;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CoinsCommands implements CommandExecutor {

    public String formatarCoins(String jogador) {
        NumberFormat nf = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        double coinsDoJogador = CoinsManager.getCoins(jogador);
        return nf.format(coinsDoJogador);
    }

    public String formatarCoinsDouble(double quantia) {
        NumberFormat nf = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        return nf.format(quantia);
    }

    String[] ComPermissão = { " ", "§bDaash Economia §7- Comandos:",
            "§e/money definir (nick) (quantia) §7- Seta o saldo do jogador.",
            "§e/money adicionar (nick) (quantia) §7- Adiciona uma quantia ao saldo do jogador.",
            "§e/money remover (nick) (quantia) §7- Remove uma quantia ao saldo do jogador.",
            "§e/money habilitar §7- Habilita o recebimento de money.",
            "§e/money desabilitar §7- Desabilita o recebimento de money.",
            "§e/money enviar (nick) (quantia) §7- Transfere money.", "§e/money (nick) §7- Vê o saldo de um jogador.",
            "§e/money top §7- Vê o saldo dos jogadores mais ricos do servídor.",
            "§e/money magnata §7- Jogador mais rico do servídor.", "§e/money help §7- Página de ajuda.", " " };

    String[] SemPermissão = { " ", "§bDaash Economia §7- Comandos:",
            "§e/money habilitar §7- Habilita o recebimento de money.",
            "§e/money desabilitar §7- Desabilita o recebimento de money.",
            "§e/money enviar (nick) (quantia) §7- Transfere money.", "§e/money (nick) §7- Vê o saldo de um jogador.",
            "§e/money top §7- Vê o saldo dos jogadores mais ricos do servídor.",
            "§e/money magnata §7- Jogador mais rico do servídor.", "§e/money help §7- Página de ajuda.", " " };

    private String Daash = "§bDaash §8» ";

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
        if (s instanceof Player) {
            Player p = (Player) s;
            if (cmd.getName().equalsIgnoreCase("coins")) {
                if (args.length == 0) {

                    p.sendMessage(Daash + "§eSeu saldo: §2$§f" + formatarCoins(p.getName()) + "§e.");

                    return true;
                }
                if (args.length > 3) {
                    p.sendMessage(Daash + "§cNão existe um sub comando com mais de 3 argumentos.");
                    return true;
                }
                if (args.length == 1) {

                    String subComando = args[0];
                    if (!subComando.equalsIgnoreCase("definir") && !subComando.equalsIgnoreCase("adicionar")
                            && !subComando.equalsIgnoreCase("definir") && !subComando.equalsIgnoreCase("habilitar")
                            && !subComando.equalsIgnoreCase("desabilitar") && !subComando.equalsIgnoreCase("enviar")
                            && !subComando.equalsIgnoreCase("top") && !subComando.equalsIgnoreCase("magnata")
                            && !subComando.equalsIgnoreCase("help")) {
                        if (!CoinsManager.existeConta(args[0])) {
                            p.sendMessage(Daash + "§cEste jogador ainda não logou no servídor.");
                            return true;
                        }
                        p.sendMessage(
                                Daash + "§eSaldo de §f" + args[0] + "§e: §2$§f" + formatarCoins(args[0]) + "§e.");
                        return true;
                    }

                    if (subComando.equalsIgnoreCase("habilitar")) {
                        Coins.statusCoins(p.getName(), CoinsEnum.HABILITADO);
                        p.sendMessage(Daash + "§aO seu recebimento de coins foi habilitado.");
                        return true;
                    }

                    if (subComando.equalsIgnoreCase("desabilitar")) {
                        Coins.statusCoins(p.getName(), CoinsEnum.DESABILITADO);
                        p.sendMessage(Daash + "§aO seu recebimento de coins foi desabilitado.");
                        return true;
                    }

                    if (subComando.equalsIgnoreCase("help")) {

                        if (!p.hasPermission("coins.admin")) {
                            p.sendMessage(SemPermissão);
                            return true;
                        }

                        p.sendMessage(ComPermissão);

                        return true;
                    }

                    if (subComando.equalsIgnoreCase("adicionar")) {

                        if (!p.hasPermission("coins.admin")) {
                            p.sendMessage(Daash + "§cVocê não tem permissão para fazer isso.");
                            return true;
                        }

                        p.sendMessage(
                                "§cUtilize /money adicionar (nick) (quantia) para adicionar coins no saldo de um jogador.");

                        return true;
                    }

                    if (subComando.equalsIgnoreCase("definir")) {

                        if (!p.hasPermission("coins.admin")) {
                            p.sendMessage(Daash + "§cVocê não tem permissão para fazer isso.");
                            return true;
                        }

                        p.sendMessage("§cUtilize /money definir (nick) (quantia) para definir os coins de um jogador.");

                        return true;
                    }

                    if (subComando.equalsIgnoreCase("remover")) {

                        if (!p.hasPermission("coins.admin")) {
                            p.sendMessage(Daash + "§cVocê não tem permissão para fazer isso.");
                            return true;
                        }

                        p.sendMessage(
                                "§cUtilize /money remover (nick) (quantia) para remover coins do saldo de um jogador.");

                        return true;
                    }

                    if (subComando.equalsIgnoreCase("enviar")) {
                        p.sendMessage("§cUtilize /money enviar (nick) (quantia) para enviar coins para um jogador.");
                        return true;
                    }

                    if (subComando.equalsIgnoreCase("top")) {

                        p.sendMessage("§e§lTOP §f10 §e§lJOGADORES MAIS RICOS DO SERVER:");
                        p.sendMessage(" ");
                        List<String> top = CoinsManager.getCoinsTop();
                        for (String jogadoresMaisRicos : top) {
                            p.sendMessage(jogadoresMaisRicos);
                        }
                        p.sendMessage(" ");

                        return true;
                    }

                    if (subComando.equalsIgnoreCase("magnata")) {

                        p.sendMessage(Daash + "§eO magnata do servídor é o jogador "
                                +  "" + CoinsManager.getMagnata() + " §ecom §2$§f" + formatarCoins(CoinsManager.getMagnata())
                                + "§e.");

                        return true;
                    }

                    return true;
                }

                if (args.length == 2) {

                    String subComando = args[0];

                    if (subComando.equalsIgnoreCase("enviar")) {
                        Player jogador = Bukkit.getPlayer(args[1]);
                        if (jogador == null) {
                            p.sendMessage(Daash + "§cEste jogador está offline no momento.");
                            return true;
                        }
                        if (jogador.getName().equalsIgnoreCase(p.getName())) {
                            p.sendMessage(Daash + "§cVocê não pode enviar coins a si mesmo.");
                            return true;
                        }
                        if(!Coins.podeReceberCoins(jogador.getName())) {
                            p.sendMessage(Daash + "§cEste jogador desabilitou o recebimento de coins.");
                            return true;
                        }
                        p.sendMessage("§cUtilize /money enviar (nick) (quantia) para enviar coins para um jogador.");
                        return true;
                    }

                    if (subComando.equalsIgnoreCase("remover")) {

                        if (!p.hasPermission("coins.admin")) {
                            p.sendMessage(Daash + "§cVocê não tem permissão para fazer isso.");
                            return true;
                        }

                        p.sendMessage(
                                "§cUtilize /money remover (nick) (quantia) para remover coins do saldo de um jogador.");

                        return true;
                    }

                    if (subComando.equalsIgnoreCase("adicionar")) {

                        if (!p.hasPermission("coins.admin")) {
                            p.sendMessage(Daash + "§cVocê não tem permissão para fazer isso.");
                            return true;
                        }

                        p.sendMessage(
                                "§cUtilize /money adicionar (nick) (quantia) para adicionar coins no saldo de um jogador.");

                        return true;
                    }

                    if (subComando.equalsIgnoreCase("definir")) {

                        if (!p.hasPermission("coins.admin")) {
                            p.sendMessage(Daash + "§cVocê não tem permissão para fazer isso.");
                            return true;
                        }

                        p.sendMessage("§cUtilize /money definir (nick) (quantia) para definir os coins de um jogador.");

                        return true;
                    }

                    return true;
                }

                if (args.length == 3) {

                    String subComando = args[0];

                    if (subComando.equalsIgnoreCase("enviar")) {

                        Player jogador = Bukkit.getPlayer(args[1]);
                        if (jogador == null) {
                            p.sendMessage(Daash + "§cEste jogador está offline no momento.");
                            return true;
                        }
                        if (jogador.getName().equalsIgnoreCase(p.getName())) {
                            p.sendMessage(Daash + "§cVocê não pode enviar coins a si mesmo.");
                            return true;
                        }

                        if(!Coins.podeReceberCoins(jogador.getName())) {
                            p.sendMessage(Daash + "§cEste jogador desabilitou o recebimento de coins.");
                            return true;
                        }

                        try {

                            @SuppressWarnings("unused")
                            double a = Double.valueOf(args[2]);

                        } catch (Exception e) {
                            p.sendMessage(Daash + "§cVocê pode inserir somente números.");
                            return true;
                        }

                        double quantia = Double.valueOf(args[2]);
                        if (CoinsManager.getCoins(p.getName()) < quantia) {
                            p.sendMessage(Daash + "§cVocê não tem coins suficientes para completar está transação.");
                            return true;
                        }

                        p.sendMessage(Daash + "§aVocê enviou §f" + formatarCoinsDouble(quantia)
                                + " §apara o jogador §f" + jogador.getName() + "§a.");
                        jogador.sendMessage(Daash + "§aVocê recebeu §2$§f" + formatarCoinsDouble(quantia)
                                + " §ado jogador §f" + p.getName() + "§a.");
                        CoinsManager.adicionarCoins(jogador.getName(), quantia);
                        CoinsManager.removerCoins(p.getName(), quantia);

                        return true;
                    }

                    if (subComando.equalsIgnoreCase("definir")) {

                        if (!p.hasPermission("coins.admin")) {
                            p.sendMessage(Daash + "§cVocê não tem permissão para fazer isso.");
                            return true;
                        }

                        if (!CoinsManager.existeConta(args[1])) {
                            p.sendMessage(Daash + "§cEste jogador ainda não logou no servídor.");
                            return true;
                        }

                        try {

                            @SuppressWarnings("unused")
                            double a = Double.valueOf(args[2]);

                        } catch (Exception e) {
                            p.sendMessage(Daash + "§cVocê pode inserir somente números.");
                            return true;
                        }

                        double quantia = Double.valueOf(args[2]);
                        p.sendMessage(Daash + "§aVocê definiu o saldo de §f" + args[1] + " §apara §2$§f"
                                + formatarCoinsDouble(quantia) + "§a.");
                        CoinsManager.definirCoins(args[1], quantia);

                        return true;
                    }

                    if (subComando.equalsIgnoreCase("adicionar")) {

                        if (!p.hasPermission("coins.admin")) {
                            p.sendMessage(Daash + "§cVocê não tem permissão para fazer isso.");
                            return true;
                        }

                        if (!CoinsManager.existeConta(args[1])) {
                            p.sendMessage(Daash + "§cEste jogador ainda não logou no servídor.");
                            return true;
                        }

                        try {

                            @SuppressWarnings("unused")
                            double a = Double.valueOf(args[2]);

                        } catch (Exception e) {
                            p.sendMessage(Daash + "§cVocê pode inserir somente números.");
                            return true;
                        }

                        double quantia = Double.valueOf(args[2]);
                        p.sendMessage(Daash + "§aVocê adicionou §2$§f" + formatarCoinsDouble(quantia)
                                + " §ano saldo de §f" + args[1] + "§a.");
                        CoinsManager.adicionarCoins(args[1], quantia);

                        return true;
                    }

                    if (subComando.equalsIgnoreCase("remover")) {

                        if (!p.hasPermission("coins.admin")) {
                            p.sendMessage(Daash + "§cVocê não tem permissão para fazer isso.");
                            return true;
                        }

                        if (!CoinsManager.existeConta(args[1])) {
                            p.sendMessage(Daash + "§cEste jogador ainda não logou no servídor.");
                            return true;
                        }

                        if(CoinsManager.getCoins(args[1]) <= 0) {
                            p.sendMessage(Daash + "§cEste jogador não possui este saldo!");
                            return true;
                        }

                        try {

                            @SuppressWarnings("unused")
                            double a = Double.valueOf(args[2]);

                        } catch (Exception e) {
                            p.sendMessage(Daash + "§cVocê pode inserir somente números.");
                            return true;
                        }

                        double quantia = Double.valueOf(args[2]);
                        p.sendMessage(Daash + "§aVocê removeu §2$§f" + formatarCoinsDouble(quantia)
                                + " §ado saldo de §f" + args[1] + "§a.");
                        CoinsManager.removerCoins(args[1], quantia);

                        return true;
                    }

                    return true;
                }
            }
        }
        return true;
    }

}







