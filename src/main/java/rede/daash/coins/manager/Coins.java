package rede.daash.coins.manager;

public class Coins {
    public static double getCoins(String jogador) {
        return CoinsManager.getCoins(jogador);
    }

    public static void adicionarCoins(String jogador, double quantia) {
        CoinsManager.adicionarCoins(jogador, quantia);
    }

    public static void removerCoins(String jogador, double quantia) {
        CoinsManager.removerCoins(jogador, quantia);
    }

    public static void definirCoins(String jogador, double quantia) {
        CoinsManager.definirCoins(jogador, quantia);
    }

    public static boolean podeReceberCoins(String jogador) {
        return CoinsStatus.getStatus(jogador).equals("Habilitado");
    }

    public static void statusCoins(String jogador, CoinsEnum status) {

        if (status == CoinsEnum.HABILITADO) {
            CoinsStatus.setarStatus(jogador, "Habilitado");
            return;
        }

        if (status == CoinsEnum.DESABILITADO) {
            CoinsStatus.setarStatus(jogador, "Desabilitado");
            return;
        }

    }

    public static String getMagnata() {
        return CoinsManager.getMagnata();
    }
}

