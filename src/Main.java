public class Main {

    public static void main(String[] args) {
        final int TAMANHO_TABULEIRO = 5;
        final int QUANTIDADE_BARCOS = 2;

        Game jogo = new Game(TAMANHO_TABULEIRO, QUANTIDADE_BARCOS);

        jogo.start();
    }
}